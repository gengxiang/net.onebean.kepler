package net.onebean.kepler.web.action.center;

import net.onebean.kepler.security.SpringSecurityUtil;
import net.onebean.util.CodeGenerateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CenterController {
	
	@RequestMapping({"/center",""})
	public String view(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("current_sys_user",SpringSecurityUtil.getCurrentLoginUser(request));
//		new CodeGenerateUtils().generate("sys_gua");
		return "center/bone";
	}

	@RequestMapping({"/index"})
	public String index() {
		return "center/view";
	}

	public static void main(String[] args) {
		String proPath = System.getProperty("user.dir");
		proPath = proPath.replaceAll("\\\\", "\\\\\\\\");
			proPath += "net.onebean.kepler";
		System.out.println(proPath);
	}
}


