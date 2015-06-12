package com.mor.weather.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mor.weather.service.ApistoreService;

@Service
public class ApistoreServiceImpl implements ApistoreService {

	private static final Logger log = LoggerFactory.getLogger(ApistoreServiceImpl.class);

	private final static String APISTORE_URL_IPLOOKUP = "http://apis.baidu.com/apistore/iplookupservice/iplookup";
	private final static String APISTORE_APIKEY = "8e9afcc75d9862ec4a1626b4b57118d8";
	// openweather.weather.com.cn morning99
	private final static String OPEN_WEATHER_APPID = "39d8ebb33578dcb9";
	private final static String OPEN_WEATHER_PRIVATE_KEY = "0f0f0b_SmartWeatherAPI_687c036";
	/**
	 * 固定URL
	 * <code>http://open.weather.com.cn/data/?areaid=""&type=""&date=""&appid=""&key=".urlencode($key);</code>
	 * <code>type(数据类型) 指数: index_v(常规接口)； 3天预报: forecast_v(常规接口)；</code>
	 * <code>date(客户端日期) 按照格式yyyyMMddHHmm获取客户端当前时间 ;</code>
	 * <code>Appid(固定分配的型号标识) 某某:004906671841487传递参数时:截取appid的前6位 生成公钥时:取完整的appid ;</code>
	 * <code>Key(令牌) 由公钥(public_key)和私钥(private_key)通过固定算法加密生成</code>
	 * 
	 */
	private final static String OPEN_WEATHER_url = "http://open.weather.com.cn/data/";

	@Cacheable(value = "serviceCache", key = "'com.mor.weather.service.impl'+#ip")
	@Override
	public Map<String, Object> iplookupservice(String ip) {
		Map<String, Object> res = new HashMap<String, Object>();
		String jsonResult = request(APISTORE_URL_IPLOOKUP, "ip=" + ip);
		if (StringUtils.isBlank(jsonResult)) {
			log.info(String.format("iplookupservice 无法通过接口获取数据ip=%s", ip));
			return res;
		}
		res = JSON.parseObject(jsonResult, new TypeReference<Map<String, Object>>() {
		});
		log.info("ipAddress:" + ip + "\tjsonResult:" + jsonResult);
		res.put("ip", ip);
		return res;
	}

	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static String request(String httpUrl, String httpArg) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", APISTORE_APIKEY);
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
