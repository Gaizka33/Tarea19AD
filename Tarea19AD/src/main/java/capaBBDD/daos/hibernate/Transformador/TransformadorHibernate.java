package capaBBDD.daos.hibernate.Transformador;

import java.util.ArrayList;
import java.util.List;

import modelos.Hibernate.AlumnoHibernate;
import modelos.Hibernate.GrupoHibernate;
import modelos.POJOS.Alumno;
import modelos.POJOS.Grupo;

public class TransformadorHibernate {

	public AlumnoHibernate tranformarAlumno(Alumno a) {
		AlumnoHibernate alumno = new AlumnoHibernate(a.getNia(), a.getNombre(), a.getApellido(), a.getCiclo(),
				a.getCurso(), a.getGenero(), a.getFechadenacimiento(), tranformarGrupo(a.getGrupo()));

		return alumno;
	}

	public GrupoHibernate tranformarGrupo(Grupo g) {
		GrupoHibernate grupo = new GrupoHibernate(g.getNombre());
		return grupo;
	}

	public List<AlumnoHibernate> transformarListaAlumnos(List<Alumno> a) {
		List<AlumnoHibernate> alumnos = new ArrayList<AlumnoHibernate>();
		for (Alumno alumno : a) {
			alumnos.add(new AlumnoHibernate(alumno.getNia(), alumno.getNombre(), alumno.getApellido(),
					alumno.getCiclo(), alumno.getCurso(), alumno.getGenero(), alumno.getFechadenacimiento(),
					tranformarGrupo(alumno.getGrupo())));
		}
		return alumnos;

	}

	public void transformarListaGrupos(List<Grupo> g) {
		List<GrupoHibernate> grupos = new ArrayList<GrupoHibernate>();
		for (Grupo grupo : g) {
			grupos.add(new GrupoHibernate(grupo.getNombre()));
		}
	}

	public Grupo transformarGrupo(GrupoHibernate g) {
		Grupo grupo = new Grupo(g.getId(), g.getNombre());
		return grupo;
	}

	public Alumno transformarAlumno(AlumnoHibernate a) {
		Alumno alumno = new Alumno(a.getNia(), a.getNombre(), a.getApellido(), a.getCiclo(), a.getCurso(),
				a.getGenero(), a.getFechadenacimiento(), transformarGrupo(a.getGrupo()));
		return alumno;
	}
}
