package com.jaca;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Klasa odpowiadaj�ca za sterowanie gracza wykorzystuj�ca klawiature oraz
 * wybrane klawisze. W grze sterowanie odbywa si� za pomoc� klawiszy strza�ek
 * kierunkowych: lewo,prawo,g�ra,d�  oraz strza�u klawiszem spacji.
 *
 */
public class KeyInput implements KeyListener {

	private Player player;

	KeyInput(Player player) {
		this.player = player;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			// System.out.println("VK_UP");
			player.setSpeedY(-1);
			player.setSpeedX(0);
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			// System.out.println("VK_DOWN");
			player.setSpeedY(1);
			player.setSpeedX(0);
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.setSpeedX(1);
			player.setSpeedY(0);
			// System.out.println("VK_RIGHT");
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.setSpeedX(-1);
			player.setSpeedY(0);
		}
		// System.out.println("VK_LEFT");

	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() != KeyEvent.VK_SPACE) {
			player.setSpeedY(0);
			player.setSpeedX(0);
		} else {
			player.shoot();
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
