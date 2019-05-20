package pers.qyj.graduationpr.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import pers.qyj.graduationpr.pojo.Bedtype;
import pers.qyj.graduationpr.pojo.Roomtype;
import pers.qyj.graduationpr.pojo.Userinformation;
import pers.qyj.graduationpr.service.BedtypeService;
import pers.qyj.graduationpr.service.RoomtypeService;
import pers.qyj.graduationpr.util.MyUtil;

@Controller
@RequestMapping("config/resource")
public class RoomtypeController {
	@Autowired
	RoomtypeService roomtypeService;
	@Autowired
	BedtypeService bedtypeService;

	@RequestMapping("listRoomtype")
	public String list(Model model, @RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "10") int size) throws Exception {
		PageHelper.startPage(start, size, "name");
		List<Roomtype> roomtypes = roomtypeService.list();
		PageInfo<Roomtype> page = new PageInfo<>(roomtypes);
		model.addAttribute("page", page);

		model.addAttribute("requestUrl", "listRoomtype");

		return "config/resource/listRoomtype";
	}

	@RequestMapping("editRoomtype")
	public String list(Model model, @RequestParam(value = "name", defaultValue = "") String name) {
		String update = "add";
		Roomtype roomtype = new Roomtype();

		// 获取所有床型列表
		List<Bedtype> bedtypes = bedtypeService.list();
		model.addAttribute("bedtypes", bedtypes);

		if (!name.equals("")) {
			update = "update";
			roomtype = roomtypeService.get(name);
		}

		Subject subject = SecurityUtils.getSubject();
		subject.getSession().setAttribute("update", update);

		model.addAttribute("roomtype", roomtype);

		return "config/resource/editRoomtype";
	}

	@RequestMapping("updateRoomtype")
	public String update(Model model, Roomtype roomtype) {
		Subject subject = SecurityUtils.getSubject();
		String update = (String) subject.getSession().getAttribute("update");
		if (update.equals("add")) {
			roomtypeService.add(roomtype);
		} else {
			roomtypeService.update(roomtype);
		}

		model.addAttribute("requestUrl", "listRoomtype");

		return "redirect:listRoomtype";
	}

	@RequestMapping("addRoomtype")
	public String list(Model model, Roomtype roomtype) {
		return "redirect:editRoomtype";
	}

	@RequestMapping("deleteRoomtype")
	public String delete(Model model, String name) {
		roomtypeService.delete(name);

		model.addAttribute("requestUrl", "listRoomtype");

		return "redirect:listRoomtype";
	}

	@RequestMapping("rtImgUpload")
	@ResponseBody
	public String rtImgUpload(@RequestParam("file") MultipartFile file, String name) {
		try {
			String uplaodPath = "";
			if (MyUtil.isOSLinux()) {
				uplaodPath = "/usr/local/uploadFiles/";
			} else {
				uplaodPath = "D:/uploadFiles/";
			}
			final String namePath = "/roomtypeImg/";
			String fileName = System.currentTimeMillis() + file.getOriginalFilename();
			String filePath = namePath + fileName;
			String destFileName = uplaodPath + filePath;
			File destFile = new File(destFileName);
			destFile.getParentFile().mkdirs();
			file.transferTo(destFile);

			String oldfilePath = roomtypeService.get(name).getImgurl();
			File oldFile = new File(uplaodPath + oldfilePath);
			oldFile.delete();

			Roomtype roomtype = new Roomtype();
			roomtype.setName(name);
			roomtype.setImgurl(filePath);
			roomtypeService.update(roomtype);

			return filePath;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "-3";
		} catch (IOException e) {
			e.printStackTrace();
			return "-2";
		} catch (NullPointerException e) {
			e.printStackTrace();
			return "-1";
		}
	}

}