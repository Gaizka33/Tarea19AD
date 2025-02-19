package exes;

import capaBBDD.daos.daoAlumno.InterfazDaoAlumnos;
import capaBBDD.daos.daoAlumno.implementacionesDaoAlumno.DaoAlumnoJdbc;
import capaLNegocio.controladores.Controlador;
import capaLNegocio.servicios.ficheros.InterfazGestionFicheros;
import capaVistas.vistas.InterfazVistas;
import capaVistas.vistas.Vista;

public class Exec1 {

	public static void main(String[] args) {
		InterfazVistas vista = new Vista();
		InterfazDaoAlumnos dao = new DaoAlumnoJdbc();
		Controlador ctr = new Controlador(vista, dao);
		ctr.run();

	}

}
