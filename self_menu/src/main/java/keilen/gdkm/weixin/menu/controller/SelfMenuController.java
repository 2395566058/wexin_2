package keilen.gdkm.weixin.menu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.util.JSONPObject;

import keilen.gdkm.weixin.menu.domain.SelfMenu;
import keilen.gdkm.weixin.menu.service.SelfMenuService;

@Controller
@RequestMapping("/kemao/menu")
public class SelfMenuController {

	@Autowired
	private SelfMenuService menuService;

	// 显示一个菜单修改的页面
	@GetMapping
	public ModelAndView index() {
		return new ModelAndView("/WEB-INF/views/menu/index.jsp");

	}

	@GetMapping(produces = "application/json") // 表明对外提供JSON数据
	@ResponseBody // 返回的对象，就是响应体
	public SelfMenu data() {
		SelfMenu a=menuService.getMenu();
		return a;

	}

	@PostMapping
	@ResponseBody
	// @RequestBody的作用：把整个请求体转换为Java对象
	public String save(@RequestBody SelfMenu selfMenu) {
		this.menuService.save(selfMenu);
		return "保存成功";
	}
}
