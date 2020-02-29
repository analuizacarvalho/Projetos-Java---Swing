import java.io.File;

import javax.swing.JFileChooser;

public class Controller {
	
	private View view;
	String caminhoAnterior;
	private File diretorio;

	
    public Controller(View view) {
        this.view = view;
    }
    
    public void initController() {
    	view.getBotaoBuscar().addActionListener(e -> buscar());
    	view.getBtnAplicar().addActionListener(e -> filtrar());
    }
    
    
    public void filtrar() {
		Thread comparadorImagem = new ComparadorImagem(view.getBarra(), diretorio.getAbsolutePath());
		comparadorImagem.start();
    }
    
    
    public void buscar() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setDialogTitle("Selecione uma pasta");

		if(caminhoAnterior !=  null && !caminhoAnterior.isEmpty()) {
			File file = new File(caminhoAnterior);
			fileChooser.setCurrentDirectory(file);
		}

		int res = fileChooser.showOpenDialog(null); 

		if(res == JFileChooser.APPROVE_OPTION) {
			view.getBtnAplicar().setEnabled(true);
			diretorio = fileChooser.getSelectedFile();
			caminhoAnterior = fileChooser.getSelectedFile().getAbsolutePath();
			view.getTxtCaminho().setText(diretorio.getAbsolutePath());
		} else {
			view.getBtnAplicar().setEnabled(false);
		}
    }

}
