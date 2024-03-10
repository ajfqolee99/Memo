package com.ajfqo.memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ajfqo.memo.user.domain.User;
import com.ajfqo.memo.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

// API를 만들기 위한 Controller
// @Controller + @ResponseBody
// UserController와 구분한 이유는 View페이지는 Response에 HTML로 채워서
// return 형식이 jsp경로라서 @ResponseBody가 붙지 않지만 
// API는 Response에 데이터를 채우기 위해 JSON문자열을 채운다.
@RestController
@RequestMapping("/user")
public class UserRestController {
	
	@Autowired
	private UserService userService;
	
	// 회원가입 API
	@PostMapping("/join")
	public Map<String, String> join(
			@RequestParam("loginId") String loginId
			,@RequestParam("password") String password
			,@RequestParam("name") String name
			,@RequestParam("email") String email
			) {
		int count = userService.addUser(loginId, password, name, email);
		
		Map<String, String> resultMap = new HashMap<>();
		if(count == 1) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
		
	}
	
	// 로그인 API
	@PostMapping("/login")
	public Map<String, String> login(
			@RequestParam("loginId") String loginId
			,@RequestParam("password") String password
			,HttpServletRequest request
			) {
		User user = userService.getUser(loginId, password);
		
		Map<String, String> resultMap = new HashMap<>();
		
		// 로그인 성공
		if(user != null) {
			
			// HttpServletRequest로부터 Session 객체를 얻어온다.
			HttpSession session = request.getSession();
			// 세션에 로그인 되었다는 정보를 저장
			// 세션에 사용자 정보를 저장
			// 세션에 사용자 정보가 저장되어 있으면 로그인으로 판단
			session.setAttribute("userId", user.getId());
			session.setAttribute("userName", user.getName());
			
			resultMap.put("result", "success");
		} else {
			// 로그인 실패시
			resultMap.put("result", "fail");
		}
		return resultMap;
		
	}
	
	
	
	
}
