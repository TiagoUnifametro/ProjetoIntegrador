package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.IMC;
import util.JPAUtil;

public class IMCDao {


	public void salvar(IMC imc) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		em.persist(imc);
		em.getTransaction().commit();
		em.close();
	}

	public static void excluir(IMC imc) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		imc = em.find(IMC.class, imc.getId());
		em.remove(imc);
		em.getTransaction().commit();
		em.close();
	}

	public static List<IMC> pesquisaTodos() {
		EntityManager em = JPAUtil.criarEntityManager();
		Query q = em.createQuery("select i from IMC i");
		List<IMC> lista = q.getResultList();
		em.close();
		return lista;
	}

	public static void editar(IMC imc) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		em.merge(imc);
		em.getTransaction().commit();
		em.close();
	}

}
