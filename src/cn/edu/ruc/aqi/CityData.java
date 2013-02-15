package cn.edu.ruc.aqi;

import java.util.ArrayList;

public class CityData extends Data{
	private String cityName;
	private ArrayList<StationData> stationDataList = new ArrayList<StationData>();
	private int minTemp;
	private int maxTemp;
	private String weather;
	private String tip;

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public int getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(int minTemp) {
		this.minTemp = minTemp;
	}

	public int getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(int maxTemp) {
		this.maxTemp = maxTemp;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public ArrayList<StationData> getStationDataList() {
		return stationDataList;
	}

	public void addStationData(StationData stationData) {
		stationDataList.add(stationData);
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public void updatePM25() {
		int numd = 0, numh = 0;
		double pm25d = 0, pm25h = 0;
		for (StationData item : stationDataList)
		{
			if (item.getPm25d() > 0) {
				pm25d += item.getPm25d();
				numd++;
			}
			if (item.getPm25h() > 0) {
				pm25h += item.getPm25h();
				numh++;
			}
		}
		this.setPm25d(pm25d / numd);
		this.setPm25h(pm25h / numh);
	}
}
