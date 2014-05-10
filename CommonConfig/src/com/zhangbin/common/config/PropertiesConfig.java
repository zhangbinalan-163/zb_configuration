package com.zhangbin.common.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhangbin.common.config.exception.ConfigException;
import com.zhangbin.common.config.exception.ConfigFileNotExsistException;
import com.zhangbin.common.config.exception.ConfigNotFoundException;

/**
 * properties���͵������ļ�
 * 
 * @author zhangbinalan
 * 
 */
public class PropertiesConfig extends FileConfig {
	// 1������������Ǵ���properties�ļ��õ�encoding��UTF-8������д��������
	// 2��Properties�ļ�Ĭ�ϻ����ǲ���ISO8859-1����
	// 3��������Properties.getProperty(String
	// key)�ӿڶ�ȡ���ݣ�����ʱ��õ��������롣��Ϊ����ISO8859-1��GBK��������ݽ��н���
	// 4�����ǰ���Properties.getProperty(String
	// key)�ӿڶ�ȡ����ת��Ϊ����properties�ļ�ʱ�õ�encoding��UTF-8��

	private static Logger logger = LoggerFactory
			.getLogger(PropertiesConfig.class);

	private Properties prop;
	private String targetCode;

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
		if (value != null && !"".equals(value)) {
			if (targetCode != null) {
				value = new String(
						value.getBytes(Charset.forName("ISO-8859-1")),
						Charset.forName(targetCode));
			}
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
		if (value != null && !"".equals(value)) {
			if (targetCode != null) {
				value = new String(
						value.getBytes(Charset.forName("ISO-8859-1")),
						Charset.forName(targetCode));
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

	public String getTargetCode() {
		return targetCode;
	}

	public void setTargetCode(String targetCode) {
		this.targetCode = targetCode;
	}
}
