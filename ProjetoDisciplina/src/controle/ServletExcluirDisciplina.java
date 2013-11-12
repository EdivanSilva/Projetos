package controle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dominio.Disciplina;
import dominio.db.DisciplinaDAO;

/**
 * Servlet implementation class ServletExcluirDisciplina
 */
public class ServletExcluirDisciplina extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletExcluirDisciplina() {
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

		Integer id;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			id = null;
		}

		// se n�o estiver vazia
		if (id != null) {
			DisciplinaDAO dao = new DisciplinaDAO();
			Disciplina disciplina = dao.obterDisciplina(id);
			dao.excluirDisciplina(disciplina);
		}

		// executa a a��o de exibir pesquisa
		request.getRequestDispatcher("abrirPesquisa")
				.forward(request, response);
	}

}
