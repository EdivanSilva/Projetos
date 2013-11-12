package dominio.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Classe utilitária para o uso com JDBC para estabelecer conexões com o banco de dados.
 */
public class UtilJdbc
{
	/**
	 * Método construtor privado para ninguém instanciar esta classe.
	 */
	private UtilJdbc()
	{
		super();
	}

	//**************************************************************************
	//****** ATRIBUTOS ESTÁTICOS
	//**************************************************************************
	public static String			DRIVER;
	public static String			URL;
	public static Properties	PARAMETROS;

	/**
	 * Este bloco estático configura os parâmetros de configuração do banco.
	 * Ele poderia ser substituído por parâmetros lidos de um arquivo.
	 */
	static
	{
		Properties parametros = new Properties();
		//parametros.setProperty("user", "SYSDBA");
		//parametros.setProperty("password", "masterkey");
		//parametros.setProperty("lc_ctype", "UNICODE_FSS"); //ISO8859_1
		parametros.setProperty("user", "sa");
		parametros.setProperty("password", "");

		//para usar o Firebird com o SGBD instalado na máquina, deve-se usar o endereço IP do servidor
		//setParametros("org.firebirdsql.jdbc.FBDriver", "jdbc:firebirdsql:127.0.0.1:caminho\banco.fdb", parametros);

		//para usar o Firebird embarcado (sem instalação do SGBD na máquina), deve-se usar "embedded" como endereço do servidor
		//setParametros("org.firebirdsql.jdbc.FBDriver", "jdbc:firebirdsql:embedded:D:/EIN/copiar_para_o_hd/eclipse/workspace/ProjetoMatriculaWEB/WEB-INF/lib/banco.fdb", parametros);

		//para usar o HSQLDB embarcado
		//setParametros("org.hsqldb.jdbcDriver", "jdbc:hsqldb:file:./workspace/ProjetoMatriculaWEB/banco/Matricula", parametros);

		//para usar o HSQLDB como servidor
		setParametros("org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://localhost/matricula", parametros);
	}


	/**
	 * Configura os parâmetros da conexão.
	 */
	public static void setParametros(String driver, String url, Properties parametros)
	{
		DRIVER = driver;
		URL = url;
		PARAMETROS = parametros;

		try
		{
			//carrega o driver (uma única vez) para ficar disponível
			Class.forName(DRIVER);
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * Cria uma nova conexão usando os parâmetros.
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

		//devolve a conexão estabelecida ou "null" se houve problema
		return conexao;
	}
}
