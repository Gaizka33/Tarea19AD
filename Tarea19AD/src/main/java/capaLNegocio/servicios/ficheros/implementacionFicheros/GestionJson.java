package capaLNegocio.servicios.ficheros.implementacionFicheros;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import capaLNegocio.servicios.ficheros.InterfazGestionFicheros;
import modelos.POJOS.Alumno;
import modelos.POJOS.Grupo;

public class GestionJson implements InterfazGestionFicheros {
	private File file = new File("src/ficheros/alumnos.json");

	private void existe() {
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdir();
		}
	}

	public void crearJSONGrupos(List<Grupo> grupos) {
		existe();
		try (FileWriter fw = new FileWriter(file)) {

			Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd").create();

			gson.toJson(grupos, fw);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void crearJSONUnGrupo(Grupo grupo) {
		existe();
		try (FileWriter fw = new FileWriter("src/out/JsonUnSoloGrupo.json")) {

			Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd").create();

			gson.toJson(grupo, fw);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Alumno> cargarDesdeJSON() {
		List<Alumno> alumnos = new ArrayList<Alumno>();
		try (FileReader fr = new FileReader(file)) {

			Gson gson = new Gson();

			Grupo[] groposArry = gson.fromJson(fr, Grupo[].class);

			for (Grupo grupo : groposArry) {

				for (Alumno alumno : grupo.getListaAlumnos()) {
					alumnos.add(alumno);

				}
			}
			return alumnos;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return alumnos;
	}

	@Override
	public void guardarBin(List<Alumno> alumnos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Alumno> sacarAlumnosBin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void crearXMLGrupos(List<Grupo> grupos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Grupo> cargarGruposDesdeXML() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Alumno> cargarAlumnosDesdeXml() {
		// TODO Auto-generated method stub
		return null;
	}

}
