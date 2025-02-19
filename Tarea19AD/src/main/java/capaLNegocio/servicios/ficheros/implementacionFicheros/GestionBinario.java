package capaLNegocio.servicios.ficheros.implementacionFicheros;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import capaLNegocio.servicios.ficheros.InterfazGestionFicheros;
import capaLNegocio.servicios.ficheros.transformacionFicheros.TransformacionBinario;
import modelos.Ficheros.binario.AlumnoBinario;
import modelos.Ficheros.binario.GrupoBinario;
import modelos.POJOS.Alumno;
import modelos.POJOS.Grupo;

public class GestionBinario implements InterfazGestionFicheros {
	private final File file = new File("src/ficheros/alumnos.dat");
	private List<AlumnoBinario> listaAlumnos;
	private List<GrupoBinario> listaGrupos;
	private TransformacionBinario tr = new TransformacionBinario();

	public void guardarBin(List<Alumno> alumnos) {
		listaAlumnos = tr.transformarListaAlumnosABinario(alumnos);

		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdir();
		}

		try (FileOutputStream fos = new FileOutputStream(file)) {

			ObjectOutputStream oos = new ObjectOutputStream(fos);
			for (AlumnoBinario a : listaAlumnos) {
				oos.writeObject(a);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Alumno> sacarAlumnosBin() {
		List<Alumno> alumnos = new ArrayList<>();
		try (FileInputStream fis = new FileInputStream(file)) {

			ObjectInputStream ois = new ObjectInputStream(fis);

			while (true) { // Leer hasta que se alcance el fin del archivo
				try {
					Alumno alumno = (Alumno) ois.readObject(); // Leer un objeto
					alumnos.add(alumno); // Agregar a la lista
				} catch (Exception e) {
					// Fin del archivo alcanzado
					break;
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error en la lectura del binario");
			e.printStackTrace();
		}

		return alumnos;
	}

	@Override
	public void crearXMLGrupos(List<Grupo> grupos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void crearJSONGrupos(List<Grupo> grupos) {
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

	@Override
	public List<Alumno> cargarDesdeJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void crearJSONUnGrupo(Grupo grupo) {
		// TODO Auto-generated method stub
		
	}

}
