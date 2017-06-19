package com.jaca;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Klasa boss zajmuj� si� stworzeniem oraz animacj� bossa w grze. Klasa
 * dziedziczy po GameObject a dodatkowo posiada funkcj� strza�u( shoot() )
 *
 */
public class Boss extends GameObject {

	private float bossSpeed;
	public static final int BASE_HEALTH = 200;
	private int health;
	// private Random random;
	private float bulletSpeed = 20.0f;
	private Game game;
	private BufferedImage image;

	/**
	 * Konstruktor klasy Boss. Argumentami s� wsp�rz�dne x i y a tak�e obiekty
	 * klasy Random z biblioteki java.util.Random oraz Game. W konstruktorze
	 * nast�puj� wczytanie obrazka statku bossa i ustawienie jego pocz�tkowych warto�ci.
	 * 
	 * @param x wsp�rz�dna X bossa
	 * @param y wsp�rz�dna Y bossa
	 * @param random obiekt klasy Random
	 * @param game obiekt klasy Game
	 */
	public Boss(int x, int y, Random random, Game game) {
		super();
		try {
			image = ImageIO.read(new File("bossship.png"));
		} catch (Exception e) {
			System.out.println(e);
		}
		this.bossSpeed = 8.0f;
		// this.random = Game.getRandom();
		this.health = BASE_HEALTH;
		this.game = game;
		setWidth(126);
		setHeight(108);
		setX(x);
		setY(y);
		setSpeedX(1.5f);
		setSpeedY(0.5f);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaca.GameObject#animate()
	 */
	@Override
	public void animate() {
		// zmiana po�o�enia w poziomie

		if (getX() < 0) {
			setSpeedX(1);

		} else if (getX() + 2 * getWidth() > Game.WIDTH) {
			// setX(Game.WIDTH - 2 * getWidth());
			setSpeedX(-1);
		}
		setX((int) (getX() + getSpeedX() * bossSpeed));

		// zmian po�o�enia w pionie

		setY((int) (getY() + getSpeedY() * bossSpeed));

		if (getY() - game.getBossHud().getHeight() - 5 < 0) {
			setY(game.getBossHud().getHeight() + 5);
			setSpeedY(1);
		} else if ((getY() + 2 * getHeight()) > Game.HEIGHT / 2) {
			setY(Game.HEIGHT / 2 - 2 * getHeight());
			setSpeedY(-1);
		}

	}

	/* (non-Javadoc)
	 * @see com.jaca.GameObject#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		Graphics2D gmd = (Graphics2D) g;
		gmd.drawImage(image, getX(), getY(), null);
	}

	/* (non-Javadoc)
	 * @see com.jaca.GameObject#getBounds()
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	/**
	 * * Funkcja tworz�ca pociski tworzone("wystrzeliwane") przez bossa.
	 */
	public void shoot() {
		game.getBullets().add(new Bullet(this.getX() + (this.getWidth() / 2 + 30), this.getY() + this.getHeight() / 2,
				bulletSpeed, 2));
		game.getBullets().add(new Bullet(this.getX() + (this.getWidth() / 2 - 30), this.getY() + this.getHeight() / 2,
				bulletSpeed, 2));
		game.getBullets().add(
				new Bullet(this.getX() + (this.getWidth() / 2), this.getY() + this.getHeight() / 2, bulletSpeed, 2));
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}
