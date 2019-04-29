package otj;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class ObjectToJson{

	private <E> String createJsonString(String name, JSONArray array) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(name, array);
		return jsonObject.toJSONString();
	}

	public String toJson(byte[] byteArray) {
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < byteArray.length; i++) {
			jsonArray.add(byteArray[i]);
		}
		return createJsonString("byte", jsonArray);
	}

	public String toJson(short[] shortArray) {
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < shortArray.length; i++) {
			jsonArray.add(shortArray[i]);
		}
		return createJsonString("short", jsonArray);
	}

	public String toJson(int[] intArray) {
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < intArray.length; i++) {
			jsonArray.add(intArray[i]);
		}
		return createJsonString("int", jsonArray);}

	public String toJson(long[] longArray) {
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < longArray.length; i++) {
			jsonArray.add(longArray[i]);
		}
		return createJsonString("long", jsonArray);}

	public String toJson(double[] doubleArray) {
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < doubleArray.length; i++) {
			jsonArray.add(doubleArray[i]);
		}
		return createJsonString("double", jsonArray);}

	public String toJson(float[] floatArray) {
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < floatArray.length; i++) {
			jsonArray.add(floatArray[i]);
		}
		return createJsonString("float", jsonArray);}

	public String toJson(char[] charArray) {
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < charArray.length; i++) {
			jsonArray.add(charArray[i]);
		}
		return createJsonString("char", jsonArray);}

	public String toJson(boolean[] booleanArray) {
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < booleanArray.length; i++) {
			jsonArray.add(booleanArray[i]);
		}
		return createJsonString("boolean", jsonArray);}

	public <T> String toJson(T[] objectArray) {
		String name = objectArray.getClass().getSimpleName().replace(']', ' ').replace('[', ' ').trim();
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(Arrays.asList(objectArray));
		return createJsonString(name, jsonArray);}

	public String toJson(Collection<? extends String> collection) {
		return collection.getClass().getSimpleName();
	}
	public String toJson(Map map) {
		return map.getClass().getSimpleName();
	}
}
