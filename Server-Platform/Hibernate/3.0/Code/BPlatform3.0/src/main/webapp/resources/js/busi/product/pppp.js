function graphTrace(fileId) {
	$('#workflowTraceDialog').dialog('close');
    if ($('#imgDialog').length > 0) {
        $('#imgDialog').remove();
    }
    $('<div/>', {
        'id': 'imgDialog',
        title: '查看流程',
        html: "<img src='" + system.contextPath + '/fileImageIO?id=' + fileId + "' />"
    }).appendTo('body').dialog({
        modal: true,
        resizable: true,
        dragable: true,
        width: document.documentElement.clientWidth * 0.95,
        height: document.documentElement.clientHeight * 0.95
    });
}
