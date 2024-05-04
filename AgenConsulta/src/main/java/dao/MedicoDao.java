package dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.Medico;
import util.JPAUtil;


public class MedicoDao {

	public void salvar(Medico m) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		em.persist(m);
		em.getTransaction().commit();
		em.close();
	}

	public static void excluir(Medico m) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		m = em.find(Medico.class, m.getId());
		em.remove(m);
		em.getTransaction().commit();
		em.close();
	}

	public static List<Medico> pesquisaTodos() {
		EntityManager em = JPAUtil.criarEntityManager();
		Query q = em.createQuery("select m from Medico m");
		List<Medico> lista = q.getResultList();
		em.close();
		return lista;
	}

	public static void editar(Medico m) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		em.merge(m);
		em.getTransaction().commit();
		em.close();
	}

	
	

}
