package capaLNegocio.servicios.ficheros.transformacionFicheros;

import java.util.ArrayList;
import java.util.List;

import modelos.Ficheros.binario.AlumnoBinario;
import modelos.Ficheros.binario.GrupoBinario;
import modelos.POJOS.Alumno;
import modelos.POJOS.Grupo;

public class TransformacionBinario {
	private List<AlumnoBinario> alumnosBinarios = null;
	private List<GrupoBinario> gruposBinarios = null;

	public List<AlumnoBinario> transformarListaAlumnosABinario(List<Alumno> alumnos) {
		alumnosBinarios = new ArrayList<AlumnoBinario>();
		for (Alumno alumno : alumnos) {
			alumnosBinarios.add(new AlumnoBinario(alumno.getNia(), alumno.getNombre(), alumno.getApellido(),
					alumno.getCiclo(), alumno.getCurso(), alumno.getGenero(), alumno.getFechadenacimiento(),
					transformarGrupoABinario(alumno.getGrupo())));
		}
		return alumnosBinarios;
	}

	public List<GrupoBinario> transformarListaGruposABinario(List<Grupo> grupos) {
		gruposBinarios = new ArrayList<GrupoBinario>();
		for (Grupo grupo : grupos) {
			gruposBinarios.add(new GrupoBinario(grupo.getId(), grupo.getNombre()));
		}
		return gruposBinarios;
	}

	private GrupoBinario transformarGrupoABinario(Grupo grupo) {
		return new GrupoBinario(grupo.getId(), grupo.getNombre());
	}
}
