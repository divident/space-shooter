package com.jaca;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.jaca.Game.GameStatus;

/**
 * Klasa board odpowiada za stworzenie g³ównego panelu gry tzn. rysuje wszystkie
 * obiekty gry oraz uruchamia animacje dla utworzonych obiektów. Zajmujê siê
 * równie¿ sprawdzaniem kolizji miêdzy obiektami,ca³¹ g³ówn¹ logik¹ gry a tak¿e
 * uruchamia strzelanie przez przeciwników . Klasa dziedzy po JPanel.
 *
 */
public class Board extends JPanel implements Runnable {

	static final long serialVersionUID = 1L;

	// private ArrayList<GameObject> gameObjects;
	private ArrayList<Enemy> enemies;
	private ArrayList<Bullet> bullets;
	private Player player;
	private Background back;
	private BufferedImage back1;
	private Upgrade upgrade;
	private Upgrade currentUpgrade;
	private Timer shootTimer;
	private Timer upgradeTimer;
	private final int SHOOT_DELAY = 300;
	private int shootInitDelay = 400;
	private int shootRandomDelay = SHOOT_DELAY;
	private int delayDiff = 50;
	private int upgradeDelay = 200;
	private int bossShootDelay = 700;
	private int upgradeInitDelay = 2600;
	private int upgradeRemoveSpeed = 15;
	private int upgradeTime;
	private float speedBoost = 2.0f;
	private Game game;

	Random random;

	/**
	 * Kontruktor klasy board. Argumentami s¹ listy obiektów klasy enemies oraz
	 * bullets, obiekty klas player,background oraz game. W konstruktorze
	 * uruchamiane s¹ równie¿ timery odpowiadaj¹ce za animacjê oraz pojawianie
	 * siê przeciwników i ulepszeñ.
	 * 
	 * @param enemies
	 *            tablica obietków Enemy
	 * @param bullets
	 *            tablica obiektów Bullet
	 * @param player
	 *            obiekt gracza
	 * @param back
	 *            obiekt t³a
	 * @param game
	 *            obiekt game
	 */
	public Board(ArrayList<Enemy> enemies, ArrayList<Bullet> bullets, Player player, Background back, Game game) {
		game.remove(game.getMenu());
		back = new Background();
		// player = new Player();
		this.enemies = enemies;
		this.bullets = bullets;
		this.player = player;
		this.game = game;
		this.back = back;
		this.random = Game.getRandom();

		// uruchamianie animacji strza³ów przeciwników
		shootTimer = new Timer(shootInitDelay + random.nextInt(shootRandomDelay), enemyShoot);
		shootTimer.setInitialDelay(0);
		shootTimer.start();

		// ulepszenia bêd¹ siê pojawiaæ po losowym up³ywie czasu
		upgradeTimer = new Timer(upgradeInitDelay + random.nextInt(upgradeDelay), upgradeSpwan);
		upgradeTimer.setInitialDelay(upgradeInitDelay + random.nextInt(upgradeDelay));
		upgradeTimer.start();

		// ustawienia timer dla ulepszeñ
		game.getUpgradeHud().setCurrentValue(0);
		game.setCurrentWave(0);

	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			// tworzenie nowej fali wrogów lub bossa
			if (enemies.isEmpty() && !game.isBossAlive()) {
				if (game.getCurrentWave() == 2) {
					if (game.getBossHud().getCurrentValue() == 0 && !game.isBossSpawn()) {
						game.getBossHud().setCurrentValue(game.getBoss().getHealth());
					}
					game.setBossSpawn(true);
					game.setBossAlive(true);
					shootTimer.setDelay(bossShootDelay);
					enemies.clear();
				} else {
					shootTimer.setDelay(shootInitDelay + random.nextInt(shootRandomDelay));
					game.spawnNewWave();
					game.setCurrentWave(game.getCurrentWave() + 1);
					shootRandomDelay = Math.max(1, shootRandomDelay - (game.getCurrentWave() * delayDiff));
					Enemy.setEnemySpeed(Enemy.getEnemySpeed() + speedBoost);
					shootTimer.setDelay(shootInitDelay + random.nextInt(shootRandomDelay));
				}
			}

			for (GameObject object : bullets) {
				object.animate();
			}

			for (GameObject object : enemies) {
				object.animate();
			}

			player.animate();
			game.getBoss().animate();
			try {
				TimeUnit.MILLISECONDS.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}

	// funkcja rysuj¹ca wszystkie obiekty gry
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D twoD = (Graphics2D) g;
		if (back1 == null)
			back1 = (BufferedImage) (createImage(getWidth(), getHeight()));
		Graphics buffer = back1.createGraphics();
		back.draw(buffer);
		twoD.drawImage(back1, null, 0, 0);

		// rysowanie obiektów gry
		for (Iterator<Bullet> bulletIterator = bullets.iterator(); bulletIterator.hasNext();) {
			boolean bulletToRemove = false;

			Bullet bullet = bulletIterator.next();

			// sprawdzanie czy pocisk jest na planszy
			if (!bullet.onBoard())
				bulletToRemove = true;

			// sprawdzanie kolizji z wrogami
			for (Iterator<Enemy> enemyIterator = enemies.iterator(); enemyIterator.hasNext();) {
				Enemy enemy = enemyIterator.next();
				if (bullet.getSpeedY() < 0 && bullet.isColliding(enemy)) {
					enemyIterator.remove();
					bulletToRemove = true;
					player.setScore(player.getScore() + 10);
				}
			}

			if (bullet.getSpeedY() > 0 && bullet.isColliding(player)) {
				bulletToRemove = true;
				if (!player.isShieldActive()) {
					game.getHpHud().setCurrentValue(game.getHpHud().getCurrentValue() - bullet.getDamage());

				}
			}

			if (game.isBossAlive() && bullet.getSpeedY() < 0 && bullet.isColliding(game.getBoss())) {
				game.getBossHud().setCurrentValue(game.getBossHud().getCurrentValue() - bullet.getDamage());
				bulletToRemove = true;
				player.setScore(player.getScore() + 10);
				if (game.getBossHud().getCurrentValue() == 0) {
					game.setBossAlive(false);
					game.setBossSpawn(false);
					game.setCurrentWave(game.getCurrentWave() + 1);
				}
			}

			if (bulletToRemove)
				bulletIterator.remove();

			if (!enemies.isEmpty()) {
				game.setWaveLeftUpperCorner();
				game.setWaveRigthtDownCorner();
			}
			if (game.getHpHud().getCurrentValue() <= 0) {
				game.setGameStatus(GameStatus.GAME_OVER);
				game.swap1();
			}

			bullet.draw(g);
		}
		
		for (GameObject object : enemies) {
			object.draw(g);
		}
		// sprawdzanie czy rysowaæ bossa
		if (game.isBossSpawn()) {
			game.getBossHud().draw(g);
			game.getBoss().draw(g);
		}

		// rysowanie ulepszenia
		if (upgrade != null) {
			upgrade.draw(g);
			if (upgrade.isColliding(player)) {
				upgrade.applyUpgrade();
				currentUpgrade = upgrade;
				upgrade = null;
				game.getUpgradeHud().setCurrentValue(upgradeInitDelay);
				upgradeTime = upgradeInitDelay;

			}

		}

		// sprawdzanie aktywnego ulepszenia
		if (currentUpgrade != null) {
			upgradeTime -= upgradeRemoveSpeed;
			game.getUpgradeHud().setCurrentValue(game.getUpgradeHud().getCurrentValue() - upgradeRemoveSpeed);
			if (upgradeTime <= 0) {
				currentUpgrade.removeUpgrade();
				currentUpgrade = null;
			}

		}

		player.draw(g);
		game.getHpHud().draw(g);
		game.getUpgradeHud().draw(g);

	}

	// obiekt odpowiadaj¹cy za strza³y przeciwników
	ActionListener enemyShoot = new ActionListener() {
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			if (!enemies.isEmpty())
				randomChooseEnemy().shoot();
			else if (game.isBossAlive()) {
				game.getBoss().shoot();
			}
		}
	};

	ActionListener upgradeSpwan = new ActionListener() {
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			// sprawdzanie czy istnieje aktywne ulepszenie
			if (currentUpgrade == null) {
				upgrade = new Upgrade(game);
				upgradeTimer.setDelay(upgradeInitDelay + random.nextInt(upgradeDelay));
			}
		}
	};

	private Enemy randomChooseEnemy() {
		return enemies.get(random.nextInt(enemies.size()));
	}

	/**
	 * Funkcja resetuj¹ca stan wszystkich obiektów tzn. ustawiaj¹ca ich
	 * pocz¹tkow¹ wartoœæ po pora¿ce gracza.
	 */
	public void reset() {
		player.setHealth(Player.BASE_HEALTH);
		player.setX(Game.PLAYER_X);
		player.setY(Game.PLAYER_Y);
		player.setSpeedX(0);
		player.setSpeedY(0);
		game.setCurrentWave(0);
		game.getUpgradeHud().setCurrentValue(0);
		game.setBossSpawn(false);
		game.setBossAlive(false);
		game.getBossHud().setCurrentValue(Boss.BASE_HEALTH);
		currentUpgrade = null;
		shootRandomDelay = SHOOT_DELAY;
		Enemy.setEnemySpeed(Enemy.BASE_SPEED);
		enemies.clear();
		bullets.clear();

		if (currentUpgrade != null)
			currentUpgrade.removeUpgrade();
	}

}
