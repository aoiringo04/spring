package com.gura.spring.users.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring.users.dto.UsersDto;
import com.gura.spring.users.service.UsersService;

// Component 스캔 시 bean 이 되고 또한 컨트롤러 역할을 할 수 있도록
@Controller
public class UsersController{
	// 의존객체 주입 되도록
	@Autowired
	private UsersService usersService;
	
	// "/users/signup.do" 요청 처리
	@RequestMapping("/users/signup")
	public ModelAndView signup(HttpServletRequest request, @ModelAttribute UsersDto dto){
		// 서비스를 이용해서 회원정보를 저장한다.
		usersService.insert(dto);
		ModelAndView mView = new ModelAndView();
		mView.addObject("msg", dto.getId() + "회원님 가입되었습니다.");
		mView.addObject("redirectUri", request.getContextPath());
		mView.setViewName("users/alert");
		return mView;
	}
	
	// ajax "/users/checkid.do" 요청 처리
	@RequestMapping("/users/checkid")
	@ResponseBody
	public Map<String, Object> checkid(@RequestParam String inputId){ // inputId는 전달되는 파라미터명
		Map<String, Object> map = usersService.canUseId(inputId);
		// json 문자열 응답하기
		return map;
	}
	
	@RequestMapping("/users/signup_form")
	public String signupform(){
		
		return "users/signup_form";
	}
}
