package pers.qyj.graduationpr.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pers.qyj.graduationpr.pojo.Bedtype;
import pers.qyj.graduationpr.pojo.Breakfast;
import pers.qyj.graduationpr.pojo.Facility;
import pers.qyj.graduationpr.pojo.Permission;
import pers.qyj.graduationpr.pojo.Policy;
import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.Role;
import pers.qyj.graduationpr.pojo.Roomtype;
import pers.qyj.graduationpr.pojo.User;
import pers.qyj.graduationpr.service.BedtypeService;
import pers.qyj.graduationpr.service.BreakfastService;
import pers.qyj.graduationpr.service.FacilityService;
import pers.qyj.graduationpr.service.PolicyService;
import pers.qyj.graduationpr.service.ResourceFacilityService;
import pers.qyj.graduationpr.service.ResourcePolicyService;
import pers.qyj.graduationpr.service.ResourceService;
import pers.qyj.graduationpr.service.RoleService;
import pers.qyj.graduationpr.service.RoomtypeService;
import pers.qyj.graduationpr.service.UserRoleService;
import pers.qyj.graduationpr.service.UserService;

@Controller
@RequestMapping("config/resource")
public class ResourceController {
	@Autowired
	ResourceService resourceService;
	@Autowired
	RoomtypeService roomtypeService;
	@Autowired
	BreakfastService breakfastService;
	@Autowired
	FacilityService facilityService;
	@Autowired
	PolicyService policyService;
	@Autowired
	ResourceFacilityService resourceFacilityService;
	@Autowired
	ResourcePolicyService resourcePolicyService;

	@RequestMapping("listResource")
	public String list(Model model) {
		List<Resource> resources = resourceService.list();
		model.addAttribute("resources", resources);

		Map<Integer, List<Facility>> resource_facilities = new HashMap<>();
		for (Resource resource : resources) {
			List<Facility> facilities = facilityService.listFacilities(resource);
			resource_facilities.put(resource.getId(), facilities);
		}
		model.addAttribute("resource_facilities", resource_facilities);

		Map<Integer, List<Policy>> resource_policies = new HashMap<>();
		for (Resource resource : resources) {
			List<Policy> policies = policyService.listPolicies(resource);
			resource_policies.put(resource.getId(), policies);
		}
		model.addAttribute("resource_policies", resource_policies);
		
		// 获取所有房型列表
		List<Roomtype> roomtypes = roomtypeService.list();
		model.addAttribute("roomtypes", roomtypes);
		
		model.addAttribute("requestUrl", "listResource");

		return "config/resource/listResource";
	}

	@RequestMapping("addResource")
	public String add() {
		return "redirect:editResource";
	}

	@RequestMapping("editResource")
	public String edit(Model model, Integer id) {
		String update = "add";
		Resource resource = new Resource();

		// 获取所有房型列表
		List<Roomtype> roomtypes = roomtypeService.list();
		model.addAttribute("roomtypes", roomtypes);

		// 获取所有早餐列表
		List<Breakfast> breakfasts = breakfastService.list();
		model.addAttribute("breakfasts", breakfasts);

		// 获取所有设施列表
		List<Facility> facilities = facilityService.list();
		model.addAttribute("facilities", facilities);

		// 获取所有酒店政策列表
		List<Policy> policies = policyService.list();
		model.addAttribute("policies", policies);

		boolean hasFacility = false;
		Map<String, Boolean> resource_hasFacility = new HashMap<>();

		boolean hasPolicy = false;
		Map<String, Boolean> resource_hasPolicy = new HashMap<>();

		if (id != null) {
			update = "update";

			// 获取资源
			resource = resourceService.get(id);

			// 获取被编辑资源的设施列表
			List<Facility> resource_facilities = facilityService.listFacilities(resource);
			model.addAttribute("resource_facilities", resource_facilities);

			// 获取被编辑资源的政策列表
			List<Policy> resource_policies = policyService.listPolicies(resource);
			model.addAttribute("resource_policies", resource_policies);

			for (Facility facility : facilities) {
				for (Facility resource_facility : resource_facilities) {
					if (facility.getName().equals(resource_facility.getName())) {
						hasFacility = true;
						resource_hasFacility.put(facility.getName(), hasFacility);
					}
				}
			}

			for (Policy policy : policies) {
				for (Policy resource_policy : resource_policies) {
					if (policy.getName().equals(resource_policy.getName())) {
						hasPolicy = true;
						resource_hasPolicy.put(policy.getName(), hasPolicy);
					}
				}
			}
		}

		Subject subject = SecurityUtils.getSubject();
		subject.getSession().setAttribute("update", update);

		model.addAttribute("resource", resource);
		model.addAttribute("resource_hasFacility", resource_hasFacility);
		model.addAttribute("resource_hasPolicy", resource_hasPolicy);

		return "config/resource/editResource";
	}

	@RequestMapping("updateResource")
	public String update(Model model, Resource resource, int[] facilities, int[] policies) {
		// 获取当前需要执行的响应
		Subject subject = SecurityUtils.getSubject();
		String update = (String) subject.getSession().getAttribute("update");
		if (update.equals("add")) {
			/*
			 * 这一步有一个细节
			 * 这里的resource因为是添加，数据库还没有，所以resource没有id字段
			 * 而后续方法setFacilities()和setPolicies()都需要通过resource的id字段来绑定数据
			 * 这里调用的add方法中实现了对模型实体id的赋值，也就是插入数据库中自动生成的id
			 * 所以resource被add()处理过，在执行add()后突然就有了id字段的值
			 * */
			resourceService.add(resource);
		} else {
			resourceService.update(resource);
		}
		resourceFacilityService.setFacilities(resource, facilities);
		resourcePolicyService.setPolicies(resource, policies);
		
		model.addAttribute("requestUrl", "listResource");
		
		return "redirect:listResource";
	}
	
	 @RequestMapping("deleteResource")
	 public String delete(Model model, int id) {
		 resourceService.delete(id);
		 
		 model.addAttribute("requestUrl", "listResource");
		 
		 return "redirect:listResource";
	 }
	
}