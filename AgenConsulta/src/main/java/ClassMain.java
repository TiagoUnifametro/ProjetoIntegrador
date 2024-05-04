import dao.MedicoDao;
import entidades.Medico;

public class ClassMain {

	public static void main(String[] args) {
		MedicoDao mDao = new MedicoDao();
		Medico m = new Medico();
		
		m.setNomeMedico("Dr. Rodrigo Pires");
		mDao.salvar(m);

	}

}
