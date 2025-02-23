package modelos.Hibernate;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "GRUPOS")
public class GrupoHibernate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;
	@OneToMany(mappedBy = "usuario")
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
