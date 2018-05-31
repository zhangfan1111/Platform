package com.k2data.k2app.domain;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by clb on 17-6-15.
 */
@Data
public class RectObject {
	private Integer code;
	private String message;
	private Map result;
	private Object pageInfo;

	public RectObject initDeviceRectMap(){
		RectObject rest = new RectObject();
		rest.setCode(20000);
		rest.setMessage("success");
		rest.setResult(new HashMap());
		return rest;
	}
}
