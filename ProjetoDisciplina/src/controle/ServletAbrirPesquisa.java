package controle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sgbd.hsqldb.ServidorHSQLDB;

/**
 * Servlet implementation class ServletAbrirPesquisa
 */
public class ServletAbrirPesquisa extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletAbrirPesquisa() {
		super();
		// carrega o servidor de banco de dados
		ServidorHSQLDB.main(null);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setAttribute("codigo", "");
		request.setAttribute("nome", "");

		// repassa para a página JSP
		request.getRequestDispatcher("jsp/pesquisarDisciplina.jsp").forward(
				request, response);
	}

}
