package com.mor.weather.service.impl;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.rpc.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.com.WebXml.WeatherWS;
import cn.com.WebXml.WeatherWSSoap;
import cn.com.WebXml.WeatherWSSoapStub;

import com.mor.constant.SystemConstant;
import com.mor.weather.service.CityService;

@Service
public class CityServiceImpl implements CityService, InitializingBean {
	private static final Logger log = LoggerFactory.getLogger(CityServiceImpl.class);
	@Autowired
	private WeatherWS ws;

	public Map<String, String> initCitys(Map<String, String> map) {

		try {
			WeatherWSSoapStub service = (WeatherWSSoapStub) ws.getPort(WeatherWSSoapStub.class);
			String[] citys = service.getRegionProvince();
			for (String ct : citys) {
				String[] ctinfo = ct.trim().split(",");
				if (ctinfo.length == 2)
					map.put(ctinfo[0], ctinfo[1]);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return map;
	}

	@Cacheable(value = "serviceCache", key = "'com.mor.weather.service.impl'+'getCityInfo'+#cityCode")
	@Override
	public Map<String, String> getCityInfo(String cityCode) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			WeatherWSSoapStub service = (WeatherWSSoapStub) ws.getPort(WeatherWSSoapStub.class);
			String[] citys;
			try {
				citys = service.getSupportCityString(cityCode);
				for (String ct : citys) {
					String[] cts = ct.trim().split(",");
					if (cts.length == 2)
						map.put(cts[0], cts[1]);
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initCitys(SystemConstant.CITY_INFO);
		Map<String, String> ctmap = SystemConstant.CITY_INFO;
		for (Entry<String, String> ct : ctmap.entrySet()) {
			System.out.println(ct.getKey() + ":" + ct.getValue());
		}
		log.info("初始化城市对");
	}

	@Cacheable(value = "serviceCache", key = "'com.mor.weather.service.impl'+'getWeather'+#cityCode")
	@Override
	public String[] getWeather(String cityCode) {
		String[] weather = null;
		try {
			WeatherWSSoap soap = ws.getWeatherWSSoap();
			weather = soap.getWeather(cityCode, "");
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return weather;
	}

}
