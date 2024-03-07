package com.ajfqo.memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ajfqo.memo.user.service.UserService;

// API를 만들기 위한 Controller
// @Controller + @ResponseBody
// UserController와 구분한 이유는 View페이지는 Response에 HTML로 채워서
// return 형식이 jsp경로라서 @ResponseBody가 붙지 않지만 
// API는 Response에 데이터를 채우기 위해 JSON문자열을 채운다.
@RestController
public class UserRestController {
	
	@Autowired
	private UserService userService;
	
	// 회원가입 API
	@PostMapping("/user/join")
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
	
	
	
	
}
