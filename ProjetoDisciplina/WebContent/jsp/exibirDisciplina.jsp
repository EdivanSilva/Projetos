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

<h1>Cadastro de Disciplina<h1>
<b>Opções:</b>
<a href="incluirDisciplina">Incluir</a>
<a href="alterarDisciplina?id=<%= disciplina.getId().toString() %>">Alterar</a>
<a href="excluirDisciplina?id=<%= disciplina.getId().toString() %>">Excluir</a>
<a href="pesquisarDisciplina">Pesquisar</a>
<br>
<br>
<% //só exibir a tabela caso o objeto DisciplinaVO seja um objeto válido
   if (disciplina.getId() != null)
   { %>
	<table border="1" cellspacing="0" cellpadding="2">
	  <tr>
	     <th>Codigo:</td>
	     <td><%= disciplina.getCodigo() %></td>
	  </tr>
	  <tr>
	     <th>Nome:</td>
	     <td><%= disciplina.getNome() %></td>
	  </tr>
	</table>
<% } %>

</body>
</html>
