package com.memory.platform.core.interceptors;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.memory.platform.common.util.ConvertUtils;
import com.memory.platform.core.springmvc.WebContextUtils;
import com.memory.platform.modules.online.Client;
import com.memory.platform.modules.online.ClientManager;


/**
 * 权限拦截器
 * 
 */
public class AuthInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	private List<String> excludeUrls;

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	/**
	 * 在controller后拦截
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 在controller前拦截
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String requestPath = WebContextUtils.getRequestPath(request);// 用户访问的资源地址
		HttpSession session = WebContextUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		if(client == null){ 
			client = ClientManager.getInstance().getClient(
					request.getParameter("sessionId"));
		}
		if (excludeUrls.contains(requestPath)) {
			return true;
		} else {
			if (client != null && client.getUser()!=null ) {
				//TODO
				if(!hasMenuAuth(request)){
					 response.sendRedirect("loginController.do?noAuth");
					//request.getRequestDispatcher("webpage/common/noAuth.jsp").forward(request, response);
					return false;
				} 
				String functionId=ConvertUtils.getString(request.getParameter("clickFunctionId"));
				if(!ConvertUtils.isEmpty(functionId)){
					request.setAttribute("operationCodes", "operationCodes");
				 
				}
				if(!ConvertUtils.isEmpty(functionId)){
					
				}
				return true;
			} else {
				//forword(request);
				forward(request, response);
				return false;
			}
		}
	}
	private boolean hasMenuAuth(HttpServletRequest request){
		String requestPath = WebContextUtils.getRequestPath(request);// 用户访问的资源地址
		String funcid=ConvertUtils.getString(request.getParameter("clickFunctionId"));
		 
		if(requestPath.indexOf("loginController.do")!=-1||funcid.length()==0){
			return true;
		} 
		String userid = ClientManager.getInstance().getClient(
				WebContextUtils.getSession().getId()).getUser().getId();
		//TODO
		//requestPath=requestPath.substring(0, requestPath.indexOf("?")+1);
//		String sql = "select ..."; 
//		List list = new ArrayList();
//		if(list.size()==0){
//			return false;
//		}else{
//			return true;
//		}
		return true;
	}
	/**
	 * 转发
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "forword")
	public ModelAndView forword(HttpServletRequest request) {
		return new ModelAndView(new RedirectView("loginController.do?login"));
	}

	private void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("webpage/login/timeout.jsp").forward(request, response);
	}

}
