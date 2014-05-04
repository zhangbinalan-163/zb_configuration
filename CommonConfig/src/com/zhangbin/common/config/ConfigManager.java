package com.zhangbin.common.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhangbin.common.config.exception.ConfigException;

/**
 * 配置文件管理类
 * 
 * @author zhangbinalan
 * 
 */
public class ConfigManager {
	private static Logger logger = LoggerFactory.getLogger(ConfigManager.class);

	private static Map<String, Config> configMap = new HashMap<String, Config>();
	private static Lock lock = new ReentrantLock();
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

	public static void addConfig(Config config) throws ConfigException {
		lock.lock();
		try {
			String key = config.getConfigPath();
			if (!configMap.containsKey(key)) {
				// 第一次添加配置文件时启动监控线程
				if (configMap.isEmpty()) {
					executorService.scheduleAtFixedRate(new CheckRunnable(), 0,
							checkInterval, TimeUnit.MILLISECONDS);
					logger.debug("start a thread to monitor config's update");
				}
				configMap.put(key, config);
				logger.debug("load config in system,configKey=" + key);
			} else {
				logger.debug("config has already loaded in system,configKey="
						+ key);
			}
		} finally {
			lock.unlock();
		}
	}

	// 监控配置文件线程,全部非后台线程执行完成，会自动关闭后台线程
	static class CheckRunnable implements Runnable {

		@Override
		public void run() {
			lock.lock();
			try {
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
			} finally {
				lock.unlock();
			}
		}
	}
}
