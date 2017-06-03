$(function(){
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

//登录处理函数
function Login() {
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
				$("#tishi").html(data.msg);
				setTimeout("window.location.href='"+actionurl+"'", 1000);
			} else {
				if(data.msg == "a"){
					$.dialog.confirm("数据库无数据,是否初始化数据?", function(){
						window.location = "init.jsp";
					}, function(){
						//
					});
				} else
					$("#tishi").html(data.msg);
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

