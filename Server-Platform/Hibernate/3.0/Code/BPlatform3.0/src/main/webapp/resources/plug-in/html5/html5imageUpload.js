/*用法：  1：上传文件的文件域id固定为file。
	  2:上传图标的class固定为addfile。
	  3：控制双击删除的控件id固定为btnUploadCtrl。
步骤：
1：
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/zepto.js"></script>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/event.js"></script>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/touch.js"></script>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/fx.js"></script>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/detect.js"></script>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/form.js"></script>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/ajax.js"></script>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/data.js"></script>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/callbacks.js"></script>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/deferred.js"></script>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/assets.js"></script>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/selector.js"></script>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/stack.js"></script>
2：
<script src="${app}/resources/plug-in/html5/html5imageUpload.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="${app}/resources/plug-in/html5/html5imageUpload.css" charset="utf-8">
3：
<script type="text/javascript">
window.jzo2o = {};
jzo2o.appPath = '${app}';
</script>
4:
<div id="btnUploadCtrl" style="margin-bottom:0.5em;
	height: 1.5em;
	line-height: 1.5em;
	color: #fff;
	background: #F8BE99;
	-webkit-box-pack: center;
	border: none;
	font-size: 0.4em;">上传图片（双击删除）</div>
5:
<div class="imagebox">
	<div class="addfile">
		<div class="speed">1%</div>
		<input id="file" type="file" class="file" />
	</div>
</div>
*/

var xhr = new XMLHttpRequest();
//监听选择文件信息
function fileSelected() {
	//HTML5文件API操作
	var file = document.getElementById('file').files[0];
	if (file) {
		var fileSize = 0;
		if (file.size > 1024 * 1024) {
			fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
		} else {
			fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
		}
	}
}
 
//上传文件
function uploadFile() {
	if(!$('#file').val()) {
		alert('请选择图片！');
		return;
	}
	var fileName = $('#file').val();
	var fileType = (fileName.substring(fileName.lastIndexOf(".")+1,fileName.length)).toLowerCase();
	if(fileType != 'jpg' && fileType != 'jpeg' && fileType != 'png' && fileType != 'gif') {
		alert('请上传[jpg,jpeg,png,gif格式的图片！].');
		return
	}
	
	var fd = new FormData();
	//关联表单数据,可以是自定义参数
	fd.append('file', document.getElementById('file').files[0]);
	
	//设置ui
	$('.addfile').addClass('loading');
	
	//监听事件
	xhr.upload.addEventListener('progress', uploadProgress, false);
	xhr.addEventListener('load', uploadComplete, false);
	xhr.addEventListener('error', uploadFailed, false);
	xhr.addEventListener('abort', uploadCanceled, false);
	//发送文件和表单自定义参数
	xhr.open('POST', jzo2o.appPath+'/jzo2o/weixin/fileUploadController/upload');
	xhr.send(fd);
}
//取消上传
function cancleUploadFile(){
    $('.addfile').removeClass('loading');
    xhr.abort();
}
 
//上传进度
function uploadProgress(evt) {
	if (evt.lengthComputable) {
		var percentComplete = Math.round(evt.loaded * 100 / evt.total);
		$('.speed').html(percentComplete.toString() + '%');
	} else {
	}
}

//上传成功响应
function uploadComplete(evt) {
	//服务断接收完文件返回的结果
	//console.log(evt.target.responseText);
	$('#file').val('');
	$('.addfile').removeClass('loading');
	//alert('上传成功！你可以继续上传！');
	
	var response = JSON.parse(evt.target.responseText);
	if(response.success) {
		var appendHtml = '<div class="imageitem">'+
							'<input type="hidden" name="orderImages" class="images" />'+
							'<div class="delete deleteHidden"></div>'+
						'</div>';
		var html = $(appendHtml).css('background-image',"url('"+jzo2o.appPath+"/resources/plug-in/jzo2o/weixin/upload/"+response.fileName+"')");
		html.find('.images').val(response.fileName);
		$('.imagebox').prepend(html);
	} else {
		alert(response.msg);
	}
}
     
//上传失败
function uploadFailed(evt) {
	$('.addfile').removeClass('loading');
}
//取消上传
function uploadCanceled(evt) {
}



var timeoutArray = [];
var deleteState = false;

$(function() {
	window.deleteAnimate1 = function(){
		if(timeoutArray[1]) {
			 window.clearTimeout(timeoutArray[1]);
		}
		 $(".imageitem").removeClass("r2"); 
		 $(".imageitem").addClass("r1");
		timeoutArray[0] = setTimeout(deleteAnimate2,90);
	 }
	window.deleteAnimate2 = function(){
		if(timeoutArray[0]) {
			 window.clearTimeout(timeoutArray[0]);
		}
	     $(".imageitem").removeClass("r1");
	     $(".imageitem").addClass("r2"); 
	     	timeoutArray[1] = setTimeout(deleteAnimate1,90);
	}
	
	window.clearDeleteAnimate = function() {
		if(timeoutArray.length > 0) {
			for(var i=0;i<timeoutArray.length;i++) {
				window.clearTimeout(timeoutArray[i]);
			}
			timeoutArray = [];
		}
	}
	
	$('#btnUploadCtrl').bind('doubleTap',function(){
		if(deleteState == false && $('.delete').length > 0) {
			$('.delete').removeClass('deleteHidden').bind("touchend",function(){
				$(this).parent().animate({width:'0em',height:'0em'},500,function() {
					$(this).remove();
					$('.delete').addClass('deleteHidden');
					$(".imageitem").removeClass("r1");
					$(".imageitem").removeClass("r2");
					window.deleteState = false;
					window.clearDeleteAnimate();
				});
				return false;
			});
			
			window.deleteAnimate1();
			window.deleteState = true;
		} else {
			$('.delete').addClass('deleteHidden')
			$(".imageitem").removeClass("r1");
			$(".imageitem").removeClass("r2");
			window.deleteState = false;
			window.clearDeleteAnimate();
		}
		return false;
	});
	
	$('.file').change(function(){
		uploadFile();
	});
	
});