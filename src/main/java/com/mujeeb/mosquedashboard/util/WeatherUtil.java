package com.mujeeb.mosquedashboard.util;

import com.mujeeb.mosquedashboard.beans.TempreatureBean;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WeatherUtil {
	
	private static final String SECRET_KEY = "b137096fb04fd9916bd6cd4af80aafb5";

	public static TempreatureBean getCurrentTempreature(String latitude, String longitude) throws Exception {
		
		String url = "https://api.darksky.net/forecast/" + SECRET_KEY + "/" + latitude + "," + longitude;

		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuilder result = new StringBuilder();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		JSONObject object = new JSONObject(result.toString());
		JSONObject current = object.getJSONObject("currently");
		String tempreature = current.getString("temperature");
		long temp = convertToCelcius(tempreature);
		String color = "cyan";
		if(temp <= 25) {
			color = "cyan"; 
		} else if(temp > 25 && temp <= 30) {
			color = "yellow";
		} else if(temp > 30 && temp <= 35) {
			color = "orange";
		} else {
			color = "red";
		}
		
		return new TempreatureBean("" + temp, color);
	}
	
	private static long convertToCelcius(String tempreature) {

		double temp = 25;
		try {
			temp = Double.parseDouble(tempreature);
			temp = (temp-32)/1.8;
		}catch(Exception ex) {
			/*Do Nothing*/
		}
		return Math.round(temp);
	}

	public static void main(String[] args) throws Exception {
		System.out.println(getCurrentTempreature("23.0201818","72.4396547").getTempreature());
	}
}
