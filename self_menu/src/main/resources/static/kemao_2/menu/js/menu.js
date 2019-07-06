let menuData ;
function start(){
	menuData= {
		    "subMenus": [
		        {
		        	"id": null,
		            "name": "菜单",
		            "type": null,
		            "url": null,
		            "mediaId": null,
		            "key": null,
		            "show": false,
		            "active": false,
		            "subMenus": []
		        },
		        {
		        	"id": null,
		            "name": "发图",
		            "type": null,
		            "url": null,
		            "mediaId": null,
		            "key": null,
		            "show": false,
		            "active": false,
		            "subMenus": []
		        }]
		};
		initMenus();
}

function initMenus(){
	let menus = new Vue({
			el: ".main-container",
		    data: menuData,
		    methods: {
		        toggleSubMenus(m) {
		            this.hideLevelTwo();
		            m.show = !m.show;
		        },
		        activeButton(m, event) {
		            this.subMenus.forEach((x) => {
		                if(x != m)
		                {
		                    x.active = false;
		                }
		                if (x.subMenus) {
		                    x.subMenus.forEach((y) => {
		                        if(y != m
		                )
		                    {
		                        y.active = false;
		                    }
		                })
		                    ;
		                }
		        	});
		            m.active = true;
		        },
		        hideLevelTwo() {
		            this.subMenus.forEach((x) => {
		                x.show = false;
		        	});
		        },
		        addNewButton(menu, event) {
		        	let emptyMenu = {
		        		    "id": null,
		        		    "name": "菜单",
		        		    "type": null,
		        		    "url": null,
		        		    "mediaId": null,
		        		    "key": null,
		        		    "show": false,
		        		    "active": false,
		        		    "subMenus": []
		        		};
		            menu.subMenus.push(emptyMenu);
		            this.activeButton(emptyMenu);
		        },
		        current(){
		        	for( let i = 0; i < this.subMenus.length; i++ ){
		        		let x = this.subMenus[i];
		        		if(x.active === true){
		        			return x;
		        		}
		        		for( let j = 0; j < x.subMenus.length; j++ ){
		        			let y = x.subMenus[j];
		        			if(y.active === true){
		            			return y;
		            		}
		        		}
		        	}
		        	return {};
		        },
		    	selectMenu(){
		    	}
		    },
		    computed: {
		    	selectedMenu(){
		    		return this.current();
		    	}
		    }
		});
}

function saveMenus(){
	let json = JSON.stringify(menuData);
	$.ajax({
		url: "/kemao/menu",
		method: "post",
		contentType: "application/json; charset=utf-8",
		data: json,
		dataType: "json",
		success: function (responseData) {
            console.log(responseData);
        },
        error: function (responseData) {
            console.error(responseData);
        }
	});
}


