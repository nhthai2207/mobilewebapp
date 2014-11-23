package com.mobileweb.utils;

import org.json.JSONObject;

public class Constants {
	public static final String BASE_URL = "http://ec2-54-69-36-245.us-west-2.compute.amazonaws.com/mobileweb/";
		
	public static enum ErrorCode {
		NOTFOUND_404(404, "This reource is not exist"), 
		NOTFOUND_400(400, "Request has not good URI syntax"),
		ENTITY_NOT_EXIST(1001, "Entity Not Exist"), 
		SUBSCIBER_NOT_EXIST(1002, "Subscriber Not Exist"), 
		EXCEPTION(1003, "Unknown Prblem. Please contact the provider for more support."),
		DB_RELATION_CONSTRAIN(1004, "Please check your data. Maybe it's using (reference to) an un-exist object");
				
		private final int code;
		private final String msg;

		ErrorCode(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public int getCode() {
			return this.code;
		}

		public String getMsg() {
			return this.msg;
		}

		public String getResponse() {
			JSONObject failJsonObj = JSONUtils.getResJsonObj(false);
			failJsonObj.put(JSONUtils.ERROR_CODE, this.code);
			failJsonObj.put(JSONUtils.MSG, this.msg);
			String ret = failJsonObj.toString();
			return ret;
		}
	}
}
