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
		// 关闭client
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
		// 解析keys并执行OSS删除
		String keys = deleteObjectsRequest.getKey();
		keys = keys.replaceAll("\'", "");
		List<String> objectKeys = new ArrayList<String>();
	    String arrs [] = keys.split(",");
	    for(int i=0; i<arrs.length; i++) {
	    	objectKeys.add(arrs[i]);
	    }
		DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(BUCKET_NAME).withKeys(objectKeys));
		// List<String> deletedObjects = deleteObjectsResult.getDeletedObjects(); // 获取删除的对象
		// System.out.println(deletedObjects);
		// 关闭client
		ossClient.shutdown();
		return 1;
	}


