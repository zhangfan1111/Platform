<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.memory.platform.web.session.SessionInfo"%>
<%
	SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
%>
<!DOCTYPE html>
<html>
<head>
<title>字典使用示例</title>
<%@ include file="/jsp/common/base.jsp"%>
<script type="text/javascript" defer>
	function sel(r) {
		console.log(r);
		console.log(r.code);
	}
</script>
</head>
<body>
	<form method="post" class="form">
		<fieldset>
			<legend>字典使用示例</legend>
		    	<table class="table" style="width: 100%;">
					<tr>
						<th>是否有工作单位</th>
						<td colspan="3">
							<tags:dictCombobox id="customerHasCompany" onSelect="sel(rec);" name="customerHasCompany" dict="10.2.1" required="true"></tags:dictCombobox>
						</td>
					</tr>
				</table>
		</fieldset>
	</form>
</body>
</html>