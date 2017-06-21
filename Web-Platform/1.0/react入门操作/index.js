import React from 'react';
import {
  render,
  ReactDOM,
} from 'react-dom';
import Register from './components/Register';
import AppComponent from './components/Main';
import Hello from './components/Life';
import Constructor from './components/Constructor';
import Login from './components/Login';
import Ajax from './components/Ajax';
import Rin from './components/Rin';
import {
  Router,
  Route,
  Link,
  IndexRoute,
  hashHistory,
  withRouter,
} from 'react-router';

// Render the main component into the dom

//第一种，直接导出router组件到html中
//render((
//	<Router history={hashHistory}>
//		<IndexRoute component={Constructor} />
//	  <Route path="/" component={AppComponent}/>
//	  <Route path="/register" component={Register}/>
//	  <Route path="/life" component={Hello}/>
//	  <Route path="/cons" component={Constructor} />
//	  <Route path="/log" component={Login} />
//	  <Route path="/aja" component={Ajax} />
//	  <Route path="/rin" component={Rin}>
//	    <IndexRoute component={Register}/>
//	    <Route path="/rin/log" component={Login} />
//	  </Route>
//	</Router>
//), document.getElementById('app'));


render((
	<Router history={hashHistory}>
	  <Route path="/" component={Rin}>
	    <IndexRoute  component={AppComponent}/>
	    <Route path="regi" component={Register}/>
	    <Route path="login" component={Login} />
	    <Route path="life" component={Hello}/>
  	  <Route path="cons" component={Constructor} />
  	  <Route path="aja" component={Ajax} />
	  </Route>
	</Router>
), document.getElementById('app'));


//第二种，定义routes，在Router组件中引入routes
//定义router的属性
//const routes = (
//  <Route path="/" component={AppComponent}>
//    <IndexRoute component={Register} />
//    <Route path="/register" component={Register} />
//    <Route path="/life" component={Life} />
//  </Route>
//);
//render(<Router routes={routes} history={hashHistory}/>, document.getElementById('app'));
