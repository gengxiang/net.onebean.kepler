package net.onebean.kepler.web.action.test;

import java.util.HashMap;
import java.util.Map;

import net.onebean.component.redis.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TestController{

	@Autowired
	private IRedisService iRedisService;


	@RequestMapping("/redisSave")
	@ResponseBody
	public Map<String, Object> redisSave(@RequestParam("value") String value,@RequestParam("key") String key) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("flag", iRedisService.set(key, value, 1000*30));
		return result;
	}

	@RequestMapping("/redisFind")
	@ResponseBody
	public Map<String, Object> redisFind(@RequestParam("key") String key) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("res", iRedisService.get(key));
		return result;
	}
}
