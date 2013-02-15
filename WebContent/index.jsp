<%@ page language="java" contentType="text/html; charset=utf-8"
	import="cn.edu.ruc.aqi.*,java.util.*" pageEncoding="utf-8"%>
<%
	String cityName = (String)request.getParameter("city");
	CityData data = DataFactory.getInstance().getData(cityName);
	if (data == null)
	{
		out.println("还没有爬这个城市的数据~");
	}
	else
	{
		out.println(data.getCityName());
		out.println(data.getAQI());
		out.println(String.format("%.2f",data.getPm25h()));
		out.println(String.format("%.2f",data.getPm25d()));
		List<StationData> stationList = data.getStationDataList();
		out.println(stationList.size());
		for (StationData item : stationList)
		{
			out.println(item.getStationName());
			out.println(item.getAQI());
			out.println(item.getPm25h());
			out.println(item.getPm25d());
		}
		System.out.println(cityName);
		out.println(data.getMaxTemp());
		out.println(data.getMinTemp());
		out.println(data.getWeather());
		if (data.getTip() == null)
		{
			//out.println("我是一个小贴士，请到aqicn.sinaapp.com/tip.jsp为我设置要显示的内容~");
			out.println("新年快乐~");
		}
		else
		{
			out.println(data.getTip());
		}
	}
%>