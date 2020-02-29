import java.awt.Component;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JProgressBar;

public class ComparadorImagem extends Thread {

	private static final Component OBSERVER = new Component() {};
	JProgressBar barraPorcentagem;
	String caminhoPastaOriginal;
	
	public ComparadorImagem(JProgressBar barra, String caminho) {
		barraPorcentagem = barra;
		caminhoPastaOriginal = caminho;
	}
	
	public void run() {
		barraPorcentagem.setValue(0);

		File pastaOriginal = new File(caminhoPastaOriginal);
		File pastaNova = new File(caminhoPastaOriginal + "\\Unicas");
		
		if(!pastaNova.exists()) {
			pastaNova.mkdirs();
		}
		
		File[] imagensPastaOriginal = pastaOriginal.listFiles(new FileImageFilter());
		File[] imagensUnicas = pastaNova.listFiles(new FileImageFilter());

		int unidade = 100 / imagensPastaOriginal.length;
		barraPorcentagem.setMaximum((100 / imagensPastaOriginal.length) * imagensPastaOriginal.length);

		for (File imagemPastaOriginal : imagensPastaOriginal) {

			int value = barraPorcentagem.getValue();
			barraPorcentagem.setValue(value + unidade);
			barraPorcentagem.setStringPainted(true);
			boolean encontrouImagemIgual = false;
			
			for (File imagemUnica : imagensUnicas) {
				if (imagensIguais(imagemPastaOriginal, imagemUnica)) {
					encontrouImagemIgual = true;
				}
			}
			if (encontrouImagemIgual == false) {
				copiaImagem(imagemPastaOriginal, pastaNova);
				imagensUnicas = pastaNova.listFiles(new FileImageFilter());
			}
		}
	}

	private boolean imagensIguais(File imagemPastaOriginal, File imagemUnica) {
		try {
			int[] pixels1 = getPixels(ImageIO.read(imagemPastaOriginal));
			int[] pixels2 = getPixels(ImageIO.read(imagemUnica));
			
			if (Arrays.equals(pixels1, pixels2)) {
				return true;
			}
			return false;


		} catch (IOException e) {
			return false;
		}

	}

	private void copiaImagem(File imagemPastaOriginal, File pastaNova) {
		
		FileInputStream origem;
		FileOutputStream destino;
		FileChannel fcOrigem;
		FileChannel fcDestino;

		try {
			origem = new FileInputStream(imagemPastaOriginal);
			destino = new FileOutputStream(pastaNova + "\\" + imagemPastaOriginal.getName());
			fcOrigem = origem.getChannel();
			fcDestino = destino.getChannel();
			fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);
			origem.close();
			destino.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private int[] getPixels(BufferedImage image) {
		final int width = image.getWidth(OBSERVER);
		final int height = image.getHeight(OBSERVER);
		int size = width * height;
		PixelGrabber pixelGrabber = new PixelGrabber(image, 0, 0, width, height, new int[size], 0, width);
		pixelGrabber.startGrabbing();
		return (int[]) pixelGrabber.getPixels();
	}

}
