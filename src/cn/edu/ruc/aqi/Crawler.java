package cn.edu.ruc.aqi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class Crawler {
	private String URL;

	public String getURL() {
		return URL;
	}

	public void setURL(String URL) {
		this.URL = URL;
	}

	public CityData getDatafromPM2D5() {
		CityData data = null;
		HttpClient httpclient = null;
		HttpGet httpget = null;
		HttpResponse response = null;
		InputStream instream = null;
		try {
			httpclient = new DefaultHttpClient();
			// Prepare a request object
			httpget = new HttpGet(URL);

			// Execute the request
			response = httpclient.execute(httpget);

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();

			// If the response does not enclose an entity, there is no need
			// to worry about connection release
			if (entity != null) {
				data = new CityData();
				instream = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(instream, "utf-8"));
				// do something useful with the response

				String line = "";
				int lineNum = 0;
				while ((line = reader.readLine()) != null) {
					lineNum++;
					if (lineNum == 49) {
						data.setCityName(line.substring(
								line.lastIndexOf("=") + 3,
								line.lastIndexOf("=") + 5));
						//System.out.println(data.getCityName());
					} else if (lineNum == 50) {
						data.setAQI(Integer.parseInt(line.substring(
								line.lastIndexOf("=") + 3,
								line.lastIndexOf("\""))));
						//System.out.println(data.getAQI());
					} else if (lineNum == 85) {
						data.getStationDataList().clear();
						while (!(line = reader.readLine()).equals("</table>")) {
							StationData stationData = new StationData();
							line = reader.readLine();
							stationData.setStationName(line.substring(line.indexOf(">", 5) + 1,
									line.lastIndexOf("</a>")));// 观测站名称
							line = reader.readLine();
							line = line.substring(4, line.lastIndexOf("<"));
							//System.out.println(line);
							if (line.equals("—")) {
								stationData.setAQI(0);
							} else {
								stationData.setAQI(Integer.parseInt(line));//AQI
							}
							reader.readLine();//去掉描述空气质量等级的文字
							line = reader.readLine();
							line = line.substring(4, line.lastIndexOf("μ"));
							if (line.equals("—")) {
								stationData.setPm25h(0);
							} else {
								stationData.setPm25h(Double.parseDouble(line));//PM2.5H
							}
							line = reader.readLine();
							line = line.substring(4, line.lastIndexOf("μ"));
							//System.out.println(line);
							if (line.equals("—")) {
								stationData.setPm25d(0);
							} else {
								stationData.setPm25d(Double.parseDouble(line));//PM2.5D
							}
							reader.readLine();
							reader.readLine();
							data.addStationData(stationData);
						}
						data.updatePM25();
						break;
					}
				}
			}
		} catch (IOException ex) {

			// In case of an IOException the connection will be released
			// back to the connection manager automatically

		} catch (RuntimeException ex) {

			// In case of an unexpected exception you may want to abort
			// the HTTP request in order to shut down the underlying
			// connection and release it back to the connection manager.
			httpget.abort();

		} finally {

			// Closing the input stream will trigger connection release
			try {
				instream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		// When HttpClient instance is no longer needed,
		// shut down the connection manager to ensure
		// immediate deallocation of all system resources
		httpclient.getConnectionManager().shutdown();
		return data;
	}
	
	public CityData getWeather(CityData data, String cityNo) {
		HttpClient httpclient = null;
		HttpGet httpget = null;
		HttpResponse response = null;
		InputStream instream = null;
		try {
			httpclient = new DefaultHttpClient();
			// Prepare a request object
			httpget = new HttpGet("http://www.weather.com.cn/data/cityinfo/" + cityNo + ".html");

			// Execute the request
			response = httpclient.execute(httpget);

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();

			// If the response does not enclose an entity, there is no need
			// to worry about connection release
			if (entity != null) {
				instream = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(instream, "utf-8"));
				// do something useful with the response

				String line = "";
				if ((line = reader.readLine()) != null) {
					int begin = line.indexOf("temp1")+8;
					int end = line.indexOf("℃");
					data.setMaxTemp(Integer.parseInt(line.substring(begin, end)));
					begin = line.indexOf("temp2")+8;
					end = line.indexOf("℃", end+1);
					data.setMinTemp(Integer.parseInt(line.substring(begin, end)));
					begin = line.indexOf("weather", end)+10;
					end = line.indexOf(",", begin)-1;
					data.setWeather(line.substring(begin, end));
				}
			}
		} catch (IOException ex) {

			// In case of an IOException the connection will be released
			// back to the connection manager automatically

		} catch (RuntimeException ex) {

			// In case of an unexpected exception you may want to abort
			// the HTTP request in order to shut down the underlying
			// connection and release it back to the connection manager.
			httpget.abort();

		} finally {

			// Closing the input stream will trigger connection release
			try {
				instream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		// When HttpClient instance is no longer needed,
		// shut down the connection manager to ensure
		// immediate deallocation of all system resources
		httpclient.getConnectionManager().shutdown();
		return data;
	}

	public static void main(String[] args) throws IOException {
		Crawler crawler = new Crawler();
		crawler.setURL("http://www.pm2d5.com/city/beijing.html");
		crawler.getWeather(crawler.getDatafromPM2D5(), "101010100");
	}
}
