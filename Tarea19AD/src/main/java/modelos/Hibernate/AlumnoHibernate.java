package modelos.Hibernate;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "ALUMNOS")
public class AlumnoHibernate {
	
	@Id
	@Column(name = "NIA")
	private int nia;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "apellidos", nullable = false)
	private String apellido;
	
	@Column(name = "ciclo")
	private String ciclo;
	
	@Column(name = "curso")
	private String curso;
	
	@Column(name = "genero")
	private char genero;	
	@Temporal(TemporalType.DATE)
	private Date fechadenacimiento;
	@ManyToOne
    @JoinColumn(name = "grupo_id")
	private GrupoHibernate grupo;

	public AlumnoHibernate(int nia, String nombre, String apellido, String ciclo, String curso, char genero,
			Date fechadenacimiento, GrupoHibernate grupo) {
		super();
		this.nia = nia;
		this.nombre = nombre;
		this.apellido = apellido;
		this.ciclo = ciclo;
		this.curso = curso;
		this.genero = genero;
		this.fechadenacimiento = fechadenacimiento;
		this.grupo = grupo;
	}

	public int getNia() {
		return nia;
	}

	public void setNia(int nia) {
		this.nia = nia;
	}

	public GrupoHibernate getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoHibernate grupo) {
		this.grupo = grupo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public char getGenero() {
		return genero;
	}

	public void setGenero(char genero) {
		this.genero = genero;
	}

	public Date getFechadenacimiento() {
		return fechadenacimiento;
	}

	public void setFechadenacimiento(Date fechadenacimiento) {
		this.fechadenacimiento = fechadenacimiento;
	}

	@Override
	public String toString() {
		return "Alumno [nia=" + nia + ", grupo=" + grupo + ", nombre=" + nombre + ", apellido=" + apellido + ", ciclo="
				+ ciclo + ", curso=" + curso + ", genero=" + genero + ", fechadenacimiento=" + fechadenacimiento + "]";
	}

}
