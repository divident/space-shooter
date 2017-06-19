package com.jaca;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Klasa odpowiadaj¹ca za sterowanie gracza wykorzystuj¹ca klawiature oraz
 * wybrane klawisze. W grze sterowanie odbywa siê za pomoc¹ klawiszy strza³ek
 * kierunkowych: lewo,prawo,góra,dó³  oraz strza³u klawiszem spacji.
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
