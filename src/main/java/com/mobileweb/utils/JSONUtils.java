package com.mobileweb.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JSONUtils {

	public static final String STATUS = "status";
	public static final String SUCCESS = "success";
	public static final String FAILURE = "failure";
	public static final String ERROR_CODE = "error_code";
	public static final String DATA = "data";
	public static final String MSG = "message";

	public static ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(ServiceUtils.dbFormat);
		return mapper;
	}

	public static JSONObject getResJsonObj(boolean isSuccess) {
		JSONObject ret = new JSONObject();
		if (isSuccess) {
			ret.put(STATUS, SUCCESS);
		} else {
			ret.put(STATUS, FAILURE);
		}
		return ret;
	}

	public static List<String> genListFromJsonArray(String data) {
		JSONArray array = new JSONArray(data);
		List<String> ret = new ArrayList<String>();
		try {
			for (int i = 0; i < array.length(); i++) {
				ret.add(array.getJSONObject(i).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static <E> List<E> genListObject(List<String> data, Class<E> clazz) {
		ObjectMapper mapper = getObjectMapper();
		E user = null;
		List<E> retList = new ArrayList<E>();
		try {
			for (String string : data) {
				user = (E) mapper.readValue(string, clazz);
				retList.add(user);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retList;
	}

	public static <E> E genObject(String data, Class<E> clazz) {
		ObjectMapper mapper = getObjectMapper();
		E user = null;
		try {
			user = (E) mapper.readValue(data, clazz);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	public static String genReponse(Serializable entity) {
		ObjectMapper mapper = getObjectMapper();
		String ret = "";
		try {
			if (entity != null) {
				JSONObject successJsonObj = getResJsonObj(true);
				String data = mapper.writeValueAsString(entity);
				successJsonObj.put(DATA, (new JSONObject(data)));
				ret = successJsonObj.toString();
			} else {
				ret = Constants.ErrorCode.ENTITY_NOT_EXIST.getResponse();
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return pretty(ret);
	}

	public static String genReponse(List<? extends Serializable> entity) {
		ObjectMapper mapper = getObjectMapper();
		String ret = "";
		try {
			JSONObject successJsonObj = getResJsonObj(true);
			String data = mapper.writeValueAsString(entity);
			System.out.println("Data " + data);
			successJsonObj.put(DATA, (new JSONArray(data)));
			ret = successJsonObj.toString();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return pretty(ret);
	}

	public static String genJSONFromMap(Map<String, ? extends Object> data) {
		ObjectMapper mapper = getObjectMapper();
		ObjectNode createObjectNode = mapper.createObjectNode();
		String ret = "";
		try {
			for (String key : data.keySet()) {
				createObjectNode.put(key, data.get(key).toString());
			}
			ret = createObjectNode.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pretty(ret);
	}

	public static String genJSON(String... data) {
		ObjectMapper mapper = getObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		ObjectNode createObjectNode = mapper.createObjectNode();
		String ret = "";
		try {
			for (int i = 0; i < data.length; i += 2) {
				createObjectNode.put(data[i], data[i + 1]);
			}
			ret = createObjectNode.toString();
			// ret = createObjectNode.textValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pretty(ret);
	}

	public static String genJSONArrayFromList(String... data) {
		ObjectMapper mapper = getObjectMapper();
		ArrayNode createObjectNode = mapper.createArrayNode();
		String ret = "";
		try {
			for (int i = 0; i < data.length; i++) {
				createObjectNode.add(data[i]);
			}
			ret = createObjectNode.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pretty(ret);
	}

	public static String pretty(String s) {
		return s.replace("\\", "");
	}

}
