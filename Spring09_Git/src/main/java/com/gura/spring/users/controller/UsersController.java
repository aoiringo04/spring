package com.gura.spring.users.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	
	// "/users/signout.do" 로그아웃 요청 처리
	@RequestMapping("/users/signout")
	public ModelAndView signout(HttpSession session){
		// 세션 초기화
		// session.invalidate();
		// 세션에서 아이디 정보 삭제
		session.removeAttribute("id");
		ModelAndView mView = new ModelAndView();
		mView.addObject("msg", "로그아웃 되었어요!");
		mView.addObject("redirectUri", session.getServletContext().getContextPath());
		mView.setViewName("users/alert");
		return mView;
	}

	// "/users/signin.do" 로그인 요청 처리
	@RequestMapping("/users/signin")
	public ModelAndView signin(HttpSession session, @ModelAttribute UsersDto dto, @RequestParam String uri){
		// 아이디와 비밀번호가 유효한지 여부를 확인한다.
		boolean isValid = usersService.isValid(dto);
		ModelAndView mView = new ModelAndView();
		if(isValid){// 아이디 비밀번호가 맞는 정보인 경우
			// 로그인 처리를 해준다.
			session.setAttribute("id", dto.getId());
			mView.addObject("msg", dto.getId() + " 님 로그인 되었어요!");
			mView.addObject("redirectUri", uri);
		}else {
			// 아이디 혹은 비밀번호가 틀리다는 정보를 응답한다.
			mView.addObject("msg", "아이디 혹은 비밀번호가 틀려요!");
			String location = session.getServletContext().getContextPath() + "/users/signin_form.do?uri=" + uri;
			mView.addObject("redirectUri", location);
		}
		// 알림 페이지로 forward 이동시킨다.
		mView.setViewName("users/alert");
		return mView;
	}

	// "/users/signin_from.do" 로그인 폼 요청 처리
	@RequestMapping("/users/signin_form")
	public String signinForm(HttpSession session){
		// 세션 초기화
		session.invalidate();
		// 뷰페이지로 forward 이동
		return "users/signin_form";
	}
	
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
