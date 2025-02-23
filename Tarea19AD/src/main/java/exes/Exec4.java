package exes;

import capaBBDD.daos.daoAlumno.InterfazDaoAlumnos;
import capaBBDD.daos.hibernate.HibernateAlumno.DaoImplementacionHibernate;
import capaBBDD.daos.mongoDb.DaoImplementacionMongoDb;
import capaLNegocio.controladores.Controlador;
import capaVistas.vistas.InterfazVistas;
import capaVistas.vistas.Vista;

public class Exec4 {

	public static void main(String[] args) {
		InterfazVistas vista = new Vista();
		InterfazDaoAlumnos dao = new DaoImplementacionMongoDb();
		Controlador ctr = new Controlador(vista, dao);
		ctr.run();

	}

}
