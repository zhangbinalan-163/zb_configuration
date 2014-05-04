package com.zhangbin.common.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhangbin.common.config.exception.ConfigException;
import com.zhangbin.common.config.exception.ConfigTypeException;

public abstract class AbstractConfig implements Config {
	private static Logger logger = LoggerFactory
			.getLogger(AbstractConfig.class);
	private String configPath;
	// 最近修改时间
	private long lastModifiedTime;
	//是否需要监控修改情况
	private boolean autoCheck=false;

	private static String DELIMITER = ",";

	public abstract String getString(String key) throws ConfigException;

	public abstract String getString(String key, String defau);

	protected abstract long getLastModifiedTime() throws ConfigException;

	public abstract void load() throws ConfigException;
	
	protected abstract void init() throws ConfigException;
	
	@Override
	public String getConfigPath(){
		return configPath;
	}
	
	public AbstractConfig(String configPath) throws ConfigException {
		this(configPath,true);
	}

	public AbstractConfig(String configPath,boolean autoCheck) throws ConfigException{
		this.configPath=configPath;
		this.autoCheck=autoCheck;
		init();
		if(this.autoCheck){
			ConfigManager.addConfig(this);
		}
	}
	@Override
	public void checkAndLoad() throws ConfigException {
		long time = getLastModifiedTime();
		if (time > lastModifiedTime) {
			logger.debug("config was update ,try to reload it,configKey="
					+ getConfigPath());
			load();
			// 设置最后一次修改时间
			lastModifiedTime = time;
		}
	}

	@Override
	public int getInt(String key) throws ConfigException {
		String value = getString(key);
		try {
			int result = Integer.parseInt(value);
			return result;
		} catch (NumberFormatException e) {
			throw new ConfigTypeException(key);
		}
	}

	@Override
	public int getInt(String key, int defau) {
		try {
			String value = getString(key);
			int result = Integer.parseInt(value);
			return result;
		} catch (Exception e) {
			return defau;
		}
	}

	@Override
	public float getFloat(String key) throws ConfigException {
		String value = getString(key);
		try {
			float result = Float.parseFloat(value);
			return result;
		} catch (NumberFormatException e) {
			throw new ConfigTypeException(key);
		}
	}

	@Override
	public float getFloat(String key, float defau) {
		try {
			String value = getString(key);
			float result = Float.parseFloat(value);
			return result;
		} catch (Exception e) {
			return defau;
		}
	}

	@Override
	public long getLong(String key) throws ConfigException {
		String value = getString(key);
		try {
			long result = Long.parseLong(value);
			return result;
		} catch (NumberFormatException e) {
			throw new ConfigTypeException(key);
		}
	}

	@Override
	public long getLong(String key, long defau) {
		try {
			String value = getString(key);
			long result = Long.parseLong(value);
			return result;
		} catch (Exception e) {
			return defau;
		}
	}

	@Override
	public double getDouble(String key) throws ConfigException {
		String value = getString(key);
		try {
			double result = Double.parseDouble(value);
			return result;
		} catch (NumberFormatException e) {
			throw new ConfigTypeException(key);
		}
	}

	@Override
	public double getDouble(String key, double defau) {
		try {
			String value = getString(key);
			double result = Double.parseDouble(value);
			return result;
		} catch (Exception e) {
			return defau;
		}
	}

	@Override
	public boolean getBoolean(String key) throws ConfigException {
		String value = getString(key);
		try {
			Boolean result = Boolean.parseBoolean(value);
			return result;
		} catch (NumberFormatException e) {
			throw new ConfigTypeException(key);
		}
	}

	@Override
	public boolean getBoolean(String key, boolean defau) {
		try {
			String value = getString(key);
			Boolean result = Boolean.parseBoolean(value);
			return result;
		} catch (Exception e) {
			return defau;
		}
	}

	@Override
	public Set<String> getSet(String key) throws ConfigException {
		String[] valueArray = getStringArray(key);

		Set<String> valueSet = new HashSet<String>();
		for (String tmp : valueArray) {
			valueSet.add(tmp);
		}
		return valueSet;
	}

	private String[] getStringArray(String key) {
		String value = getString(key, "");
		String[] valueArray = value.split(DELIMITER);
		return valueArray;
	}

	@Override
	public List<String> getList(String key) {
		String[] valueArray = getStringArray(key);
		List<String> valueList = new ArrayList<String>();
		for (String tmp : valueArray) {
			valueList.add(tmp);
		}
		return valueList;
	}
}
