package GameObjects;

import UserInterface.GameWindow;
import Utilities.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Clouds {
	private final List<ImageCloud> cloudList;
	private final BufferedImage cloud;
	
	private final MainCharacter mainCharacter;
	
	public Clouds(MainCharacter mainCharacter) {
		this.mainCharacter = mainCharacter;
		cloud = Resources.getResourceImage("data/cloud.png");
		cloudList = new ArrayList<ImageCloud>();
		
		ImageCloud imageCloud = new ImageCloud();
		imageCloud.posX = 0;
		imageCloud.posY = 80;
		cloudList.add(imageCloud);
		
		imageCloud = new ImageCloud();
		imageCloud.posX = 150;
		imageCloud.posY = 50;
		cloudList.add(imageCloud);
		
		imageCloud = new ImageCloud();
		imageCloud.posX = 300;
		imageCloud.posY = 70;
		cloudList.add(imageCloud);
		
		imageCloud = new ImageCloud();
		imageCloud.posX = 450;
		imageCloud.posY = 40;
		cloudList.add(imageCloud);
		
		imageCloud = new ImageCloud();
		imageCloud.posX = 600;
		imageCloud.posY = 90;
		cloudList.add(imageCloud);
	}
	
	public void update(){
		Iterator<ImageCloud> itr = cloudList.iterator();
		ImageCloud firstElement = itr.next();
		firstElement.posX -= mainCharacter.getSpeedX()/8;
		while(itr.hasNext()) {
			ImageCloud element = itr.next();
			element.posX -= mainCharacter.getSpeedX()/8;
		}
		if(firstElement.posX < -cloud.getWidth()) {
			cloudList.remove(firstElement);
			firstElement.posX = GameWindow.PANEL_WIDTH;
			cloudList.add(firstElement);
		}
	}
	
	public void draw(Graphics g) {
		for(ImageCloud imgLand : cloudList) {
			g.drawImage(cloud, (int) imgLand.posX, imgLand.posY, null);
		}
	}
	
	private static class ImageCloud {
		float posX;
		int posY;
	}
}
