package net.onebean.kepler.web.action.center;

import net.onebean.kepler.security.SpringSecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CenterController {
	
	@RequestMapping({"/center",""})
	public String view(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("current_sys_user",SpringSecurityUtil.getCurrentLoginUser(request));
		return "center/bone";
	}

	@RequestMapping({"/index"})
	public String index() {
		return "center/view";
	}

}


