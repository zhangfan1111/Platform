import React from 'react';

class Ajax extends React.Component {
  
  constructor () {
	super();
	
	this.state = {
		
	}
  }
  
  //请求
  componentDidMount () {
  	//fetch("http://www.tngou.net/tnfs/api/classify").then(function(response){console.log(response)})
    fetch("http://www.7shilu.com/Navigation/company/enterpanorama?url=qishilukeji", {  
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
            }
//          body: JSON.stringify({
//    				username : this.refs.username.value,
//    				password : this.refs.password.value,//这里不知道需要问号吗
//					  })
        }).then(function (res) {  
        	console.log(res);
        }).catch(function (e) {  
            console.log("失败");  
        });  
  }
  
  render() {
    return (
      <div style={{textAlign:"center"}}>
        <div>heheh</div>
      </div>
    );
  }
}

Ajax.defaultProps = {
};

export default Ajax;
