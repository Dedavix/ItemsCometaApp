<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
table, th, td {
	border: 1px solid black;
}</style>
</head>
<body>
<div align = "left" style = "margin-top:50 px;">
	<h1>Righe Documento:</h1><br><br>
	</div>
	<table style="width: 100%">
		<tr>
			<th>Codice Articolo</th>
			<th>Descrizione</th>
			<th>Codice Lotto</th>
			<th>Quantita</th>
		</tr>
		<c:forEach items="${listaRighe}" var="riga">
			<tr>
				<td>${riga.codiceArticolo}</td>
				<td>${riga.descrizioneArticolo}</td>
				<td>${riga.codiceLotto}</td>
				<td>${riga.quantita}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>