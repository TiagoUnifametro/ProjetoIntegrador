import java.time.Instant;
import java.util.Date;

import dao.ConsultaDao;
import dao.MedicoDao;
import dao.PacienteDao;
import entidades.Consulta;
import entidades.Medico;
import entidades.Paciente;

public class ClassMain {

	public static void main(String[] args) {
		MedicoDao mDao = new MedicoDao();
		Medico m = new Medico();
		m.setNomeMedico("Dr. Rodrigo Pires");
		mDao.salvar(m);
		
		
		Paciente p = new Paciente();
		PacienteDao pDao = new PacienteDao();
		p.setDataDeNascimento(Date.from(Instant.now()));
		p.setDocumento("465465");
		p.setNome("Ogait");
		p.setSexo("Masculino");
		pDao.salvar(p);
	
		Consulta c = new Consulta();
		ConsultaDao cDao = new ConsultaDao();
		c.setDiagnostico("Enxaqueca");
		c.setMedico(m);
		c.setPaciente(p);
		c.setQueixaPrincipal("Dor de Cabeça");
		cDao.salvar(c);
		
		
	}

}
