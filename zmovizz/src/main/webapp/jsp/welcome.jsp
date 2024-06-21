<%@page import="com.zmovizz.persistance.DbConnector"%>
<%@page import="com.zmovizz.models.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h3> Z Movizzzz</h3>
<% DbConnector db = new DbConnector();
String name = db.getUser(1).getName();
%>
<h3> <%= name %></h3>
</body>
</html>