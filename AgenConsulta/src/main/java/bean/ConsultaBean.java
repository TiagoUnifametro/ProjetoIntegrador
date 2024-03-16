package bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import dao.CadastroDao;
import entidades.Cadastro;

@ManagedBean
@ApplicationScoped
public class ConsultaBean {

	private Cadastro cadastro = new Cadastro();

	public	Cadastro getCadastro() {
		return cadastro;
	}

	public void setCadastro(Cadastro cadastro) {
		this.cadastro = cadastro;
	}

//Cadastro Início	
	
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  // Especifica o formato da data
		Date date = new Date(); // Puxa Data atual do sistema
		String dataString = sdf.format(date); // Coloca a data atual em uma String
		Date data = sdf.parse(dataString); // converte a data de String para Date
		cadastro.setDataCadastro(data); // Seta em cadastro a data atual
		return sdf.format(date);
	}

	public String salvar() {
		CadastroDao cDao = new CadastroDao();
		cDao.salvar(cadastro);
		//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dados salvos com sucesso!!!", "Sucesso"));
		Cadastro cadastro = new Cadastro();
		return "login.xhtml";
	}
//Cadastro Fim

//Login Início
	public String logar(){
		CadastroDao cDao = new CadastroDao(); //Cria o objeto Dao para pesquisar os dados no banco
		List<Cadastro> lista = cDao.pesquisaTodos(); // Retorna uma lista dos usuarios armazenados no banco
		
		for(Cadastro cadastroc : lista) { // loop para pesquisar dentro da lista cadastro se há o usuario digitado na tela de login
			System.out.println(cadastroc.getApelido() + " - " + cadastroc.getSenha());
			
			if(cadastro.getApelido().equals(cadastroc.getApelido()) && cadastro.getSenha().equals(cadastroc.getSenha())) {
				System.out.println("Usuario liberado");
				cadastro = cadastroc;
				return "home.xhtml"; //se o usuario digitado e senha estiverem cadastrada no banco então ele valida e retorna a tela do home
			}else {
				System.out.println("Usuario não liberado");
			}
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error:", "Apelido ou Senha inválidos, favor tentar novamente ou cadastre-se!"));
		
		return null;
	}
//Login Fim
	
//Trocar Senha Início
	public String trocarSenha(){
		CadastroDao cDao = new CadastroDao(); //Cria o objeto Dao para pesquisar os dados no banco
		List<Cadastro> lista = cDao.pesquisaTodos(); // Retorna uma lista dos usuarios armazenados no banco
		
		for(Cadastro cadastroc : lista) { // loop para pesquisar dentro da lista cadastro se há o usuario digitado na tela de login
			System.out.println(cadastroc.getApelido() + " - " + cadastroc.getSenha());
			
			if(cadastro.getApelido().equals(cadastroc.getApelido()) && cadastro.getDataNascimento().equals(cadastroc.getDataNascimento())) {
				System.out.println("Usuario liberado");
				cadastroc.setSenha(cadastro.getSenha());
				CadastroDao cDaoE = new CadastroDao();
				cDaoE.editar(cadastroc);
				Cadastro c = new Cadastro();
				return "login";
			}else {
				System.out.println("Usuario não liberado");
			}
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error:", "Apelido ou Data de Nascimento inválidos, favor tentar novamente ou cadastre-se!"));
		
		return null;
	}
}
