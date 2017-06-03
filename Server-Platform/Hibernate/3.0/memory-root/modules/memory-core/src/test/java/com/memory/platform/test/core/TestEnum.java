package com.memory.platform.test.core;

import java.util.Arrays;

public class TestEnum {

	public static void main(String[] args) {
		System.out.println(FlowEnum.FlOW_NAME.getValue());
		System.out.println(Arrays.toString(FlowEnum.values()));
	}
	
	public enum FlowEnum {
		/**
		 * 
		 */
		FlOW_NAME("transition"),
		/**
		 * dasf
		 */
		FLOW_TYPE("transitionType"),
		/**
		 * aa
		 */
		FLOW_ID("transitionId");
		
		
		private String flag;
		private FlowEnum(String flag) {
			this.flag = flag;
		} 
		
		public String getValue() {
			return flag;
		}
	}
	
}


