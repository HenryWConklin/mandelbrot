import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Mandelbrot {

	private static final int MAX_ITER = 256;


	public static BufferedImage computeMandelbrotSet(int width, int height, Complex bottomLeft, Complex topRight) {
		// Note using iterative formula: Z_k+1 = Z_k^2 + c

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				double a = i * (topRight.re() - bottomLeft.re()) / width;
				double b = j * (topRight.im() - bottomLeft.im()) / height;
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
					img.setRGB(i,j, Color.HSBtoRGB((float) (0.95f + 10 * smoothColor), 0.6f, 0.8f));
				} else {
					img.setRGB(i,j, new Color(0, 0, 0).getRGB());
				}
			}
		}
		return img;
	}

}