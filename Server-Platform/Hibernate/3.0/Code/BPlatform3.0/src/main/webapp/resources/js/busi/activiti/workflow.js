function graphTrace(elt,options) {

    var _defaults = {
        srcEle: elt,
        pid: $(elt).attr('pid'),
	    pdid: $(elt).attr('pdid')
    };
    var opts = $.extend(true, _defaults, options);

    // 处理使用js跟踪当前节点坐标错乱问题
//    $(document).on("click", "#changeImg", function(){
//        $('#workflowTraceDialog').dialog('close');
//        if ($('#imgDialog').length > 0) {
//            $('#imgDialog').remove();
//        }
//        $('<div/>', {
//            'id': 'imgDialog',
//            title: '此对话框显示的图片是由引擎自动生成的，并用红色标记当前的节点<button id="diagram-viewer">Diagram-Viewer</button>',
//            html: "<img src='" + system.contextPath + '/workflow/process/trace/auto/' + opts.pid + "' />"
//        }).appendTo('body').dialog({
//            modal: true,
//            resizable: false,
//            dragable: false,
//            width: document.documentElement.clientWidth * 0.95,
//            height: document.documentElement.clientHeight * 0.95
//        });
//    });

	/*
	用官方开发的Diagram-Viewer跟踪
	 */
//    $(document).on("click", "#diagram-viewer", function(){
//		$('#workflowTraceDialog').dialog('close');
//
//		if ($('#imgDialog').length > 0) {
//			$('#imgDialog').remove();
//		}
//
//		var url = system.contextPath + '/diagram-viewer/index.html?processDefinitionId=' + opts.pdid + '&processInstanceId=' + opts.pid;
//
//		$('<div/>', {
//			'id': 'imgDialog',
//			title: '此对话框显示的图片是由引擎自动生成的，并用红色标记当前的节点',
//			html: '<iframe src="' + url + '" width="100%" height="' + document.documentElement.clientHeight * 0.90 + '" />'
//		}).appendTo('body').dialog({
//			modal: true,
//			resizable: false,
//			dragable: false,
//			width: document.documentElement.clientWidth * 0.95,
//			height: document.documentElement.clientHeight * 0.95
//		});
//	});

    // 获取图片资源
    var imageUrl = system.contextPath + "/workflow/resource/process-instance?pid=" + opts.pid + "&type=image";
    $.getJSON(system.contextPath + '/workflow/process/trace?pid=' + opts.pid, function(infos) {

        var positionHtml = "";

        // 生成图片
        var varsArray = new Array();
        $.each(infos, function(i, v) {
            var $positionDiv = $('<div/>', {
                'class': 'activity-attr'
            }).css({
                position: 'absolute',
                left: (v.x - 77),
                top: (v.y - 28),
                width: (v.width - 2),
                height: (v.height - 2),
                backgroundColor: 'black',
                opacity: 0,
                zIndex: $.fn.qtip.zindex - 1
            });

            // 节点边框
            var $border = $('<div/>', {
                'class': 'activity-attr-border'
            }).css({
                position: 'absolute',
                left: (v.x - 77),
                top: (v.y - 28),
                width: (v.width - 4),
                height: (v.height - 3),
                zIndex: $.fn.qtip.zindex - 2
            });

            if (v.currentActiviti) {
                $border.addClass('ui-corner-all-12').css({
                    border: '3px solid red'
                });
            }
            positionHtml += $positionDiv.outerHTML() + $border.outerHTML();
            varsArray[varsArray.length] = v.vars;
        });

        if ($('#workflowTraceDialog').length == 0) {
            $('<div/>', {
                id: 'workflowTraceDialog',
                html: "<div><img src='" + imageUrl + "' style='' />" +
                "<div id='processImageBorder'>" +
                positionHtml +
                "</div>" +
                "</div>"
            }).appendTo('body');
        } else {
            $('#workflowTraceDialog img').attr('src', imageUrl);
            $('#workflowTraceDialog #processImageBorder').html(positionHtml);
        }

        // 设置每个节点的data
        $('#workflowTraceDialog .activity-attr').each(function(i, v) {
            $(this).data('vars', varsArray[i]);
        });

        $('#workflowTraceDialog').dialog({
			title : '查看流程',
			modal : true,
		    resizable : true,
			width: document.documentElement.clientWidth * 0.95,
            height: document.documentElement.clientHeight * 0.95,
			toolbar:[{
		        	text:'如果坐标错乱请点击这里',
		        	handler:function(){
		                $('#workflowTraceDialog').dialog('close');
		                if ($('#imgDialog').length > 0) {
		                    $('#imgDialog').remove();
		                }
		                $('<div/>', {
		                    'id': 'imgDialog',
		                    title: '查看流程',
		                    html: "<img src='" + system.contextPath + '/workflow/process/trace/auto/' + opts.pid + "' />"
		                }).appendTo('body').dialog({
		                    modal: true,
		                    resizable: true,
		                    dragable: true,
		                    width: document.documentElement.clientWidth * 0.95,
		                    height: document.documentElement.clientHeight * 0.95
		                });
		            }
		        },{
		        	text:'动态图（ie9以上支持）',
		        	handler:function(){
		        		$('#workflowTraceDialog').dialog('close');

		        		if ($('#imgDialog').length > 0) {
		        			$('#imgDialog').remove();
		        		}

		        		var url = system.contextPath + '/diagram-viewer/index.html?processDefinitionId=' + opts.pdid + '&processInstanceId=' + opts.pid;

		        		$('<div/>', {
		        			'id': 'imgDialog',
		        			title: '查看流程',
		        			html: '<iframe src="' + url + '" width="100%" height="' + document.documentElement.clientHeight * 0.90 + '" />'
		        		}).appendTo('body').dialog({
		        			modal: true,
		        			resizable: true,
		        			dragable: true,
		        			width: document.documentElement.clientWidth * 0.95,
		        			height: document.documentElement.clientHeight * 0.95
		        		});
		        	}
		        }],
		        onBeforeOpen : function() {
	                $('#workflowTraceDialog').css('padding', '0.2em');
	                $('#workflowTraceDialog .ui-accordion-content').css('padding', '0.2em').height($('#workflowTraceDialog').height() - 75);

	                // 此处用于显示每个节点的信息，如果不需要可以删除
	                $('.activity-attr').qtip({
	                    content: function() {
	                        var vars = $(this).data('vars');
	                        var tipContent = "<table class='need-border'>";
	                        $.each(vars, function(varKey, varValue) {
	                            if (varValue) {
	                                tipContent += "<tr><td class='label'>" + varKey + "</td><td>" + varValue + "<td/></tr>";
	                            }
	                        });
	                        tipContent += "</table>";
	                        return tipContent;
	                    },
	                    position: {
	                        at: 'bottom left',
	                        adjust: {
	                            x: 3
	                        }
	                    }
	                });
	                // end qtip
	            },
	            onOpen : function() {
	            },
	            onClose : function() {
	            	$('#workflowTraceDialog').remove();
	            }
		});
        
//        // 打开对话框
//        $('#workflowTraceDialog').dialog({
//            modal: true,
//            resizable: false,
//            dragable: false,
//            open: function() {
//                $('#workflowTraceDialog').dialog('option', 'title', '查看流程（按ESC键可以关闭）<button id="changeImg">如果坐标错乱请点击这里</button><button id="diagram-viewer">Diagram-Viewer</button>');
//                $('#workflowTraceDialog').css('padding', '0.2em');
//                $('#workflowTraceDialog .ui-accordion-content').css('padding', '0.2em').height($('#workflowTraceDialog').height() - 75);
//
//                // 此处用于显示每个节点的信息，如果不需要可以删除
//                $('.activity-attr').qtip({
//                    content: function() {
//                        var vars = $(this).data('vars');
//                        var tipContent = "<table class='need-border'>";
//                        $.each(vars, function(varKey, varValue) {
//                            if (varValue) {
//                                tipContent += "<tr><td class='label'>" + varKey + "</td><td>" + varValue + "<td/></tr>";
//                            }
//                        });
//                        tipContent += "</table>";
//                        return tipContent;
//                    },
//                    position: {
//                        at: 'bottom left',
//                        adjust: {
//                            x: 3
//                        }
//                    }
//                });
//                // end qtip
//            },
//            close: function() {
//                $('#workflowTraceDialog').remove();
//            },
//            width: document.documentElement.clientWidth * 0.95,
//            height: document.documentElement.clientHeight * 0.95
//        });

    });
}
