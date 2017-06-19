package com.jaca;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Klasa przedstawiaj¹ca na ekranie aktualny stan ¿ycia gracza, bossa oraz pasek
 * bonusu.
 *
 */
public class Hud {
	private int offset;
	private int currentValue;
	private int width;
	private int height;
	private Point position;
	private int baseValue = 100;
	private final int BASE_OFFSET = 10;
	private Color color;

	/**
	 * Konstruktor klasy Hud. Argumentami s¹ szerokoœæ i wysokoœæ oraz
	 * wspó³rzêdne po³o¿enia x i y
	 * 
	 * @param width d³ugoœæ hud
	 * @param height wysokoœæ hud
	 * @param x wspó³rzêdna x hud
	 * @param y wspó³rzêdna y hud
	 */
	public Hud(int width, int height, int x, int y) {
		this.offset = BASE_OFFSET;
		this.currentValue = baseValue;
		this.width = width;
		this.height = height;
		this.position = new Point(x, y);
		this.color = Color.RED;
	}

	/**
	 * Konstruktor klasy Hud. Argumentami s¹ szerokoœæ i wysokoœæ, wspó³rzêdne
	 * po³o¿enia x i y oraz obiekt klasy Color
	 * 
	 * @param width d³ugoœæ hud
	 * @param height wysokoœæ hud
	 * @param x wspó³rzêdna x hud
	 * @param y wspó³rzêdna y hud
	 * @param color kolor paska hud
	 */
	public Hud(int width, int height, int x, int y, Color color) {
		this.offset = BASE_OFFSET;
		this.currentValue = baseValue;
		this.width = width;
		this.height = height;
		this.position = new Point(x, y);
		this.color = color;
	}
	
	/**
	 * Funkcja rysuj¹ca paski stanów ¿ycia tzw. progress bar
	 * @param g obiekt Graphics
	 */
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(color);
		g.fillRect(getX(), getY(), getWidth() - getWidth() * (baseValue - currentValue) / baseValue, getHeight());
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(int currentValue) {
		baseValue = Math.max(baseValue, currentValue);
		this.currentValue = currentValue < 0 ? 0 : currentValue;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getX() {
		return (int) position.getX();
	}

	public int getY() {
		return (int) position.getY();
	}

}
