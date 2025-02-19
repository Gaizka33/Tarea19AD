package capaBBDD.daos.hibernate.HibernateAlumno;

import java.util.ArrayList;
import java.util.List;

import capaBBDD.daos.daoAlumno.InterfazDaoAlumnos;
import capaBBDD.daos.hibernate.Transformador.TransformadorHibernate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import modelos.Hibernate.AlumnoHibernate;
import modelos.Hibernate.GrupoHibernate;
import modelos.POJOS.Alumno;
import modelos.POJOS.Grupo;

public class DaoImplementacionHibernate implements InterfazDaoAlumnos {

	private TransformadorHibernate tr = new TransformadorHibernate();
	private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Persistencia");;
	private EntityManager em;

	@Override
	public void insertarAlumno(Alumno alumno) {
		AlumnoHibernate alumnoHibernate = tr.tranformarAlumno(alumno); // Suponiendo que este es el objeto que quieres
																		// persistir
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.persist(alumnoHibernate); // Persistir el objeto convertido
			et.commit();
		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
			}
			// Log de error o manejo adecuado de la excepción
		} finally {
			em.close(); // Cerrar siempre el EntityManager
		}
	}

	@Override
	public void insertarGrupo(Grupo grupo) {
		GrupoHibernate grupoHibernate = tr.tranformarGrupo(grupo);

		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();
			em.persist(grupoHibernate);
			et.commit();
		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
			}
		} finally {
			em.close();
		}
	}

	@Override
	public List<Alumno> selectAlumnos() {
		EntityManager manager = emf.createEntityManager();
		EntityTransaction et = manager.getTransaction();
		List<Alumno> listaAlumnos = new ArrayList<>();
		List<AlumnoHibernate> listaAlumnosHibernate = new ArrayList<>();
		try {
			et.begin();
			listaAlumnosHibernate = manager.createQuery("SELECT a FROM AlumnoHibernate a", AlumnoHibernate.class)
					.getResultList();

			for (AlumnoHibernate aHb : listaAlumnosHibernate) {
				listaAlumnos.add(tr.transformarAlumno(aHb));
			}

			et.commit();

		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
			}
		} finally {
			manager.close();
		}
		return listaAlumnos;
	}

	@Override
	public List<Alumno> selectAlumnoByGroupId(int id) {
		EntityManager manager = emf.createEntityManager();
		EntityTransaction et = manager.getTransaction();
		List<Alumno> listaAlumnos = new ArrayList<>();
		List<AlumnoHibernate> listaAlumnosHibernate = new ArrayList<>();

		try {
			et.begin();

			listaAlumnosHibernate = manager
					.createQuery("SELECT a FROM AlumnoHibernate a where a.grupo = :grupo", AlumnoHibernate.class)
					.setParameter("grupo", tr.tranformarGrupo(selectGrupo(id))).getResultList();

			for (AlumnoHibernate aHb : listaAlumnosHibernate) {
				listaAlumnos.add(tr.transformarAlumno(aHb));
			}
			et.commit();

		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
			}
		} finally {
			manager.close();
		}
		return listaAlumnos;
	}

	@Override
	public void selectNombreAlumnos() {
		// TODO Auto-generated method stub

	}

	@Override
	public Alumno selectAlumnoNia(int Nia) {
		EntityManager manager = emf.createEntityManager();
		Alumno alumno = null;

		try {
			AlumnoHibernate aHb = manager.find(AlumnoHibernate.class, Nia);
			if (aHb != null) {
				alumno = tr.transformarAlumno(aHb);
			}
		} catch (Exception e) {

		} finally {
			manager.close();
		}

		return alumno;
	}

	@Override
	public void cambiarNombre(String nombre, int nia) {
		EntityManager manager = emf.createEntityManager();
		EntityTransaction et = manager.getTransaction();

		try {
			et.begin();
			manager.createQuery("UPDATE AlumnoHibernate a SET a.nombre = :nombre WHERE a.dni = :nia")
					.setParameter("nombre", nombre).setParameter("nia", nia).executeUpdate();
			et.commit();
		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
			}
		} finally {
			manager.close();
		}

	}

	@Override
	public void eliminarByNia(int nia) {
		EntityManager manager = emf.createEntityManager();
		EntityTransaction et = manager.getTransaction();

		try {
			et.begin();
			manager.createQuery("DELETE FROM AlumnoHibernate a WHERE a.dni = :nia").setParameter("nia", nia)
					.executeUpdate();
			et.commit();

		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
			}

		} finally {
			manager.close();
		}

	}

	@Override
	public List<Grupo> selectGrupos() {
		EntityManager manager = emf.createEntityManager();
		EntityTransaction et = manager.getTransaction();
		List<Grupo> listaGrupos = new ArrayList<>();
		List<GrupoHibernate> listaGruposHibernate = new ArrayList<>();
		try {
			et.begin();
			listaGruposHibernate = manager.createQuery("SELECT a FROM GrupoHibernate a", GrupoHibernate.class)
					.getResultList();

			for (GrupoHibernate gHb : listaGruposHibernate) {
				listaGrupos.add(tr.transformarGrupo(gHb));
			}
			et.commit();
		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
			}
		} finally {
			manager.close();
		}
		return listaGrupos;
	}

	@Override
	public void eliminarByGroup(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cambiarGrupo(int nia, int grupId) {
		EntityManager manager = emf.createEntityManager();
		EntityTransaction et = manager.getTransaction();
		try {
			et.begin();

			manager.createQuery("UPDATE AlumnoHibernate a SET a.grupo = :grupo WHERE a.dni = :nia")
					.setParameter("grupo", tr.tranformarGrupo(obtenerGrupoPorID(grupId)))
					.setParameter("nia", nia).executeUpdate();

			et.commit();

		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
			}
		} finally {
			manager.close();
		}

	}

	@Override
	public boolean comprobarSiExiste(int grupo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Grupo selectGrupo(int codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertarGrupos(List<Grupo> grupo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertarAlumnos(List<Alumno> alumnos) {
		// TODO Auto-generated method stub

	}
	
	public Grupo obtenerGrupoPorID(int idGrupo) {
	    EntityManager manager = emf.createEntityManager();
	    Grupo grupo = null;
	    try {
	        // Busca la entidad GrupoHibernate por su PK
	        GrupoHibernate gHb = manager.find(GrupoHibernate.class, idGrupo);
	        if (gHb != null) {
	            // Transforma la entidad Hibernate a tu objeto de negocio Grupo
	            grupo = tr.transformarGrupo(gHb);
	        } else {
	        }
	    } catch (Exception e) {
	    } finally {
	        manager.close();
	    }
	    return grupo;
	}

	private void eliminargrupoporId(int id) {
		EntityManager manager = emf.createEntityManager();
		EntityTransaction et = manager.getTransaction();

		try {
			et.begin();

			List<AlumnoHibernate> listaAlumnosEnGrupo = manager
					.createQuery("SELECT a FROM AlumnoHibernate a WHERE a.grupo = :grupo", AlumnoHibernate.class)
					.setParameter("grupo", tr.tranformarGrupo(obtenerGrupoPorID(id))).getResultList();

			if (!listaAlumnosEnGrupo.isEmpty()) {
				et.rollback();
			}

			manager.createQuery("DELETE FROM GrupoHibernate g WHERE g.ID = :id").setParameter("id", id)
					.executeUpdate();

			et.commit();

		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
			}
		} finally {
			manager.close();
		}
	}

}
