package dominio.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Classe utilit�ria para o uso com JDBC para estabelecer conex�es com o banco de dados.
 */
public class UtilJdbc
{
	/**
	 * M�todo construtor privado para ningu�m instanciar esta classe.
	 */
	private UtilJdbc()
	{
		super();
	}

	//**************************************************************************
	//****** ATRIBUTOS EST�TICOS
	//**************************************************************************
	public static String			DRIVER;
	public static String			URL;
	public static Properties	PARAMETROS;

	/**
	 * Este bloco est�tico configura os par�metros de configura��o do banco.
	 * Ele poderia ser substitu�do por par�metros lidos de um arquivo.
	 */
	static
	{
		Properties parametros = new Properties();
		//parametros.setProperty("user", "SYSDBA");
		//parametros.setProperty("password", "masterkey");
		//parametros.setProperty("lc_ctype", "UNICODE_FSS"); //ISO8859_1
		parametros.setProperty("user", "sa");
		parametros.setProperty("password", "");

		//para usar o Firebird com o SGBD instalado na m�quina, deve-se usar o endere�o IP do servidor
		//setParametros("org.firebirdsql.jdbc.FBDriver", "jdbc:firebirdsql:127.0.0.1:caminho\banco.fdb", parametros);

		//para usar o Firebird embarcado (sem instala��o do SGBD na m�quina), deve-se usar "embedded" como endere�o do servidor
		//setParametros("org.firebirdsql.jdbc.FBDriver", "jdbc:firebirdsql:embedded:D:/EIN/copiar_para_o_hd/eclipse/workspace/ProjetoMatriculaWEB/WEB-INF/lib/banco.fdb", parametros);

		//para usar o HSQLDB embarcado
		//setParametros("org.hsqldb.jdbcDriver", "jdbc:hsqldb:file:./workspace/ProjetoMatriculaWEB/banco/Matricula", parametros);

		//para usar o HSQLDB como servidor
		setParametros("org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://localhost/matricula", parametros);
	}


	/**
	 * Configura os par�metros da conex�o.
	 */
	public static void setParametros(String driver, String url, Properties parametros)
	{
		DRIVER = driver;
		URL = url;
		PARAMETROS = parametros;

		try
		{
			//carrega o driver (uma �nica vez) para ficar dispon�vel
			Class.forName(DRIVER);
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * Cria uma nova conex�o usando os par�metros.
	 */
	public static Connection criarConexao(String url, Properties parametros)
	{
		Connection conexao = null;

		try
		{
			conexao = DriverManager.getConnection(url, parametros);
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			conexao = null;
		}

		//devolve a conex�o estabelecida ou "null" se houve problema
		return conexao;
	}
}
