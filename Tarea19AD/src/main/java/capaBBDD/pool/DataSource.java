package capaBBDD.pool;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSource  {
	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource source;

	private DataSource() {};

	static {
		config.setJdbcUrl("jdbc:mysql://localhost:3306/Alumnos15");
		config.setUsername("root");
		config.setPassword("linarespajero");
		config.addDataSourceProperty("maximumPoolSize", 1);
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

		source = new HikariDataSource(config);
	}

	public static Connection getConnection() throws SQLException {
		return source.getConnection();
	}
}
