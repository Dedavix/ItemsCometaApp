<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<style>
html, body {
	height: 100%;
	width: 100%;
}

table, th, td {
	border: 1px solid black;
}

a {
	color: #7FFFD4;
}

.bg {
	/* The image used */
	background-image: url(img/capelli-background.jpg);
	width: 100%;
	height: auto;
	color: #dc05f1;
}
</style>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body class="bg">
	<div align="right">
		<h1 align="left">Tabella Articoli:</h1>
		<p>${msg}</p>
		<div align="left" style="margin-top: 40px;">
			<p align="left">
				<a href="Modifica"><input type=button value="Aggiungi Articolo"></a>
			</p>
		</div>
		<form name="form" method="get" action ="TabellaArticoli">
			<b>Filtra Articoli:</b> <input type="text" name="filtro"
				value="${sessionScope.filtroSes}"><br>
			<br>
			<br>
		</form>
	</div>
	<table style="width: 100%">
		<tr>
			<th>Codice</th>
			<th>Descrizione</th>
			<th>Quantita Totale</th>
			<th>Azione
			<th>
		</tr>
		<c:forEach items="${items}" var="articolo">
			<tr>
				<td><a href="TabellaLotti?idArticolo=${articolo.id}">${articolo.codice}</a></td>
				<td><a href="TabellaLotti?idArticolo=${articolo.id}">${articolo.descrizione}</a></td>
				<td><a href="TabellaLotti?idArticolo=${articolo.id}">${articolo.quantita_tot}</a></td>
				<td align="center"><a href="Modifica?idArticolo=${articolo.id}"><input
						type="button" value="Modifica"></a></td>
			</tr>
		</c:forEach>
	</table>
	<div align="left" style="margin-down: 50px;">
		<p>
			<a href="Modifica"><input type=button value="Aggiungi Articolo"></a>
		</p>
	</div>
</body>
</html>