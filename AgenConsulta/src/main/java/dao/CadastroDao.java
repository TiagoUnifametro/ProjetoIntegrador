package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.Cadastro;
import util.JPAUtil;

public class CadastroDao {

	public void salvar(Cadastro c) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		em.persist(c);
		em.getTransaction().commit();
		em.close();
	}

	public static void excluir(Cadastro c) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		c = em.find(Cadastro.class, c.getId());
		em.remove(c);
		em.getTransaction().commit();
		em.close();
	}
	
	public static List<Cadastro> pesquisaTodos() {
		EntityManager em = JPAUtil.criarEntityManager();
		Query q = em.createQuery("select c from Cadastro c");
		List<Cadastro> lista = q.getResultList();
		em.close();
		return lista;
	}
	
	public static void editar(Cadastro c) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		em.merge(c);
		em.getTransaction().commit();
		em.close();
	}
	
	
	
}
