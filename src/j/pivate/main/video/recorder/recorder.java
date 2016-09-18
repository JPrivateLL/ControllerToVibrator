package j.pivate.main.video.recorder;

import java.awt.MouseInfo;

public class recorder {

	private int x = 0;
	private int y = 0;

	private float delta = 0;

	public recorder() {
		System.out.println("start recorder");
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					int mouseX = Math
							.abs(MouseInfo.getPointerInfo().getLocation().x);
					int mouseY = Math
							.abs(MouseInfo.getPointerInfo().getLocation().y);

					int deltaX = Math.abs(mouseX - x);
					int deltaY = Math.abs(mouseY - y);

					delta = (float) Math
							.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

					x = mouseX;
					y = mouseY;
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		Math.sqrt(x + y);

	}

	public float getStrength() {
		float delta = this.delta;
		delta /= 100;
		if (delta > 1f) {
			delta = 1;
		}
		System.out.println(delta);
		return delta;
	}
}
