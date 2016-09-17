package j.pivate.main.vibrator;

import java.io.IOException;

import javax.swing.JOptionPane;

public class VibratorTrance extends Vibrator {
	private final int minstrength = 33;
	Process p;
	String s;

	public VibratorTrance(boolean[] types) {
		super("Trance Vibrator", types);

		try {
			s = System.getProperty("user.dir")
					+ "\\lib\\Trance vibrator\\trance-vibrator.exe";
			p = new ProcessBuilder(s, Integer.toString(0)).start();
			p.waitFor();

		} catch (final InterruptedException e) {
			p = null;
		} catch (final IOException e) {
			p = null;
		}
		if (p == null) {
			JOptionPane
					.showMessageDialog(null,
							"Trance drivers not found, redownload or contact me on the forum.");
			System.exit(0);
		}
	}

	@Override
	public boolean isConnected() {
		return (p != null);
	}

	@Override
	public void release() {
		p = null;
	}

	@Override
	public void rumbleSub(float strength) {
		if (strength <= 0)
			strength = 0f;
		else {
			strength = strength * (255 - minstrength) + minstrength;
		}

		int i = (int) (strength * 255);

		if (i > 255) {
			i = 255;
		}

		try {
			p = new ProcessBuilder(s, Integer.toString(i)).start();
			p.waitFor();

		} catch (final InterruptedException e) {
			p = null;
		} catch (final IOException e) {
			p = null;
		}

	}
}
