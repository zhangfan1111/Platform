var system = system || {};

system.dictMap = [];

/**
 * 去字符串空格
 * 
 * @author 吴宇
 */
system.trim = function(str) {
	return str.replace(/(^\s*)|(\s*$)/g, '');
};
system.ltrim = function(str) {
	return str.replace(/(^\s*)/g, '');
};
system.rtrim = function(str) {
	return str.replace(/(\s*$)/g, '');
};

/**
 * 判断开始字符是否是XX
 * 
 * @author 吴宇
 */
system.startWith = function(source, str) {
	var reg = new RegExp("^" + str);
	return reg.test(source);
};
/**
 * 判断结束字符是否是XX
 * 
 * @author 吴宇
 */
system.endWith = function(source, str) {
	var reg = new RegExp(str + "$");
	return reg.test(source);
};

/**
 * iframe自适应高度
 * 
 * @author 吴宇
 * 
 * @param iframe
 */
system.autoIframeHeight = function(iframe) {
	iframe.style.height = iframe.contentWindow.document.body.scrollHeight + "px";
};

/**
 * 设置iframe高度
 * 
 * @author 吴宇
 * 
 * @param iframe
 */
system.setIframeHeight = function(iframe, height) {
	iframe.height = height;
};

/**
 * 全局字典控制
 * 
 * @author 吴宇
 * 
 */
system.dictCode2Name = function(code) {
	if(code) {
		var parentCode =  code.substr(0,code.lastIndexOf('.'));
		if(system.dictMap[parentCode]) {
			return system.dictMap[parentCode][code].name;
		} else {
			system.dictMap[parentCode] = [];
			var request = $.ajax({
				type: "POST"
					,cache : false
					//非阻塞
					,async : false
					,dataType: "json"
						,url: system.contextPath + '/system/systemDictController/getByCode/'+parentCode+'/'
			}).success(function(data,result,resp){
				if(data) {
					for(var index in data) {
						system.dictMap[parentCode][data[index].code] = data[index];
					}
				}
			});
			if(system.dictMap[parentCode][code]) {
				var name = system.dictMap[parentCode][code].name;
				return name;
			} else {
				return '';
			}
		}
	}
};
/**
 * 得到当前日期
 * 
 * @author 吴宇
 * 
 */
system.formatterDate = function(date) {
	var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
	var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0" + (date.getMonth() + 1);
	var hour = date.getHours()> 9 ? date.getHours() : "0" + date.getHours();
	var minute = date.getMinutes()> 9 ? date.getMinutes() : "0" + date.getMinutes();
	var second = date.getSeconds()> 9 ? date.getSeconds() : "0" + date.getSeconds();
	var nowTime = date.getFullYear() + '-' + month + '-' + day + " " + hour + ":" + minute + ":" + second;
	return nowTime;
};

/**
 * 将数值四舍五入(保留2位小数)后格式化成金额形式
 * @author 吴宇
 * @param num 数值(Number或者String)
 * @return 金额格式的字符串,如'1,234,567.45'
 * @type String
 */
system.formatCurrency = function(num) {
	if(num) {
		num = num.toString().replace(/\$|\,/g,'');
		if(isNaN(num))
			num = "0";
		sign = (num == (num = Math.abs(num)));
		num = Math.floor(num*100+0.50000000001);
		cents = num%100;
		num = Math.floor(num/100).toString();
		if(cents<10)
			cents = "0" + cents;
		for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
			num = num.substring(0,num.length-(4*i+3))+','+
			num.substring(num.length-(4*i+3));
		return (((sign)?'':'-') + num + '.' + cents);
	} else {
		return '';
	}
}