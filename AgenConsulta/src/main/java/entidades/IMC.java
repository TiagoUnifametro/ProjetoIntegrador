package entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
@Entity
public class IMC {
	@Id
	@GeneratedValue
	private Integer Id;
	private Double peso;
	private Double altura;
	private Double resultado;
	private String classificacao;
	@OneToOne
	private Paciente paciente;
	
	public String getClassificacao() {
		return classificacao;
	}
	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	public Double getAltura() {
		return altura;
	}
	public void setAltura(Double altura) {
		this.altura = altura;
	}
	public Double getResultado() {
		return resultado;
	}
	public void setResultado(Double resultado) {
		this.resultado = resultado;
	}
	public String getPaciente() {
		
		if(paciente == null) {
			return "";
		}
		
		return paciente.getNome().toString();
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
}