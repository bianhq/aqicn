<%@ page language="java" contentType="text/html; charset=utf-8"
	import="cn.edu.ruc.aqi.*,java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AQICN</title>
</head>
<body>
<%
String cityName = (String)request.getParameter("city");
CityData data = DataFactory.getInstance().getData(cityName);
String tip = (String)request.getParameter("tip");
data.setTip(tip);
%>
<p align="center">修改成功！</p>
<p align="center"><a href="tip.jsp">返回</a></p>
</body>
</html>