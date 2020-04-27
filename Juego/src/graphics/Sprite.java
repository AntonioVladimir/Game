package graphics;

public final class Sprite {
	private final int size;

	private int x;
	private int y;

	public int[] pixels;
	private final SpritesPage page;

	public Sprite(final int size, final int column, final int row, final SpritesPage page) {
		this.size = size;

		pixels = new int[size * size];

		this.x = column * size;
		this.y = row * size;
		this.page = page;

		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				pixels[x + y * size] = page.pixels[(x + this.x) + (y + this.y) * page.getWidth()];
			}
		}
	}
}
