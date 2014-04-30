package com.zhangbin.common.config.exception;

/**
 * 配置项无法找到
 * @author zhangbinalan
 *
 */
public class ConfigNotFoundException extends ConfigException {

	private static final long serialVersionUID = 2189530227553281691L;

	public ConfigNotFoundException(String key) {
		super("Config can not find,key="+key);
	}
}
