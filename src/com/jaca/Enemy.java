package com.jaca;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * Klasa Enemy zajmujê siê stworzeniem oraz animacj¹ przeciwników w grze. Klasa
 * dziedziczy po GameObject a dodatkowo posiada funkcjê strza³u.
 *
 */
public class Enemy extends GameObject {
	private int health;
	public final static float BASE_SPEED = 5.0f;
	private static float enemySpeed = BASE_SPEED;
	private float bulletSpeed = 15.0f;
	private Game game;
	static int staticSpeedX = 1;
	static int staticSpeedY = 0;
	private BufferedImage image = new BufferedImage(40, 30, BufferedImage.TYPE_INT_ARGB);

	/**
	 * Konstruktor klasy Enemy. Argumentami s¹ wspó³rzêdne x i y szerokoœæ i
	 * wysokoœæ a tak¿e obiekt klasy Game. W konstruktorze nastêpujê wczytanie
	 * obrazka statku bossa i ustawienie jego pocz¹tkowych wartoœci.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param game
	 */
	Enemy(int x, int y, int width, int height, Game game) {
		try {
			image = ImageIO.read(new File("enemyship.png"));
		} catch (Exception e) {
			System.out.println(e);
		}
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setSpeedX(1);
		this.game = game;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaca.GameObject#animate()
	 */
	@Override
	public void animate() {
		// zmiana po³o¿enia w poziomie

		if (game.getWaveLeftUpperCorner().getX() < 0) {
			// setX(0);
			setStaticSpeedX(1);
		} else if (game.getWaveRigthtDownCorner().getX() + 2 * getWidth() > Game.WIDTH) {
			// setX(Game.WIDTH - 2 * getWidth());
			setStaticSpeedX(-1);
		}
		setX((int) (getX() + getStaticSpeedX() * enemySpeed));
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

	/**
	 * Funkcja odpowiadaj¹ca za tworzenie pocisków przez przeciwnika.
	 */
	public void shoot() {
		game.getBullets()
				.add(new Bullet(this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2, bulletSpeed, 1));
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

	public static int getStaticSpeedX() {
		return staticSpeedX;
	}

	public static void setStaticSpeedX(int staticSpeedX) {
		Enemy.staticSpeedX = staticSpeedX;
	}

	public static int getStaticSpeedY() {
		return staticSpeedY;
	}

	public static void setStaticSpeedY(int staticSpeedY) {
		Enemy.staticSpeedY = staticSpeedY;
	}

	public static float getEnemySpeed() {
		return Enemy.enemySpeed;
	}

	public static void setEnemySpeed(float enemySpeed) {
		Enemy.enemySpeed = enemySpeed;
	}
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}
