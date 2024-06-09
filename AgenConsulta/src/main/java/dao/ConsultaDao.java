package dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.Consulta;
import entidades.Cadastro;
import entidades.Medico;
import util.JPAUtil;

public class ConsultaDao {

	public void salvar(Consulta c) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		em.persist(c);
		em.getTransaction().commit();
		em.close();
	}

	public static void excluir(Consulta c) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		c = em.find(Consulta.class, c.getId());
		em.remove(c);
		em.getTransaction().commit();
		em.close();
	}

	public static List<Consulta> pesquisaTodos() {
		EntityManager em = JPAUtil.criarEntityManager();
		Query q = em.createQuery("select c from Consulta c");
		List<Consulta> lista = q.getResultList();
		em.close();
		return lista;
	}

	public static void editar(Consulta c) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		em.merge(c);
		em.getTransaction().commit();
		em.close();
	}

}
