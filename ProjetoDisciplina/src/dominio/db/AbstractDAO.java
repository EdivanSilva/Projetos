/**
 * Esta classe tem como finalidade fornecer facilidades
 * de ger�ncia de banco de dados SQL utilizando JDBC.
 * 
 * Deve ser utilizada atrav�s de especializa��o, criando-se DAO's
 * espec�ficos para cada objeto persistido pela aplica��o.
 */
package dominio.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class AbstractDAO
{

	//**************************************************************************
	//****** ATRIBUTOS DA CLASSE
	//**************************************************************************
	protected Connection				con				= null;
	protected PreparedStatement	comandoSQL		= null;
	protected PreparedStatement	comandoInsert	= null;
	protected PreparedStatement	comandoUpdate	= null;
	protected PreparedStatement	comandoDelete	= null;
	protected CallableStatement	procedure		= null;
	protected ResultSet				dadosSQL			= null;


	//**************************************************************************
	//****** M�TODOS DA CLASSE
	//**************************************************************************
	/**
	 * Construtor que recebe como par�metro um provedor de conex�es.
	 */
	public AbstractDAO()
	{
		super();
	}


	/**
	 * Verifica se existe uma conex�o configurada, e caso n�o exista, cria uma nova.
	 */
	public boolean testarConexao()
	{
		try
		{
			//se n�o existir ou estiver fechada, deve tentar criar uma nova conex�o
			if ((this.con == null) || (this.con.isClosed()))
			{
				this.con = UtilJdbc.criarConexao(UtilJdbc.URL, UtilJdbc.PARAMETROS);

				//se n�o conseguiu criar, deve retornar false
				if (this.con == null)
					return false;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return false;
		}

		return (this.con != null);
	}


	public boolean abrirTransacaoSQL()
	{
		//se a conex�o for inv�lida, devolve false
		if (!this.testarConexao())
			return false;

		try
		{
			this.con.setAutoCommit(false);

			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return false;
		}
	}


	public boolean gravarTransacaoSQL()
	{
		//se a conex�o for inv�lida, devolve false
		if (!this.testarConexao())
			return false;

		try
		{
			this.con.commit();
			this.con.setAutoCommit(true);

			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return false;
		}
	}


	public boolean desfazerTransacaoSQL()
	{ //se a conex�o for inv�lida, devolve false

		if (!this.testarConexao())
			return false;

		try
		{
			this.con.rollback();
			this.con.setAutoCommit(true);

			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return false;
		}
	}


	// ****************** Comando SQL Geral **********************
	/**
	 * M�todo que obt�m o pr�ximo n�mero sequencial gerado pelo banco de dados.
	 * A maneira de se gerar "sequences" muda de acordo com o banco de dados utilizado, sendo assim,
	 * este m�todo deve ser configurado de acordo com o banco a ser utilizado.
	 */
	public final int obterProximoSequence(String nomeDoSequence) throws SQLException
	{
		int sequence = -1;

		if (!this.testarConexao())
			return sequence;

		Statement comando = this.con.createStatement();

		//comando para gerar sequence no HSQLDB
		ResultSet dados = comando.executeQuery("SELECT NEXT VALUE FOR " + nomeDoSequence + " FROM DUAL");

		//comando para gerar sequence no FirebirdSQL
		//ResultSet dados = comando.executeQuery("SELECT GEN_ID(" + nomeDoSequence + ", 1) FROM RDB$DATABASE");

		//comando para gerar sequence no PostgreeSQL
		//ResultSet dados = comando.executeQuery("select nextval('" + nomeDoSequence + "')");

		//comando para gerar sequence no Oracle
		//ResultSet dados = comando.executeQuery("select " + nomeDoSequence + ".nextval from dual");

		if (dados.next())
			sequence = dados.getInt(1);

		dados.close();
		comando.close();
		dados = null;
		comando = null;

		return sequence;
	}


	public boolean prepararComandoSQL(String comando)
	{ //se a conex�o for inv�lida, deve abortar

		if (!this.testarConexao())
			return false;

		//caso j� exista um comando preparado, deve liber�-lo antes de preparar outro
		this.liberarDadosSQL();
		this.liberarComandoSQL();

		try
		{
			this.comandoSQL = this.con.prepareStatement(comando);

			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return false;
		}
	}


	public int executarComandoSQL()
	{
		if (this.comandoSQL == null)
			return -1;

		try
		{
			return this.comandoSQL.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return -1;
		}
	}


	public boolean executarConsultaSQL()
	{
		if (this.comandoSQL == null)
			return false;

		this.liberarDadosSQL();

		try
		{
			this.dadosSQL = this.comandoSQL.executeQuery();

			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.dadosSQL = null;

			return false;
		}
	}


	// ****************** Comando Insert **********************
	public boolean prepararComandoInsert(String comando)
	{ //se a conex�o for inv�lida, deve abortar

		if (!this.testarConexao())
			return false;

		//caso j� exista um comando preparado, deve liber�-lo antes de preparar outro
		this.liberarComandoInsert();

		try
		{
			this.comandoInsert = this.con.prepareStatement(comando);

			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return false;
		}
	}


	public int executarComandoInsert()
	{
		if (this.comandoInsert == null)
			return -1;

		try
		{
			return this.comandoInsert.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return -1;
		}
	}


	// ****************** Comando Update **********************
	public boolean prepararComandoUpdate(String comando)
	{ //se a conex�o for inv�lida, deve abortar

		if (!this.testarConexao())
			return false;

		//caso j� exista um comando preparado, deve liber�-lo antes de preparar outro
		this.liberarComandoUpdate();

		try
		{
			this.comandoUpdate = this.con.prepareStatement(comando);

			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return false;
		}
	}


	public int executarComandoUpdate()
	{
		if (this.comandoUpdate == null)
			return -1;

		try
		{
			return this.comandoUpdate.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return -1;
		}
	}


	/**
	 * Executa um comando SQL sem prepar�-lo antes.
	 */
	public int executarComandoSQL(String comando)
	{
		if (!this.testarConexao())
			return -1;

		try
		{
			Statement sql = this.con.createStatement();
			int retorno = sql.executeUpdate(comando);
			sql.close();

			return retorno;
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return -1;
		}
	}


	// ****************** Comando Delete **********************
	public boolean prepararComandoDelete(String comando)
	{ //se a conex�o for inv�lida, deve abortar

		if (!this.testarConexao())
			return false;

		//caso j� exista um comando preparado, deve liber�-lo antes de preparar outro
		this.liberarComandoDelete();

		try
		{
			this.comandoDelete = con.prepareStatement(comando);

			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return false;
		}
	}


	public int executarComandoDelete()
	{
		if (this.comandoDelete == null)
			return -1;

		try
		{
			return this.comandoDelete.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return -1;
		}
	}


	// ****************** Procedure **********************
	public boolean prepararProcedure(String comando)
	{ //se a conex�o for inv�lida, deve abortar

		if (!this.testarConexao())
			return false;

		//caso j� exista uma procedure preparada, deve liber�-la antes de preparar outra
		this.liberarProcedure();

		try
		{
			this.procedure = this.con.prepareCall(comando);

			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return false;
		}
	}


	public int executarProcedure()
	{
		if (this.procedure == null)
			return -1;

		try
		{
			return this.procedure.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return -1;
		}
	}


	// ****************** Liberar recursos **********************
	private boolean liberarProcedure()
	{
		try
		{
			if (this.procedure != null)
			{
				this.procedure.close();
				this.procedure = null;
			}

			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return false;
		}
	}


	private boolean liberarComandoSQL()
	{
		try
		{
			if (this.comandoSQL != null)
			{
				this.comandoSQL.close();
				this.comandoSQL = null;
			}

			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return false;
		}
	}


	private boolean liberarComandoInsert()
	{
		try
		{
			if (this.comandoInsert != null)
			{
				this.comandoInsert.close();
				this.comandoInsert = null;
			}

			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return false;
		}
	}


	private boolean liberarComandoUpdate()
	{
		try
		{
			if (this.comandoUpdate != null)
			{
				this.comandoUpdate.close();
				this.comandoUpdate = null;
			}

			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return false;
		}
	}


	private boolean liberarComandoDelete()
	{
		try
		{
			if (this.comandoDelete != null)
			{
				this.comandoDelete.close();
				this.comandoDelete = null;
			}

			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return false;
		}
	}


	private boolean liberarDadosSQL()
	{
		try
		{
			if (this.dadosSQL != null)
			{
				this.dadosSQL.close();
				this.dadosSQL = null;
			}

			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return false;
		}
	}


	//libera os recursos usados pelo objeto
	public void liberarRecursos()
	{
		//fecha os comandos usados
		this.liberarDadosSQL();
		this.liberarComandoSQL();
		this.liberarComandoInsert();
		this.liberarComandoUpdate();
		this.liberarComandoDelete();
		this.liberarProcedure();

		this.con = null;
	}


	/**
	 * M�todo executado pelo garbage collector da JVM quando o objeto for
	 * destru�do.  Ele deve chamar o m�todo liberarRecursos() para garantir
	 * que a conex�o e os objetos instanciados pelo gerente sejam liberados,
	 * mesmo se o programador se esquecer de invocar este m�todo ao final da
	 * utiliza��o do DAO.
	 */
	protected void finalize() throws Exception
	{
		this.liberarRecursos();
	}
}
