package UserInterface;

import GameObjects.Clouds;
import GameObjects.ObstacleManager;
import GameObjects.Land;
import GameObjects.MainCharacter;
import Utilities.Music;
import Utilities.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GamePanel
		extends JPanel
		implements Runnable, KeyListener {

	private static final int gameStart = 0;
	private static final int gamePlay = 1;
	private static final int gameOver = 2;

	private int gameState = gameStart;
	
	private final Land land;
	private final Image background;
	private final MainCharacter mainCharacter;
	private final ObstacleManager obstacleManager;
	private final Clouds clouds;

	private final Music music;

	private boolean isKeyPressed;

	private final BufferedImage gameOverImage;

	public GamePanel() {
		mainCharacter = new MainCharacter();
		land = new Land(GameWindow.PANEL_WIDTH, mainCharacter);
		mainCharacter.setSpeedX(8);
		gameOverImage = Resources.getResourceImage("data/gameover_text.png");
		obstacleManager = new ObstacleManager(mainCharacter);
		clouds = new Clouds(mainCharacter);
		background = Toolkit.getDefaultToolkit().createImage("data/background.png");
		music = new Music("data/music.wav");
	}

	public void startGame() {
		Thread thread = new Thread(this);
		thread.start();
		music.play();
	}

	public void gameUpdate() {
		if (gameState == gamePlay) {
			clouds.update();
			land.update();
			mainCharacter.update();
			obstacleManager.update();
			if (obstacleManager.isCollision()) {
				gameState = gameOver;
				mainCharacter.dead(true);
				//music.play(false);
			}
		}
	}

	public void paint(Graphics g) {
		g.drawImage(background, 0, -30, null);

		switch (gameState) {
			case gameStart -> mainCharacter.draw(g);
			case gamePlay, gameOver -> {
				clouds.draw(g);
				land.draw(g);
				obstacleManager.draw(g);
				mainCharacter.draw(g);
				g.setColor(Color.BLACK);
				g.drawString("CURRENT SCORE: " + mainCharacter.currentScore, 400, 20);
				g.drawString("HIGH SCORE: " + mainCharacter.highScore, 550, 20);
				if (gameState == gameOver) {
					g.drawImage(gameOverImage, (GameWindow.PANEL_WIDTH - gameOverImage.getWidth()) / 2,
							((GameWindow.PANEL_HEIGHT - gameOverImage.getHeight()) / 2) - 120, null);
					try {
						mainCharacter.writeHighScore();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public void run() {

		int fps = 100;
		long msPerFrame = 1000 * 1000000 / fps;
		long lastTime = 0;
		long elapsed;
		
		int msSleep;
		int nanoSleep;

		while (true) {
			gameUpdate();
			repaint();
			elapsed = (lastTime + msPerFrame - System.nanoTime());
			msSleep = (int) (elapsed / 1000000);
			nanoSleep = (int) (elapsed % 1000000);
			if (msSleep <= 0) {
				lastTime = System.nanoTime();
				continue;
			}
			try {
				Thread.sleep(msSleep, nanoSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lastTime = System.nanoTime();
		}
	}
// Beginning of user input methods
	@Override
	public void keyPressed(KeyEvent e) {
		if (!isKeyPressed) {
			isKeyPressed = true;
			switch (gameState) {
			case gameStart:
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					gameState = gamePlay;
				}
				break;
			case gamePlay:
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					mainCharacter.jump();
				}
				break;
			case gameOver:
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					gameState = gamePlay;
					mainCharacter.currentScore = 0;
					resetGame();
				}
				break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		isKeyPressed = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// no action taken
	}
// End of user input methods

	private void resetGame() {
		obstacleManager.reset();
		mainCharacter.dead(false);
		mainCharacter.reset();
	}
}
