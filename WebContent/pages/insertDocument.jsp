<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>InserisciDocumento</title>
<style>
div {
	display: flex;
	justify-content: space-between;
}
</style>
</head>
<body>
	<h1>Inserisci Documento</h1>
	<div>
		<form method="get" action="effettuaInserimento">
			<select name="profilo" required="required" style='margin:5px'>
				<c:forEach items="${profiles}" var="profile">
					<option value="${profile.id}">${profile.codice}</option>
				</c:forEach>
			</select>
				Data Documento: <input type="date" name="data">
			<br>
			<br>  Codice Articolo: <input type="text"
				name="codiceArticolo"> Codice Lotto: <input type="text"
				name="codiceLotto">  Quantita: <input type="text"
				name="quantita"> <br>
			<br>
			<br> <input type="submit" value="OK">
		</form>
	</div>
</body>
</html>