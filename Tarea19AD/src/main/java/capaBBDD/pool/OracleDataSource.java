package capaBBDD.pool;

import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class OracleDataSource {

	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource dataSource;

	static {
		// Configurar conexión para Oracle
		config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:XE"); // Reemplaza con tu URL
		config.setUsername("SYS as SYSDBA"); // Reemplaza con tu usuario
		config.setPassword("familia3773"); // Reemplaza con tu contraseña

		// Configuraciones adicionales para Oracle
		config.setMaximumPoolSize(10);
		config.setMinimumIdle(2);
		config.setIdleTimeout(30000);
		config.setMaxLifetime(1800000);
		config.setConnectionTimeout(30000);
		config.setPoolName("OracleHikariCP");

		// Propiedades de optimización para Oracle
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

		dataSource = new HikariDataSource(config);
	}

	private OracleDataSource() {
	}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}