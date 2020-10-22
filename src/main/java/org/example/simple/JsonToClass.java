package org.example.simple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonToClass {
	public void run() throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		String carJsonString = "{ \"brand\" : \"Mercedes\", \"doors\" : 5 }";
		String tokenJsonString = "{\"valid\":false,\"data\":\"Not Authorized\"}";
		String tokenJsonString2 = "{\"valid\":true,\"data\":[{\"status\":\"success\",\"token\":\"102e4ee6\"}]}";

		JsonNode carJsonNode = mapper.readTree(carJsonString);
		JsonNode tokenJsonNode = mapper.readTree(tokenJsonString);
		JsonNode tokenJsonNode2 = mapper.readTree(tokenJsonString2);

		Car car = mapper.convertValue(carJsonNode, Car.class);
		KoToken token = mapper.convertValue(tokenJsonNode, KoToken.class);
		KoToken token2 = mapper.convertValue(tokenJsonNode2, KoToken.class);

		System.out.println("car brand = " + car.getBrand());
		System.out.println("car doors = " + car.getDoors());

		System.out.println("token = " + tokenJsonNode.toString());
		System.out.println("valid = " + token.valid);
		System.out.println("data = " + token.data);
		System.out.println("getToken = " + token.getToken());

		System.out.println("token2 = " + tokenJsonNode2.toString());
		System.out.println("valid = " + token2.valid);
		System.out.println("data = " + token2.data);
		System.out.println("getToken = " + token2.getToken());

		Iterator<Entry<String, JsonNode>> fields = tokenJsonNode2.fields();
		while (fields.hasNext()) {
			Entry<String, JsonNode> jsonField = fields.next();
			System.out.println("key=" + jsonField.getKey() + " value=" + jsonField.getValue());
		}

		System.out.println("findvalue = " + tokenJsonNode2.findPath("valid").asBoolean());
		System.out.println("findvalue = " + tokenJsonNode2.findPath("toke").asText().equals("102e4ee6"));
	}

	public static void main(String[] args) throws IOException {
		JsonToClass app = new JsonToClass();
		app.run();
	}

	public static class Car {
		private String brand = null;
		private int doors = 0;

		public String getBrand() {
			return this.brand;
		}

		public void setBrand(String brand) {
			this.brand = brand;
		}

		public int getDoors() {
			return this.doors;
		}

		public void setDoors(int doors) {
			this.doors = doors;
		}
	}
}

class KoToken {
	public Boolean valid;
	public Object data;

	/**
	 * 
	 * @return
	 */
	public boolean isValid() {
		if (valid == null) {
			return false;
		}
		return valid.booleanValue();
	}

	public String getToken() {
		if (!isValid()) {
			return null;
		}

		try {
			ArrayList<?> dataList = (ArrayList<?>) data;
			Map<?, ?> dataMap = (Map<?, ?>) dataList.get(0);
			return (String) dataMap.get("token");
		} catch (Exception e) {
			return null;
		}
	}
}
