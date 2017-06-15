require('styles/App.css');

import React from 'react';

class Login extends React.Component {
  
constructor (props) {
	super(props);
	
	
	this.state = {
		uservalue : '请输入账号',
		passvalue : '请输入密码',
	}
}
  
  getInitialState () {
  	return {passvalue: '呵呵呵'};
  };
  
  //点击事件
  myClick() {
		console.log(this.refs.username.value);
		console.log(this.refs.password.value);
  	//提交账号密码发送到后台
  	//这里写下测试用的fetch代码，不知道可行否
  	fetch("url", {  
           method: "post",
            //same-origin（同源请求）、no-cors（默认）和cors（允许跨域请求）
            //第一种跨域求情会报error，第二种可以请求其他域的脚本、图片和其他资源，但是不能访问response里面的属性，第三种可以获取第三方数据，前提是所访问的服务允许跨域访问
            mode: "cors",
            cache: 'default',
            headers: {  
            	//后台那边必须支持cors才行，不然是跨域
              "Content-Type": "application/x-www-form-urlencoded",
              'Access-Control-Allow-Origin':'*',
              'Content-Type': 'text/plain'
            },
            body: JSON.stringify({
      				username : this.refs.username.value,
      				password : this.refs.password.value,//这里不知道需要问号吗
					  })
        }).then(function (res) {  
        	console.log(res);
        }).catch(function (e) {  
            console.log("失败");  
        });
  }
  
  //onchangge事件
  myChange(e) {
  	console.log(e.target.value);
  }
  
  
  
  render() {
    return (
      <div style={{textAlign:"center",height:'300px'}}>
       <div>账号<input type="text" ref='username' placeholder={this.state.uservalue} style={{margin:'20px'}}/></div>
       <div>密码<input type="text" ref='password' placeholder={this.state.passvalue} style={{margin:'20px'}}/></div>
       <button onClick={this.myClick.bind(this)}>提交</button>
      </div>
    );
  }
}

Login.defaultProps = {
};

export default Login;
