package com.jaca;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Klasa tworzy 3 rodzaje ulepszeñ pojawiaj¹ce siê podczas gry. S¹ to ulepszenia
 * szybkoœci poruszania siê statku, podwójny strza³ oraz tarcza absorbuj¹ca
 * pociski. Upgrade dziedziczy po GameObject oraz zawiera dodatkowo funkcje:
 * applyUpgrade(w³¹czenie ulepszenia) ,removeUpgrade(usuniêcie ulepszenia) oraz
 * isColliding(sprawdzenie kolizji z danym obiektem)
 *
 */
public class Upgrade extends GameObject {

	static enum upgradeType {
		speedUpgrade, shootUpgrade, shieldUpgrade
	};

	private Random random;
	private upgradeType type;
	private Game game;
	private int offsetX = Game.WIDTH / 5;
	private BufferedImage image;

	/**
	 * Konstruktor klasy Upgrade. Argumentem jest obiekt klasy Game. Kontruktor
	 * ustawia pocz¹tkow¹ pozycje, szerokoœæ oraz wysokoœæ dla pierwszego
	 * pojawionego siê bonusu. Pozycja jest ustawiana losowo.
	 * 
	 * @param game obiekt klasy Game
	 */
	Upgrade(Game game) {
		super();
		this.game = game;
		random = Game.getRandom();
		setX(offsetX + random.nextInt(Game.WIDTH - 2 * offsetX));
		setY(Game.HEIGHT / 2 + random.nextInt(Game.HEIGHT / 2 - 2 * game.getHudOffset()));
		setWidth(30);
		setHeight(30);

		type = upgradeType.values()[random.nextInt(upgradeType.values().length)];

	}

	/* (non-Javadoc)
	 * @see com.jaca.GameObject#animate()
	 */
	@Override
	public void animate() {
	}

	/* (non-Javadoc)
	 * @see com.jaca.GameObject#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		Graphics2D gmd = (Graphics2D) g;

		if (type == upgradeType.speedUpgrade) {
			try {
				image = ImageIO.read(new File("speedbonus.png"));
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (type == upgradeType.shootUpgrade) {

			try {
				image = ImageIO.read(new File("doublebulletbonus.png"));
			} catch (Exception e) {
				System.out.println(e);
			}

		} else {
			try {
				image = ImageIO.read(new File("shieldbonus.png"));
			} catch (Exception e) {
				System.out.println(e);
			}

		}
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
	 * Funkcja ustawiaj¹ca bonusy.
	 */
	public void applyUpgrade() {
		Player player = game.getPlayer();

		if (type == upgradeType.speedUpgrade) {
			player.setPlayerSpeed(player.getPlayerSpeed() + 2.5f);
		} else if (type == upgradeType.shootUpgrade) {
			player.setShootUpgraded(true);
		} else {
			player.setShieldActive(true);

		}
	}

	/**
	 * Funkcja usuwaj¹ca bonusy.
	 */
	public void removeUpgrade() {
		Player player = game.getPlayer();
		if (type == upgradeType.speedUpgrade) {
			player.setPlayerSpeed(player.BASE_SPEED);
		} else if (type == upgradeType.shootUpgrade) {
			player.setShootUpgraded(false);
		} else {
			player.setShieldActive(false);

		}
	}

	/**
	 * Funkcja logiczna zwracaj¹ca true jeœli bonus koliduje z obiektem klasy
	 * GameObject (przekazanym w argumencie) lub false w przeciwnym przypadku.
	 * 
	 * @param gameObject obiekt abstrakcyjnej klasy GameObject
	 * @return true,false
	 */
	public boolean isColliding(GameObject gameObject) {
		return this.getBounds().intersects(gameObject.getBounds());
	}

}
