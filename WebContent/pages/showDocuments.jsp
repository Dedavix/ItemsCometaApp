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
	<div>
		<h1 align="left">Tabella Documenti:</h1>
		<p>${msg}</p>
		<div align="left" style="margin-top: 40px;">
			<p align="left">
				<a href="Inserisci"><input type=button
					value="Aggiungi Documento"></a>
			</p>
		</div>
	</div>
	<div align = "right">
	<form name="form" method="post" action ="/ItemsApp/MostraDocumenti">
	        <p align ="left">Filtro: </p>
			<select name="profilo" required="required" style='margin:5px'>
				<c:forEach items="${profiles}" var="profile">
					<option value="${profile.id}">${profile.codice}</option>
				</c:forEach>
			</select>
				Da: <input type="date" name="data1">
				A: <input type = "date" name="data2">
				<br>
				<input type = "submit" value = "VAI">
				<br><br>
		</form></div>
	<table style="width: 100%">
		<tr>
			<th>Codice</th>
			<th>Progressivo</th>
			<th>Data</th>
		</tr>
		<c:forEach items="${listaDocumenti}" var="documento">
			<tr>
				<td><a href="mostraRighe?idDocumento=${documento.id}">${documento.profilo}</a></td>
				<td><a href="mostraRighe?idDocumento=${documento.id}">${documento.progressivo}</a></td>
				<td><a href="mostraRighe?idDocumento=${documento.id}">${documento.data}</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>