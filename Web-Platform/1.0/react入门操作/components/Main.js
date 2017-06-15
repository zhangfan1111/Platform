require('normalize.css/normalize.css');
require('styles/App.css');

import React from 'react';

let yeomanImage = require('../images/yeoman.png');
let time = '5' ;


class AppComponent extends React.Component {
	
	constructor (props) {
		super(props);
		
		this.state = {
			time : '5'
		}
	}
	
	componentDidMount () {
		this.timer = setInterval (function () {
			this.setState({time : this.state.time - 1});
			if (this.state.time == '0') {
				clearInterval(this.timer);
				window.location.hash = 'regi';
			}
		}.bind(this),1000);
	}
	
	componentWillUnMount () {
		clearInterval(this.timer);
	}
	
	render() {
		return(
			<div className = "index" >
			<img src = {yeomanImage} / >
			<div className = "notice" > 
			  请牢记本站域名
			  <br />
			  <br />
			  {this.state.time}S秒后自动跳转到注册页面
			</div>
			</div>
		);
	}
}

AppComponent.defaultProps = {};

export default AppComponent;