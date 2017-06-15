require('normalize.css/normalize.css');
require('styles/App.css');

import React from 'react';

class Register extends React.Component {

  constructor(props) {
  	//定义构造器
  	//第一步调用super
  	super(props);
  	//初始化状态
  	this.state = {}
  };
    
    userChange () {
    	console.log(this.refs.user.value);
    	if(!(/^1[34578]\d{9}$/.test(this.refs.user.value))){ 
        this.refs.user.style.border = '4px solid red'; 
        return false; 
      } 
    }
    
    passChange () {
    	console.log(this.refs.pass.value.length);
    	if(this.refs.pass.value.length < 6) {
    		this.refs.pass.style.border = '4px solid red';
    	}else{
    		this.refs.pass.style.border = '';
    	}
    }
    
    myClick() {
  	
    };//此处用分号
  
  render() {
    return (
      <div className="loginForm">
        <div>
          账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号
          <input type='text' placeholder="请输入手机号" ref='user' onChange={this.userChange.bind(this)}/>
        </div>
        <div>
          密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码
          <input type='password' placeholder="请输入密码" ref="pass" onChange={this.passChange.bind(this)}/>
        </div>
        <div>
          重复密码
          <input type='password' placeholder="请输入密码" ref='pass2'/>
        </div>
        <div className="register_btn" ref='sub'>提&nbsp;&nbsp;交</div>
      </div>
    );
  }
}

Register.defaultProps = {
};

export default Register;
