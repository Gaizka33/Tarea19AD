package exes;

import capaBBDD.daos.daoAlumno.InterfazDaoAlumnos;
import capaBBDD.daos.daoAlumno.implementacionesDaoAlumno.DaoAlumnoJdbc;
import capaBBDD.daos.hibernate.HibernateAlumno.DaoImplementacionHibernate;
import capaBBDD.daos.oracle.DaoImplementacionOracle;
import capaLNegocio.controladores.Controlador;
import capaVistas.vistas.InterfazVistas;
import capaVistas.vistas.Vista;

public class Exec3 {

	public static void main(String[] args) {
		InterfazVistas vista = new Vista();
		InterfazDaoAlumnos dao = new DaoImplementacionOracle();
		Controlador ctr = new Controlador(vista, dao);
		ctr.run();

	}

}
