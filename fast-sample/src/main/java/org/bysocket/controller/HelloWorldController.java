package org.bysocket.controller;

import java.util.HashMap;
import java.util.Map;

import org.bysocket.entity.User;
import org.bysocket.excel.ExcelOne;
import org.fastframework.mvc.ModelAndView;
import org.fastframework.mvc.annotation.Controller;
import org.fastframework.mvc.annotation.MediaTypes;
import org.fastframework.mvc.annotation.RequestMapping;
import org.fastframework.mvc.annotation.RequestMethod;

@Controller
public class HelloWorldController {

	@RequestMapping(value = "/hello",
			method = RequestMethod.GET,
			produces = MediaTypes.TEXT_PLAIN_UTF_8)
	public String hello(String name) {
		return "Hello," + name + "!";
	}

	@RequestMapping(value = "/hellobysocket",
			method = RequestMethod.GET,
			produces = MediaTypes.TEXT_PLAIN_UTF_8)
	public String hellobysocket(String name, Integer age) {
		User user = new User();
		user.setName(name);
		return "Hello," + name + "!" + age;
	}
	
	@RequestMapping(value = "/excel",
			method = RequestMethod.GET)
	public ModelAndView helloExcel() {
		ExcelOne excel = new ExcelOne();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", "age");
		map.put("张三", 12);
		map.put("张三留", 14);
		map.put("张三无", 22);
		map.put("张三四", 44);
		return new ModelAndView(excel,map);
	}
	
	@RequestMapping(value = "/page",
			method = RequestMethod.GET)
	public ModelAndView helloWorld(String name) {
		ModelAndView model  = new ModelAndView();
		model.addObject("name", name);
		model.setViewName("/WEB-INF/jsp/hello.jsp");
		return model;
	}

}
