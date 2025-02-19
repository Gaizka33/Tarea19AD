package modelos.Ficheros.binario;

import java.io.Serializable;
import java.util.Date;

import modelos.POJOS.Grupo;

public class AlumnoBinario implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int nia;
	private String nombre, apellido, ciclo, curso;
	private char genero;
	private Date fechadenacimiento;
	private GrupoBinario grupo;

	public AlumnoBinario(int nia, String nombre, String apellido, String ciclo, String curso, char genero,
			Date fechadenacimiento, GrupoBinario grupo) {
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

	public GrupoBinario getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoBinario grupo) {
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
