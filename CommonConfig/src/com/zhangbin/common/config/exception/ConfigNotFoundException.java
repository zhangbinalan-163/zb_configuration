package com.zhangbin.common.config.exception;

/**
 * �������޷��ҵ�
 * @author zhangbinalan
 *
 */
public class ConfigNotFoundException extends ConfigException {

	private static final long serialVersionUID = 2189530227553281691L;

	public ConfigNotFoundException(String key) {
		super("Config can not find,key="+key);
	}
}
