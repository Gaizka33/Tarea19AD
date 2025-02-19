package capaLNegocio.servicios.ficheros;

import java.util.List;

import modelos.POJOS.Alumno;
import modelos.POJOS.Grupo;

public interface InterfazGestionFicheros {
	public void guardarBin(List<Alumno> alumnos);
	
	public List<Alumno> sacarAlumnosBin();
	
	public void crearXMLGrupos(List<Grupo> grupos);
	
	public void crearJSONGrupos(List<Grupo> grupos);
	
	public List<Grupo> cargarGruposDesdeXML();
	
	public List<Alumno> cargarAlumnosDesdeXml();
	
	public List<Alumno> cargarDesdeJSON();
	
	public void crearJSONUnGrupo(Grupo grupo);

}
