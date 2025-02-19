package capaBBDD.daos.daoAlumno.implementacionesDaoAlumno;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import capaBBDD.daos.daoAlumno.InterfazDaoAlumnos;
import capaBBDD.pool.DataSource;
import modelos.POJOS.Alumno;
import modelos.POJOS.Grupo;

public class DaoAlumnoJdbc implements InterfazDaoAlumnos {
	private static final Logger logger = (Logger) LogManager.getLogger(DaoAlumnoJdbc.class);

	private static DaoAlumnoJdbc instance;

	static {
		instance = new DaoAlumnoJdbc();
	}

	public DaoAlumnoJdbc() {
	}

	public static DaoAlumnoJdbc getInstance() {
		return instance;
	}

	@Override
	public void insertarAlumno(Alumno alumno) {
		String query = "INSERT INTO ALUMNO(NIA,NOMBRE,APELLIDO,GENERO,FECHANACIMIENTO,CICLO,CURSO,GRUPO)"
				+ " VALUES (?,?,?,?,?,?,?,?)";
		try (Connection conexion = DataSource.getConnection();
				PreparedStatement partedelaquery = conexion.prepareStatement(query)) {
			logger.info("Insertando Alumno con nia: {}", alumno.getNia());
			partedelaquery.setInt(1, alumno.getNia());
			partedelaquery.setString(2, alumno.getNombre());
			partedelaquery.setString(3, alumno.getApellido());
			partedelaquery.setString(4, String.valueOf(alumno.getGenero()));
			Date sqldate = new Date(alumno.getFechadenacimiento().getTime());
			partedelaquery.setDate(5, sqldate);
			partedelaquery.setString(6, alumno.getCiclo());
			partedelaquery.setString(7, alumno.getCurso());
			partedelaquery.setInt(8, alumno.getGrupo().getId());

			partedelaquery.executeUpdate();

			logger.info("Alumno con NIA: {} insertado correctamente.", alumno.getNia());
		} catch (SQLException e) {
			logger.error("Error al insertar alumno con Nia: {}", alumno.getNia(), e);
		}

	}

	@Override
	public void insertarGrupo(Grupo grupo) {
		String sql = "INSERT INTO grupos(id, nombre) VALUES(?,?)";

		try (Connection conn = DataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
			logger.info("Insertando grupo con ID: {}", grupo.getId());

			pstm.setInt(1, grupo.getId());
			pstm.setString(2, grupo.getNombre());

			pstm.executeUpdate();
			logger.info("Grupo con ID: {} insertado correctamente.", grupo.getId());
		} catch (SQLException e) {
			logger.error("Error al insertar grupo con ID: {}", grupo.getId(), e);
		}
	}

	@Override
	public List<Alumno> selectAlumnos() {
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		String sql = "SELECT NIA,NOMBRE,APELLIDO,GENERO,FECHANACIMIENTO,CICLO,CURSO,GRUPO FROM alumnos";
		List<Alumno> alumnos = new ArrayList<>();

		try (Connection conn = DataSource.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql);
				ResultSet resultSet = pstm.executeQuery()) {

			logger.info("Consultando todos los alumnos...");

			while (resultSet.next()) {
				int nia = resultSet.getInt("NIA");
				String nombre = resultSet.getString("NOMBRE");
				String apellidos = resultSet.getString("APELLIDO");
				String genero = resultSet.getString("GENERO");
				Date fecha = resultSet.getDate("FECHANACIMIENTO");
				String ciclo = resultSet.getString("CICLO");
				String curso = resultSet.getString("CURSO");
				int grupo = resultSet.getInt("GRUPO");

				java.util.Date fechaNacimiento = formato.parse(fecha.toString());

				alumnos.add(new Alumno(nia, nombre, apellidos, ciclo, curso, genero.charAt(0), fechaNacimiento,
						selectGrupo(grupo)));
			}

			logger.info("Se encontraron {} alumnos.", alumnos.size());
		} catch (SQLException e) {
			logger.error("Error al consultar los alumnos.", e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return alumnos;
	}

	@Override
	public List<Alumno> selectAlumnoByGroupId(int id) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		String sql = "SELECT NIA,NOMBRE,APELLIDO,GENERO,FECHANACIMIENTO,CICLO,CURSO,GRUPO  FROM alumnos WHERE GRUPO = ?";
		List<Alumno> alumnos = new ArrayList<>();

		try (Connection conn = DataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
			logger.info("Consultando alumnos del grupo con ID: {}", id);

			pstm.setInt(1, id);
			ResultSet resultSet = pstm.executeQuery();

			while (resultSet.next()) {
				int nia = resultSet.getInt("nia");
				String nombre = resultSet.getString("nombre");
				String apellidos = resultSet.getString("apellidos");
				String genero = resultSet.getString("genero");
				Date fecha = resultSet.getDate("fecha_nacimiento");
				String ciclo = resultSet.getString("ciclo");
				String curso = resultSet.getString("curso");
				int grupo = resultSet.getInt("grupo_id");

				java.util.Date fechaNacimiento = formato.parse(fecha.toString());

				alumnos.add(new Alumno(nia, nombre, apellidos, ciclo, curso, genero.charAt(0), fechaNacimiento,
						selectGrupo(grupo)));
			}

			logger.info("Se encontraron {} alumnos en el grupo con ID: {}", alumnos.size(), id);
		} catch (SQLException e) {
			logger.error("Error al consultar alumnos del grupo con ID: {}", id, e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return alumnos;
	}

	@Override
	public void selectNombreAlumnos() {
		String sql = "SELECT nombre FROM grupos WHERE id = ?";
		List<Alumno> alumnos = selectAlumnos();

		try (Connection conn = DataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
			logger.info("Consultando nombres de alumnos y sus grupos...");

			for (Alumno alumno : alumnos) {
				pstm.setInt(1, alumno.getGrupo().getId());
				ResultSet resultSet = pstm.executeQuery();

				while (resultSet.next()) {
					logger.info("Alumno: {} - Grupo: {}", alumno, resultSet.getString("nombre"));
				}
			}
		} catch (SQLException e) {
			logger.error("Error al consultar nombres de alumnos y sus grupos.", e);
		}
	}

	@Override
	public void cambiarNombre(String nombre, int nia) {
		String sql = "UPDATE alumnos SET nombre = ? WHERE nia = ?";

		try (Connection conn = DataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
			logger.info("Actualizando nombre del alumno con NIA: {}", nia);

			pstm.setString(1, nombre);
			pstm.setInt(2, nia);

			int rowsAffected = pstm.executeUpdate();
			if (rowsAffected > 0) {
				logger.info("Nombre del alumno con NIA: {} actualizado a '{}'.", nia, nombre);
			} else {
				logger.warn("No se encontró un alumno con NIA: {} para actualizar.", nia);
			}
		} catch (SQLException e) {
			logger.error("Error al actualizar nombre del alumno con NIA: {}", nia, e);
		}
	}

	public void cambiarGrupo(int nia, int grupId) {
		String sql = "UPDATE alumnos SET grupo_id = ? WHERE nia = ?";

		try (Connection conn = DataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
			logger.info("Actualizando nombre del alumno con NIA: {}", nia);

			pstm.setInt(1, grupId);
			pstm.setInt(2, nia);

			int rowsAffected = pstm.executeUpdate();
			if (rowsAffected > 0) {
				logger.info("Grupo del alumno con NIA: {} actualizado a '{}'.", nia, grupId);
			} else {
				logger.warn("No se encontró un alumno con NIA: {} para actualizar.", nia);
			}
		} catch (SQLException e) {
			logger.error("Error al actualizar grupo del alumno con NIA: {}", nia, e);
		}
	}

	@Override
	public void eliminarByNia(int nia) {
		String sql = "DELETE FROM alumnos WHERE nia = ?";

		try (Connection conn = DataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
			logger.info("Eliminando alumno con NIA: {}", nia);

			pstm.setInt(1, nia);

			int rowsAffected = pstm.executeUpdate();
			if (rowsAffected > 0) {
				logger.info("Alumno con NIA: {} eliminado correctamente.", nia);
			} else {
				logger.warn("No se encontró un alumno con NIA: {} para eliminar.", nia);
			}
		} catch (SQLException e) {
			logger.error("Error al eliminar alumno con NIA: {}", nia, e);
		}
	}

	@Override
	public List<Grupo> selectGrupos() {
		String sql = "SELECT id, nombre FROM grupos";
		List<Grupo> grupos = new ArrayList<>();

		try (Connection conn = DataSource.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql);
				ResultSet resultSet = pstm.executeQuery()) {

			logger.info("Consultando todos los grupos...");

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String nombre = resultSet.getString("nombre");
				grupos.add(new Grupo(id, nombre));
			}

			logger.info("Se encontraron {} grupos.", grupos.size());
		} catch (Exception e) {
			logger.error("Error al consultar los grupos.", e);
		}

		return grupos;
	}

	public Grupo selectGrupo(int codigo) {
		Grupo grupo = null;
		String sql = "SELECT id, nombre FROM grupos" + " WHERE ID = ?";

		try (Connection conn = DataSource.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql);
				ResultSet resultSet = pstm.executeQuery()) {
			pstm.setInt(1, codigo);

			logger.info("Consultando grupo...");

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String nombre = resultSet.getString("nombre");
				return grupo = new Grupo(id, nombre);
			}

		} catch (Exception e) {
			logger.error("Error al consultar los grupos.", e);
		}

		return grupo;
	}

	@Override
	public void eliminarByGroup(int id) {
		String sql = "DELETE FROM alumnos WHERE grupo_id = ?";

		try (Connection conn = DataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
			logger.info("Eliminando alumnos del grupo con ID: {}", id);

			pstm.setInt(1, id);

			int rowsAffected = pstm.executeUpdate();
			logger.info("Se eliminaron {} alumnos del grupo con ID: {}", rowsAffected, id);
		} catch (Exception e) {
			logger.error("Error al eliminar alumnos del grupo con ID: {}", id, e);
		}
	}

	@Override
	public boolean comprobarSiExiste(int grupo) {
		String sql = "SELECT id FROM grupos WHERE ID = ?";

		try (Connection conn = DataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {

			pstm.setInt(1, grupo);

			try (ResultSet resultSet = pstm.executeQuery()) {
				return resultSet.next();
			}

		} catch (Exception e) {
			logger.error("Error al consultar los grupos.", e);
		}

		return false;
	}

	@Override
	public void insertarGrupos(List<Grupo> grupo) {
		for (Grupo grupo2 : grupo) {
			insertarGrupo(grupo2);
		}

	}

	@Override
	public void insertarAlumnos(List<Alumno> alumnos) {
		for (Alumno alumno : alumnos) {
			insertarAlumno(alumno);
		}

	}

	@Override
	public Alumno selectAlumnoNia(int Nia) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		String sql = "SELECT NIA,NOMBRE,APELLIDO,GENERO,FECHANACIMIENTO,CICLO,CURSO,GRUPO FROM alumnos WHERE NIA = ?";
		Alumno a = null;
		try (Connection conn = DataSource.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql);
				ResultSet resultSet = pstm.executeQuery()) {

			logger.info("Consultando todos los alumnos...");
			pstm.setInt(1, Nia);

			while (resultSet.next()) {
				int nia = resultSet.getInt("NIA");
				String nombre = resultSet.getString("NOMBRE");
				String apellidos = resultSet.getString("APELLIDO");
				String genero = resultSet.getString("GENERO");
				Date fecha = resultSet.getDate("FECHANACIMIENTO");
				String ciclo = resultSet.getString("CICLO");
				String curso = resultSet.getString("CURSO");
				int grupo = resultSet.getInt("GRUPO");

				java.util.Date fechaNacimiento = formato.parse(fecha.toString());

				a = new Alumno(nia, nombre, apellidos, ciclo, curso, genero.charAt(0), fechaNacimiento,
						selectGrupo(grupo));
			}

			return a;
		} catch (SQLException e) {
			logger.error("Error al consultar los alumnos.", e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return a;
	}

}
