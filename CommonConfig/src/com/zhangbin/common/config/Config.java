package com.zhangbin.common.config;

import java.util.List;
import java.util.Set;

import com.zhangbin.common.config.exception.ConfigException;

/**
 * �����ļ��ӿ�
 * 
 * @author zhangbinalan
 * 
 */
public interface Config {
	/**
	 * ������ϵͳ��Ψһ���
	 * @return
	 */
	String getConfigPath();
	
	String getString(String key) throws ConfigException;

	String getString(String key, String defau);

	int getInt(String key) throws ConfigException;

	int getInt(String key, int defau);

	float getFloat(String key) throws ConfigException;

	float getFloat(String key, float defau);

	long getLong(String key) throws ConfigException;

	long getLong(String key, long defau);

	double getDouble(String key) throws ConfigException;

	double getDouble(String key, double defau);

	boolean getBoolean(String key) throws ConfigException;

	boolean getBoolean(String key, boolean defau);

	Set<String> getSet(String key) throws ConfigException;

	List<String> getList(String key) throws ConfigException;

	/**
	 * ������������Դ
	 * 
	 * @param
	 * @throws ConfigException
	 */
	void load() throws ConfigException;

	/**
	 * ����Ƿ���Ҫ���¼���
	 * 
	 * @throws ConfigException
	 */
	void checkAndLoad() throws ConfigException;
}
