package com.jaca;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Klasa abstrakcyjna definiuj¹ca interfejs dla obiektów gry
 *
 */
public abstract class GameObject {
	private int x;
	private int y;
	private float speedX;
	private float speedY;
	private int height;
	private int width;

	/**
	 * Metoda odpowiadaj¹ca za animacjê obiektu
	 */
	public abstract void animate();

	/**
	 * Metoda odpowiadaj¹ca za rysowanie obiektu
	 * 
	 * @param g obiekt klasy Graphics
	 */
	public abstract void draw(Graphics g);

	/**
	 * Funkcja zwracaj¹ca krawêdzie obiektu jako obiekt Rectangle
	 * 
	 * @return Rectangle
	 */
	public abstract Rectangle getBounds();

	/**
	 * Funkcja pobiera wspó³rzêdna X obiektu.
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Setter ustawia now¹ wspó³rzêdn¹ x pozycji obiektu
	 * 
	 * @param x wspó³rzêdna x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Zwraca prêdkoœc obiektu wzd³ó¿ osi X
	 * 
	 * @return speedX
	 */
	public float getSpeedX() {
		return speedX;
	}

	/**
	 * Setter ustawia prêdkoœæ obiektu wzd³ó¿ osi X
	 * 
	 * @param speedX prêdkoœæ we wspó³rzêdnej 
	 */
	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	/**
	 * Zwraca prêdkoœc obiektu wzd³ó¿ osi Y
	 * 
	 * @return speedY 
	 */
	public float getSpeedY() {
		return speedY;
	}

	/**
	 * Setter ustawia prêdkoœæ obiektu wzd³ó¿ osi Y
	 * 
	 * @param speedY prêdkoœæ we wspó³rzêdnej Y
	 */
	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}

	/**
	 * Zwraca wspó³rzêdn¹ Y pozycji obiektu
	 * 
	 * @return Y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Setter ustawia now¹ pozycje we wspó³rzêdnej y obiektu
	 * 
	 * @param y wspó³rzêdna y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Zwraca wysokoœæ obiektu
	 * 
	 * @return height 
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Setter ustawia wysokoœæ obiektu
	 * 
	 * @param height wysokoœæ obiektu
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Zwraca szerokoœæ obiektu
	 * 
	 * @return width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Setter ustawia szerokoœæ obiektu
	 * 
	 * @param width szerokoœæ obiektu
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Zwraca wartoœæ logiczn¹ true lub false w zale¿noœci czy obiekt znajdujê
	 * siê na g³ównym panelu gry
	 * 
	 * @return true,false
	 */
	public boolean onBoard() {
		if (getX() < 0 || getX() > Game.WIDTH || getY() > Game.HEIGHT || getY() < 0)
			return false;
		return true;
	}
}
