package j.pivate.main.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JCheckBox;

class QuadCheckBox extends JCheckBox implements Icon, ActionListener {

	private static final long serialVersionUID = 1L;
	private int selectedState;

	public QuadCheckBox() {
		this("");
	}

	public QuadCheckBox(final String text) {
		super(text);
		putClientProperty("SelectionState", 0);
		setIcon(this);
		addActionListener(this);
		selectedState = 0;
	}

	public QuadCheckBox(final String text, final int sel) {
		super(text, sel > 1 ? true : false);
		if (sel < 0 || sel > 3) {
			throw new IllegalArgumentException();
		}

		selectedState = sel;

		addActionListener(this);
		setIcon(this);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		selectedState++;
		if (selectedState > 3) {
			selectedState = 0;
		}

	}

	@Override
	public int getIconHeight() {
		return 10;
	}

	@Override
	public int getIconWidth() {
		// TODO Auto-generated method stub
		return 10;
	}

	public int getSelectionState() {
		return selectedState;
	}

	@Override
	public void paintIcon(final Component c, final Graphics g, final int x,
			final int y) {
		final int w = getIconWidth();
		final int h = getIconHeight();

		Color fillColor;
		if (c.isEnabled()) {
			g.setColor(new Color(52, 52, 52));
			switch (getSelectionState()) {
			case 1:// vibrate
				fillColor = new Color(255, 0, 0);
				break;
			case 2:// shock
				fillColor = new Color(0, 0, 255);
				break;
			case 3:// both
				fillColor = new Color(52, 52, 52);
				break;
			default:
				fillColor = new Color(255, 0, 0);
				break;
			}
		} else {// checkBox disabled
			g.setColor(new Color(182, 182, 182));
			switch (getSelectionState()) {
			case 1:// vibrate
				fillColor = new Color(255, 137, 137);
				break;
			case 2:// shock
				fillColor = new Color(137, 137, 255);
				break;
			case 3:// both
				fillColor = new Color(104, 104, 104);
				break;
			default:
				fillColor = new Color(255, 0, 0);
				break;
			}
		}

		g.drawRect(4, 6, w, h);
		g.setColor(new Color(240, 240, 240));
		g.fillRect(4 + 1, 6 + 1, w - 1, h - 1);
		if (getSelectionState() != 0) {
			g.setColor(fillColor);
			g.fillRect(4 + 1, 6 + 1, w - 2, h - 2);
		}
	}

	public void setSelectionState(final int sel) {
		if (sel == 3 || sel == 2 || sel == 1) {
			setSelected(false);
		} else if (sel == 0) {
			setSelected(false);
		} else {
			throw new IllegalArgumentException();
		}
		selectedState = sel;
	}
}