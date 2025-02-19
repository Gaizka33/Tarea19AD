package capaLNegocio.controladores;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import capaBBDD.daos.daoAlumno.InterfazDaoAlumnos;
import capaLNegocio.servicios.ficheros.InterfazGestionFicheros;
import capaLNegocio.servicios.ficheros.implementacionFicheros.GestionBinario;
import capaLNegocio.servicios.ficheros.implementacionFicheros.GestionJson;
import capaLNegocio.servicios.ficheros.implementacionFicheros.GestionXml;
import capaVistas.vistas.InterfazVistas;
import modelos.POJOS.Alumno;
import modelos.POJOS.Grupo;

public class Controlador {
	private final InterfazVistas vista;
	private final InterfazDaoAlumnos dao;
	private final InterfazGestionFicheros gestBinario = new GestionBinario();
	private final InterfazGestionFicheros gestJson = new GestionJson();
	private final InterfazGestionFicheros gestXml = new GestionXml();
	private List<Alumno> alumnos;
	private List<Grupo> grupos;

	public Controlador(InterfazVistas vista, InterfazDaoAlumnos dao) {
		super();
		this.vista = vista;
		this.dao = dao;
	}

	public void run() {
		int eleccion = vista.chuparYGuardarInt("elige opcion pana");
		do {
			generarMenu();
			switch (eleccion) {
			case 1 -> generarAlumno();
			case 2 -> generarGrupo();
			case 3 -> mostrarAlumnos();
			case 4 -> guardarBinario();
			case 5 -> cargarBinario();
			case 6 -> cambiarNombre();
			case 7 -> eliminarNia();
			case 8 -> eliminarGrupo();
			case 9 -> guardarXml();
			case 10 -> guardarJson();
			case 11 -> cargarXml();
			case 12 -> cargarJson();
			case 13 -> chuparAlumnoNia();
			case 14 -> cambiarGrupo();
			case 15 -> guardarJson1();
			case 16 -> vista.mostrarMensaje("saliste del programa");
			}
			eleccion = vista.chuparYGuardarInt("elige opcion pana");
		} while (eleccion != 16);

	}

	private void guardarJson1() {
		selccionarGrupos();
        int id = vista.chuparYGuardarInt("selecciona el grupo pls");
        Grupo g = dao.selectGrupo(id);
        gestJson.crearJSONUnGrupo(g);
        vista.mostrarMensaje("Grupo guardado");
	}

	private void selccionarGrupos() {
		grupos = dao.selectGrupos();
		for (Grupo grupo : grupos) {
			vista.mostrarMensaje(grupo.toString());
		}
		
	}

	private void cambiarGrupo() {
		int nia = vista.chuparYGuardarInt("dame el nia del alumno anda");
		int idgrupo = vista.chuparYGuardarInt("dame el id del grupo majo");
		dao.cambiarGrupo(nia, idgrupo);
	}

	private void chuparAlumnoNia() {
		vista.chuparYGuardarInt("dame el Nia anda");
		Alumno a =dao.selectAlumnoNia(0);
		vista.mostrarMensaje(a.toString());
	}

	private void cargarJson() {
		alumnos = gestJson.cargarDesdeJSON();
		dao.insertarAlumnos(alumnos);
	}

	private void cargarXml() {
		grupos = gestXml.cargarGruposDesdeXML();
		alumnos = gestXml.cargarAlumnosDesdeXml();
		dao.insertarAlumnos(alumnos);
		dao.insertarGrupos(grupos);
	}

	private void guardarJson() {
		PonerAlumnosEnGrupos();
		gestJson.crearJSONGrupos(grupos);
	}

	private void PonerAlumnosEnGrupos() {
		List<Alumno> listadoDeAlumnos = new ArrayList<Alumno>();
		for (Grupo grupo : grupos) {
			for (Alumno alumno : alumnos) {
				if (alumno.getGrupo().equals(grupo)) {
					listadoDeAlumnos.add(alumno);
				}
				grupo.setListaAlumnos(listadoDeAlumnos);
			}
		}

	}

	private void guardarXml() {
		PonerAlumnosEnGrupos();
		gestXml.crearXMLGrupos(grupos);
	}

	private void eliminarNia() {
		int nia = vista.chuparYGuardarInt("dame el nia");
		dao.eliminarByNia(nia);
	}

	private void eliminarGrupo() {
		int id = vista.chuparYGuardarInt("dame el id del grupo");
		dao.eliminarByGroup(id);
	}

	private void cambiarNombre() {
		int nia = vista.chuparYGuardarInt("dame el nia de el alumno");
		String nombre = vista.chuparYGuardarString("dame el nombre del alumno");
		dao.cambiarNombre(nombre, nia);
	}

	private void cargarBinario() {
		alumnos = gestBinario.sacarAlumnosBin();
	}

	private void guardarBinario() {
		gestBinario.guardarBin(alumnos);
	}

	private void mostrarAlumnos() {
		alumnos = dao.selectAlumnos();
		for (Alumno alumno : alumnos) {
			vista.mostrarMensaje(alumno.toString());
		}
	}

	private void generarGrupo() {
		grupos = new ArrayList<Grupo>();
		int id = vista.chuparYGuardarInt("dame el id del grupo anda");
		String nombre = vista.chuparYGuardarString("dame el nombre del grupo anda");
		grupos.add(new Grupo(id, nombre));
		dao.insertarGrupos(grupos);
	}

	private void generarAlumno() {
		alumnos = new ArrayList<Alumno>();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		int nia = vista.chuparYGuardarInt("Dame el nia del alumno pls");
		String nombre = vista.chuparYGuardarString("Dame el nombre del alumno pls");
		String apellido = vista.chuparYGuardarString("Dame el apellido del alumno pls");
		String ciclo = vista.chuparYGuardarString("Dame el ciclo del alumno pls");
		String curso = vista.chuparYGuardarString("Dame el curso del alumno pls");
		char genero = vista.chuparYGuardarString("Dame el genero del alumno pls").charAt(0);
		String fechaDeNacimientoString = vista.chuparYGuardarString("Dame la fecha de nacimiento del alumno pls");
		Date fechaDeNacimiento = null;
		try {
			fechaDeNacimiento = formato.parse(fechaDeNacimientoString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int grupo = vista.chuparYGuardarInt("Dame el grupo del alumno pls");

		if (dao.comprobarSiExiste(grupo)) {
			alumnos.add(
					new Alumno(nia, nombre, apellido, ciclo, curso, genero, fechaDeNacimiento, dao.selectGrupo(grupo)));
		} else {
			if (vista.chuparYGuardarString("no existe el grupo pana, quieres crearlo?").equalsIgnoreCase("NO")) {
				vista.mostrarMensaje("pues se te va a poner en nulo el grupo");
				alumnos.add(new Alumno(nia, nombre, apellido, ciclo, curso, genero, fechaDeNacimiento, null));
			} else if (vista.chuparYGuardarString("no existe el grupo pana, quieres crearlo?").equalsIgnoreCase("SI")) {
				generarGrupo();
				alumnos.add(new Alumno(nia, nombre, apellido, ciclo, curso, genero, fechaDeNacimiento,
						dao.selectGrupo(grupo)));
			} else {
				vista.mostrarMensaje("respuesta incorrecta");
			}

		}

	}

	private void generarMenu() {
		vista.mostrarMensaje("""
					Opciones:
				          1- Insertar alumno
				          2- Insertar grupo
				          3- Mostrar alumnos
				          4- Guardar en binario
				          5- Cargar desde binario
				          6- Cambiar nombre
				          7- Eliminar por NIA
				          8- Eliminar por grupo
				          9- Guardar en XML
				          10- Guardar en JSON
				          11- Cargar desde XML
				          12- Cargar desde JSON
				          13- Seleccionar alumno por NIA
				          14- Cambiar de grupo
				          15- Guardar un grupo en JSON
				          16- Salir
				""");
	}

}
