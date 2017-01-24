package com.gura.spring.shop.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring.shop.service.ShopService;

@Controller
public class ShopController{
	
	// 의존 객체 주입
	@Autowired
	private ShopService shopService;
	
	@RequestMapping("/shop/index")
	public ModelAndView index(ModelAndView mView){
		List<String> info = new ArrayList<String>();
		info.add("구정 특별 SALE");
		info.add("10% 의 보너스 포인트를 적립해 드립니다.");
		info.add("기회를 놓치지 마세요!");
		// 모델을 info 라는 키 값으로 밤기
		mView.addObject("info", info);
		// view 페이지의 정보 담아서
		mView.setViewName("shop/index");
		// 리턴해주기
		return mView;
	}
	
}