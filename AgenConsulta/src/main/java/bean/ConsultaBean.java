package bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

	private Cadastro cadastro = new Cadastro(); //Instancio objeto Cadastro


	public	Cadastro getCadastro() {
		return cadastro;
	}

	public void setCadastro(Cadastro cadastro) {
		this.cadastro = cadastro;
	}

	
//Cadastro - Início		
	public String ativo() { //Retorna na tela de cadastro o status que o usuário vai ficar
		String ativo;
		cadastro.setAtivo(true);
		if(cadastro.isAtivo()) {
			ativo = "ATIVO";
		}else {
			ativo = "INATIVO";
		}
		return ativo;
	}

	public String dataAtual() throws ParseException{ //Retorna na tela de cadastro a data atual de cadastro
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  // Especifica o formato da data
		Date date = new Date(); // Puxa Data atual do sistema
		String dataString = sdf.format(date); // Coloca a data atual em uma String
		Date data = sdf.parse(dataString); // converte a data de String para Date
		cadastro.setDataCadastro(data); // Seta em cadastro a data atual
		return sdf.format(date);
	}

	public String salvar() throws ParseException { //Metodo para validação do apelido e salvar o usuario no banco
			CadastroDao cDao = new CadastroDao(); //Cria o objeto Dao para pesquisar os dados no banco e salvar
			List<Cadastro> lista = cDao.pesquisaTodos(); // Retorna uma lista dos usuarios armazenados no banco
			
			for(Cadastro cadastroc : lista) { // loop para pesquisar dentro da lista cadastro se o apelido está em uso
				if(cadastro.getApelido().equals(cadastroc.getApelido())) {
					//System.out.println("Usuario liberado");
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario não cadastrado:", "Apelido já está em uso, favor tentar novamente!"));
					return null; // caso apelido esteja em uso ele sai do metódo e retorna o erro de cadastro
				}
			}

		cDao.salvar(cadastro);// salva o usuario no banco de dados
		cadastro = new Cadastro();
		return "login.xhtml";
	}
//Cadastro - Fim

	
//Login - Início
	public String logar(){
		CadastroDao cDao = new CadastroDao(); //Cria o objeto Dao para pesquisar os dados no banco
		List<Cadastro> lista = cDao.pesquisaTodos(); // Retorna uma lista dos usuarios armazenados no banco
		
		for(Cadastro cadastroc : lista) { // loop para pesquisar dentro da lista cadastro se há o usuario digitado na tela de login
			if(cadastro.getApelido().equals(cadastroc.getApelido()) && cadastro.getSenha().equals(cadastroc.getSenha())) {
				//System.out.println("Usuario liberado");
				cadastro = cadastroc;
				return "home.xhtml"; //se o usuario digitado e senha estiverem cadastrada no banco então ele valida e retorna a tela de home
			}
		
		}
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error:", "Apelido ou Senha inválidos, favor tentar novamente ou cadastre-se!"));
		cadastro = new Cadastro();
		return null;
	}
//Login - Fim
	

//Trocar Senha - Início
	public String trocarSenha(){
		CadastroDao cDao = new CadastroDao(); //Cria o objeto Dao para pesquisar os dados no banco
		List<Cadastro> lista = cDao.pesquisaTodos(); // Retorna uma lista dos usuarios armazenados no banco
		
		for(Cadastro cadastroc : lista) { // loop para pesquisar dentro da lista cadastro se há o usuario digitado na tela de login
			//System.out.println(cadastroc.getApelido() + " - " + cadastroc.getSenha());
			
			if(cadastro.getApelido().equals(cadastroc.getApelido()) && cadastro.getDataNascimento().equals(cadastroc.getDataNascimento())) {
				//System.out.println("Usuario liberado");
				cadastroc.setSenha(cadastro.getSenha()); // seta no objeto retornado do banco a senha nova do usuário
				CadastroDao cDaoE = new CadastroDao();
				cDaoE.editar(cadastroc); // adiciona no banco a nova senha do usuario
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
	public String editar() { // Edita o usuario que está sendo referenciado no momento da execução
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
	
}
