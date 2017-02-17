import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame {
	private BufferedImage img;
	private double increment = 0.0015;

	final int MAX_ITER = 256;
	final int WIDTH = 1920;
	final int HEIGHT = 1080;


	public static void main(String[] args) {
		Mandelbrot mandelbrot = new Mandelbrot();
		mandelbrot.initializeFrame();
		
	}

	private void writeImage() {
		File writeFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "/mandelbrot.png");
		try {
			ImageIO.write(img, "png", writeFile);
			System.out.println("File written");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initializeFrame() {
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(0, 0, WIDTH, HEIGHT);
		setVisible(true);
	}

	private void computeMandelbrotSet(Graphics g) {
		// Note using iterative formula: Z_k+1 = Z_k^2 + c

		img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

		for (double a = -(WIDTH / 2 * increment); a <= +(WIDTH / 2 * increment); a += increment) {
			for (double b = +(HEIGHT / 2 * increment); b >= -(HEIGHT / 2 * increment); b -= increment) {
				Complex c = new Complex(a, b);
				Complex z = new Complex(0.0, 0.0);
				int iter = 0;
				while (iter <= MAX_ITER && Math.pow(z.re(), 2) + Math.pow(z.im(), 2) <= 4) {
					z = (z.times(z)).plus(c);
					iter++;
				}
				double smooth = iter + 1 - Math.log(Math.log(z.abs())) / Math.log(2);
				double smoothColor = smooth / MAX_ITER;
				if (iter <= MAX_ITER) {
					img.setRGB((int) ((a / increment) + (WIDTH / 2)), (int) ((HEIGHT / 2) - (b / increment)),
							Color.HSBtoRGB((float) (0.95f + 10 * smoothColor), 0.6f, 0.8f));
				} else {
					img.setRGB((int) ((a / increment) + (WIDTH / 2)), (int) ((HEIGHT / 2) - (b / increment)),
							new Color(0, 0, 0).getRGB());
				}
			}
			g.drawImage(img, 0, 0, this);
		}
	}

	@Override
	public void paint(Graphics g) {
		if (img == null) {
			computeMandelbrotSet(g);
		}
		g.drawImage(img, 0, 0, this);
		writeImage();
		System.out.println("Image drawn");
	}

}