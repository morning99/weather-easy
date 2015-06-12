package com.mor.weather.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mor.constant.SystemConstant;
import com.mor.weather.service.ApistoreService;
import com.mor.weather.service.CityService;

@Controller
@RequestMapping("/*")
public class HomeController {
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private CityService cityService;
	@Autowired
	private ApistoreService ApistoreService;

	/**
	 * 获取国内城市信息列表
	 * 
	 * @author morning99
	 * @date 2014年11月19日 下午4:51:24
	 * @param name
	 * @return
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/findstate", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> findstate(String name) {
		log.info("获取城市列表 " + name);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", name + "OK!");
		map.put("cts", SystemConstant.CITY_INFO);
		return map;
	}

	/**
	 * 根据城市code获取下级区域信息
	 * 
	 * @author morning99
	 * @date 2014年11月19日 下午4:51:03
	 * @param code
	 * @return
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/findCity", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> findcity(String code) {
		log.info(String.format("根据城市code：%s,获取区县列表", code));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", code + "OK");
		Map<String, String> ctmap = cityService.getCityInfo(code);
		map.put("cts", ctmap);
		return map;
	}

	/**
	 * 根据区域code获取天气预报
	 * 
	 * @author morning99
	 * @date 2014年11月19日 下午4:51:03
	 * @param code
	 * @return
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/getWeather", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> searchWeather(String code) {
		log.info(String.format("获取天气信息， 城市code：%s", code));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", code + "OK");
		String[] ctmap = cityService.getWeather(code);
		map.put("cts", ctmap);
		return map;
	}

	@RequestMapping(value = "", method = { RequestMethod.POST, RequestMethod.GET })
	public String index() {
		log.info("进入主页 index.jsp");
		return "index";
	}

	/**
	 * 获取用户地理位置信息
	 * 
	 * @author wanggengqi
	 * @date 2015年6月8日 下午3:38:17
	 * @return
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/getlocationInfo", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> getlocationInfo() {
		String ip = getIP();
		if (StringUtils.isNotBlank(ip)) {
			if (ip.indexOf(",") > 0) {
				ip = ip.split(",")[0];
			}
		}
		return ApistoreService.iplookupservice(ip);
	}

	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * 获得用户IP
	 * 
	 * @author wanggengqi
	 * @date 2015年6月8日 下午2:51:28
	 * @return
	 * @return String
	 */
	protected String getIP() {
		HttpServletRequest request = getRequest();
		String ip = getRequest().getHeader("X-Forwarded-For");
		if (StringUtils.isEmpty(ip) || StringUtils.equals("unknown", ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || StringUtils.equals("unknown", ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || StringUtils.equals("unknown", ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
