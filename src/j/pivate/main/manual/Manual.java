package j.pivate.main.manual;

import j.pivate.main.vibrator.Vibrator;

import java.util.List;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class Manual {

	public Manual(final List<Vibrator> v, final Controller controller) {

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					controller.poll();
					final EventQueue queue = controller.getEventQueue();
					final Event event = new Event();
					while (queue.getNextEvent(event)) {

						final StringBuffer buffer = new StringBuffer(
								controller.getName());
						buffer.append(" at ");
						buffer.append(event.getNanos()).append(", ");
						final Component comp = event.getComponent();
						buffer.append(comp.getName()).append(" changed to ");
						final float value = event.getValue();
						if (comp.isAnalog()) {
							buffer.append(value);
						} else {
							if (value == 1.0f) {
								buffer.append("On");
							} else {
								buffer.append("Off");
							}
						}
						System.out.println(buffer.toString());
					}

					try {
						Thread.sleep(20);
					} catch (final InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}).start();

	}
}