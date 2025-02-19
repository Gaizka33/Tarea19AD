package capaBBDD.daos.daoAlumno;

import java.util.List;

import modelos.POJOS.Alumno;
import modelos.POJOS.Grupo;

public interface InterfazDaoAlumnos {
	public void insertarAlumno(Alumno alumno);

	public void insertarGrupo(Grupo grupo);

	public List<Alumno> selectAlumnos();

	public List<Alumno> selectAlumnoByGroupId(int id);

	public void selectNombreAlumnos();
	
	public Alumno selectAlumnoNia(int Nia);

	public void cambiarNombre(String nombre, int nia);

	public void eliminarByNia(int nia);

	public List<Grupo> selectGrupos();

	public void eliminarByGroup(int id);

	public void cambiarGrupo(int nia, int grupId);

	public boolean comprobarSiExiste(int grupo);
	
	public Grupo selectGrupo(int codigo);
	
	public void insertarGrupos(List<Grupo> grupo);
	
	public void insertarAlumnos(List<Alumno> alumnos);

}
