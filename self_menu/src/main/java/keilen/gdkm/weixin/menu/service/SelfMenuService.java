package keilen.gdkm.weixin.menu.service;

import keilen.gdkm.weixin.menu.domain.SelfMenu;

public interface SelfMenuService {

	SelfMenu getMenu();

	void save(SelfMenu selfMenu);

}
