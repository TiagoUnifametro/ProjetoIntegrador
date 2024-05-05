package bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.hibernate.internal.build.AllowSysOut;

import dao.AgendamentoDao;
import dao.CadastroDao;
import dao.MedicoDao;
import entidades.Agendamento;
import entidades.Cadastro;
import entidades.Medico;

@ManagedBean
@ApplicationScoped
public class ConsultaBean {

	private Cadastro cadastro = new Cadastro(); 
	private Agendamento agendamento = new Agendamento();
	private Medico medico = new Medico(); 
	private List<Medico> listaMedico;
	private List<Agendamento> listaAgendamento;
	

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public	Cadastro getCadastro() {
		return cadastro;
	}

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}

	public void setCadastro(Cadastro cadastro) {
		this.cadastro = cadastro;
	}


//Cadastro - Início		
	public String ativo() { 
		String ativo;
		cadastro.setAtivo(true);
		if(cadastro.isAtivo()) {
			ativo = "ATIVO";
		}else {
			ativo = "INATIVO";
		}
		return ativo;
	}

	public String dataAtual() throws ParseException{ 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		Date atual = Date.from(Instant.now()); 
		String dAtual = sdf.format(atual); 
		cadastro.setDataCadastro(atual);
		return dAtual;
	}

	public String salvar() throws ParseException { 
			CadastroDao cDao = new CadastroDao(); 
			List<Cadastro> lista = cDao.pesquisaTodos(); 
			
			for(Cadastro cadastroc : lista) { 
				if(cadastro.getApelido().equals(cadastroc.getApelido())) {
					//System.out.println("Usuario liberado");
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario não cadastrado:", "Apelido já está em uso, favor tentar novamente!"));
					return null; 
				}
			}

		cDao.salvar(cadastro);
		cadastro = new Cadastro();
		return "login.xhtml";
	}
//Cadastro - Fim

	
//Login - Início
	public String logar(){
		CadastroDao cDao = new CadastroDao(); 
		List<Cadastro> lista = cDao.pesquisaTodos(); 
		
		for(Cadastro cadastroc : lista) { 
			if(cadastro.getApelido().equals(cadastroc.getApelido()) && cadastro.getSenha().equals(cadastroc.getSenha())) {
				//System.out.println("Usuario liberado");
				cadastro = cadastroc;
				return "home.xhtml"; 
			}
		}
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error:", "Apelido ou Senha inválidos, favor tentar novamente ou cadastre-se!"));
		cadastro = new Cadastro();
		agendamento = new Agendamento();
		return null;
	}
//Login - Fim
	

//Trocar Senha - Início
	public String trocarSenha(){
		CadastroDao cDao = new CadastroDao(); 
		List<Cadastro> lista = cDao.pesquisaTodos(); 
		
		for(Cadastro cadastroc : lista) { 
			//System.out.println(cadastroc.getApelido() + " - " + cadastroc.getSenha());
			
			if(cadastro.getApelido().equals(cadastroc.getApelido()) && cadastro.getDataNascimento().equals(cadastroc.getDataNascimento())) {
				//System.out.println("Usuario liberado");
				cadastroc.setSenha(cadastro.getSenha()); 
				CadastroDao cDaoE = new CadastroDao();
				cDaoE.editar(cadastroc); 
				Cadastro c = new Cadastro();
				return "login";
			}//else {
				//System.out.println("Usuario não liberado");
			//}
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Apelido ou Data de Nascimento inválidos, favor tentar novamente ou cadastre-se!"));
		
		return null;
	}
//Trocar Senha - Fim


//Editar Perfil - Início
	public String editar() { 
		CadastroDao cDao = new CadastroDao(); 
		cDao.editar(cadastro);
		return "home.xhtml";
	}
	
	public String excluir() {
		CadastroDao cDao = new CadastroDao();
		cDao.excluir(cadastro);
		cadastro = new Cadastro();
		return "login.xhtml";
	}
//Editar Perfil - Fim
	
//Listagem - Início
	public List<Cadastro> lista(){
		CadastroDao cDao = new CadastroDao();
		List<Cadastro> lista = cDao.pesquisaTodos();
			return lista;
		}
	
//Listagem - Fim
	
//Home - Início
	public String ativoHome(){
		String ativoHome;
		if(cadastro.isAtivo()) {
			ativoHome = "ATIVO";
		}else {
			ativoHome = "INATIVO";
		}
		return ativoHome;
	}
//Home - Fim
	
	
	
	
	
	
	
//AGENDAMENTO DE CONSULTAS
	
	
	
	
	
	
//Agendamento - Início
	
	public String statusConsulta() {
		return agendamento.getStatus().toString();
	}
		
	public List<Medico> listaMedico(){
		MedicoDao mDao = new MedicoDao();
		listaMedico = mDao.pesquisaTodos();
		//System.out.println("REFLETIUUUUUUU");
		return listaMedico;
	}
	
	public String dataAtualAgenda() throws ParseException{ 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		Date atual = Date.from(Instant.now()); 
		String dAtual = sdf.format(atual); 
		agendamento.setDataCadastro(atual);
		return dAtual;
	}
	
	public String salvarAgendamento() {
		
		System.out.println(agendamento.getClinica());
		System.out.println(agendamento.getDataHoraAgendamento());
		System.out.println(agendamento.getIdMedico());
		AgendamentoDao aDao = new AgendamentoDao();
		Agendamento result = aDao.pesquisaAgendamento(agendamento.getClinica(), agendamento.getDataHoraAgendamento(), agendamento.getIdMedico());
	
		if(result != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Já existe agendamento para esse horário e médico, favor escolher outro!"));
			return null;
		}else {
			for(Medico m : listaMedico) {
				if(m.getId() == agendamento.getIdMedico()) {
					agendamento.setMedico(m.getNomeMedico());
				}
			}
			
			aDao.salvar(agendamento);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso:", "Agendamento marcado com sucesso! O número do agendamento é: " + agendamento.getId()));
		}
		
		agendamento = new Agendamento();
		return null;
	}
	
//Agendamento - Fim
	
//Listgem Médico - Ínicio
	
	public String salvarMedico() {
		MedicoDao mDao = new MedicoDao();
		mDao.salvar(medico);
		return null;
	}
	
//Listgem Médico - Fim
	
	
//Listagem Agendamento - Ínicio
	
	public List<Agendamento> listaAgendamento() {
		AgendamentoDao aDao = new AgendamentoDao();		
		listaAgendamento = aDao.pesquisaTodos();
		return listaAgendamento;
	}

	
	public String excluirAgendamento(Agendamento list){
		AgendamentoDao aDao = new AgendamentoDao();
		aDao.excluir(list);
		return null;
	}
	
	public String editarAgendamento() {
		AgendamentoDao aDao = new AgendamentoDao();
		
		Agendamento resultPorId = (Agendamento) aDao.pesquisaAgendamentoPorID(agendamento.getId());
		
		if(resultPorId != null) {
			
			if(agendamento.getClinica().equals(resultPorId.getClinica()) != true || agendamento.getDataHoraAgendamento().compareTo(resultPorId.getDataHoraAgendamento()) != 0  || resultPorId.getIdMedico() != agendamento.getIdMedico()) {
			
				Agendamento pesquisaPorAgendamento = aDao.pesquisaAgendamento(agendamento.getClinica(), agendamento.getDataHoraAgendamento(), agendamento.getIdMedico());
				
				if(pesquisaPorAgendamento != null) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Já existe agendamento para esse horário e médico, favor escolher outro!"));
					return null;
				}else {
					inputMedicoAgendamento();
					aDao.editar(agendamento);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso:", "Agendamento marcado com sucesso! O número do agendamento é: " + agendamento.getId()));
				}
			}else {
				inputMedicoAgendamento();
				aDao.editar(agendamento);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso:", "Agendamento marcado com sucesso! O número do agendamento é: " + agendamento.getId()));
			}
		}
		return null;
	}
	
	private void inputMedicoAgendamento() {
		for(Medico m : listaMedico) {
			if(m.getId() == agendamento.getIdMedico()) {
				agendamento.setMedico(m.getNomeMedico());
			}
		}
	}
	
	public String cancelarAgendamento(Agendamento a) {
		AgendamentoDao aDao = new AgendamentoDao();
		a.setStatus("Cancelado");
		aDao.editar(a);
		return null;
	}
	
}
