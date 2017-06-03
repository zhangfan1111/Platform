
var uploader = FileUpload.Render({
	 ContainerId : 'container',
	 BrowseButton : 'addfiles',
	 MaxFileSize : '100mb',
	 MimeTypes : [
					//{title : "Image files", extensions : "jpg,gif,png"},
					{title : "files", extensions : "*"}
				],
	 PostInit : function(){
		/*$('#files-content').html('');
		$('#uploadfiles').click(function() {
			
			uploader.start();
			return false;
		});*/
	 },
	 FilesAdded : function(up, files){
//		 $('#files-content').parent().show();
//		 plupload.each(files, function(file) {
//			//<div>名称(10.1M) 90% 1.2K/s 00:02:03 <a href="javascript:delete_file();">删除</a></div>
//			var html = '<div id="'+file.id+'"><label id="'+file.id+'label">'+file.name+'</label><a href="javascript:uploader.delete_file(\''+file.id+'\');">删除</a></div>';
//			$('#files-content').append(html);
//		});
		uploader.start();
	 },
	 UploadProgress: function(up, file){
		//<div>名称(10.1M) 90% 1.2K/s 00:02:03 <a href="javascript:delete_file();">删除</a></div>
		// timeCost timeCostStr upSpeed
//		var html = file.name+'('+uploader.filesize(file.size)+') '+(file.percent<100?file.percent:99)+'% '+file.upSpeed+' '+file.timeCostStr;
//		$('#'+file.id+'label').html(html);
		 $.messager.progress({
		        title:'请稍等...',
		        msg:'图片上传中...'
		    });
	 },
	 FileUploaded : function(up, file, info){
		$.messager.progress('close');
		var data = $.parseJSON(info.response);
		if(data.status) {			
			$('#person_img').attr('src', data.fileUrl);
			$('#person_img').attr('key', data.fileId);
		}
		
	 },
	 Error: function(up, err){
		 
	 },
	 DeleteFile : function(clientid,serverid){
		 
	 }
});