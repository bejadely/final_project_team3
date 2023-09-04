package com.trip.finalProject.api;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.trip.finalProject.lodging.service.LodgingVO;

public class ReadJson {

	public static List<LodgingVO> readJson(String jsonData) {

		List<LodgingVO> list = null;

		try {

			JSONParser parser = new JSONParser();

			JSONObject obj = (JSONObject) parser.parse(jsonData);

			// response 가져오기
			JSONObject parse_response = (JSONObject) obj.get("response");
			// response러 부터 body 찾아오기
			JSONObject parse_body = (JSONObject) parse_response.get("body");
			// body로 부터 items 받아오기
			JSONObject parse_items = (JSONObject) parse_body.get("items");
			// items로 부터 item 받아오기
			JSONArray parse_item = (JSONArray) parse_items.get("item");

			// System.out.println(parse_item.toString());

			Gson gson = new Gson();

			TypeToken<List<LodgingVO>> typeToken = new TypeToken<List<LodgingVO>>() {
			};

			list = gson.fromJson(parse_item.toString(), typeToken.getType());

			
			for(LodgingVO lodgingVO : list) {
				System.out.println(lodgingVO); 
				}
			 

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return list;

	}
}
