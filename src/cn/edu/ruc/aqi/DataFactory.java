package cn.edu.ruc.aqi;

import java.util.HashMap;
import java.util.Map;


public class DataFactory {
	private static DataFactory instance = new DataFactory();
	private long lastUpdate = 0;
	private Map<String, CityData> dataMap = null;
	
	private DataFactory() {
		lastUpdate = System.currentTimeMillis();
		dataMap = new HashMap<String, CityData>();
		updateData();
	}

	public static DataFactory getInstance() {
		return instance;
	}
	
	public void addData(String name, CityData data) {
		dataMap.put(name, data);
	}
	
	public void updateData() {
		dataMap.clear();
		Crawler crawler = new Crawler();
		crawler.setURL("http://www.pm2d5.com/city/beijing.html");
		dataMap.put("beijing", crawler.getWeather(crawler.getDatafromPM2D5(), "101010100"));
		crawler.setURL("http://www.pm2d5.com/city/tianjin.html");
		dataMap.put("tianjin", crawler.getWeather(crawler.getDatafromPM2D5(), "101030100"));
	}
	
	public CityData getData(String name) {
		if (System.currentTimeMillis() - lastUpdate > 3600 * 1000) {
			updateData();
			lastUpdate = System.currentTimeMillis();
		}
		return dataMap.get(name);
	}
}
