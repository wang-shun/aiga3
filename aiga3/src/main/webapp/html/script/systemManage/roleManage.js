define(function(require,exports,module){
	// 路径重命名
	var pathAlias = "systemManage/roleManage/";

	// 角色管理接口查询
	srvMap.add("getRoleinfoList", pathAlias + "getRoleinfoList.json", "sys/role/list");
	// 获取单个角色信息
	srvMap.add("getRoleinfo", pathAlias + "getRoleinfo.json", "sys/role/listA");
	// 新增角色
	srvMap.add("addRoleinfo", pathAlias + "retMessage.json", "sys/role/save");
	// 修改角色
	srvMap.add("updateRoleinfo", pathAlias + "retMessage.json", "sys/role/update");
	// 删除角色
	srvMap.add("delRoleinfo", pathAlias + "retMessage.json", "sys/role/del");

	// 模板对象
    var Tpl = {
        getRoleinfoList: require('tpl/systemManage/roleManage/getRoleinfoList.tpl'),
        getRoleinfo: require('tpl/systemManage/roleManage/getRoleinfo.tpl')
    };

    // 容器对象
    var Dom = {
        getRoleinfoList: '#JS_getRoleinfoList',
        manageRoleinfo: '#JS_manageRoleinfo'
    };

    var Data = {
    	roleId: null,
    	setPageType:function(type){
    		return {
    			"data":{
    				"type":type
    			}
    		}
    	}
    }

	var Query = {
		init: function(){
			this._render();
		},
		_render: function() {
			this.getRoleinfoList();
		},
		// 角色列表
		getRoleinfoList: function(){
			var self = this;
			Rose.ajax.getJson(srvMap.get('getRoleinfoList'), '', function(json, status) {
				if(status) {
					var template = Handlebars.compile(Tpl.getRoleinfoList);
            		$(Dom.getRoleinfoList).html(template(json.data));

            		// 添加、删除
            		self.addRoleinfo();
            		self.delRoleinfo();

            		// 点击选中行
				    self.eventClickChecked($(Dom.getRoleinfoList));

				    // 绑定双击当前行事件
				    self.eventDClickCallback($(Dom.getRoleinfoList),function(){
			        	self.getRoleinfo();
				    })

				    //设置分页
				    self.initPaging($(Dom.getRoleinfoList),8)

				}
	  		});
		},
		// 获取角色信息
		getRoleinfo: function(){
			var self = this;
			var _data = self.getCheckedRow();
			var cmd = 'roleId='+_data.roleId;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.getJson(srvMap.get('getRoleinfo'), cmd, function(json, status) {
				if(status) {
					window.XMS.msgbox.hide();
					$(Dom.manageRoleinfo).removeClass('hide');
					json.data["type"]="修改角色";
					var template = Handlebars.compile(Tpl.getRoleinfo);
            		$(Dom.manageRoleinfo).html(template(json.data))
            		// 提交保存
            		self.updateRoleinfo();
				}
  			});

		},
		// 添加角色
		addRoleinfo: function(){
			var self = this;
			var _domAdd = $(Dom.getRoleinfoList).find("[name='add']");
			_domAdd.bind('click', function() {
				$(Dom.manageRoleinfo).removeClass('hide');
				var json = Data.setPageType("添加角色")
		        var template = Handlebars.compile(Tpl.getRoleinfo);
            	$(Dom.manageRoleinfo).html(template(json.data))
            	// 提交保存
            	self.updateRoleinfo();
			});

		},
		// 保存角色
		updateRoleinfo:function(){
			var self = this;
    		var _domSave = $(Dom.manageRoleinfo).find("[name='save']");
    		_domSave.bind('click', function() {
				var cmd = $(this).parents("form").serialize();
				XMS.msgbox.show('数据加载中，请稍候...', 'loading');
				Rose.ajax.getJson(srvMap.get('updateRoleinfo'), cmd, function(json, status) {
					if(status) {
						window.XMS.msgbox.show('保存成功！', 'success', 2000)
						setTimeout(function(){
							self.getRoleinfoList();
						},1000)
					}
	  			});
  			});
  			/*var _domReset = $(Dom.manageRoleinfo).find("[name='reset']");
  			_domReset.bind('click', function() {

  			});*/

		},
		// 删除角色
		delRoleinfo: function(){
			var self = this;
			var _domDel = $(Dom.getRoleinfoList).find("[name='del']");
			_domDel.bind('click', function() {
				var _data = self.getCheckedRow();
				if(_data){
					var cmd = 'roleId='+_data.roleId;
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					Rose.ajax.getJson(srvMap.get('delRoleinfo'), cmd, function(json, status) {
						if(status) {
							window.XMS.msgbox.show('删除成功！', 'success', 2000)
							setTimeout(function(){
								self.getRoleinfoList();
								$(Dom.manageRoleinfo).addClass('hide');
							},1000)
						}
		  			});
				}
			});
		},
		// 获取列表当前选中行
		getCheckedRow : function(){
			var _obj = $(Dom.getRoleinfoList).find("input[type='radio']:checked").parents("tr");
			var _id = _obj.find("input[name='roleId']")
			var data = {
		        roleId: _id.val()
		    }
		    if(_id.length==0){
		    	window.XMS.msgbox.show('请先选择一个组织结构！', 'info', 2000);
		    	return;
		    }
		    return data;
		},
		// 事件：单机选中当前行
		eventClickChecked:function(obj,callback){
			obj.find('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
			      checkboxClass: 'icheckbox_square-blue',
			      radioClass: 'iradio_square-blue'
			});
			obj.find("tr").bind('click', function(event) {
		        $(this).find('.minimal').iCheck('check');
	        	if (callback) {
					callback();
				}
		    });
		},
		// 事件：双击绑定事件
		eventDClickCallback:function(obj,callback){
			obj.find("tr").bind('dblclick ', function(event) {
		        	if (callback) {
						callback();
					}
		    });
		},
		// 事件：双击绑定事件
		initPaging:function(obj,length){
			obj.find("table").DataTable({
			  "iDisplayLength":length,
	          "paging": true,
	          "lengthChange": false,
	          "searching": false,
	          "ordering": false,
	          "info": true,
	          "autoWidth": false
	        });
		}
	};
	module.exports = Query;
});

