说明
  OSS文件上传需要调用OSS的client对象，这个对象封装了对象存储的所有方法，需要详细了解朋友可以去研究。 
  本示例主要演示文件上传流程

阿里OSS文件上传步骤
  1. 准备需要上传的form表
  2. 编写文件上传方法(本例中form表单以easyUI框架为例)
  3. 编写SpringMVC控制层
  4. 调用编写的OSSUtils类中OSS文件上传方法（包括文件上传及上传成功后获取url）
  
----------
    // 1: 需要上传的form
    <form method="post" class="file-form">
    	<input id="logo_filebox" class="easyui-filebox file-input" data-options="buttonText:'选择图片',onChange:OSSfileUpload,width:250,height:30,accept:'image/png,image/jpeg'," name="file" ><br>
    	// 当然，这里也可以有其他的一些标签，上传时放到formData中后台去接收就行了
    </form>
    
----------
    // 2: OSS文件上传方法
    function OSSfileUpload(newValue, oldValue){
    	if(!extend.isblank(newValue)){
    		var from = $(this).parent();
    		var formData = new FormData(from[0]);
    		parent.$.messager.progress();
    		$.ajax({
    	type: "POST",
    	url: system.contextPath + '/whcd/manage/shops/uploadOSSImage',  // 上传formData对象路径
    	data: formData,
    	cache: false,
    	contentType: false,
    	processData: false,
    	dataType: "json",
    	error: function(data) {
    		parent.$.messager.progress('close');
    		parent.$.messager.show({title : "提示",msg : "上传失败"});
    	},
    	success: function(data) {
    		// your idea ... 
    	}
    	});
    	}
    }
    
----------
    // 3: Controller层
    @RequestMapping(value = "/uploadOSSImage")
    @ResponseBody
    public Object uploadOSSImage(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
    	Map<String, Object> m = new HashMap<String, Object>();
    	try {
    		// 调用OssUtils中的方法
    		m = OssUtils.uploadOSSImage(file);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return m;
    }
   
 ---------- 
    // 4: OssUtils中的文件上传方法-->即最基础的阿里云OSS文件上传
    public static Map<String, Object> uploadOSSImage(MultipartFile file) throws Exception {
    	// 初始化OSSClient、获取文件输入流、创建上传Object的Metadata以及必须设置的ContentLength
    	OSSClient client = new OSSClient(EDP_POINT, ACCESS_KEY_ID, ACCESS_KEY_ID_SCRECT);
    	InputStream content = file.getInputStream();
    	ObjectMetadata meta = new ObjectMetadata();
    	meta.setContentLength(file.getSize());
    	// 获取文件名称以及给文件新命名<根据自己的需求做出调整即可>
    	String fileName = file.getOriginalFilename();
    	String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
    	String UID = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
    	String newFileName = "VrSource/" + "/Image" + UID + "."+fileType;
    	// 调用client的putObject方法，上传Object到阿里云
    	client.putObject(BUCKET_NAME, newFileName, content, meta);
    	// 封装上传成功后的参数
    	Map<String, Object> data = new HashMap<String, Object>();
    	data.put("key", newFileName);
    	// 调用getResourceUrl获取上传成功后文件的URL地址
    	data.put("url", getResourceUrl(newFileName));
    	client.shutdown();
    	return data;
    }

----------
    // 5: 通过key获取文件url
    public static String getResourceUrl(String key) {
    	OSSClient client = new OSSClient(EDP_POINT, ACCESS_KEY_ID, ACCESS_KEY_ID_SCRECT);
    	Date expiration = new Date(new Date().getTime() + 36000 * 1000);	// URL过期时间设置为10个小时
    	URL url = client.generatePresignedUrl(BUCKET_NAME, key, expiration);
    	try {
    		return url.toURI().toString();
    	} catch (URISyntaxException e) {
    		return null;
    	} finally {
    		client.shutdown();
    	}
    }
