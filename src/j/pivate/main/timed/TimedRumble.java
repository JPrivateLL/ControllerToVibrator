package j.pivate.main.timed;

import j.pivate.main.vibrator.Vibrator;

import javax.swing.JOptionPane;

public class TimedRumble {

	boolean back, start = false;
	int time = 0;
	Vibrator vib;

	public TimedRumble(final Vibrator vib) throws InterruptedException {
		this.vib = vib;
		final String i = JOptionPane.showInputDialog("");
		if (i == null) {
			vib.release();
			System.exit(0);
		}
		time = Integer.parseInt(i) * 1000;
		this.run();
		vib.release();
		System.exit(0);
	}

	private void run() {

		final Thread timer = new Thread() {
			@Override
			public void run() {
				do {
					if (time >= 100) {
						try {
							Thread.sleep(100);
						} catch (final InterruptedException e) {
							e.printStackTrace();
						}
						time -= 100;
					} else {
						try {
							Thread.sleep(time);
						} catch (final InterruptedException e) {
							e.printStackTrace();
						}
						time = 0;
					}
				} while (time > 0);

				vib.rumble(100);
			}
		};
		timer.start();

		JOptionPane.showMessageDialog(null, "waiting:" + time);
	}
}
