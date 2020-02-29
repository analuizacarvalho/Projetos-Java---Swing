import java.awt.EventQueue;

public class TelaPrincipal {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					inicializarTela();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void inicializarTela() throws Exception {
		View view = new View();
		Controller c = new Controller(view);
		c.initController();
	}

}
