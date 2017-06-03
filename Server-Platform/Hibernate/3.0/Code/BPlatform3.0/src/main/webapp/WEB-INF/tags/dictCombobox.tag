<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="id" type="java.lang.String" required="true"%>
<%@ attribute name="name" type="java.lang.String" required="true"%>
<%@ attribute name="valueField" type="java.lang.String" required="false"%>
<%@ attribute name="required" type="java.lang.String" required="false"%>
<%@ attribute name="textField" type="java.lang.String" required="false"%>
<%@ attribute name="dict" type="java.lang.String" required="false"%>
<%@ attribute name="url" type="java.lang.String" required="false"%>
<%@ attribute name="defaultValue" type="java.lang.String" required="false"%>
<%@ attribute name="readonly" type="java.lang.String" required="false"%>
<%@ attribute name="onLoadSuccess" type="java.lang.String" required="false"%>
<%@ attribute name="onSelect" type="java.lang.String" required="false"%>

<%@ include file="/jsp/common/taglibs.jsp"%>

<c:if test="${empty valueField}">
	<c:set var="valueField" value="code"></c:set>
</c:if>
<c:if test="${empty textField}">
	<c:set var="textField" value="name"></c:set>
</c:if>
<c:if test="${not empty dict}">
	<c:set var="url">${app}/system/systemDictController/getByCode/${dict}/</c:set>
</c:if>
<c:if test="${not empty readonly}">
	<c:set var="readonly">readonly="readonly"</c:set>
</c:if>
<c:if test="${not empty onSelect}">
	<c:set var="onSelect">,onSelect: function(rec){
		${onSelect}
	}
	</c:set>
</c:if>
<c:if test="${not empty defaultValue}">
	<c:set var="onLoadSuccess">,onLoadSuccess: function(rec){
		$('#${id}').combobox('setValue', '${defaultValue}');
	}</c:set>
</c:if>
<c:if test="${not empty required}">
	<c:set var="required">,required:true</c:set>
</c:if>

<input id="${id}" name="${name}" class="easyui-combobox" ${readonly} data-options="
        valueField : '${valueField}',
		textField : '${textField}',
		panelHeight : 'auto',
        url: '${url}',
        editable : false
        ${required }
        ${onLoadSuccess }
        ${onSelect }
			 "/> 