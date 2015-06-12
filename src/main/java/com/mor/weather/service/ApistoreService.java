package com.mor.weather.service;

import java.util.Map;

public interface ApistoreService {

	/**
	 * flag:是否成功 ；{ "errNum": 0, "errMsg": "success", "retData": { "ip":
	 * "117.89.35.58", "country": "中国", "province": "江苏", "city": "南京",
	 * "district": "鼓楼", "carrier": "中国电信" } }
	 * 
	 * @author wanggengqi
	 * @date 2015年6月8日 下午2:29:23
	 * @param ip
	 * @return
	 * @return Map<String,Object>
	 */
	Map<String, Object> iplookupservice(String ip);

}
