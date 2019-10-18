<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<style>
table, th, td {
	border: 1px solid black;
}
</style>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
    <div align = "left" style = "margin-top:50 px;">
	<h1>Tabella Lotti:</h1><br><br>
	</div>
	<table style="width: 100%">
		<tr>
			<th>Codice Lotto</th>
			<th>Quantita</th>
		</tr>
		<c:forEach items="${lotti}" var="lotto">
			<tr>
				<td>${lotto.codice}</td>
				<td>${lotto.quantita}</td>
			</tr>
		</c:forEach>
	</table>
	<div align="right" style="margin-down: 50px;">
		<p><a href="index"><input type = button value ="Indietro"></a></p>
	</div>
</body>
</html>