package GameObjects;

import Utilities.Animation;
import Utilities.Resources;
import Utilities.Sound;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class MainCharacter {

	public static final int LAND_POSY = 135;
	public static final float GRAVITY = 0.4f;
	
	private static final int RUN = 0;
	private static final int JUMPING = 1;
	private static final int DEATH = 2;
	
	private float posY;
	private final float posX;
	private float speedX;
	private float speedY;
	private Rectangle rectBound;

	public int currentScore = 0;
	public int highScore;
	
	private int state = RUN;

	private final Animation normalRunAnim;
	private final BufferedImage jumping;
	private final BufferedImage deathImage;

	private final Sound jump = new Sound("data/jump.wav");
	private final Sound dead = new Sound("data/dead.wav");
	private final Sound scoreUp = new Sound("data/scoreup.wav");
	private final Sound beatHighScore = new Sound("data/beaths.wav");

	public MainCharacter() {
		try {
			highScore = readHighScore();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		posX = 50;
		posY = LAND_POSY;
		rectBound = new Rectangle();
		normalRunAnim = new Animation(90);
		normalRunAnim.addFrame(Resources.getResourceImage("data/main_character1.png"));
		normalRunAnim.addFrame(Resources.getResourceImage("data/main_character2.png"));
		normalRunAnim.addFrame(Resources.getResourceImage("data/main_character3.png"));
		normalRunAnim.addFrame(Resources.getResourceImage("data/main_character4.png"));
		normalRunAnim.addFrame(Resources.getResourceImage("data/main_character5.png"));
		normalRunAnim.addFrame(Resources.getResourceImage("data/main_character6.png"));
		jumping = Resources.getResourceImage("data/main_character_jump.png");
		deathImage = Resources.getResourceImage("data/main_character_dead.png");

	}

	public float getSpeedX() { return speedX; }

	public void setSpeedX(int speedX) { this.speedX = speedX; }
	
	public void draw(Graphics g) {
		switch (state) {
			case RUN -> g.drawImage(normalRunAnim.getFrame(), (int) posX, (int) posY, null);
			case JUMPING -> g.drawImage(jumping, (int) posX, (int) posY, null);
			case DEATH -> g.drawImage(deathImage, (int) posX, (int) posY, null);
		}
	}
	
	public void update() {
		normalRunAnim.updateFrame();
		if(posY >= LAND_POSY) {
			posY = LAND_POSY;
				state = RUN;
		} else {
			speedY += GRAVITY;
			posY += speedY;
		}
	}
	
	public void jump() {
		if(posY >= LAND_POSY) {
			jump.play();
			speedY = -9.5f;
			posY += speedY;
			state = JUMPING;
		}
	}

	public Rectangle getBound() {
		rectBound = new Rectangle();

			rectBound.x = (int) posX + 5;
			rectBound.y = (int) posY;
			rectBound.width = normalRunAnim.getFrame().getWidth() - 10;
			rectBound.height = normalRunAnim.getFrame().getHeight();

		return rectBound;
	}
	
	public void dead(boolean isDeath) {
		if(isDeath) {
			dead.play();
			state = DEATH;
		} else {
			state = RUN;
		}
	}
	
	public void reset() {
		posY = LAND_POSY;
	}

	public void upScore() {
		scoreUp.play();
		currentScore += 20;
		if (currentScore == highScore) beatHighScore.play();
	if (currentScore >= highScore)
		highScore = currentScore;
	}

	public int getHighScore() {
		return highScore;
	}

	public void writeHighScore()
			throws IOException {
		String highScore = String.valueOf(getHighScore());
		BufferedWriter writer = new BufferedWriter(new FileWriter("data/highscore.txt"));
		writer.write(highScore);

		writer.close();
	}
	public int readHighScore() throws FileNotFoundException {
		int output;
		Scanner scanner = new Scanner(new File("data/highscore.txt"));
		output = scanner.nextInt();
		return output;
	}
}
