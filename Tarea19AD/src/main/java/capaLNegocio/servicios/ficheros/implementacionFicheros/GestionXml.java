package capaLNegocio.servicios.ficheros.implementacionFicheros;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import capaLNegocio.servicios.ficheros.InterfazGestionFicheros;
import modelos.POJOS.Alumno;
import modelos.POJOS.Grupo;

public class GestionXml implements InterfazGestionFicheros {
	private File file = new File("src/ficheros/alumnos.xml");
	private DocumentBuilderFactory documFactory = DocumentBuilderFactory.newInstance();
	private DocumentBuilder dbuilder;
	private Element elementoGrupo = null;

	private void existe() {
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdir();
		}
	}

	public void crearXMLGrupos(List<Grupo> grupos) {
		existe();
		List<Alumno> alumnos;
		try {
			dbuilder = documFactory.newDocumentBuilder();
			Document document = dbuilder.newDocument();

			Element raiz = document.createElement("grupos");
			document.appendChild(raiz);

			for (Grupo grupo : grupos) {
				Element hijo = document.createElement("grupo");
				hijo.setAttribute("id", String.valueOf(grupo.getId()));
				hijo.setAttribute("nombre", grupo.getNombre());

				alumnos = grupo.getListaAlumnos();

				if (alumnos != null) {

					for (Alumno alumno : alumnos) {
						Element alumnoGrupo = document.createElement("alumno");
						alumnoGrupo.setAttribute("nia", String.valueOf(alumno.getNia()));
						alumnoGrupo.setAttribute("nombre", alumno.getNombre());
						alumnoGrupo.setAttribute("apellidos", alumno.getApellido());
						alumnoGrupo.setAttribute("genero", String.valueOf(alumno.getGenero()));

						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String fechaNacimiento = sdf.format(alumno.getFechadenacimiento());
						alumnoGrupo.setAttribute("fechaNacimento", fechaNacimiento);

						alumnoGrupo.setAttribute("ciclo", alumno.getCiclo());
						alumnoGrupo.setAttribute("curso", alumno.getCurso());
						alumnoGrupo.setAttribute("grupo", String.valueOf(alumno.getGrupo()));

						hijo.appendChild(alumnoGrupo);
					}

				}

				raiz.appendChild(hijo);
			}

			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(file);

			transformer.transform(source, result);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Grupo> cargarGruposDesdeXML() {
		List<Grupo> grupos = new ArrayList<Grupo>();

		try {
			dbuilder = documFactory.newDocumentBuilder();
			Document document = dbuilder.parse(file);

			// Obtén la lista de nodos "grupo"
			NodeList nodelist = document.getElementsByTagName("grupo");

			for (int i = 0; i < nodelist.getLength(); i++) {
				Node nodoGrupo = nodelist.item(i);
				if (nodoGrupo.getNodeType() == Node.ELEMENT_NODE) {
					elementoGrupo = (Element) nodoGrupo;

					// Parseo del id del grupo (asegúrate de que es un número válido)
					int id = 0;
					try {
						id = Integer.parseInt(elementoGrupo.getAttribute("id"));
					} catch (NumberFormatException e) {
						System.out.println("Error al parsear el id del grupo: " + elementoGrupo.getAttribute("id"));
					}

					String name = elementoGrupo.getAttribute("nombre");

					// Crear el grupo y añadirlo a la lista
					Grupo grupo = new Grupo(id, name);
					grupos.add(grupo);

				}
			}
			return grupos;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return grupos;
	}

	public List<Alumno> cargarAlumnosDesdeXml() {
		List<Alumno> alumnos = new ArrayList<Alumno>();
		NodeList nodeAlumnosList = elementoGrupo.getElementsByTagName("alumno");
		List<Grupo> listagrupos = cargarGruposDesdeXML();

		for (int j = 0; j < nodeAlumnosList.getLength(); j++) {
			Element elementoAlumno = (Element) nodeAlumnosList.item(j);

			// Parseo de los atributos del alumno
			int nia = 0;
			try {
				nia = Integer.parseInt(elementoAlumno.getAttribute("nia"));
			} catch (NumberFormatException e) {
				System.out.println("Error al parsear el NIA del alumno: " + elementoAlumno.getAttribute("nia"));
			}

			String nombre = elementoAlumno.getAttribute("nombre");
			String apellidos = elementoAlumno.getAttribute("apellidos");
			String genero = elementoAlumno.getAttribute("genero");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String fecha = elementoAlumno.getAttribute("fechaNacimento");
			Date fechaNacimiento = null;
			try {
				fechaNacimiento = sdf.parse(fecha);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String ciclo = elementoAlumno.getAttribute("ciclo");
			String curso = elementoAlumno.getAttribute("curso");

			int grupoid = 0;
			String nombreGrupo = null;
			try {
				grupoid = Integer.parseInt(elementoAlumno.getAttribute("grupo"));
				for (Grupo grupo : listagrupos) {
					if (grupoid == grupo.getId()) {
						nombreGrupo = grupo.getNombre();
						break;
					}
				}
			} catch (NumberFormatException e) {
				System.out.println("Error al parsear el grupo del alumno: " + elementoAlumno.getAttribute("grupo"));
			}

			// Crear un objeto Alumno y añadirlo a la lista de alumnos
			Alumno alumno = new Alumno(nia, nombre, apellidos, ciclo, curso, genero.charAt(0), fechaNacimiento,
					new Grupo(grupoid, nombreGrupo));

			alumnos.add(alumno);

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
	public void crearJSONGrupos(List<Grupo> grupos) {
		// TODO Auto-generated method stub

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
