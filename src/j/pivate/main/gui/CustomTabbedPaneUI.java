package j.pivate.main.gui;

import java.awt.Color;

import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class CustomTabbedPaneUI extends BasicTabbedPaneUI {

	@Override
	protected void installDefaults() {
		super.installDefaults();
		highlight = Color.red;
		lightHighlight = new Color(57, 57, 57);
		shadow = new Color(57, 57, 57);
		darkShadow = new Color(25, 25, 25);
		focus = GUIStartMenu.colorHeader;
	}
}
