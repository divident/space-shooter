package com.jaca;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * G³ówna metoda game tworz¹ca obiekty,tablice obiektów, dodaj¹c¹ obs³ugê
 * klawatury a tak¿e tworz¹ca JPanel cards ktora zarz¹dza przestawianie pomiêdzy
 * dwoma panelami. Klasa game dziedziczy po klasie JFrame tworz¹c okno o wymiarach
 * 800x600 w którym bêdzie wyœwietlana gra.
 * 
 * @author Piotr Chmielewski, Kamil Go³êbiewski
 * @version 1.0
 */

public class Game extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 800, HEIGHT = 600;

	static enum GameStatus {
		GAME_START, GAME_RUNNING, GAME_OVER
	};

	private Background back;
	private Player player;
	private Boss boss;
	private Hud hpHud;
	private Hud bossHud;
	private Hud upgradeHud;
	private int enemyWidth = 25;
	private int enemyHeight = 25;
	private int enemyRow = 3;
	private int enemyCol = 8;
	private int enemyOffset = 15;
	private int waveCount = 3;
	private int currentWave;
	private Point waveLeftUpperCorner;
	private Point waveRigthtDownCorner;
	private static int hudOffset = 70;
	private int hpHudWidth = 150;
	private int hpHudHeight = 15;
	private int bossHudWidth = 250;
	private int bossHudHeight = 20;
	private int bossHudPosX = WIDTH / 2 - bossHudWidth / 2;
	private int bossHudPosY = 5;
	private int hpHudPosX = 25;
	private int hpHudPosY = HEIGHT - hudOffset;
	private boolean bossSpawn = false;
	private boolean bossAlive = false;
	private static Random random = new Random();
	private Board board;
	private Menu menu;
	private GameStatus gameStatus;
	public static final String MENU_PANEL = "menu panel";
	public static final String BOARD_PANEL = "board panel";
	private JPanel cards;
	public static final int PLAYER_X = WIDTH / 2;
	public static final int PLAYER_Y = HEIGHT - hudOffset * 2 - 100;
	// private ArrayList<GameObject> gameObjects;
	private ArrayList<Enemy> enemies;
	private ArrayList<Bullet> bullets;
	public CardLayout cl;

	/**
	 * Konstruktor klasy game, powo³uje wszystkie obiekty gry.
	 */
	public Game() {
		super("SpaceShooter by Jaca");
		gameStatus = GameStatus.GAME_START;
		hpHud = new Hud(hpHudWidth, hpHudHeight, hpHudPosX, hpHudPosY);
		bossHud = new Hud(bossHudWidth, bossHudHeight, bossHudPosX, bossHudPosY);
		upgradeHud = new Hud(hpHudWidth, hpHudHeight, WIDTH - 2 * hpHudPosX - hpHudWidth, hpHudPosY, Color.BLUE);

		player = new Player(PLAYER_X, PLAYER_Y, this);

		boss = new Boss(WIDTH / 2, 0, random, this);
		bossHud.setCurrentValue(boss.getHealth());
		// dodanie obs³ugi klawiatury
		// addKeyListener(new KeyInput(player));

		// tablica ze wszystkimi objektami gry
		enemies = new ArrayList<Enemy>();
		bullets = new ArrayList<Bullet>();

		spawnNewWave();
		// ustawianie wiercho³ków fali przeciwników
		setWaveLeftUpperCorner();
		setWaveRigthtDownCorner();

		setCurrentWave(0);

		menu = new Menu(this);
		board = new Board(enemies, bullets, player, back, this);
		cards = new JPanel(new CardLayout());
		cards.add(menu, MENU_PANEL);
		cards.add(board, BOARD_PANEL);
		add(cards);

		// obs³uga klawiatury
		KeyInput keyInput = new KeyInput(player);
		this.addKeyListener(keyInput);
		board.addKeyListener(keyInput);

		cl = (CardLayout) (cards.getLayout());

		// ustawienia GUI Swing
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setVisible(true);
	}

	/**
	 * Funkcja zmieniaj¹ca JPanel cards na grê, wywo³ywana po naciœniêciu
	 * przycisku start w menu.
	 */
	public void swap() {
		cl.show(cards, BOARD_PANEL);
		board.requestFocus();

	}

	/**
	 * Funkcja zmieniaj¹ca JPanel cards na menu, wywo³ywana po pora¿ce gracza.
	 */
	public void swap1() {
		cl.show(cards, MENU_PANEL);
	}

	@Override
	public void run() {

	}

	/**
	 * G³ówna funkcja main tworz¹ca now¹ grê
	 * 
	 * @param args tablica String
	 */
	public static void main(String[] args) {
		Game game = new Game();
		SwingUtilities.invokeLater(game);
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public void setBullets(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}

	public Point getWaveLeftUpperCorner() {
		return waveLeftUpperCorner;
	}

	public void setWaveLeftUpperCorner() {
		this.waveLeftUpperCorner = new Point(enemies.get(0).getX(), enemies.get(0).getY());
	}

	public Point getWaveRigthtDownCorner() {
		return waveRigthtDownCorner;
	}

	public void setWaveRigthtDownCorner() {
		this.waveRigthtDownCorner = new Point(enemies.get(enemies.size() - 1).getX(),
				enemies.get(enemies.size() - 1).getY());
	}

	public int getWaveCount() {
		return waveCount;
	}

	public void setWaveCount(int waveCount) {
		this.waveCount = waveCount;
	}

	public int getCurrentWave() {
		return currentWave;
	}

	public void setCurrentWave(int currentWave) {
		this.currentWave = currentWave;
	}

	public void spawnNewWave() {
		for (int i = 0; i < enemyCol; i++) {
			for (int j = 1; j <= enemyRow; j++) {
				Enemy enemy = new Enemy((enemyWidth + enemyOffset) * i, (enemyHeight + enemyOffset) * j, enemyWidth,
						enemyHeight, this);
				enemies.add(enemy);
			}
		}
	}

	public Hud getHpHud() {
		return hpHud;
	}

	public void setHpHud(Hud hpHud) {
		this.hpHud = hpHud;
	}

	public int getHudOffset() {
		return hudOffset;
	}

	public void setHudOffset(int hudOffset) {
		Game.hudOffset = hudOffset;
	}

	public Boss getBoss() {
		return boss;
	}

	public void setBoss(Boss boss) {
		this.boss = boss;
	}

	public static Random getRandom() {
		return Game.random;
	}

	public static void setRandom(Random random) {
		Game.random = random;
	}

	public boolean isBossSpawn() {
		return bossSpawn;
	}

	public void setBossSpawn(boolean bossSpawn) {
		this.bossSpawn = bossSpawn;
	}

	public Hud getBossHud() {
		return bossHud;
	}

	public void setBossHud(Hud bossHud) {
		this.bossHud = bossHud;
	}

	public synchronized GameStatus getGameStatus() {
		return gameStatus;
	}

	public synchronized void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}

	public boolean isBossAlive() {
		return bossAlive;
	}

	public void setBossAlive(boolean bossAlive) {
		this.bossAlive = bossAlive;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Hud getUpgradeHud() {
		return upgradeHud;
	}

	public synchronized Board getBoard() {
		return board;
	}

	public void setUpgradeHud(Hud upgradeHud) {
		this.upgradeHud = upgradeHud;
	}

	public synchronized Menu getMenu() {
		return menu;
	}
}
