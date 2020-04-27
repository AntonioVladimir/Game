package game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1922430375395542294L;

	private static final int WIDTH = 800;
	private static final int HEIGTH = 600;

	private static volatile boolean isRunning = false;

	private static final String NAME = "game";
	private static int aps = 0;
	private static int fps = 0;

	private static JFrame window;
	private static Thread thread;

	private Game() {
		setPreferredSize(new Dimension(WIDTH, HEIGTH));

		window = new JFrame(NAME);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLayout(new BorderLayout());
		window.add(this, BorderLayout.CENTER);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

	private synchronized void start() {
		isRunning = true;

		thread = new Thread(this, "Graphics");
		thread.start();
	}

	private synchronized void stop() {
		isRunning = false;
		try {
			thread.join(); // No usar .stop puede corromper las variables.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void update() {
		aps++;
	}

	private void toShow() {
		fps++;
	}

	@Override
	public void run() {
		final int NS_BY_SECONDS = 1000000000;
		final byte APS_OBJECT = 60;
		final double NS_BY_ACTUALIZATION = NS_BY_SECONDS / APS_OBJECT;
		// [���NO!!!] System.currentTimeMillis();

		long actualizationReference = System.nanoTime();
		long contReference = System.nanoTime();

		double elapsedTime;
		double delta = 0;// Cantidad de tiempo transcurrido en una actualizac�n.

		while (isRunning) {
			final long bucleStart = System.nanoTime();

			elapsedTime = bucleStart - actualizationReference;
			actualizationReference = bucleStart;

			delta += elapsedTime / NS_BY_ACTUALIZATION;

			while (delta >= 1) {
				update();
				delta--;
			}
			toShow();

			if (System.nanoTime() - contReference > NS_BY_SECONDS) {
				window.setTitle(NAME + " ## APS: " + aps + " ## FPS: " + fps);
				aps = 0;
				fps = 0;
				contReference = System.nanoTime();
			}
		}
	}
}
