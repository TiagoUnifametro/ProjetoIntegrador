package bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import dao.AgendamentoDao;
import dao.CadastroDao;
import dao.ConsultaDao;
import dao.IMCDao;
import dao.MedicoDao;
import dao.PacienteDao;
import entidades.Agendamento;
import entidades.Cadastro;
import entidades.Consulta;
import entidades.IMC;
import entidades.Medico;
import entidades.Paciente;

@ManagedBean
@ApplicationScoped
public class ConsultaBean {

	private Cadastro cadastro = new Cadastro(); 
	private Agendamento agendamento = new Agendamento();
	private Medico medico = new Medico(); 
	private Paciente paciente = new Paciente();
	private IMC imc = new IMC();
	private Consulta consulta = new Consulta();
	private List<Medico> listaMedico;
	private List<Agendamento> listaAgendamento;
	private List<Paciente> listaPaciente;
	private List<IMC> listaIMC;
	private List<Consulta> listaConsulta;

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

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public IMC getIMC() {
		return imc;
	}

	public void setIMC(IMC imc) {
		this.imc = imc;
	}

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
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
		
		AgendamentoDao aDao = new AgendamentoDao();
		Agendamento result = aDao.pesquisaAgendamento(agendamento.getClinica(), agendamento.getDataHoraAgendamento(), agendamento.getIdMedico());
	
		if(result != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Já existe agendamento para esse horário e médico, favor escolher outro!"));
			return null;
		}else {
			inputMedicoAgendamento();
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
		medico = new Medico();
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
	
	
	
	
	
	
//PACIENTES E IMC
	
	
	
	
	
	
//Agendamento - Início

	public List<Paciente> listaPaciente() {
		PacienteDao aDao = new PacienteDao();		
		listaPaciente = aDao.pesquisaTodos();
		return listaPaciente;
	}
	
	
	public String salvarPaciente() {
		PacienteDao pDao = new PacienteDao();
		pDao.salvar(paciente);
		paciente = new Paciente();
		return null;
	}
	
	public String excluirPaciente(Paciente p) {
		PacienteDao pDao = new PacienteDao();
		pDao.excluir(p);
		paciente = new Paciente();
		return null;
	}
	
	public String editarPaciente() {
		PacienteDao pDao = new PacienteDao();
		pDao.editar(paciente);
		paciente = new Paciente();
		return null;
	}

//IMC
	
	public List<IMC> listaIMC() {
		IMCDao iDao = new IMCDao();		
		listaIMC = iDao.pesquisaTodos();
		return listaIMC;
	}
	
	public String salvarIMC(){
		Double resultado = (imc.getPeso() / (imc.getAltura() * imc.getAltura()))*10000;
		
		String classificacao = classificacaoIMC(resultado);
		
		imc.setClassificacao(classificacao);
		imc.setResultado(resultado);
		imc.setPaciente(paciente);
		
		IMCDao imcDao = new IMCDao();
		imcDao.salvar(imc);
		imc = new IMC();
		
		return null;
	}
	
	public String excluirIMC(IMC imc) {
		IMCDao imcDao = new IMCDao();
		imcDao.excluir(imc);
		return null;
	}
	
	public String editarIMC() {
		IMCDao imcDao = new IMCDao();
		
		Double resultado = (imc.getPeso() / (imc.getAltura() * imc.getAltura()))*10000;
		
		System.out.println(resultado);

		String classificacao = classificacaoIMC(resultado);
		
		imc.setResultado(resultado);
		imc.setClassificacao(classificacao);
		
		imcDao.editar(imc);
		imc = new IMC();
		return null;
	}
	
	private String classificacaoIMC(Double resultado) {
		String classificacao = "";
		
		if(resultado < 17) {
			classificacao = "Muito Abaixo do Peso";
		}else if(resultado < 18.4){
			classificacao = "Abaixo do Peso";
		}else if(resultado < 24.9) {
			classificacao = "Peso Normal";
		}else if(resultado < 29.9) {
			classificacao = "Acima do Peso";
		}else if(resultado < 34.9) {
			classificacao = "Obesidade Grau I";
		}else if(resultado < 40) {
			classificacao = "Obesidade Grau II";
		}else {
			classificacao = "Obesidade Grau III";
		}
		
		return classificacao;
	}

	public String nomePaciente() {
		String nomePaciente = "";
		PacienteDao aDao = new PacienteDao();		
		listaPaciente = aDao.pesquisaTodos();
		
		
		return nomePaciente;
	}
	
	
	public List<Consulta> listaConsulta() {
		ConsultaDao cDao = new ConsultaDao();
		listaConsulta = cDao.pesquisaTodos();
		return listaConsulta;
	}
	
	
	public String salvarConsulta() {
		ConsultaDao cDao = new ConsultaDao();
		
		for (Paciente p : listaPaciente) {
			if(paciente.getId().equals(p.getId())) {
				consulta.setPaciente(p);
			}
		}
		for (Medico m : listaMedico) {
			if(medico.getId().equals(m.getId())) {
				consulta.setMedico(m);	
			}
		}
		cDao.salvar(consulta);
		consulta = new Consulta();
		paciente = new Paciente();
		medico = new Medico();
		return null;
	}
	
	public String excluirConsulta(Consulta c) {
		ConsultaDao cDao = new ConsultaDao();
		cDao.excluir(c);
		return null;
	}
	
	public String editarConsulta() {
		ConsultaDao cDao = new ConsultaDao();
		cDao.editar(consulta);
		consulta = new Consulta();
		return null;
	}
	
	
	public String cancelarConsulta() {
		consulta = new Consulta();
		paciente = new Paciente();
		medico = new Medico();
		return null;
	}
	
}
