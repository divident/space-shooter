package com.jaca;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * Klasa odpowiadaj¹ca za stworzenie gracza. Klasa dziedziczy po GameObject
 * posiadaj¹ca dodatkowo funkcjê strza³u pociskami( shoot() ),rysuj¹c statek
 * gracza(uprzednio wczytuj¹c obrazek statku) oraz okreœla animacjê poruszania.
 *
 */
public class Player extends GameObject {

	public final float BASE_SPEED = 10.0f;
	public static final int BASE_HEALTH = 100;
	private float playerSpeed = BASE_SPEED;
	private float bulletSpeed = -15.0f;
	private int hudOffset;
	private int offsetX = 35;
	private boolean shieldAcitve = false;
	private boolean shootUpgraded = false;
	private Game game;
	private int health;
	private int score;

	private BufferedImage image;

	/**
	 * Konstruktor klasy Player. Argumentami s¹ wspó³rzêdne po³o¿enia x i y oraz
	 * obiekt klasy game. W konstruktorze wczytywany jest obraz statku oraz
	 * ustawiane pocz¹tkowe wartoœci.
	 * 
	 * @param x
	 * @param y
	 * @param game
	 */
	Player(int x, int y, Game game) {
		try {
			image = ImageIO.read(new File("playership.png"));
		} catch (Exception e) {
			System.out.println(e);
		}
		this.game = game;
		setX(x);
		setY(y);
		setWidth(69);
		setHeight(60);
		setSpeedY(0);
		setSpeedX(0);
		hudOffset = game.getHudOffset();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaca.GameObject#animate()
	 */
	@Override
	public void animate() {

		// zmiana po³o¿enia ze sprawdzaniem kolizji
		int oldPosX = getX();
		int oldPosY = getY();

		setX((int) (getX() + getSpeedX() * getPlayerSpeed()));
		if (getX() < 0 || (getX() + 2 * getWidth()) - offsetX > Game.WIDTH)
			setX(oldPosX);

		setY((int) (getY() + getSpeedY() * getPlayerSpeed()));
		if (getY() < 0 || (getY() + 2 * getHeight()) > Game.HEIGHT - hudOffset / 2)
			setY(oldPosY);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaca.GameObject#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		Graphics2D gmd = (Graphics2D) g;
		gmd.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
		if (isShieldActive()) {
			float opacity = 0.3f;
			gmd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
			gmd.setColor(Color.YELLOW);
			gmd.fillOval(getX(), getY(), 70, 70);
			opacity = 1f;
			gmd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		}

	}

	/**
	 * Funkcja tworz¹ca pociski gracza, sprawdzane jest równie¿ czy pociski s¹
	 * ulepszone co skutkuje wystrzeleniem 2 pocisków przez statek gracza.
	 */
	public void shoot() {
		if (isShootUpgraded()) {
			game.getBullets().add(
					new Bullet(this.getX() + this.getWidth() / 2 + 6, this.getY() + this.getHeight() / 2, bulletSpeed));
			game.getBullets().add(new Bullet(this.getX() + this.getWidth() / 2 - 15, this.getY() + this.getHeight() / 2,
					bulletSpeed));
		} else {
			game.getBullets().add(
					new Bullet(this.getX() + this.getWidth() / 2 - 5, this.getY() + this.getHeight() / 2, bulletSpeed));
		}
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

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health < 0 ? 0 : health;
	}

	public boolean isShieldActive() {
		return shieldAcitve;
	}

	public void setShieldActive(boolean shield) {
		this.shieldAcitve = shield;
	}

	public boolean isShootUpgraded() {
		return shootUpgraded;
	}

	public void setShootUpgraded(boolean shootUpgraded) {
		this.shootUpgraded = shootUpgraded;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public float getPlayerSpeed() {
		return playerSpeed;
	}

	public void setPlayerSpeed(float playerSpeed) {
		this.playerSpeed = playerSpeed;
	}
}
