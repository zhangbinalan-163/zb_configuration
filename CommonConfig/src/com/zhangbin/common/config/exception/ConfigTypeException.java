package com.zhangbin.common.config.exception;

/**
 * �������ʽ�쳣
 * 
 * @author zhangbinalan
 * 
 */
public class ConfigTypeException extends ConfigException {

	private static final long serialVersionUID = 2189530227553281691L;

	public ConfigTypeException(String key) {
		super("Config type can not convert to expect,key=" + key);
	}
}
