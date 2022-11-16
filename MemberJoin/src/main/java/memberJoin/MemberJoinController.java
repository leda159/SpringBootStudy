package memberJoin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member/*")
public class MemberJoinController {

	//http://localhost:8080/member/join
	@GetMapping("/join")
	public String join() {
		
		return "join";
	}
	
	
	@PostMapping("/process")
	public String process(Model model,
			@RequestParam("id") String id,
			@RequestParam("passwd") String passwd,
			@RequestParam("name") String name,
			@RequestParam("phone") String phone,
			@RequestParam("email") String email	) {
		
		//유효범위 : request
		model.addAttribute("id",id);
		model.addAttribute("passwd",passwd);
		model.addAttribute("name",name);
		model.addAttribute("phone",phone);
		model.addAttribute("email",email);
		
		return "joinProcess";
	}
	
}





