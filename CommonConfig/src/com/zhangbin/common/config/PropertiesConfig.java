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
 * properties类型的配置文件
 * 
 * @author zhangbinalan
 * 
 */
public class PropertiesConfig extends FileConfig {
	// 1、假设如果我们创建properties文件用的encoding是UTF-8，我们写入了中文
	// 2、Properties文件默认机制是采用ISO8859-1处理
	// 3、我们用Properties.getProperty(String
	// key)接口读取内容，这是时候得到的是乱码。因为想用ISO8859-1对GBK编码的内容进行解码
	// 4、我们把用Properties.getProperty(String
	// key)接口读取内容转换为创建properties文件时用的encoding（UTF-8）

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
