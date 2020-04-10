package com.fciensy.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*@ResponseBody
@Controller*/
@RestController
public class HelloSpringController {
	@Value("${person.lastName}")
	private String name ;
	@RequestMapping("/hello")
	public String helo(){
		return "this is Spring boot demo!"+name;
	}

}
