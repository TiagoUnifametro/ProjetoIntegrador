package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import entidades.Paciente;
import util.JPAUtil;

public class PacienteDao {

	public void salvar(Paciente p) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		em.persist(p);
		em.getTransaction().commit();
		em.close();
	}

	public static void excluir(Paciente p) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		p = em.find(Paciente.class, p.getId());
		em.remove(p);
		em.getTransaction().commit();
		em.close();
	}

	public static List<Paciente> pesquisaTodos() {
		EntityManager em = JPAUtil.criarEntityManager();
		Query q = em.createQuery("select p from Paciente p");
		List<Paciente> lista = q.getResultList();
		em.close();
		return lista;
	}

	public static void editar(Paciente p) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		em.merge(p);
		em.getTransaction().commit();
		em.close();
	}




}
