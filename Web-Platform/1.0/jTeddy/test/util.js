var utils = {};
/**
 * 动态加载指定的js到基础页面
 * @param {} url js 路径
 * @param {} callback 加载完成后执行的回调方法
 * @param {} params 回调方法参数
 * 需要依赖sea.js文件
 */
utils.loadJsFile = function(url, callback, params)
{
	params = params || {};

	var executeCallBack = function(){
		if (callback) {
			if(typeof callback == "string" && eval("typeof " + callback + " == 'function'"))
			{
				eval(callback + "(params);");
			}
			else if (typeof callback == "function")
			{
				callback(params);
			}
		}
	}

	if(url && url != "")
	{
		var flag = false;
		// 获取所有的<script>标记
		var scripts = document.getElementsByTagName("script");
		// 判断指定的文件是否已经包含，如果已包含则返回
		for (var i = 0; i < scripts.length; i++) {
			if (scripts[i].src && scripts[i].src.indexOf(url) != -1) {
				flag = true;
				break;
			}
		}

		if(!flag){
			seajs.use(url, function(param){
				executeCallBack();
			});
		} else{
			executeCallBack();
		}
	}
	else
	{
		executeCallBack();
	}
}

/**
 * 批量加载js文件
 * @param {} urlJsons
 */
utils.batchLoadJsFiles = function(urlJsons)
{
	var number = urlJsons.length;
	for (var i = 0; i < number; i++) {
		var urlJson = urlJsons[i];
		if(urlJson.type == "js")
		{
			utils.loadJsFile(urlJson.url, urlJson.callback || "");
		}
	}
}
