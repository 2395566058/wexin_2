package keilen.gdkm.weixin.menu.controller;

import keilen.gdkm.weixin.menu.domain.SelfMenu;
import keilen.gdkm.weixin.menu.service.SelfMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SelfMenuController {

	@Autowired
	private SelfMenuService menuService;

	@RequestMapping(value="/kemao/menu",method=RequestMethod.GET)
	public ModelAndView index() {
		return new ModelAndView("/WEB-INF/views/menu/index.jsp");
	}

	@RequestMapping(value="/kemao/menu",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public SelfMenu data() {
		return menuService.getMenu();
	}

	@RequestMapping(value="/kemao/menu",method=RequestMethod.POST)
	@ResponseBody
	public String save(@RequestBody SelfMenu selfMenu) {
		this.menuService.save(selfMenu);
		return "保存成功";
	}
}
