package capaBBDD.daos.oracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import capaBBDD.daos.daoAlumno.InterfazDaoAlumnos;
import capaBBDD.pool.OracleDataSource;
import modelos.POJOS.Alumno;
import modelos.POJOS.Grupo;

public class DaoImplementacionOracle implements InterfazDaoAlumnos {

    private Connection conectar() throws SQLException {
        return OracleDataSource.getConnection();
    }

    @Override
    public void insertarAlumno(Alumno alumno) {
        String sql = "INSERT INTO alumnos (nia, nombre, apellido, ciclo, curso, genero, fecha_nacimiento, grupo_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, alumno.getNia());
            stmt.setString(2, alumno.getNombre());
            stmt.setString(3, alumno.getApellido());
            stmt.setString(4, alumno.getCiclo());
            stmt.setString(5, alumno.getCurso());
            stmt.setString(6, String.valueOf(alumno.getGenero()));
            stmt.setDate(7, new java.sql.Date(alumno.getFechadenacimiento().getTime()));
            stmt.setInt(8, alumno.getGrupo().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertarGrupo(Grupo grupo) {
        String sql = "INSERT INTO grupos (id, nombre) VALUES (?, ?)";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, grupo.getId());
            stmt.setString(2, grupo.getNombre());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Alumno> selectAlumnos() {
        List<Alumno> alumnos = new ArrayList<>();
        String sql = "SELECT * FROM alumnos";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                alumnos.add(new Alumno(rs.getInt("nia"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("ciclo"), rs.getString("curso"), rs.getString("genero").charAt(0), rs.getDate("fecha_nacimiento"), new Grupo(rs.getInt("grupo_id"), "")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumnos;
    }

    @Override
    public List<Alumno> selectAlumnoByGroupId(int id) {
        List<Alumno> alumnos = new ArrayList<>();
        String sql = "SELECT * FROM alumnos WHERE grupo_id = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    alumnos.add(new Alumno(rs.getInt("nia"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("ciclo"), rs.getString("curso"), rs.getString("genero").charAt(0), rs.getDate("fecha_nacimiento"), new Grupo(id, "")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumnos;
    }

    @Override
    public void cambiarGrupo(int nia, int grupoId) {
        String sql = "UPDATE alumnos SET grupo_id = ? WHERE nia = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, grupoId);
            stmt.setInt(2, nia);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarByGroup(int id) {
        String sql = "DELETE FROM alumnos WHERE grupo_id = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Grupo selectGrupo(int codigo) {
        String sql = "SELECT * FROM grupos WHERE id = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Grupo(rs.getInt("id"), rs.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean comprobarSiExiste(int grupo) {
        String sql = "SELECT COUNT(*) FROM grupos WHERE id = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, grupo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

	@Override
	public void selectNombreAlumnos() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Alumno selectAlumnoNia(int Nia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cambiarNombre(String nombre, int nia) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarByNia(int nia) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Grupo> selectGrupos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertarGrupos(List<Grupo> grupo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertarAlumnos(List<Alumno> alumnos) {
		// TODO Auto-generated method stub
		
	}
}
