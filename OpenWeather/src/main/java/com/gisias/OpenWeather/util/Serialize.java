package com.gisias.OpenWeather.util;

import java.util.Vector;

import com.gisias.OpenWeather.model.Weather;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Class that contains methods for serialization
 * 
 * @authour Andrea Iasenzaniro
 * @authour Carlo Gissi
 *
 */
public class Serialize {
	
	/**
	 * Method that serializes a JsonObject into a Weather object
	 * 
	 * @param toSerialize input string from which to extract fields
	 * @return JSON string of the serialized object
	 */
	public static String currentSerializer(String toSerialize) {
		Weather weather = new Weather();
		JsonElement jelement = JsonParser.parseString(toSerialize);
	    JsonObject wth = jelement.getAsJsonObject();
	    
	    weather.setCityName(wth.get("name").toString());
	    weather.setDt(wth.get("dt").getAsLong());
	    
	    JsonObject clouds = wth.getAsJsonObject("clouds");
	    weather.setClouds(clouds.get("all").getAsDouble());
	    
	    JsonObject main = wth.getAsJsonObject("main");
	    weather.setFeels_like(main.get("feels_like").getAsDouble());
	    weather.setTemp(main.get("temp").getAsDouble());
	    weather.setTempMax(main.get("temp_max").getAsDouble());
	    weather.setTempMin(main.get("temp_min").getAsDouble());
	    
	    JsonObject wind = wth.getAsJsonObject("wind");
	    weather.setWind_speed(wind.get("speed").getAsDouble());
	    
	    JsonObject coord = wth.getAsJsonObject("coord");
	    weather.setLat(coord.get("lat").getAsDouble());
	    weather.setLon(coord.get("lon").getAsDouble());
	    
	    Gson gson = new Gson();
		String weath = gson.toJson(weather);
		return weath;
	}

	/**
	 * Method that serializes a JsonObject into a list of Weather objects
	 * 
	 * @param toSerialize input string from which to extract fields
	 * @param cityname name of the city to be serialized
	 * @return JSON string of the serialized objects
	 */
	public static String forecastSerializer(String toSerialize, String cityname) {
		
		Vector<Weather> weathers = new Vector<Weather>();
		
		JsonElement jelement = JsonParser.parseString(toSerialize);
	    JsonObject wth = jelement.getAsJsonObject();
	    
	    Double lat = wth.get("lat").getAsDouble();
		Double lon = wth.get("lon").getAsDouble();
	    
	    JsonArray daily = wth.getAsJsonArray("daily");
	    for(JsonElement o: daily) {
	    	if(o instanceof JsonObject) {
	    		Weather weather = new Weather();
	    		weather.setCityName(cityname);
	    		JsonObject obj = (JsonObject) o;
	    		
	    		weather.setDt(obj.get("dt").getAsLong());
	    		weather.setClouds(obj.get("clouds").getAsDouble());
	    		weather.setWind_speed(obj.get("wind_speed").getAsDouble());
	    		
	    		JsonObject feels = obj.getAsJsonObject("feels_like");
	    		weather.setFeels_like(feels.get("day").getAsDouble());
	    		
	    		JsonObject temp = obj.getAsJsonObject("temp");
	    		weather.setTemp(temp.get("day").getAsDouble());
	    		weather.setTempMax(temp.get("max").getAsDouble());
	    		weather.setTempMin(temp.get("min").getAsDouble());
	    		
	    		weather.setLat(lat);
	    		weather.setLon(lon);
	    		
	    		weathers.add(weather);
	    	}
	    }
	    
	    Gson gson = new Gson();
		String weath = gson.toJsonTree(weathers).toString();
		return weath;
	}
}