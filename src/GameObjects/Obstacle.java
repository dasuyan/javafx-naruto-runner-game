package GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Obstacle implements IObstacle {
	
	public static final int Y_LAND = 195;
	
	private int posX;
	private final int width;
	private final int height;
	
	private final BufferedImage image;
	private final MainCharacter mainCharacter;
	
	private Rectangle rectBound;
	
	public Obstacle(MainCharacter mainCharacter, int posX, int width, int height, BufferedImage image) {
		this.posX = posX;
		this.width = width;
		this.height = height;
		this.image = image;
		this.mainCharacter = mainCharacter;
		rectBound = new Rectangle();
	}
	
	public void update() {
		posX -= mainCharacter.getSpeedX();
	}
	
	public void draw(Graphics g) {
		g.drawImage(image, posX, Y_LAND - image.getHeight(), null);
	}
	
	public Rectangle getBound() {
		rectBound = new Rectangle();
		rectBound.x = posX + (image.getWidth() - width)/2;
		rectBound.y = Y_LAND - image.getHeight() + (image.getHeight() - height)/2;
		rectBound.width = width;
		rectBound.height = height;
		return rectBound;
	}

	@Override
	public boolean isOutOfScreen() {
		return posX < -image.getWidth();
	}
	
}
