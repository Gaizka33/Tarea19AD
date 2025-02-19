package capaBBDD.daos.mongoDb;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import capaBBDD.daos.daoAlumno.InterfazDaoAlumnos;
import modelos.POJOS.Alumno;
import modelos.POJOS.Grupo;
import capaBBDD.pool.MongoDBDataSource;

public class DaoImplementacionMongoDb implements InterfazDaoAlumnos {

	private MongoDatabase conectar() {
		return MongoDBDataSource.getDatabase();
	}

	@Override
	public void insertarAlumno(Alumno alumno) {
		MongoCollection<Document> collection = conectar().getCollection("alumnos");
		Document doc = new Document("nia", alumno.getNia()).append("nombre", alumno.getNombre())
				.append("apellido", alumno.getApellido()).append("ciclo", alumno.getCiclo())
				.append("curso", alumno.getCurso()).append("genero", String.valueOf(alumno.getGenero()))
				.append("fecha_nacimiento", alumno.getFechadenacimiento())
				.append("grupo_id", alumno.getGrupo().getId());
		collection.insertOne(doc);
	}

	@Override
	public void insertarGrupo(Grupo grupo) {
		MongoCollection<Document> collection = conectar().getCollection("grupos");
		Document doc = new Document("id", grupo.getId()).append("nombre", grupo.getNombre());
		collection.insertOne(doc);
	}

	@Override
	public List<Alumno> selectAlumnos() {
		List<Alumno> alumnos = new ArrayList<>();
		MongoCollection<Document> collection = conectar().getCollection("alumnos");
		FindIterable<Document> docs = collection.find();
		for (Document doc : docs) {
			alumnos.add(new Alumno(doc.getInteger("nia"), doc.getString("nombre"), doc.getString("apellido"),
					doc.getString("ciclo"), doc.getString("curso"), doc.getString("genero").charAt(0),
					doc.getDate("fecha_nacimiento"), new Grupo(doc.getInteger("grupo_id"), "")));
		}
		return alumnos;
	}

	@Override
	public List<Alumno> selectAlumnoByGroupId(int id) {
		List<Alumno> alumnos = new ArrayList<>();
		MongoCollection<Document> collection = conectar().getCollection("alumnos");
		FindIterable<Document> docs = collection.find(Filters.eq("grupo_id", id));
		for (Document doc : docs) {
			alumnos.add(new Alumno(doc.getInteger("nia"), doc.getString("nombre"), doc.getString("apellido"),
					doc.getString("ciclo"), doc.getString("curso"), doc.getString("genero").charAt(0),
					doc.getDate("fecha_nacimiento"), new Grupo(id, "")));
		}
		return alumnos;
	}

	@Override
	public void cambiarGrupo(int nia, int grupoId) {
		MongoCollection<Document> collection = conectar().getCollection("alumnos");
		collection.updateOne(Filters.eq("nia", nia), Updates.set("grupo_id", grupoId));
	}

	@Override
	public void eliminarByGroup(int id) {
		MongoCollection<Document> collection = conectar().getCollection("alumnos");
		collection.deleteMany(Filters.eq("grupo_id", id));
	}

	@Override
	public Grupo selectGrupo(int codigo) {
		MongoCollection<Document> collection = conectar().getCollection("grupos");
		Document doc = collection.find(Filters.eq("id", codigo)).first();
		return (doc != null) ? new Grupo(doc.getInteger("id"), doc.getString("nombre")) : null;
	}

	@Override
	public boolean comprobarSiExiste(int grupo) {
		MongoCollection<Document> collection = conectar().getCollection("grupos");
		return collection.countDocuments(Filters.eq("id", grupo)) > 0;
	}

	@Override
	public void selectNombreAlumnos() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Alumno selectAlumnoNia(int Nia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cambiarNombre(String nombre, int nia) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarByNia(int nia) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Grupo> selectGrupos() {
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
}
