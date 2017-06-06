var panel;
var form;
var random = 0;

$(function(){
	panel = $("#login_panel");
	form = $("#formLogin");
	random = Math.ceil(Math.random() * 3);
	//random = 3;
	
	randomEffectShow();		//随机效果显示
	
	getCookie();
    var height_ = $(window).height();
    $(".big").height(height_);
    //设置上边蓝色区域高度为窗口一半
    $(".top_div").height(height_/2);
   
    //点击登录触发效果
    $("#login").click(function(){
    	submit();
        
    });
    //获取焦点
    $(".ipt").focus(function(){
    	//anmi();
    	$(".error").hide();
    });
    $(".ipt").focusout(function(){
    	reset();
    });

});

//随机效果显示
function randomEffectShow(){
	if(random == 1){		//淡出
		panel.hide();
		panel.fadeIn(1500);
	}else if(random == 2){		//滑动显示
		panel.hide();
		panel.slideDown(900);
	}else if(random == 3){		//伸展
		var wid = 0;
		var hei = 0;
		var timer = null;
		
		timer = setInterval(function(){
			wid += 10;
			hei += 7.3;
			form.hide();
			
			panel.css({"width": wid + "px", "height" : hei + "px"});
			if(wid >= 370){
				form.show();
				clearInterval(timer);
				return;
			}
		}, 13);
	}
}

//效果隐藏 对应显示效果
function effectHide(){
	if(random == 1){	//淡入
		panel.fadeOut(900);
	}else if(random == 2){		//滑动隐藏
		panel.slideUp(900);
	}else if(random == 3){	
		var wid = panel.width();
		var hei = panel.height();
		form.hide();
		
		var timer = null;
		
		timer = setInterval(function(){
			wid -= 10;
			hei -= 7.3;
			
			panel.css({"width": wid + "px", "height" : hei + "px"});
			if(wid <= 0){
				clearInterval(timer);
				return;
			}
		}, 13);
	}
}

function keyLogin(){
	if (event.keyCode==13){  //回车键的键值为13
		submit();
	}
}

function reset() {
	$(".error").hide();
    $("#left_hand").attr("class","initial_left_hand");
    $("#left_hand").attr("style","left:100px;top:-12px;");
    $("#right_hand").attr("class","initial_right_hand");
    $("#right_hand").attr("style","right:-112px;top:-12px");
}

function submit() {
	reset();
	if($("#user").val()==""){
        $("#tishi").text("请输入用户名");
        return;
    }
    if($("#password").val()==""){
        $("#tishi").text("请输入密码");
        return;
    }
    Login();
};

var count = 3;
var canClick = true;

//登录处理函数
function Login() {
	if(!canClick){
		return alert("请" + count + "秒后重试！");
	}
	setCookie();
	var actionurl=$('form').attr('action');//提交路径
	var checkurl=$('form').attr('check');//验证路径
	 var formData = new Object();
	var data=$(":input").each(function() {
		 formData[this.name] =$("#"+this.name ).val();
	});
	$.ajax({
		type: "POST",
		url : checkurl,// 请求的action路径
		data:{
        	loginName:$("#user").val(),
        	password:$("#password").val()
        },
		error : function(data) {// 请求失败处理函数
			console.log(data);
		},
		success : function(data) {
			//var d = $.parseJSON(data);
			if (data.success) {
				//隐藏效果
				effectHide();
				
				setTimeout("window.location.href='" + actionurl + "'", 1200);
			} else {
				canClick = false;
				
				if(data.msg == "a"){
					$.dialog.confirm("数据库无数据,是否初始化数据?", function(){
						window.location = "init.jsp";
					}, function(){
						//
					});
				} else {
					var msg = data.msg;
					
					$("#login").css("background-color", "red");
					
					count = 3;
					var timer = null;
					$("#login").html(msg + count);
					
					timer = setInterval(function(){
						count --;
						
						$("#login").html(msg + count);
						
						if(count == 0){
							clearInterval(timer);
							canClick = true;
							count == 3;
							
							$("#login").html("登录");
							$("#login").css("background-color", "#00A2EA");
						}
					}, 1000);
				}
			}
		}
	});
}

//设置cookie
function setCookie()
{
	if(!$('#check').is(':checked')){
		$("input[iscookie='true']").each(function() {
			$.cookie(this.name,null);
			$.cookie("COOKIE_NAME",null);
		});
	}else{
		$("input[iscookie='true']").each(function() {
			$.cookie(this.name, $("#"+this.name).val(), "/",24);
			$.cookie("COOKIE_NAME","true", "/",24);
		});
	}
}
//读取cookie
function getCookie()
{
	var COOKIE_NAME=$.cookie("COOKIE_NAME");
	if (COOKIE_NAME !=null) {
		$("input[iscookie='true']").each(function() {
			$($("#"+this.name).val( $.cookie(this.name)));
		});
		$('#check').prop("checked",true);
	} 
	else
	{
		$('#check').prop("checked",false);
	}
}

