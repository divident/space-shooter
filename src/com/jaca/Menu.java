package com.jaca;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.jaca.Game.GameStatus;

/**
 * Klasa uruchamiana przy starcie wyœwietla menu gry programu oraz pozostawia do
 * wyboru gracza: start nowej gry lub wyjœcie z gry, a tak¿e wyœwietla wynik po
 * ukoñczonej grze. Klasa dziedziczy po JPanel a funkcja paintComponent maluje
 * t³o oraz wyœwietla wynik.
 *
 */

@SuppressWarnings("serial")
public class Menu extends JPanel {
	private Game game;
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	private BufferedImage back1;

	/**
	 * Konstruktor klasy Menu. Argumentem jest obiekt klasy Game. W
	 * konstruktorze tworzone s¹ przyciski startu i exit, ich ob³uga oraz
	 * wczytywany jest obraz dla podanych przycisków.
	 * 
	 * @param game obiekt klasy Game
	 */
	public Menu(Game game) {
		super();

		setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton button1 = new JButton();
		try {
			Image img = ImageIO.read(new File("Start.png"));
			button1.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		button1.setPreferredSize(new Dimension(230, 62));
		buttons.add(button1);
		add(button1);
		JButton button2 = new JButton();
		try {
			Image img1 = ImageIO.read(new File("Exit.png"));
			button2.setIcon(new ImageIcon(img1));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		button2.setPreferredSize(new Dimension(230, 62));
		buttons.add(button2);
		add(button2);
		buttons.get(0).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.setGameStatus(GameStatus.GAME_RUNNING);
				game.swap();
				game.getBoard().reset();
			}
		});
		buttons.get(1).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(-1);

			}
		});
		this.game = game;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			back1 = ImageIO.read(new File("background.png"));
		} catch (Exception e) {
			System.out.println(e);
		}
		g.drawImage(back1, 0, 0, 800, 600, null);
		if (game.getGameStatus() == GameStatus.GAME_OVER) {
			g.setColor(Color.BLUE);
			g.setFont(new Font("Bauhaus 93", Font.BOLD, 36));
			g.drawString("Koniec gry zdobyles ", 200, 200);
			g.drawString("" + game.getPlayer().getScore() + " punktów", 270, 300);
			game.getPlayer().setScore(0);
			game.getHpHud().setCurrentValue(100);

		}
	}
}
