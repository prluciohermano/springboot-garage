package br.com.garagecontrol;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping("/meuprojeto")
	public String index() {
	 return "meuprojeto";	
	}

}
