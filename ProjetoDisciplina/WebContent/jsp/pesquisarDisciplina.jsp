<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="controle.*, dominio.*"%>
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
  java.util.List lista = (java.util.List) request.getAttribute("lista");
  if (lista == null)
     lista = new java.util.ArrayList();
%>

  <h1>Pesquisa de Disciplina<h1>
  <b>Opções:</b> <a href="incluirDisciplina">Incluir</a>

  <form name="formDados" method="post" action="pesquisarDisciplina">
	<table border="1" cellspacing="0" cellpadding="2">
	  <tr>
	     <th>Codigo:</th>
	     <td><input type="text" name="codigo" value="<%= request.getAttribute("codigo") %>" maxlength="6"></td>
	  </tr>
	  <tr>
	     <th>Nome:</th>
	     <td><input type="text" name="nome" value="<%= request.getAttribute("nome") %>" maxlength="50"></td>
	  </tr>
	</table>
	<input type="submit" name="Pesquisar" value="Pesquisar">
  </form>
<% 
   if (lista.size() > 0)
   {
%>
  <h1>Resultados da Pesquisa<h1>
	<table border="1" cellspacing="0" cellpadding="2">
	  <tr>
	     <th>Opções</th>
	     <th>Codigo</th>
	     <th>Nome</th>
	  </tr>
<%
	for (int i=0; i < lista.size() ; i++)
	   {
	      Disciplina disciplina = (dominio.Disciplina) lista.get(i);
%>
		  <tr>
		     <th><a href="exibirDisciplina?id=<%= disciplina.getId().toString() %>">Abrir</a></th>
		     <th><%= disciplina.getCodigo() %></th>
		     <th><%= disciplina.getNome() %></th>
		  </tr>
<%     } // for
    } // if %>

	</table>
</body>
</html>