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
	<div align="right" style="margin-top: 50px;">
		<h1 align = "left">Tabella Articoli:</h1>
		<form name="form" method="post">
		    filtra articoli: 
			<input type="text" name="filtro" formaction="TabellaArticoli"><br><br><br>
		</form>
		</div>
	<table style="width:100%">
  <tr>
    <th>Codice</th>
    <th>Descrizione</th> 
    <th>Quantita Totale</th>
  </tr>
  <c:forEach items="${items}" var="articolo">
		<tr>
		    <td>${articolo.codice}</td>
		    <td>${articolo.descrizione}</td>
		    <td>${articolo.quantita_tot}</td>		    
	    </tr>
	</c:forEach>
</table>	
</body>
</html>