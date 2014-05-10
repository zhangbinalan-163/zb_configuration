package com.zhangbin.common.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhangbin.common.config.exception.ConfigException;
import com.zhangbin.common.config.exception.ConfigFileNotExsistException;
import com.zhangbin.common.config.exception.ConfigNotFoundException;

/**
 * xml类型的配置文件 格式固定，不能多级
 * 
 * @author zhangbinalan
 * 
 */
public class XMLConfig extends FileConfig {
	private static Logger logger = LoggerFactory.getLogger(XMLConfig.class);

	private Map<String, String> propMap;

	public XMLConfig(String filePath) throws ConfigException {
		this(filePath, true);
	}

	public XMLConfig(String configPath, boolean autoCheck)
			throws ConfigException {
		super(configPath, autoCheck);
	}

	@Override
	public String getString(String key) throws ConfigException {
		if (propMap == null) {
			throw new ConfigNotFoundException(key);
		}
		String value = propMap.get(key);
		if (value == null || "".equals(value)) {
			throw new ConfigNotFoundException(key);
		}
		return value;
	}

	@Override
	public String getString(String key, String defau) {
		String value = defau;
		if (propMap != null) {
			String tmp = propMap.get(key);
			if (tmp != null && !"".equals(tmp)) {
				value = tmp;
			}
		}
		return value;
	}

	@Override
	public void load() throws ConfigException {
		if(propMap!=null){
			propMap.clear();
			propMap = null;
		}
		propMap = new HashMap<String, String>();

		InputStream is = null;
		try {
			is = new FileInputStream(configFile);
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(is);
			// 获取根节点对象
			Element rootElement = document.getRootElement();
			if (!rootElement.getName().equals("common-config")) {
				throw new ConfigException(
						"xml config error,root is not <common-config>");
			}
			@SuppressWarnings("unchecked")
			Iterator<Element> ite = rootElement.elementIterator();
			while (ite.hasNext()) {
				Element element = ite.next();
				String key = element.getName();
				String value = element.getTextTrim();
				propMap.put(key, value);
			}
			logger.debug("config load success,configKey=" + getConfigPath());
		} catch (FileNotFoundException e) {
			throw new ConfigFileNotExsistException(getConfigPath());
		} catch (DocumentException e) {
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
