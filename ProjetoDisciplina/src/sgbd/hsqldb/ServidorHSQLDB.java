package sgbd.hsqldb;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;

import dominio.db.UtilJdbc;

public class ServidorHSQLDB {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			URL url = ServidorHSQLDB.class.getResource("ServidorHSQLDB.class");
			String caminho = url.getFile().substring(0,
					url.getFile().indexOf("WEB-INF"));
			org.hsqldb.Server.main(new String[] { "-database.0",
					"file:" + caminho + "WEB-INF/banco/Matricula", "-dbname.0",
					"matricula", "-user", "sa" });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void shutdown() {
		try {
			Connection conn = UtilJdbc.criarConexao(UtilJdbc.URL,
					UtilJdbc.PARAMETROS);
			Statement st = conn.createStatement();
			st.execute("SHUTDOWN");
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
