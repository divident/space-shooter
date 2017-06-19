package com.jaca;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * Klasa Bullet zajmujê siê rysowaniem pocisku i jego animacj¹ . Klasa
 * dziedziczy po GameObject a dodatkowo posiada funkcjê sprawdzenia
 * kolizji z danym obiektem.
 *
 */
public class Bullet extends GameObject {

	private int damage = 10;
	private float bulletSpeed = 1.0f;
	private BufferedImage image;

	/**
	 * Konstruktor klasy Bullet. Argumentami jest po³o¿enie x i y pocisku a
	 * tak¿e jego prêdkoœæ. Konstruktor wczytujê obrazek pocisku
	 * 
	 * @param x
	 * @param y
	 * @param speed
	 */
	Bullet(int x, int y, float speed) {
		try {
			image = ImageIO.read(new File("playerbullet.png"));
		} catch (Exception e) {
			System.out.println(e);
		}
		setX(x);
		setY(y);
		setWidth(10);
		setHeight(10);
		setSpeedY(speed);
		// this.color=Color.BLUE;
	}

	/**
	 * Konstruktor klasy Bullet u¿ywany dla statków przeciwnika. Argumentami s¹
	 * wspó³rzêdne po³o¿enia x i y pocisku a tak¿e jego prêdkoœæ i noimg.
	 * Konstruktor wczytujê obrazek pocisku w zale¿noœci od wartoœci noimg.
	 * 
	 * @param x
	 * @param y
	 * @param speed
	 * @param noimg
	 */
	Bullet(int x, int y, float speed, int noimg) {

		setX(x);
		setY(y);
		setWidth(10);
		setHeight(10);
		setSpeedY(speed);
		if (noimg == 1) {
			try {
				image = ImageIO.read(new File("enemybullet.png"));
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (noimg == 2) {
			try {
				image = ImageIO.read(new File("bossbullet.png"));
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public float getBulletSpeed() {
		return bulletSpeed;
	}

	public void setBulletSpeed(float bulletSpeed) {
		this.bulletSpeed = bulletSpeed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaca.GameObject#animate()
	 */
	@Override
	public void animate() {
		setX((int) (getX() + getSpeedX() * bulletSpeed));
		setY((int) (getY() + getSpeedY() * bulletSpeed));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaca.GameObject#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		Graphics2D gmd = (Graphics2D) g;
		gmd.drawImage(image, getX(), getY(), null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaca.GameObject#getBounds()
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	/**
	 * Funkcja logiczna zwracaj¹ca true jeœli pocisk koliduje z obiektem klasy
	 * GameObject (przekazanym w argumencie) lub false w przeciwnym przypadku.
	 * 
	 * @param gameObject obiekt abstrakcyjne klasy GameObject
	 * @return true,false
	 */
	public boolean isColliding(GameObject gameObject) {
		return this.getBounds().intersects(gameObject.getBounds());
	}

}
