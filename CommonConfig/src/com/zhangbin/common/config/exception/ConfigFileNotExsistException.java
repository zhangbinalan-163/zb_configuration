package com.zhangbin.common.config.exception;

/**
 * 配置文件找不到的异常
 * 
 * @author zhangbinalan
 * 
 */
public class ConfigFileNotExsistException extends ConfigException {

	private static final long serialVersionUID = -4392889897646190519L;

	public ConfigFileNotExsistException(String path) {
		super("Config file not exsist,file path=" + path);
	}
}
