package sgbd.hsqldb;

public class ConsoleHSQLDB
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		org.hsqldb.util.DatabaseManagerSwing.main(new String[] {"-driver", "org.hsqldb.jdbcDriver", "-url", "jdbc:hsqldb:hsql://localhost/matricula", "-user", "sa"});
	}

}
