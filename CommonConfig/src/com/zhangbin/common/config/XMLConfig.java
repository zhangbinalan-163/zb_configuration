package com.zhangbin.common.config;

import java.util.HashMap;
import java.util.Map;

import com.zhangbin.common.config.exception.ConfigException;
import com.zhangbin.common.config.exception.ConfigNotFoundException;

/**
 * xml类型的配置文件
 * 
 * @author zhangbinalan
 * 
 */
public class XMLConfig extends FileConfig {
	//private static Logger logger = LoggerFactory.getLogger(XMLConfig.class);

	private Map<String, String> propMap = new HashMap<String, String>();;

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
		//TODO
	}
}
