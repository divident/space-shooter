package com.jaca;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Klasa abstrakcyjna definiuj�ca interfejs dla obiekt�w gry
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
	 * Metoda odpowiadaj�ca za animacj� obiektu
	 */
	public abstract void animate();

	/**
	 * Metoda odpowiadaj�ca za rysowanie obiektu
	 * 
	 * @param g obiekt klasy Graphics
	 */
	public abstract void draw(Graphics g);

	/**
	 * Funkcja zwracaj�ca kraw�dzie obiektu jako obiekt Rectangle
	 * 
	 * @return Rectangle
	 */
	public abstract Rectangle getBounds();

	/**
	 * Funkcja pobiera wsp�rz�dna X obiektu.
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Setter ustawia now� wsp�rz�dn� x pozycji obiektu
	 * 
	 * @param x wsp�rz�dna x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Zwraca pr�dko�c obiektu wzd�� osi X
	 * 
	 * @return speedX
	 */
	public float getSpeedX() {
		return speedX;
	}

	/**
	 * Setter ustawia pr�dko�� obiektu wzd�� osi X
	 * 
	 * @param speedX pr�dko�� we wsp�rz�dnej 
	 */
	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	/**
	 * Zwraca pr�dko�c obiektu wzd�� osi Y
	 * 
	 * @return speedY 
	 */
	public float getSpeedY() {
		return speedY;
	}

	/**
	 * Setter ustawia pr�dko�� obiektu wzd�� osi Y
	 * 
	 * @param speedY pr�dko�� we wsp�rz�dnej Y
	 */
	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}

	/**
	 * Zwraca wsp�rz�dn� Y pozycji obiektu
	 * 
	 * @return Y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Setter ustawia now� pozycje we wsp�rz�dnej y obiektu
	 * 
	 * @param y wsp�rz�dna y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Zwraca wysoko�� obiektu
	 * 
	 * @return height 
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Setter ustawia wysoko�� obiektu
	 * 
	 * @param height wysoko�� obiektu
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Zwraca szeroko�� obiektu
	 * 
	 * @return width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Setter ustawia szeroko�� obiektu
	 * 
	 * @param width szeroko�� obiektu
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Zwraca warto�� logiczn� true lub false w zale�no�ci czy obiekt znajduj�
	 * si� na g��wnym panelu gry
	 * 
	 * @return true,false
	 */
	public boolean onBoard() {
		if (getX() < 0 || getX() > Game.WIDTH || getY() > Game.HEIGHT || getY() < 0)
			return false;
		return true;
	}
}
