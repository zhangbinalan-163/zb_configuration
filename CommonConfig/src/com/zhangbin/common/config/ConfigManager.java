package com.zhangbin.common.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhangbin.common.config.exception.ConfigException;

/**
 * �����ļ�������
 * 
 * @author zhangbinalan
 * 
 */
public class ConfigManager {
	private static Logger logger = LoggerFactory.getLogger(ConfigManager.class);

	private static Map<String, Config> configMap = new ConcurrentHashMap<String, Config>();
	private static long checkInterval = 10 * 1000L;
	private static ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(
			1, new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					Thread thread = new Thread(r);
					thread.setDaemon(true);
					return thread;
				}
			});

	public synchronized static void addConfig(Config config)
			throws ConfigException {
		String key = config.getConfigPath();
		if (!configMap.containsKey(key)) {
			// ��һ�����������ļ�ʱ��������߳�
			if (configMap.isEmpty()) {
				executorService.scheduleAtFixedRate(new CheckRunnable(), 0,
						checkInterval, TimeUnit.MILLISECONDS);
				logger.debug("start a thread to monitor config's update");
			}
			configMap.put(key, config);
			logger.debug("load config in system,configKey=" + key);
		} else {
			logger.debug("config has already loaded in system,configKey=" + key);
		}
	}

	// ��������ļ��߳�,ȫ���Ǻ�̨�߳�ִ����ɣ����Զ��رպ�̨�߳�
	static class CheckRunnable implements Runnable {

		@Override
		public void run() {
			for (String key : configMap.keySet()) {
				Config config = configMap.get(key);
				try {
					config.checkAndLoad();
				} catch (ConfigException e) {
					logger.error(
							"occur error when config monitor thread try to reload,configKey="
									+ key, e);
				}
			}
		}
	}
}