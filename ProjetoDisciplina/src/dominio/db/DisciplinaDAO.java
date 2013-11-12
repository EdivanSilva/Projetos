package dominio.db;

import java.util.ArrayList;
import java.util.List;

import dominio.Disciplina;

public class DisciplinaDAO extends AbstractDAO {
	/**
	 * M�todo para recuperar uma disciplina da base de dados a partir do seu
	 * c�digo.
	 */
	public Disciplina obterDisciplina(String codigo) {
		Disciplina retorno = null;

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ID_DISCIPLINA, CODIGO, NOME ");
			sql.append("  FROM DISCIPLINA ");
			sql.append(" WHERE CODIGO = ?");

			this.abrirTransacaoSQL();

			// prepara o comando sql
			if (this.prepararComandoSQL(sql.toString())) {
				// passa os par�metros
				this.comandoSQL.setString(1, codigo);

				// executa a consulta
				if (this.executarConsultaSQL()) {
					// l� os dados da consulta
					if (this.dadosSQL.next()) {
						retorno = new Disciplina();

						// declara um contador para usar na leitura sequencial
						// dos campos
						byte i = 1;
						retorno.setId(this.dadosSQL.getInt(i++));
						retorno.setCodigo(this.dadosSQL.getString(i++));
						retorno.setNome(this.dadosSQL.getString(i++));
					}
				}
			}

			this.gravarTransacaoSQL();
		} catch (Exception e) {
			e.printStackTrace();

			this.desfazerTransacaoSQL();
		}

		return retorno;
	}

	/**
	 * M�todo para recuperar uma disciplina da base de dados a partir do seu ID.
	 */
	public Disciplina obterDisciplina(Integer id) {
		Disciplina retorno = null;

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ID_DISCIPLINA, CODIGO, NOME ");
			sql.append("  FROM DISCIPLINA ");
			sql.append(" WHERE ID_DISCIPLINA = ?");

			this.abrirTransacaoSQL();

			// prepara o comando sql
			if (this.prepararComandoSQL(sql.toString())) {
				// passa os par�metros
				this.comandoSQL.setInt(1, id);

				// executa a consulta
				if (this.executarConsultaSQL()) {
					// l� os dados da consulta
					if (this.dadosSQL.next()) {
						retorno = new Disciplina();

						// declara um contador para usar na leitura sequencial
						// dos campos
						byte i = 1;
						retorno.setId(this.dadosSQL.getInt(i++));
						retorno.setCodigo(this.dadosSQL.getString(i++));
						retorno.setNome(this.dadosSQL.getString(i++));
					}
				}
			}

			this.gravarTransacaoSQL();
		} catch (Exception e) {
			e.printStackTrace();

			this.desfazerTransacaoSQL();
		}

		return retorno;
	}

	/**
	 * M�todo para salvar uma disciplina na base de dados.
	 */
	public boolean salvarDisciplina(Disciplina disciplina) {
		/*
		 * Estrat�gia para gravar uma disciplina: 1) verificar se a disciplina
		 * possui o atributo ID preenchido. 1.1) caso n�o possua � porque nunca
		 * foi gravada, ent�o deve inserir um novo registro. 1.2) se a
		 * disciplina j� tiver um ID, basta fazer altera��o do registro que j�
		 * existe.
		 */
		if (disciplina.getId() == null)
			return this.incluirDisciplina(disciplina);
		else
			return this.alterarDisciplina(disciplina);
	}

	/**
	 * M�todo para incluir uma disciplina na base de dados.
	 */
	private boolean incluirDisciplina(Disciplina disciplina) {
		int atualizado = 0;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO DISCIPLINA (ID_DISCIPLINA, CODIGO, NOME) ");
			sql.append(" VALUES (?, ?, ?) ");

			this.abrirTransacaoSQL();

			// prepara o comando sql
			if (this.prepararComandoInsert(sql.toString())) {
				// calcula o pr�ximo sequence para ser usado como ID da
				// disciplina
				int idDisciplina = super.obterProximoSequence("SQ_DISCIPLINA");
				disciplina.setId(idDisciplina);

				// declara um contador para usar na leitura sequencial dos
				// campos
				byte i = 1;
				// passa os par�metros
				this.comandoInsert.setInt(i++, disciplina.getId());
				this.comandoInsert.setString(i++, disciplina.getCodigo());
				this.comandoInsert.setString(i++, disciplina.getNome());

				// executa o comando retornando o n�mero de linhas atualizadas
				atualizado = this.executarComandoInsert();
			}

			this.gravarTransacaoSQL();
		} catch (Exception e) {
			e.printStackTrace();

			this.desfazerTransacaoSQL();
		}

		return atualizado > 0;
	}

	/**
	 * M�todo para alterar os dados de uma disciplina.
	 */
	private boolean alterarDisciplina(Disciplina disciplina) {
		int atualizado = 0;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE DISCIPLINA SET CODIGO=?, NOME=?");
			sql.append(" WHERE ID_DISCIPLINA = ?");

			this.abrirTransacaoSQL();

			// prepara o comando sql
			if (this.prepararComandoUpdate(sql.toString())) {
				// declara um contador para usar na leitura sequencial dos
				// campos
				byte i = 1;
				// passa os par�metros
				this.comandoUpdate.setString(i++, disciplina.getCodigo());
				this.comandoUpdate.setString(i++, disciplina.getNome());
				this.comandoUpdate.setInt(i++, disciplina.getId());

				// executa o comando retornando o n�mero de linhas atualizadas
				atualizado = this.executarComandoUpdate();
			}

			this.gravarTransacaoSQL();
		} catch (Exception e) {
			e.printStackTrace();

			this.desfazerTransacaoSQL();
		}

		return atualizado > 0;
	}

	/**
	 * M�todo para apagar os dados de uma disciplina.
	 */
	public boolean excluirDisciplina(Disciplina disciplina) {
		int atualizado = 0;
		try {
			this.abrirTransacaoSQL();

			// prepara o comando sql
			if (this
					.prepararComandoDelete("DELETE FROM DISCIPLINA  WHERE ID_DISCIPLINA = ?")) {
				// passa os par�metros
				this.comandoDelete.setInt(1, disciplina.getId());

				// executa o comando retornando o n�mero de linhas atualizadas
				atualizado = this.executarComandoDelete();
			}

			this.gravarTransacaoSQL();
		} catch (Exception e) {
			e.printStackTrace();

			this.desfazerTransacaoSQL();
		}

		return atualizado > 0;
	}

	/**
	 * M�todo para recuperar a lista com todas as disciplinas da base de dados.
	 */
	public List<Disciplina> obterListaDeDisciplinas(String codigo, String nome) {
		List<Disciplina> retorno = new ArrayList<Disciplina>();
		Disciplina disciplina = null;

		try {
			String conector = " WHERE ";
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ID_DISCIPLINA, CODIGO, NOME  FROM DISCIPLINA ");

			// verifica se passou algum c�digo para filtrar
			if (codigo != null && !"".equals(codigo)) {
				sql.append(conector + " CODIGO like ?");
				// troca o conector do comando SQL para AND
				conector = " AND ";
			}

			// verifica se passou algum nome para filtrar
			if (nome != null && !"".equals(nome))
				sql.append(conector + " NOME like ?");

			this.abrirTransacaoSQL();

			// prepara o comando sql
			if (this.prepararComandoSQL(sql.toString())) {
				// declara um contador para usar nos par�metros sequenciais
				byte i = 1;

				// se passou algum c�digo deve colocar o par�metro
				if (codigo != null && !"".equals(codigo))
					this.comandoSQL.setString(i++, codigo + "%");

				// se passou algum nome deve colocar o par�metro
				if (nome != null && !"".equals(nome))
					this.comandoSQL.setString(i++, nome + "%");

				// executa a consulta
				if (this.executarConsultaSQL()) {
					// l� os dados da consulta
					while (this.dadosSQL.next()) {
						disciplina = new Disciplina();

						// reinicia o contador da leitura sequencial dos campos
						i = 1;
						disciplina.setId(this.dadosSQL.getInt(i++));
						disciplina.setCodigo(this.dadosSQL.getString(i++));
						disciplina.setNome(this.dadosSQL.getString(i++));

						retorno.add(disciplina);
					}
				}
			}

			this.gravarTransacaoSQL();
		} catch (Exception e) {
			e.printStackTrace();

			this.desfazerTransacaoSQL();
		}

		return retorno;
	}
}
