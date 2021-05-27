package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;








@Controller
public class GuiController {

	 
	 @GetMapping("/cielogs")
	 public String logs(){
		 return "index";
	 }

	
}
