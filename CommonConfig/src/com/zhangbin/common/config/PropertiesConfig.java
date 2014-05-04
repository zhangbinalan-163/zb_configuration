package com.zhangbin.common.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhangbin.common.config.exception.ConfigException;
import com.zhangbin.common.config.exception.ConfigFileNotExsistException;
import com.zhangbin.common.config.exception.ConfigNotFoundException;

/**
 * properties类型的配置文件
 * 
 * @author zhangbinalan
 * 
 */
public class PropertiesConfig extends FileConfig {
	private static Logger logger = LoggerFactory
			.getLogger(PropertiesConfig.class);

	private Properties prop;

	public PropertiesConfig(String filePath) throws ConfigException {
		this(filePath, true);
	}

	public PropertiesConfig(String configPath, boolean autoCheck)
			throws ConfigException {
		super(configPath, autoCheck);
	}

	@Override
	public String getString(String key) throws ConfigException {
		if (prop == null) {
			throw new ConfigNotFoundException(key);
		}
		String value = prop.getProperty(key);
		if (value == null || "".equals(value)) {
			throw new ConfigNotFoundException(key);
		}
		return value;
	}

	@Override
	public String getString(String key, String defau) {
		String value = defau;
		if (prop != null) {
			String tmp = prop.getProperty(key);
			if (tmp != null && !"".equals(tmp)) {
				value = tmp;
			}
		}
		return value;
	}

	@Override
	public void load() throws ConfigException {
		InputStream is = null;
		try {
			is = new FileInputStream(configFile);
			Properties p = new Properties();
			p.load(is);

			if (prop != null) {
				prop.clear();
			}
			prop = p;

			logger.debug("config load success,configKey=" + getConfigPath());
		} catch (FileNotFoundException e) {
			throw new ConfigFileNotExsistException(getConfigPath());
		} catch (IOException e) {
			throw new ConfigException(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
