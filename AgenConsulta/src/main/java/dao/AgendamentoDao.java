package dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.Agendamento;
import entidades.Cadastro;
import entidades.Medico;
import util.JPAUtil;

public class AgendamentoDao {

	public void salvar(Agendamento a) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		em.persist(a);
		em.getTransaction().commit();
		em.close();
	}

	public static void excluir(Agendamento a) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		a = em.find(Agendamento.class, a.getId());
		em.remove(a);
		em.getTransaction().commit();
		em.close();
	}

	public static List<Agendamento> pesquisaTodos() {
		EntityManager em = JPAUtil.criarEntityManager();
		Query q = em.createQuery("select a from Agendamento a");
		List<Agendamento> lista = q.getResultList();
		em.close();
		return lista;
	}

	public static void editar(Agendamento a) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		em.merge(a);
		em.getTransaction().commit();
		em.close();
	}


	public static Agendamento pesquisaAgendamento(String clinica, Date dataHora, Integer idMedico) {
		EntityManager em = JPAUtil.criarEntityManager();
		Agendamento agendamento = new Agendamento();
		Query q = em.createQuery("select a from Agendamento a where a.idMedico = :idMedico AND a.dataHoraAgendamento = :dataHora AND a.clinica = :clinica");
		q.setParameter("dataHora", dataHora);
		q.setParameter("idMedico", idMedico);
		q.setParameter("clinica", clinica);
		
		try {
			agendamento = (Agendamento) q.getSingleResult();
		}catch (Exception e) {
			agendamento = null;
		}
		
		return agendamento;
	}
	
	public static Agendamento pesquisaAgendamentoPorID(Integer id) {
		EntityManager em = JPAUtil.criarEntityManager();
		Agendamento agendamento = new Agendamento();
		Query q = em.createQuery("select a from Agendamento a where a.id = :id");
		q.setParameter("id", id);
		
		try {
			agendamento = (Agendamento) q.getSingleResult();
		}catch (Exception e) {
			agendamento = null;
		}
		
		return agendamento;
	}
	

}
