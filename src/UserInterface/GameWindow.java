package UserInterface;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
	
	public static final int PANEL_WIDTH = 704;
	public static final int PANEL_HEIGHT = 423;
	private GamePanel gamePanel;
	
	public GameWindow() {
		super("Naruto Endless Runner");
		setSize(PANEL_WIDTH, PANEL_HEIGHT);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		int screenWidth = (int) ((screenSize.getWidth() - PANEL_WIDTH) / 2);
		int screenHeight = (int) ((screenSize.getHeight() - PANEL_HEIGHT) / 2);

		setLocation(screenWidth, screenHeight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		initialiseComponents();
	}

	private void initialiseComponents() {
		gamePanel = new GamePanel();
		addKeyListener(gamePanel);
		this.add(gamePanel, BorderLayout.CENTER);
	}
	
	public void startGame() {
		setVisible(true);
		gamePanel.startGame();
	}

}
