/** 
说 明
  OSS文件删除同样需要调用OSS的client对象。
  无论是上传或是删除都需要初始化client对象。

OSS文件删除
  1. 原理和上传基本类似，下面贴出删除相关方法。
  2. 调用这里的OSS删除方法另一方面不要忘记删除数据库对应的记录达到“同步删除”的目的。

释 疑
  你可以自己编写OSSUtils类，然后再里面编写client对象的一些基本参数
  private static final String EDP_POINT = "oss-cn-shenzhen.aliyuncs.com";					//OSS外网域名
	private static final String ACCESS_KEY_ID = "LTAIkAN6zDcenx3x";				//AccessKey 获取方法文档中有说明
	private static final String ACCESS_KEY_ID_SCRECT = "2pZ9Ont3LgHt5WQz3NzRFlC47wPzrG";		//ScrectKey 获取方法文档中有说明
	private static final String BUCKET_NAME = "hekunlin";				//空间名  创建空间时自定义的名称
  
  // 上传方法...
  // 获取URL方法...
  // 单个删除方法...
  // 批量删除方法...
  // 碎片上传、进度条等等其他方法...
  
  初始化cilent很简单，在使用时直接写
    OSSClient client = new OSSClient(EDP_POINT, ACCESS_KEY_ID, ACCESS_KEY_ID_SCRECT);
   即可完成client的初始化
*/

/**
	 * @title 删除单个Object
	 * @author HeKunLin 2017年5月24日 上午10:34:15
	 * @param key
	 * @return
	 */
	public static int deleteObject(String key){
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(EDP_POINT, ACCESS_KEY_ID, ACCESS_KEY_ID_SCRECT);
		// 删除Object
		ossClient.deleteObject(BUCKET_NAME, key);
		ossClient.shutdown();
		return 1;
	}
	
	/**
	 * @title 删除多个Object、批量删除
	 * @author HeKunLin 2017年5月24日 下午2:20:47
	 * @param deleteObjectsRequest
	 * @return
	 */
	public static int deleteObjects(DeleteObjectsRequest deleteObjectsRequest) {
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(EDP_POINT, ACCESS_KEY_ID, ACCESS_KEY_ID_SCRECT);
		// 如果放入deleteObjectsRequest是一个List<String>就不需要解析了；这里因为传过来的是一个字符串所以必须解析keys再执行OSS删除
		String keys = deleteObjectsRequest.getKey();
		keys = keys.replaceAll("\'", "");
		List<String> objectKeys = new ArrayList<String>();
	        String arrs [] = keys.split(",");
	        for(int i=0; i<arrs.length; i++) {
	     		objectKeys.add(arrs[i]); // 这才是批量删除用到的真正的keys，它必须是一个List
	        }
		// 解析完成执行OSSClient的deleteObjects方法
		DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(BUCKET_NAME).withKeys(objectKeys));
		// 关闭client
		ossClient.shutdown();
		return 1;
	}
----------
	/**
	 * 下面例子说明 执行批量删除时传入的DeleteObjectsRequest对象
	 */
	@SuppressWarnings("null")
	@Override
	public int confirmDelAll(HttpServletRequest request, String id, String keys) {
		// 将所有id解析并执行本地数据库的相关记录的删除
		String arrays = id;
		// 执行本地数据库删除
		executeSql("DELETE FROM `vr_source` WHERE `vr_source`.`id` IN(" + arrays + ")"); // id格式为  '2','4','5','7' 这种
		// 执行OSS批量删除
	    	DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest("hekunlin"); // 传入的“hekunlin”为你的BUCKET_NAME
	   	/** 将需要删除的key放入DeleteObjectsRequest对象，在OSSUtils去解析keys （本例的做法）; 
		    当然你也可以直接在这里将keys封装为List、name在代用OSSUtils方法是就不需要再多做处理了（推荐）。
		    如果需要自己进行更加完善的封装，请参考阿里云对象存储相关API
		*/
		deleteObjectsRequest.setKey(keys); 
		OssUtils.deleteObjects(deleteObjectsRequest);
		return 1;
	}



