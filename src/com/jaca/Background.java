package com.jaca;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * Klasa wczytuj¹ca t³o oraz rysuj¹ca j¹ tworz¹c poruszaj¹ce siê w dó³ zapêtlane t³o
 *
 */
public class Background extends Thread {

	private BufferedImage image;
  
	/**
	 * Konstruktor klasy Background, wczytuje obraz t³a oraz ustalaj¹c jego wymiary.
	 */
	public Background() {

		// Try to open the image file background.png
		try {
			image = ImageIO.read(new File("background.png"));
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	int mapWidth = 800;
	int mapHeight = 600;
	int offsety = 0;
	int tilesx = mapWidth / 200;
	int tilesy = mapHeight / 150;
	
	/**
	 * G³ówna funkcja rysuj¹ca poruszaj¹ce siê, zapêtlane t³o 
	 * 
	 * @param g obiekt klasy Graphics
	 */
	public void draw(Graphics g) {
		Graphics2D gmd = (Graphics2D) g;
		for (int y = 0; y < tilesy; y++) {
			for (int x = 0; x < tilesx; x++) {
				gmd.drawImage(image, x * 800, y * 2400 + offsety, null);
				gmd.drawImage(image, x * 800, (-1 * y) * 2400 + offsety, null);
				offsety = (offsety + 1) % 4800;

			}
			try {
				Thread.sleep(4);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}