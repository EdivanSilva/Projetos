package controle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dominio.Disciplina;
import dominio.db.DisciplinaDAO;

/**
 * Servlet implementation class ServletSalvarDisciplina
 */
public class ServletSalvarDisciplina extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletSalvarDisciplina() {
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
		String codigo = request.getParameter("codigo");
		String nome = request.getParameter("nome");
		String cancelar = request.getParameter("Cancelar");

		DisciplinaDAO dao = new DisciplinaDAO();
		Disciplina disciplina;

		if ("Cancelar".equals(cancelar)) {
			if (id != null) {
				disciplina = dao.obterDisciplina(id);
				request.setAttribute("disciplina", disciplina);
			}
		} else {

			// verifica se é uma disciplina nova ou se está alterando
			if (id == null)
				disciplina = new Disciplina();
			else {
				disciplina = dao.obterDisciplina(id);
			}

			// copia os dados que vieram do formulário para o objeto
			disciplina.setCodigo(codigo);
			disciplina.setNome(nome);

			// salvar a disciplina
			dao.salvarDisciplina(disciplina);

			request.setAttribute("disciplina", disciplina);
		}

		// repassa para a página JSP
		request.getRequestDispatcher("jsp/exibirDisciplina.jsp").forward(
				request, response);

	}

}
