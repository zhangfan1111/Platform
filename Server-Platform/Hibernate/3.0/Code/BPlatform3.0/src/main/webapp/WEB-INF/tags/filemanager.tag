<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="edit" type="java.lang.String" required="true"%>
<%@ attribute name="parentFiledId" type="java.lang.String" required="true"%>
<%@ attribute name="filePaths" type="java.lang.String"%>
<%@ include file="/jsp/common/taglibs.jsp"%>
<!-- <script type="text/javascript">
	$("#showHtml").innerHTML = system.contextPath + '/addFileForm?parentFiledId='+${parentFiledId}+'&edit='+${edit};
	$("#containerID").load(system.contextPath + '/addFileForm?parentFiledId='+${parentFiledId}+'&edit='+${edit});
</script> -->
<!-- <div id="showHtml"></div> -->

<iframe src="${app}/fileManager/addFileForm?parentFiledId=${parentFiledId}&edit=${edit}" allowTransparency="true" style="border:0px; width:100%; height:99%;" frameBorder="0">
</iframe>
<input id="${parentFiledId}" name="${parentFiledId}" value="${filePaths}" type="hidden"/>