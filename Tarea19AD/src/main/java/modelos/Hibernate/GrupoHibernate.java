package modelos.Hibernate;

import java.util.List;

public class GrupoHibernate {
	private int id;
	private String nombre;
	private List<AlumnoHibernate> listaAlumnos;

	public GrupoHibernate(String nombre) {
		super();
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<AlumnoHibernate> getListaAlumnos() {
		return listaAlumnos;
	}

	public void setListaAlumnos(List<AlumnoHibernate> listaAlumnos) {
		this.listaAlumnos = listaAlumnos;
	}

	@Override
	public String toString() {
		return "Grupo [id=" + id + ", nombre=" + nombre + ", listaAlumnos=" + listaAlumnos + "]";
	}
}
