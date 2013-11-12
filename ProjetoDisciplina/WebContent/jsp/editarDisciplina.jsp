<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="controle.*"%>
<jsp:useBean class="dominio.Disciplina" id="disciplina" scope="request"></jsp:useBean>
<html>
<head>
  <title>Disciplina</title><meta http-equiv="Content-Style-Type" content="text/css">
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <meta http-equiv="Pragma" content="no-cache">
  <meta http-equiv="Expires" content="Mon, 06 Jan 1990 00:00:01 GMT">
  <meta http-equiv="Cache-Control" content="no-store">
  <meta http-equiv="Expires" content="-1">
  <link rel="stylesheet" type="text/css" href="/matricula/css/estilo.css">
</head>
<body>
<%
  String id = null;
  if (disciplina.getId() != null)
     id = disciplina.getId().toString();
%>
<h1>Edição de Disciplina<h1>
<form name="formDados" method="post" action="salvarDisciplina">
<input type="hidden" name="id" value="<%= id %>">
	<table border="1" cellspacing="0" cellpadding="2">
	  <tr>
	     <th>Codigo:</td>
	     <td><input type="text" name="codigo" value="<%= disciplina.getCodigo() %>" size="8" maxlength="6"></td>
	  </tr>
	  <tr>
	     <th>Nome:</td>
	     <td><input type="text" name="nome" value="<%= disciplina.getNome() %>" size="50" maxlength="50"></td>
	  </tr>
	</table>
	<input type="submit" name="Confirmar" value="Confirmar">
	<input type="submit" name="Cancelar" value="Cancelar">
</form>
</body>
</html>