import React from 'react';

class Constructor extends React.Component {
	constructor(props) {
		super(props);
		
		this.state = {name : 'myname',
					   mytime : '5',	
					 }
	}
	
	
	componentWillMount () {
		//alert(111);
    // add event listeners (Flux Store, WebSocket, document, etc.)
    //设置state
  	  //this.setState({name : '未加载'})警告写法，setState只能在mounting或者mounted发生
	};//这里是分号

	componentDidMount () {
      // React.getDOMNode()
  	  this.setState({name : '已加载'})
  	  this.kk = setInterval(function () {
  	  	this.setState({mytime : this.state.mytime-1});
	  	if (this.state.mytime == '0') {
//	  	  clearInterval(this.kk);
	  	  window.location.hash = '/register';
		  //this.setState({mytime : '10'})
	    }
  	  }.bind(this),1000);
	}
	
	componentWillUnmount () {
	  clearInterval(this.kk);
	}
	
	shouldComponentUpdate () {
		//这个阶段用来判断组件再更改状态时是否重新render,用来返回true或者false
		//if(this.state.mytime == '0') clearInterval(this.kk),console.log("停止计时");
		return true;
	}
	
	render() {
		return(
			<div>
				<h1>声明周期以及定时器的显示</h1>
				<h1>{this.state.name}</h1>
				<h1>{this.state.mytime}S后跳转到主页</h1>
			</div>
		);
	}
}

export default Constructor;