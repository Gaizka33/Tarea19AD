package exes;

import capaBBDD.daos.daoAlumno.InterfazDaoAlumnos;
import capaBBDD.daos.daoAlumno.implementacionesDaoAlumno.DaoAlumnoJdbc;
import capaBBDD.daos.hibernate.HibernateAlumno.DaoImplementacionHibernate;
import capaLNegocio.controladores.Controlador;
import capaVistas.vistas.InterfazVistas;
import capaVistas.vistas.Vista;

public class Exec2 {

	public static void main(String[] args) {
		InterfazVistas vista = new Vista();
		InterfazDaoAlumnos dao = new DaoImplementacionHibernate();
		Controlador ctr = new Controlador(vista, dao);
		ctr.run();

	}

}
