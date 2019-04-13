package pers.qyj.graduationpr.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pers.qyj.graduationpr.pojo.Policy;
import pers.qyj.graduationpr.service.PolicyService;

@Controller
@RequestMapping("config/resource")
public class PolicyController {
	@Autowired
	PolicyService policyService;
	
	@RequestMapping("listPolicy")
	public String list(Model model) {
		List<Policy> policies = policyService.list();
		model.addAttribute("policies", policies);
		
		model.addAttribute("requestUrl", "listPolicy");
		
		return "config/resource/listPolicy";
	}

	@RequestMapping("editPolicy")
	public String list(Model model, int id) {
		Policy policy = policyService.get(id);
		model.addAttribute("policy", policy);
		return "config/resource/editPolicy";
	}

	@RequestMapping("updatePolicy")
	public String update(Model model, Policy policy) {
		policyService.update(policy);
		
		model.addAttribute("requestUrl", "listPolicy");
		
		return "redirect:listPolicy";
	}

	@RequestMapping("addPolicy")
	public String list(Model model, Policy policy) {
		policyService.add(policy);
		
		model.addAttribute("requestUrl", "listPolicy");
		
		return "redirect:listPolicy";
	}

	@RequestMapping("deletePolicy")
	public String delete(Model model, int id) {
		policyService.delete(id);
		
		model.addAttribute("requestUrl", "listPolicy");
		
		return "redirect:listPolicy";
	}
}
