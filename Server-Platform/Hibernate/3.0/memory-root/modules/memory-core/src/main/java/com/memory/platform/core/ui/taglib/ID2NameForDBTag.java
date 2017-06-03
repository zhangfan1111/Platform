package com.memory.platform.core.ui.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <p>
 * Title:数据库的id2name
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:2014-06-09 11:11:13
 * </p>
 * 
 * @author 
 * @version 1.0
 * 
 */
public class ID2NameForDBTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6089139100246144693L;

	/**
	 * spring dao中的bean id
	 */
	private String beanId;

	/**
	 * 要转成name的id
	 */
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the beanId
	 */
	public String getBeanId() {
		return beanId;
	}

	/**
	 * @param beanId
	 *            the beanId to set
	 */
	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}

	public int doEndTag() throws JspException {
		return SKIP_BODY;
	}

	public int doStartTag() throws JspException {
		return super.doStartTag();
	}

}
