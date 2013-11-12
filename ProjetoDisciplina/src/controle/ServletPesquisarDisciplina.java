package controle;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dominio.Disciplina;
import dominio.db.DisciplinaDAO;

/**
 * Servlet implementation class ServletPesquisarDisciplina
 */
public class ServletPesquisarDisciplina extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletPesquisarDisciplina() {
		super();
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
		String codigo = request.getParameter("codigo");
		String nome = request.getParameter("nome");

		DisciplinaDAO dao = new DisciplinaDAO();
		List<Disciplina> lista = dao.obterListaDeDisciplinas(codigo, nome);
		request.setAttribute("lista", lista);
		request.setAttribute("codigo", codigo);
		request.setAttribute("nome", nome);

		// repassa para a página JSP
		request.getRequestDispatcher("jsp/pesquisarDisciplina.jsp").forward(
				request, response);

	}

}
