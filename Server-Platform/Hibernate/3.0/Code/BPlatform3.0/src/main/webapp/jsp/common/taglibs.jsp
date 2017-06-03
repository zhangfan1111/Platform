<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>    
<%@ taglib uri="/WEB-INF/tlds/system.tld" prefix="sys" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="app" scope="page" value="${pageContext.request.contextPath}" />
<c:set var="scheme" scope="page" value="${pageContext.request.scheme}"/>
<c:set var="serverName" scope="page" value="${pageContext.request.serverName}"/>
<c:set var="serverPort" scope="page" value="${pageContext.request.serverPort}"/>
<c:set var="theme" scope="session" value="default"/>
<c:if test="${!empty param.theme}">
  <c:set var="theme" scope="session" value="${param.theme}"/>
</c:if>
