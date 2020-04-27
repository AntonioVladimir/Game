package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpritesPage {
	private final int width;
	private final int heigth;
	public final int[] pixels;

	public SpritesPage(final String url, final int width, final int heigth) {
		this.width = width;
		this.heigth = heigth;

		pixels = new int[width * heigth];

		BufferedImage image;
		try {
			image = ImageIO.read(SpritesPage.class.getResource(url));

			image.getRGB(0, 0, width, heigth, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getWidth() {
		return width;
	}
}
