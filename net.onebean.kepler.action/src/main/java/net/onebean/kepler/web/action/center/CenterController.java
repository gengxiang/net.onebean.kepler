package net.onebean.kepler.web.action.center;

import net.onebean.kepler.security.SpringSecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CenterController {
	
	@RequestMapping({"/center",""})
	public String view(HttpServletRequest request, Model model) {
		model.addAttribute("current_sys_user",SpringSecurityUtil.getCurrentLoginUser(request));
		return "center/bone";
	}

	@RequestMapping({"/index"})
	public String index() {
		return "center/view";
	}
}


