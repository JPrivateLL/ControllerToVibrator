package j.pivate.main.sound;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Plotter extends JPanel {

	private static final long serialVersionUID = -5678880967121466447L;
	private int yNew = 0;
	private int[] pixels;

	public Plotter(final JFrame frame) {
		pixels = new int[frame.getContentPane().getWidth() + 1];
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final int h = this.getHeight();
		final int w = this.getWidth();

		// clean old
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, w, h);

		// create new array for screen resize
		final int[] oldPixels = pixels;
		pixels = new int[w + 1];

		// shift pixels
		final int shift = pixels.length - oldPixels.length;
		for (int p = 0; p < pixels.length - 1; p++) {
			if (p < shift) {
				pixels[p] = 0;
			} else {
				pixels[p] = oldPixels[p - shift + 1];
			}

		}
		pixels[w] = yNew;

		// repaint
		g.setColor(Color.GREEN);
		for (int p = 1; p < pixels.length; p++) {
			g.drawLine(p - 1, (int) (h - pixels[p - 1] / 100f * h), p,
					(int) (h - pixels[p] / 100f * h));
		}

		// resize plotter to match JFrame
		this.setSize(w, h);

	}

	public void setNewAmp(final int yNew) {
		this.yNew = yNew;
	}
}