package com.zhangbin.common.config;

import java.io.File;

import com.zhangbin.common.config.exception.ConfigException;
import com.zhangbin.common.config.exception.ConfigFileNotExsistException;

public abstract class FileConfig extends AbstractConfig {

	protected File configFile;

	public FileConfig(String filePath) throws ConfigException {
		super(filePath);
	}

	@Override
	public long getLastModifiedTime() throws ConfigException {
		if (configFile == null || !configFile.exists()) {
			throw new ConfigFileNotExsistException(getConfigPath());
		}
		return configFile.lastModified();
	}

	@Override
	protected void init() throws ConfigException {
		configFile = new File(getConfigPath());
		checkAndLoad();
	}
}
