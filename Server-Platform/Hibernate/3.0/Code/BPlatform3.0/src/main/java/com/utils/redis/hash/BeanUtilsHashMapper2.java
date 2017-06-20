package com.utils.redis.hash;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.springframework.data.redis.hash.HashMapper;

/**
 * <p>HashMapper based on Apache Commons BeanUtils project. Does NOT supports nested properties.</p>
 * <p>Supports util Date.</p>
 * 
 * @author memory 2017年6月20日 下午2:17:22
 * @version V1.0   
 * @param <T>
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2017年6月20日
 * @modify by reason:{方法名}:{原因}
 */
public class BeanUtilsHashMapper2<T> implements HashMapper<T, String, String> {

	private Class<T> type;

	public BeanUtilsHashMapper2(Class<T> type) {
		this.type = type;
	}

	@Override
	public T fromHash(Map<String, String> hash) {

		T instance = org.springframework.beans.BeanUtils.instantiate(type);
		
		//ConvertUtils.register(new DateLocaleConverter(), Date.class);  
	    ConvertUtils.register(new Converter()    
	    {    
	        @SuppressWarnings("rawtypes")    
	        @Override    
	        public Object convert(Class arg0, Object arg1)    
	        {    
	            if(arg1 == null)    
	            {    
	                return null;    
	            }    
	            if(!(arg1 instanceof String))    
	            {    
	                throw new ConversionException("只支持字符串转换 !");    
	            }    
	            
	            String str = (String)arg1;    
	            if(str.trim().equals(""))    
	            {    
	                return null;    
	            }    
//	                 
	            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
	            Timestamp ts = null;
	            try{    
	            	ts = new Timestamp(sd.parse(str).getTime());
	                return ts; 
	            }    
	            catch(ParseException e)    
	            {    
	            	SimpleDateFormat sd1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
	            	try {
	            		ts = new Timestamp(sd1.parse(str).getTime());
		                return ts; 
					} catch (ParseException e1) {
						throw new RuntimeException(e1); 
					}
	            }  
	                 
	        }    
	             
	    }, java.util.Date.class);  
		
		try {
			BeanUtils.populate(instance, hash);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.redis.hash.HashMapper#toHash(java.lang.Object)
	 */
	@Override
	public Map<String, String> toHash(T object) {
		try {

			Map<String, String> map = BeanUtils.describe(object);

			Map<String, String> result = new LinkedHashMap<String, String>();
			for (Entry<String, String> entry : map.entrySet()) {
				if (entry.getValue() != null) {
					result.put(entry.getKey(), entry.getValue());
				}
			}
			if(result.containsKey("class")) {
				result.remove("class");
			}
			return result;

		} catch (Exception ex) {
			throw new IllegalArgumentException("Cannot describe object " + object, ex);
		}
	}
}
