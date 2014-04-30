package com.zhangbin.common.config.exception;

/**
 * �����ļ��Ҳ������쳣
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
