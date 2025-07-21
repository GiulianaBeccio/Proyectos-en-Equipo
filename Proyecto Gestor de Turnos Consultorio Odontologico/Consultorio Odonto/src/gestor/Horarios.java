package gestor;

public class Horarios {

	private Horario[][] horarios = new Horario[8][5];

	public Horarios() {
		int turno = 11;
		for (int fila = 0; fila < 8; fila++) {
			for (int col = 0; col < 5; col++) {
				horarios[fila][col] = new Horario(turno++, true);
			}
		}
	}

	public Horario[][] getHorarios() {
		return horarios;
	}
}
	
			
			
			
	
	
	

