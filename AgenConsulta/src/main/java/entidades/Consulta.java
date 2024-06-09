package entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Consulta {
	@Id
	@GeneratedValue
	private Integer id;
	private String queixaPrincipal;
	@ManyToOne
	private Medico medico;
	private String diagnostico;
	@ManyToOne
	private Paciente paciente;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQueixaPrincipal() {
		return queixaPrincipal;
	}

	public void setQueixaPrincipal(String queixaPrincipal) {
		this.queixaPrincipal = queixaPrincipal;
	}

	public String getMedico() {
		if (medico == null) {
			return "";
		}
		return medico.getNomeMedico().toString();
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getPaciente() {
		if (paciente == null) {
			return "";
		}
		return paciente.getNome().toString();
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

}
