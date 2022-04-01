package com.smhrd.Hwagae.controller;


import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smhrd.Hwagae.Service.HomeService;
import com.smhrd.Hwagae.VO.TestVO;
import com.smhrd.Hwagae.util.BusinessException;


@Controller
public class HomeController {
	
	@Autowired
    HomeService homeService;
	
    @RequestMapping(value="/Main")
    public String retrieveUserInfoCmd(HttpServletRequest request, HttpServletResponse response, Model model){
    	
    	try {
    		
    		HashMap<String, Object> requestMap = new HashMap<String, Object>();
    		requestMap.put("name", "TEST");
    		
    		TestVO testVO = homeService.retrieveUserInfo(requestMap);
    		
    		model.addAttribute("name", testVO.getName());
    		model.addAttribute("age", testVO.getAge());
    		model.addAttribute("address", testVO.getAddress());
    		
    	}catch(Exception err) {
    		err.printStackTrace();
    	}
    	
    	return "Main/main";
    }
}
