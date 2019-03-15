package com.dfs.model;
/**
 * 系统参数枚举
 * @author taoxy 2019/1/3
 */
public enum SystemVariableEnum {

	SUCCESS("1", "操作成功"),
	FAIL("0", "操作失败");

	private String code;
	private String msg;
	
	private SystemVariableEnum(String code, String msg) {		
		this.code = code;		
		this.msg = msg;			
	}

	public String getCode() {          
		return code;      		
	}
	
	public String getMsg() {		
		return msg;
	}
	
	public static String getDescByValue(String code) {		
		for (SystemVariableEnum enums : SystemVariableEnum.values()) {		
			if (enums.getCode().equals(code)) {				
				return enums.getMsg();			
			}		
		}
		return "";	
	}
	
}
