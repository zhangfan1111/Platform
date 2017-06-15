require('styles/App.css');

//展示路由嵌套的组件
import React from 'react';
import {
  render,
  ReactDOM,
} from 'react-dom';

import Register from './Register';
import Login from './Login';
import { Link } from 'react-router';

class Rin extends React.Component {
  
  constructor (props) {
	  super(props);
	  this.state = {
	  	 lis : ['跳转页','登录','注册']
	  };
  }

  
  
  componentDidMount () {
  	//this.refs.listpage.onClick={this.myClick}
  	//我在这里遍历ul下的所有li，分别给其绑定事件
  	//this.refs.listpage得到一个obj
		//console.log(this.refs.listpage.childNodes)
//		this.refs.listpage.childNodes
		//得到当前的hash
    console.log(window.location.hash);
    this.refs.listpage.childNodes[0].style.background = 'lightblue';
    this.timer = setTimeout (function () {
				console.log(this.refs.listpage.childNodes[2]);
				this.refs.listpage.childNodes[0].style.background = '#222';
				this.refs.listpage.childNodes[2].style.background = 'lightblue';
		}.bind(this),5000);
  }
  
  componentWillUnMount () {
//		clearInterval(this.timer);
	}
  
  myClick (i,index) {
		window.location.hash = i;
		for (var i=0;i<this.refs.listpage.childNodes.length;i++) {
			this.refs.listpage.childNodes[i].style.background = '#222';
		}
		this.refs.listpage.childNodes[index].style.background = 'lightblue';
  }
  
  render() {
	var xxx = [{'text':'跳转页','url':'/'},{'text':'登录','url':'login'},{'text':'注册','url':'regi'}];
//		var xxx = ["跳转页","登录","注册"]
  	var xxxmap = xxx.map(function(val,index){
      	      return	<li key={index} url={val.url} onClick={this.myClick.bind(this,val.url,index)}>{val.text}</li>
      	   },this);
    return (
      <div className='all'>
      	<ul className="list" ref='listpage' >
      	  {xxxmap}
      	</ul>
      	<div className="content">
      	  {this.props.children}
      	</div>
      </div>
    );
  }
}

Rin.defaultProps = {
};

export default Rin;
