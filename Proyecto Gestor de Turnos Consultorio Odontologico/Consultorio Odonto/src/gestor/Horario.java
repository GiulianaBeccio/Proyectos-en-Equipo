package gestor;

public class Horario {
	private int idTurno;
	private boolean disp;
	
	
	
	
	public Horario(int idTurno, boolean disp) {
		this.idTurno = idTurno;
		this.disp = disp;
	}
	public int getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(int idTurno) {
		this.idTurno = idTurno;
	}
	public boolean isDisp() {
		return disp;
	}
	public void setDisp(boolean disp) {
		this.disp = disp;
	}
}
