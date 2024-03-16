package bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import dao.CadastroDao;
import entidades.Cadastro;

@ManagedBean
@ApplicationScoped
public class CadastroBean {

	private Cadastro cadastro = new Cadastro();

	public	Cadastro getCadastro() {
		return cadastro;
	}

	public void setCadastro(Cadastro cadastro) {
		this.cadastro = cadastro;
	}

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

	public void salvar() {
		CadastroDao cDao = new CadastroDao();
		cDao.salvar(cadastro);
		//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dados salvos com sucesso!!!", "Sucesso"));
		Cadastro cadastro = new Cadastro();
	}
}
