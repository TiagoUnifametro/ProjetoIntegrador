package bean;

import java.sql.SQLData;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.event.DateViewChangeEvent;

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

	public String dataAtual(){
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		return formato.format(date);
	}

	public void salvar() {
		CadastroDao cDao = new CadastroDao();
		cDao.salvar(cadastro);
		//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dados salvos com sucesso!!!", "Sucesso"));
	}	
}
