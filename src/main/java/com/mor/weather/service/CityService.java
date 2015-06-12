package com.mor.weather.service;

import java.util.Map;

public interface CityService {
	/**
	 * 初始化城市信息
	 * 
	 * @author morning99
	 * @date 2014年11月19日 下午4:10:45
	 * @param map
	 * @return
	 * @return Map<String,String>
	 */
	Map<String, String> initCitys(Map<String, String> map);

	/**
	 * 根据城市code获取城市下级信息
	 * 
	 * @author morning99
	 * @date 2014年11月19日 下午4:05:13
	 * @param cityCode
	 * @return
	 * @return Map<String,String>
	 */
	Map<String, String> getCityInfo(String cityCode);

	/**
	 * 根据区域code获取天气信息
	 * 
	 * @author morning99
	 * @date 2014年11月19日 下午4:57:37
	 * @param cityCode
	 * @return
	 * @return String[]
	 */
	String[] getWeather(String cityCode);

}
