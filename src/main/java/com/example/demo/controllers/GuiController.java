package com.example.demo.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;








@Controller
public class GuiController {

	 
	 @GetMapping("/cielogs")
	 public String logs(){
		 return "index";
	 }
//	 @GetMapping("/mylogout")
//     public String fetchSignoutSite(HttpServletRequest request, HttpServletResponse response) {        
//         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//         if (auth != null) {
//             new SecurityContextLogoutHandler().logout(request, response, auth);
//         }
//           
//         return "redirect:/login?logout";
//     }
//	 @RequestMapping(value = {"/mylogout1"}, method = RequestMethod.GET)
//	  public String fetchSignoutSite1(HttpServletRequest request, HttpServletResponse response){
//	        
//	      HttpSession session = request.getSession(false);
//	      SecurityContextHolder.clearContext();
//
//	      session = request.getSession(false);
//	      if(session != null) {
//	          session.invalidate();
//	      }
//         
//	      for(Cookie cookie : 
//	    	  request.getCookies()) {
//	          cookie.setMaxAge(0);
//	      }
//
//	      return "redirect:/login?logout";
//	  }
//	 

	
}
