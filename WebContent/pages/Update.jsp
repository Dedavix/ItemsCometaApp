<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Modifica/Inserisci Articolo</h1>
	<p>${msg}</p>
	<div align="left" style="margin-top: 50px;">
		<form method="post" action="Modifica">
		<input type = "hidden" name = "idArticolo" value ="${idArticolo}">
			Codice : <input type="text" name="codiceArticolo" value="${codiceArticolo}"><br>
			<br>
			<br> Descrizione : <input type="text" name="descrizioneArticolo"
				value="${descrizioneArticolo}"><br>
			<br>
			<br> <input type="submit" value="OK">
		</form>
	</div>

</body>
</html>