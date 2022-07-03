package GameObjects;

import Utilities.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ObstacleManager {
	
	private final BufferedImage obstacle1;
	private final BufferedImage obstacle2;

	private final Random rand;
	
	private final List<IObstacle> obstacles;
	private final MainCharacter mainCharacter;

	public ObstacleManager(MainCharacter mainCharacter) {
		rand = new Random();
		obstacle1 = Resources.getResourceImage("data/obstacle1.png");
		obstacle2 = Resources.getResourceImage("data/obstacle2.png");
		obstacles = new ArrayList<>();
		this.mainCharacter = mainCharacter;
		obstacles.add(createObstacle());
	}
	
	public void update() {
		for(IObstacle e : obstacles) {
			e.update();
		}
		IObstacle IObstacle = obstacles.get(0);
		if(IObstacle.isOutOfScreen()) {
			mainCharacter.upScore();
			obstacles.clear();
			obstacles.add(createObstacle());
		}
	}
	
	public void draw(Graphics g) {
		for(IObstacle e : obstacles) {
			e.draw(g);
		}
	}
	
	private IObstacle createObstacle() {
		int type = rand.nextInt(2 );
		if (type == 0){
			return new Obstacle(mainCharacter, 800, obstacle1.getWidth() - 10, obstacle1.getHeight() - 10, obstacle1);
		}
		else {
			return new Obstacle(mainCharacter, 800, obstacle2.getWidth() - 10, obstacle2.getHeight() - 10, obstacle2);
		}
	}
	
	public boolean isCollision() {
		for(IObstacle e : obstacles) {
			if (mainCharacter.getBound().intersects(e.getBound())) {
				return true;
			}
		}
		return false;
	}
	
	public void reset() {
		obstacles.clear();
		obstacles.add(createObstacle());
	}
}
