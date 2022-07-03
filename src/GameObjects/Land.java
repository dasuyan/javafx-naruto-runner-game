package GameObjects;

import Utilities.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Land {
	
	public static final int LAND_POSY = 185;
	
	private final List<landPicture> landList;
	private final BufferedImage land1;
	
	private final MainCharacter mainCharacter;
	
	public Land(int width, MainCharacter mainCharacter) {
		this.mainCharacter = mainCharacter;
		land1 = Resources.getResourceImage("data/land0.png");
		int landOverlap = width / land1.getWidth() + 2;
		landList = new ArrayList<>();

		for(int i = 0; i < landOverlap; i++) {
			landPicture landPicture = new landPicture();
			landPicture.posX = i * land1.getWidth();
			landPicture.image = land1;
			landList.add(landPicture);
		}
	}
	
	public void update(){
		Iterator<landPicture> itr = landList.iterator();
		landPicture firstElement = itr.next();
		firstElement.posX -= mainCharacter.getSpeedX();
		float previousPosX = firstElement.posX; // so that land goes in the opposite direction

		while(itr.hasNext()) {
			landPicture element = itr.next();
			element.posX = previousPosX + land1.getWidth();
			previousPosX = element.posX;
		}

		if(firstElement.posX < -land1.getWidth()) {
			landList.remove(firstElement); // remove from list after one 'chunk' of land leaves the screen
			firstElement.posX = previousPosX + land1.getWidth();
			firstElement.image = land1;
			landList.add(firstElement);
		}
	}

	private static class landPicture {
		BufferedImage image;
		float posX;
	}

	public void draw(Graphics g) {
		for(landPicture imgLand : landList) {
			g.drawImage(imgLand.image, (int) imgLand.posX, LAND_POSY, null);
		}
	}


	
}
