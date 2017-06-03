var system = system || {};

/**
 * 更改easyui加载panel时的提示文字
 * 
 * @author zhangkeqi
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.panel.defaults, {
	loadingMessage : '加载中....'
});

/**
 * 更改easyui加载grid时的提示文字
 * 
 * @author zhangkeqi
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.datagrid.defaults, {
	loadMsg : '数据加载中....'
});

/**
 * panel关闭时回收内存，主要用于layout使用iframe嵌入网页时的内存泄漏问题
 * 
 * 
 * @requires jQuery,EasyUI
 * 
 */
$.extend($.fn.panel.defaults, {
	onBeforeDestroy : function() {
		var frame = $('iframe', this);
		try {
			if (frame.length > 0) {
				for (var i = 0; i < frame.length; i++) {
					frame[i].src = '';
					frame[i].contentWindow.document.write('');
					frame[i].contentWindow.close();
				}
				frame.remove();
				if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
					try {
						CollectGarbage();
					} catch (e) {
					}
				}
			}
		} catch (e) {
		}
	}
});

/**
 * 防止panel/window/dialog组件超出浏览器边界
 * 
 * @author zhangkeqi
 * 
 * @requires jQuery,EasyUI
 */
system.onMove = {
	onMove : function(left, top) {
		var l = left;
		var t = top;
		if (l < 1) {
			l = 1;
		}
		if (t < 1) {
			t = 1;
		}
		var width = parseInt($(this).parent().css('width')) + 14;
		var height = parseInt($(this).parent().css('height')) + 14;
		var right = l + width;
		var buttom = t + height;
		var browserWidth = $(window).width();
		var browserHeight = $(window).height();
		if (right > browserWidth) {
			l = browserWidth - width;
		}
		if (buttom > browserHeight) {
			t = browserHeight - height;
		}
		$(this).parent().css({/* 修正面板位置 */
			left : l,
			top : t
		});
	}
};
$.extend($.fn.dialog.defaults, system.onMove);
$.extend($.fn.window.defaults, system.onMove);
$.extend($.fn.panel.defaults, system.onMove);

/**
 * 
 * 通用错误提示
 * 
 * 用于datagrid/treegrid/tree/combogrid/combobox/form加载数据出错时的操作
 * 
 * @author zhangkeqi
 * 
 * @requires jQuery,EasyUI
 */
system.onLoadError = {
	onLoadError : function(XMLHttpRequest) {
		if (parent.$ && parent.$.messager) {
			parent.$.messager.progress('close');
			parent.$.messager.alert('错误', XMLHttpRequest.responseText);
		} else {
			$.messager.progress('close');
			$.messager.alert('错误', XMLHttpRequest.responseText);
		}
	}
};
$.extend($.fn.datagrid.defaults, system.onLoadError);
$.extend($.fn.treegrid.defaults, system.onLoadError);
$.extend($.fn.tree.defaults, system.onLoadError);
$.extend($.fn.combogrid.defaults, system.onLoadError);
$.extend($.fn.combobox.defaults, system.onLoadError);
$.extend($.fn.form.defaults, system.onLoadError);

/**
 * 扩展combobox在自动补全模式时，检查用户输入的字符是否存在于下拉框中，如果不存在则清空用户输入
 * 
 * @author zhangkeqi
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.combobox.defaults, {
	onShowPanel : function() {
		var _options = $(this).combobox('options');
		if (_options.mode == 'remote') {/* 如果是自动补全模式 */
			var _value = $(this).combobox('textbox').val();
			var _combobox = $(this);
			if (_value.length > 0) {
				$.post(_options.url, {
					q : _value
				}, function(result) {
					if (result && result.length > 0) {
						_combobox.combobox('loadData', result);
					}
				}, 'json');
			}
		}
	},
	onHidePanel : function() {
		var _options = $(this).combobox('options');
		if (_options.mode == 'remote') {/* 如果是自动补全模式 */
			var _data = $(this).combobox('getData');/* 下拉框所有选项 */
			var _value = $(this).combobox('getValue');/* 用户输入的值 */
			var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */
			for (var i = 0; i < _data.length; i++) {
				if (_data[i][_options.valueField] == _value) {
					_b = true;
				}
			}
			if (!_b) {/* 如果在下拉列表中没找到用户输入的字符 */
				$(this).combobox('setValue', '');
			}
		}
	}
});

/**
 * 扩展combogrid在自动补全模式时，检查用户输入的字符是否存在于下拉框中，如果不存在则清空用户输入
 * 
 * @author zhangkeqi
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.combogrid.defaults, {
	onShowPanel : function() {
		var _options = $(this).combogrid('options');
		if (_options.mode == 'remote') {/* 如果是自动补全模式 */
			var _value = $(this).combogrid('textbox').val();
			if (_value.length > 0) {
				$(this).combogrid('grid').datagrid("load", {
					q : _value
				});
			}
		}
	},
	onHidePanel : function() {
		var _options = $(this).combogrid('options');
		if (_options.mode == 'remote') {/* 如果是自动补全模式 */
			var _data = $(this).combogrid('grid').datagrid('getData').rows;/* 下拉框所有选项 */
			var _value = $(this).combogrid('getValue');/* 用户输入的值 */
			var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */
			for (var i = 0; i < _data.length; i++) {
				if (_data[i][_options.idField] == _value) {
					_b = true;
				}
			}
			if (!_b) {/* 如果在下拉列表中没找到用户输入的字符 */
				$(this).combogrid('setValue', '');
			}
		}
	}
});

/**
 * 扩展validatebox，添加新的验证功能
 * 
 * @author zhangkeqi
 * 
 * @requires jQuery,EasyUI
 */
var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"};  

/**
 * 验证身份证
 * @param sId
 * @returns
 */
function isCardID(sId){   
    var iSum=0 ;  
    var info="" ;  
    if(!/^\d{17}(\d|x)$/i.test(sId)) return "你输入的身份证长度或格式错误";   
    sId=sId.replace(/x$/i,"a");   
    if(aCity[parseInt(sId.substr(0,2))]==null) return "你的身份证地区非法";   
    sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2));   
    var d=new Date(sBirthday.replace(/-/g,"/")) ;  
    if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate()))return "身份证上的出生日期非法";   
    for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;  
    if(iSum%11!=1) return "你输入的身份证号非法";   
    return true;//aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"男":"女")   
}   
$.extend($.fn.validatebox.defaults.rules, {
	idcared: {     
        validator: function(value,param){    
            var flag= isCardID(value);  
            return flag==true?true:false;    
        },     
        message: '不是有效的身份证号码'    
    },
    minLength : { // 判断最小长度
        validator : function(value, param) {
            return value.length >= param[0];
        },
        message : "最少输入 {0} 个字符。"
    },
    length:{validator:function(value,param){
        var len=$.trim(value).length;
            return len>=param[0]&&len<=param[1];
        },
            message:"输入内容长度必须介于{0}和{1}之间."
        },
    phone : {// 验证电话号码
        validator : function(value) {
            return /^(((d{2,3}))|(d{3}-))?((0d{2,3})|0d{2,3}-)?[1-9]d{6,7}(-d{1,4})?$/i.test(value);
        },
        message : "格式不正确,请使用下面格式:020-88888888"
    },
    mobile : {// 验证手机号码
        validator : function(value) {
            return /^(13|15|18)d{9}$/i.test(value);
        },
        message : "手机号码格式不正确"
    },
    intOrFloat : {// 验证整数或小数
        validator : function(value) {
            return /^d+(.d+)?$/i.test(value);
        },
        message : "请输入数字，并确保格式正确"
    },
    currency : {// 验证货币
        validator : function(value) {
            return /^d+(.d+)?$/i.test(value);
        },
        message : "货币格式不正确"
    },
    qq : {// 验证QQ,从10000开始
        validator : function(value) {
            return /^[1-9]d{4,9}$/i.test(value);
        },
        message : "QQ号码格式不正确"
    },
    integer : {// 验证整数
        validator : function(value) {
            return /^[+]?[1-9]+d*$/i.test(value);
        },
        message : "请输入整数"
    },
    chinese : {// 验证中文
        validator : function(value) {
            return /^[u0391-uFFE5]+$/i.test(value);
        },
        message : "请输入中文"
    },
    noChinese : {// 验证非中文
        validator : function(value) {
            return /^[u4e00-u9fa5]+$/i.test(value);
        },
        message : "请输入非中文"
    },
    english : {// 验证英语
        validator : function(value) {
            return /^[A-Za-z]+$/i.test(value);
        },
        message : "请输入英文"
    },
    numberAndPoint : {// 验证数字和小数点
        validator : function(value) {
            return /^[1-9]\d*\,\d*|[1-9]\d*$/i.test(value);
        },
        message : "请输入数字和小数点"
    },
    unnormal : {// 验证是否包含空格和非法字符
        validator : function(value) {
            return /.+/i.test(value);
        },
        message : "输入值不能为空和包含其他非法字符"
    },
    username : {// 验证用户名
        validator : function(value) {
            return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
        },
        message : "用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）"
    },
    faxno : {// 验证传真
        validator : function(value) {
//            return /^[+]{0,1}(d){1,3}[ ]?([-]?((d)|[ ]){1,12})+$/i.test(value);
            return /^(((d{2,3}))|(d{3}-))?((0d{2,3})|0d{2,3}-)?[1-9]d{6,7}(-d{1,4})?$/i.test(value);
        },
        message : "传真号码不正确"
    },
    zip : {// 验证邮政编码
        validator : function(value) {
            return /^[1-9]d{5}$/i.test(value);
        },
        message : "邮政编码格式不正确"
    },
    ip : {// 验证IP地址
        validator : function(value) {
            return /d+.d+.d+.d+/i.test(value);
        },
        message : "IP地址格式不正确"
    },
    name : {// 验证姓名，可以是中文或英文
            validator : function(value) {
                return /^[u0391-uFFE5]+$/i.test(value)|/^w+[ws]+w+$/i.test(value);
            },
            message : "请输入姓名"
    },
    carNo:{
        validator : function(value){
            return /^[u4E00-u9FA5][da-zA-Z]{6}$/.test(value);
        },
        message : "车牌号码无效（例：粤J12350）"
    },
    carenergin:{
        validator : function(value){
            return /^[a-zA-Z0-9]{16}$/.test(value);
        },
        message : "发动机型号无效(例：FG6H012345654584)"
    },
    email:{
        validator : function(value){
        return /^w+([-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*$/.test(value);
    },
    message : "请输入有效的电子邮件账号(例：abc@126.com)"   
    },
    msn:{
        validator : function(value){
        return /^w+([-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*$/.test(value);
    },
    message : "请输入有效的msn账号(例：abc@hotnail(msn/live).com)"
    },
    same:{
        validator : function(value, param){
        	return value == $(param[0]).val();
        },
        message : "两次输入的密码不一致！"
    }
});


/**
 * 扩展tree和combotree，使其支持平滑数据格式
 * 
 * @author zhangkeqi
 * 
 * @requires jQuery,EasyUI
 * 
 */
system.loadFilter = {
	loadFilter : function(data, parent) {
		var opt = $(this).data().tree.options;
		var idField, textField, parentField,nodeIconClsAtrribute,checkedField;
		nodeIconClsAtrribute = opt.nodeIconClsAtrribute || 'iconCls';
		checkedField = opt.checkedField || 'checked';
		for (i = 0, l = data.length; i < l; i++) {
			if(data[i][checkedField]) {
				data[i]['checked'] = data[i][checkedField];
			}
		}
		if(nodeIconClsAtrribute != 'iconCls') {
			for (i = 0, l = data.length; i < l; i++) {
				data[i]['iconCls'] = data[i][nodeIconClsAtrribute];
			}
		}
		if (opt.parentField) {
			idField = opt.idField || 'id';
			textField = opt.textField || 'text';
			parentField = opt.parentField || 'parentId';
			state = opt.state || 'closed';
			var i, l, treeData = [], tmpMap = [];
			for (i = 0, l = data.length; i < l; i++) {
				data[i]['state'] = data[i]['state'] || state;
				tmpMap[data[i][idField]] = data[i];
			}
			for (i = 0, l = data.length; i < l; i++) {
				if (tmpMap[data[i][parentField]] && data[i][idField] != data[i][parentField]) {
					if (!tmpMap[data[i][parentField]]['children'])
						tmpMap[data[i][parentField]]['children'] = [];
					data[i]['text'] = data[i][textField];
					tmpMap[data[i][parentField]]['children'].push(data[i]);
				} else {
					data[i]['text'] = data[i][textField];
					treeData.push(data[i]);
				}
			}
			return treeData;
		}
		return data;
	},
	onClick : function(node) {
		var treeOpts=$(this).tree('options');
		var valueField = treeOpts.valueField || 'id';
		var textField = treeOpts.textField || 'text';
		var linkFields = treeOpts.linkFields;
		var linkFieldsValue = [];
		var combotreeId = treeOpts.combotreeId;
		var vv=[],ss=[];
		if(treeOpts.multiple){
			var chkdNode=$(this).tree("getChecked");
			for(var i=0;i<chkdNode.length;i++){
				vv.push(chkdNode[i][valueField]);
				ss.push(chkdNode[i][textField]);
				
				for(var k in linkFields) {
					linkFieldsValue[k].push(chkdNode[i][linkFields[k]]);
				}
			}
		}else{
			var slctNode=$(this).tree("getSelected");
			if(slctNode){
				vv.push(slctNode[valueField]);
				ss.push(slctNode[textField]);
				for(var k in linkFields) {
					linkFieldsValue[k] = slctNode[linkFields[k]];
				}
			}
		}
		
		if(combotreeId) {
			$('#'+combotreeId).combotree("setValues",vv);
			$('#'+combotreeId).combotree("setText",ss.join(treeOpts.separator));
		}
		
		if(linkFields) {
			if(treeOpts.multiple){
//				for(var k in linkFields) {
//					$('#'+k).val(linkFieldsValue[k].join(treeOpts.separator));
//				}
			} else {
				for(var k in linkFields) {
					$('#'+k).val(linkFieldsValue[k]);
				}
			}
		}
	}
};
$.extend($.fn.combotree.defaults, system.loadFilter,{
	onLoadSuccess : function(row, data) {
//			var _14=$(this).tree('options');
//			var combotreeId = _14.combotreeId;
//			var vv = $('#'+combotreeId).combotree("getValues");
//			$('#'+combotreeId).combotree("setValues",['4028778146b361220146b3837c4f0002']);
		}
	}
);
$.extend($.fn.tree.defaults, system.loadFilter,{
	addChecked:[],
	delChecked:[],
	onCheck : function(n,c) {
		var id = n.id;
		var hasDeleted = false;
		var hasAdded = false;
		if(c === true) {
			for(var i in $(this).tree.defaults.delChecked) {
				if($(this).tree.defaults.delChecked[i].id === id) {
					$(this).tree.defaults.delChecked.splice(i,1);
					hasDeleted = true;
					break;
				}
			}
			if(!hasDeleted) {
				$(this).tree.defaults.addChecked.push(n);
			}
		}
		if(c === false) {
			for(var i in $(this).tree.defaults.addChecked) {
				if($(this).tree.defaults.addChecked[i].id === id) {
					$(this).tree.defaults.addChecked.splice(i,1);
					hasAdded = true;
					break;
				}
			}
			if(!hasAdded) {
				$(this).tree.defaults.delChecked.push(n);
			}
		}
	}
});
$.extend($.fn.tree.methods,{
	getAddChecked : function() {
		return $(this).tree.defaults.addChecked;
	},
	getDeleteChecked : function() {
		return $(this).tree.defaults.delChecked;
	}
});

/**
 * 扩展treegrid，使其支持平滑数据格式
 * 
 * @author zhangkeqi
 * 
 * @requires jQuery,EasyUI
 * 
 */
$.extend($.fn.treegrid.defaults, {
	addChecked:[],
	delChecked:[],
	defaultChecked:[],
	defaultCheck:false,
	loadFilter : function(data, parentId) {
		if(parentId) {
			var parentNode = $(this).treegrid('find',parentId);
			parentNode['state']='open';
		}
		var opt = $(this).data().treegrid.options;
		var idField, treeField, parentField, state;
		nodeIconClsAtrribute = opt.nodeIconClsAtrribute || 'iconCls';
		if(nodeIconClsAtrribute != 'iconCls') {
			for (i = 0, l = data.length; i < l; i++) {
				data[i]['iconCls'] = data[i][nodeIconClsAtrribute];
			}
		}
		if (opt.parentField) {
			idField = opt.idField || 'id';
			treeField = opt.textField || 'text';
			parentField = opt.parentField || 'parentId';
			state = opt.state || 'closed';
			var i, l, treeData = [], tmpMap = [];
			for (i = 0, l = data.length; i < l; i++) {
				data[i]['state'] = data[i]['state'] || state;
				tmpMap[data[i][idField]] = data[i];
			}
			for (i = 0, l = data.length; i < l; i++) {
				//tmpMap[data[i][parentField]] 当前结点的父节点
				if (tmpMap[data[i][parentField]] && data[i][idField] != data[i][parentField]) {
					if (!tmpMap[data[i][parentField]]['children'])
						tmpMap[data[i][parentField]]['children'] = [];
					data[i]['text'] = data[i][treeField];
					//以下代码的作用是找到当前节点的父节点，并将当前节点追加到父节点的子节点列表中
					tmpMap[data[i][parentField]]['children'].push(data[i]);
				} else {
					data[i]['text'] = data[i][treeField];
					treeData.push(data[i]);
				}
			}
			return treeData;
		}
		return data;
	},
	onCheck : function(rowIndex,rowData) {
		if($(this).treegrid.defaults.defaultCheck === true) {
			return false;
		} 
		var n,c;
		n=rowIndex;
		c=true;
		var id = n.id;
		var hasDeleted = false;
		var hasAdded = false;
		if(c === true) {
			for(var i in $(this).treegrid.defaults.delChecked) {
				if($(this).treegrid.defaults.delChecked[i].id === id) {
					$(this).treegrid.defaults.delChecked.splice(i,1);
					hasDeleted = true;
					break;
				}
			}
			if(!hasDeleted) {
				$(this).treegrid.defaults.addChecked.push(n);
			}
		}
		if(c === false) {
			for(var i in $(this).treegrid.defaults.addChecked) {
				if($(this).treegrid.defaults.addChecked[i].id === id) {
					$(this).treegrid.defaults.addChecked.splice(i,1);
					hasAdded = true;
					break;
				}
			}
			if(!hasAdded) {
				$(this).treegrid.defaults.delChecked.push(n);
			}
		}
	},
	onUncheck : function(rowIndex,rowData) {
		if($(this).treegrid.defaults.defaultCheck === true) {
			return false;
		} 
		var n,c;
		n=rowIndex;
		c=false;
		var id = n.id;
		var hasDeleted = false;
		var hasAdded = false;
		if(c === true) {
			for(var i in $(this).treegrid.defaults.delChecked) {
				if($(this).treegrid.defaults.delChecked[i].id === id) {
					$(this).treegrid.defaults.delChecked.splice(i,1);
					hasDeleted = true;
					break;
				}
			}
			if(!hasDeleted) {
				$(this).treegrid.defaults.addChecked.push(n);
			}
		}
		if(c === false) {
			for(var i in $(this).treegrid.defaults.addChecked) {
				if($(this).treegrid.defaults.addChecked[i].id === id) {
					$(this).treegrid.defaults.addChecked.splice(i,1);
					hasAdded = true;
					break;
				}
			}
			if(!hasAdded) {
				$(this).treegrid.defaults.delChecked.push(n);
			}
		}
	},
	onCheckAll : function(rows) {
		var hasDeleted = false;
		for(var rowIndex in rows) {
			var hasDeleted = false;
			for(var i in $(this).treegrid.defaults.defaultChecked) {
				if(rows[rowIndex].id === $(this).treegrid.defaults.defaultChecked[i].id) {
					hasDeleted = true;
					break;
				}
			}
			for(var i in $(this).treegrid.defaults.delChecked) {
				if($(this).treegrid.defaults.delChecked[i].id === rows[rowIndex].id) {
					$(this).treegrid.defaults.delChecked.splice(i,1);
					hasDeleted = true;
					break;
				}
			}
			if(!hasDeleted) {
				$(this).treegrid.defaults.addChecked.push(rows[rowIndex]);
			}
		}
	},
	onUncheckAll : function(rows) {
		var hasAdded = false;
		for(var rowIndex in rows) {
			for(var i in $(this).treegrid.defaults.addChecked) {
				if($(this).treegrid.defaults.addChecked[i].id === rows[rowIndex].id) {
					$(this).treegrid.defaults.addChecked.splice(i,1);
					hasAdded = true;
					break;
				}
			}
			if(!hasAdded) {
				$(this).treegrid.defaults.delChecked.push(rows[rowIndex]);
			}
		}
	} ,
	onBeforeExpand : function(row) {
//		var isLeaf = node.isLeaf || true;
//		if(isLeaf === true) {
//			node.state = 'open';
//			return false;
//		}
	}
});


$.extend($.fn.treegrid.methods,{
	getAddChecked : function() {
		return $(this).treegrid.defaults.addChecked;
	},
	getDeleteChecked : function() {
		return $(this).treegrid.defaults.delChecked;
	},
	getDefaultChecked : function() {
		return $(this).treegrid.defaults.defaultChecked;
	},
	clearChecked : function() {
		$(this).treegrid.defaults.addChecked = [];
		$(this).treegrid.defaults.delChecked = [];
		$(this).treegrid.defaults.defaultChecked = [];
	},
	checkRowDefault : function(jq,id) {
		jq.each(function(){
			$(this).treegrid.defaults.defaultCheck = true;
			$(this).treegrid('checkRow',id);
			$(this).treegrid.defaults.defaultCheck = false;
		});
	},
	uncheckRowDefault : function(jq,id) {
		jq.each(function(){
			$(this).treegrid.defaults.defaultCheck = true;
			$(this).treegrid('uncheckRow',id);
			$(this).treegrid.defaults.defaultCheck = false;
		});
	},
	addDefaultCheck : function(jq,item) {
		jq.each(function(){
			if(item) {
				$(this).treegrid.defaults.defaultChecked.push(item);
			}
		});
	}
});


/**
 * 扩展datagrid，增加差量增加与删除项
 * 
 * @author zhangkeqi
 * 
 * @requires jQuery,EasyUI
 * 
 */
$.extend($.fn.datagrid.defaults, {
	addChecked:[],
	delChecked:[],
	defaultChecked:[],
	defaultCheck:false,
	onCheck : function(rowIndex,rowData) {
		if($(this).datagrid.defaults.defaultCheck === true) {
			return false;
		} 
		var n,c;
		//注意在datagrid中onCheck传入的第一个参数是序号，而在treegrid中传过来的是序号对应的那一行的对象
		n=$(this).datagrid('getRows')[rowIndex];
		c=true;
		var id = n.id;
		var hasDeleted = false;
		var hasAdded = false;
		if(c === true) {
			for(var i in $(this).datagrid.defaults.delChecked) {
				if($(this).datagrid.defaults.delChecked[i].id === id) {
					$(this).datagrid.defaults.delChecked.splice(i,1);
					hasDeleted = true;
					break;
				}
			}
			if(!hasDeleted) {
				$(this).datagrid.defaults.addChecked.push(n);
			}
		}
		if(c === false) {
			for(var i in $(this).datagrid.defaults.addChecked) {
				if($(this).datagrid.defaults.addChecked[i].id === id) {
					$(this).datagrid.defaults.addChecked.splice(i,1);
					hasAdded = true;
					break;
				}
			}
			if(!hasAdded) {
				$(this).datagrid.defaults.delChecked.push(n);
			}
		}
	},
	onUncheck : function(rowIndex,rowData) {
		if($(this).datagrid.defaults.defaultCheck === true) {
			return false;
		} 
		var n,c;
		n=$(this).datagrid('getRows')[rowIndex];
		c=false;
		var id = n.id;
		var hasDeleted = false;
		var hasAdded = false;
		if(c === true) {
			for(var i in $(this).datagrid.defaults.delChecked) {
				if($(this).datagrid.defaults.delChecked[i].id === id) {
					$(this).datagrid.defaults.delChecked.splice(i,1);
					hasDeleted = true;
					break;
				}
			}
			if(!hasDeleted) {
				$(this).datagrid.defaults.addChecked.push(n);
			}
		}
		if(c === false) {
			for(var i in $(this).datagrid.defaults.addChecked) {
				if($(this).datagrid.defaults.addChecked[i].id === id) {
					$(this).datagrid.defaults.addChecked.splice(i,1);
					hasAdded = true;
					break;
				}
			}
			if(!hasAdded) {
				$(this).datagrid.defaults.delChecked.push(n);
			}
		}
	},
	onCheckAll : function(rows) {
		var hasDeleted = false;
		for(var rowIndex in rows) {
			var hasDeleted = false;
			for(var i in $(this).datagrid.defaults.defaultChecked) {
				if(rows[rowIndex].id === $(this).datagrid.defaults.defaultChecked[i].id) {
					hasDeleted = true;
					break;
				}
			}
			for(var i in $(this).datagrid.defaults.delChecked) {
				if($(this).datagrid.defaults.delChecked[i].id === rows[rowIndex].id) {
					$(this).datagrid.defaults.delChecked.splice(i,1);
					hasDeleted = true;
					break;
				}
			}
			if(!hasDeleted) {
				$(this).datagrid.defaults.addChecked.push(rows[rowIndex]);
			}
		}
	},
	onUncheckAll : function(rows) {
		var hasAdded = false;
		for(var rowIndex in rows) {
			for(var i in $(this).datagrid.defaults.addChecked) {
				if($(this).datagrid.defaults.addChecked[i].id === rows[rowIndex].id) {
					$(this).datagrid.defaults.addChecked.splice(i,1);
					hasAdded = true;
					break;
				}
			}
			if(!hasAdded) {
				$(this).datagrid.defaults.delChecked.push(rows[rowIndex]);
			}
		}
	}
});



$.extend($.fn.datagrid.methods,{
	getAddChecked : function() {
		return $(this).datagrid.defaults.addChecked;
	},
	getDeleteChecked : function() {
		return $(this).datagrid.defaults.delChecked;
	},
	getDefaultChecked : function() {
		return $(this).datagrid.defaults.defaultChecked;
	},
	clearChecked : function() {
		jq.each(function(){
			$(this).datagrid.defaults.addChecked = [];
			$(this).datagrid.defaults.delChecked = [];
			$(this).datagrid.defaults.defaultChecked = [];
		});
	},
	checkRowDefault : function(jq,index) {
		jq.each(function(){
			$(this).datagrid.defaults.defaultCheck = true;
			$(this).datagrid('checkRow',index);
			$(this).datagrid.defaults.defaultCheck = false;
		});
	},
	uncheckRowDefault : function(jq,index) {
		//这里将方法写到each里是有原因的。
		jq.each(function(){
			$(this).datagrid.defaults.defaultCheck = true;
			$(this).datagrid('uncheckRow',index);
			$(this).datagrid.defaults.defaultCheck = false;
		});
	},
	addDefaultCheck : function(jq,item) {
		if(item) {
			$(this).datagrid.defaults.defaultChecked.push(item);
		}
	}
});

/**
 * 创建一个模式化的dialog
 * 
 * @author zhangkeqi
 * 
 * @requires jQuery,EasyUI
 * 
 */
system.modalDialog = function(options) {
	var opts = $.extend({
		title : '&nbsp;',
		width : 640,
		height : 480,
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		}
	}, options);
	opts.modal = true;// 强制此dialog为模式化，无视传递过来的modal参数
	if (options.url) {
		opts.content = '<iframe id="" src="' + options.url + '" allowTransparency="true" scrolling="auto" width="100%" height="98%" frameBorder="0" name=""></iframe>';
	}
	return $('<div/>').dialog(opts);
};

/**
 * 等同于原form的load方法，但是这个方法支持{data:{name:''}}形式的对象赋值
 */
$.extend($.fn.form.methods, {
	loadData : function(jq, data) {
		return jq.each(function() {
			load(this, data);
		});

		function load(target, data) {
			if (!$.data(target, 'form')) {
				$.data(target, 'form', {
					options : $.extend({}, $.fn.form.defaults)
				});
			}
			var opts = $.data(target, 'form').options;

			if (typeof data == 'string') {
				var param = {};
				if (opts.onBeforeLoad.call(target, param) == false)
					return;

				$.ajax({
					url : data,
					data : param,
					dataType : 'json',
					success : function(data) {
						_load(data);
					},
					error : function() {
						opts.onLoadError.apply(target, arguments);
					}
				});
			} else {
				_load(data);
			}
			function _load(data) {
				var form = $(target);
				var formFields = form.find("input[name],select[name],textarea[name]");
				formFields.each(function() {
					var name = this.name;
					var value = jQuery.proxy(function() {
						try {
							return eval('this.' + name);
						} catch (e) {
							return "";
						}
					}, data)();
					var rr = _checkField(name, value);
					if (!rr.length) {
						var f = form.find("input[numberboxName=\"" + name + "\"]");
						if (f.length) {
							f.numberbox("setValue", value);
						} else {
							$("input[name=\"" + name + "\"]", form).val(value);
							$("textarea[name=\"" + name + "\"]", form).val(value);
							$("select[name=\"" + name + "\"]", form).val(value);
						}
					}
					_loadCombo(name, value);
				});
				opts.onLoadSuccess.call(target, data);
				$(target).form("validate");
			}

			function _checkField(name, val) {
				var rr = $(target).find('input[name="' + name + '"][type=radio], input[name="' + name + '"][type=checkbox]');
				rr._propAttr('checked', false);
				rr.each(function() {
					var f = $(this);
					if (f.val() == String(val) || $.inArray(f.val(), val) >= 0) {
						f._propAttr('checked', true);
					}
				});
				return rr;
			}

			function _loadCombo(name, val) {
				var form = $(target);
				var cc = [ 'combobox', 'combotree', 'combogrid', 'datetimebox', 'datebox', 'combo' ];
				var c = form.find('[comboName="' + name + '"]');
				if (c.length) {
					for (var i = 0; i < cc.length; i++) {
						var type = cc[i];
						if (c.hasClass(type + '-f')) {
							if (c[type]('options').multiple) {
								c[type]('setValues', val);
							} else {
								c[type]('setValue', val);
							}
							return;
						}
					}
				}
			}
		}
	}
});

/**
 * 更换主题
 * 
 * @author zhangkeqi
 * @requires jQuery,EasyUI
 * @param themeName
 */
system.changeTheme = function(themeName) {
	var $easyuiTheme = $('#easyuiTheme');
	var url = $easyuiTheme.attr('href');
	var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
	$easyuiTheme.attr('href', href);

	var $iframe = $('iframe');
	if ($iframe.length > 0) {
		for (var i = 0; i < $iframe.length; i++) {
			var ifr = $iframe[i];
			try {
				$(ifr).contents().find('#easyuiTheme').attr('href', href);
			} catch (e) {
				try {
					ifr.contentWindow.document.getElementById('easyuiTheme').href = href;
				} catch (e) {
				}
			}
		}
	}

	system.cookie('easyuiTheme', themeName, {
		expires : 7
	});
};

/**
 * 滚动条
 * 
 * @author zhangkeqi
 * @requires jQuery,EasyUI
 */
system.progressBar = function(options) {
	if (typeof options == 'string') {
		if (options == 'close') {
			$('#syProgressBarDiv').dialog('destroy');
		}
	} else {
		if ($('#syProgressBarDiv').length < 1) {
			var opts = $.extend({
				title : '&nbsp;',
				closable : false,
				width : 300,
				height : 60,
				modal : true,
				content : '<div id="syProgressBar" class="easyui-progressbar" data-options="value:0"></div>'
			}, options);
			$('<div id="syProgressBarDiv"/>').dialog(opts);
			$.parser.parse('#syProgressBarDiv');
		} else {
			$('#syProgressBarDiv').dialog('open');
		}
		if (options.value) {
			$('#syProgressBar').progressbar('setValue', options.value);
		}
	}
};
/**
 * 加载表单元素
 * 
 * @author norain
 * @requires jQuery,EasyUI
 */
system.initEasyUIFormData = function(result,append) {
	var form = {};
	for(var key in result) {
		form[key] = result[key];
	}
	return $.extend(form,append);
};
