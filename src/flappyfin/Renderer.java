package flappyfin;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Renderer extends JPanel{
	private static final long serialVersionID = 1L;
	public FlappyFin main;
	public Renderer(FlappyFin main) {
		this.main = main;
	}
	protected void paintComponent(Graphics g) {
		//inherets from paint class
		super.paintComponent(g);
		main.repaint(g);
	}
}
