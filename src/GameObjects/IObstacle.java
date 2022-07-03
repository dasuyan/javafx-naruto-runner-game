package GameObjects;

import java.awt.*;

public interface IObstacle {
	 void update();
	 void draw(Graphics g);
	 Rectangle getBound();
	 boolean isOutOfScreen();
}
