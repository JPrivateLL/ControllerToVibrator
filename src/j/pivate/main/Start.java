package j.pivate.main;

import j.pivate.main.gui.GUIStartMenu;

import java.awt.EventQueue;
import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.nio.channels.FileLock;
import java.util.Arrays;

import javax.swing.JOptionPane;

class Handler implements Thread.UncaughtExceptionHandler {
	@Override
	public void uncaughtException(final Thread t, final Throwable e) {
		System.out.println("GLOBAL ERROR CATCHER:" + e);
		if (e.getMessage().contains("rxtxSerial")) {
			JOptionPane
					.showMessageDialog(
							null,
							"Something went wrong! dont panic this one can be fixed, best is to send me a private message on the forum\n"
									+ "Error message: " + e.getMessage(),
							"Fatal Error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
}

public class Start {

	
	public static final String version = "3.0";
	private static boolean debug;
	
	
	public static void addLibraryPath(final String pathToAdd) throws Exception {
		final Field usrPathsField = ClassLoader.class
				.getDeclaredField("usr_paths");
		usrPathsField.setAccessible(true);

		// get array of paths
		final String[] paths = (String[]) usrPathsField.get(null);

		// check if the path to add is already present
		for (final String path : paths) {
			if (path.equals(pathToAdd)) {
				return;
			}
		}

		// add the new path
		final String[] newPaths = Arrays.copyOf(paths, paths.length + 1);
		newPaths[newPaths.length - 1] = pathToAdd;
		usrPathsField.set(null, newPaths);
	}

	public static boolean lockInstance() {
		try {
			final File file = new File("lock");

			final RandomAccessFile randomAccessFile = new RandomAccessFile(
					file, "rw");
			final FileLock fileLock = randomAccessFile.getChannel().tryLock();

			if (fileLock != null) {
				Runtime.getRuntime().addShutdownHook(new Thread() {
					@Override
					public void run() {

						try {
							fileLock.release();
							randomAccessFile.close();
							file.delete();
						} catch (final Exception e) {
							System.out.println("Unable to remove lock file");
						}
					}
				});
				return true;
			}
		} catch (final Exception e) {
			System.out.println("Unable to create and/or lock file: " + e);
		}
		return false;
	}

	private Start(boolean debug){
		Start.debug = debug;
		if (!lockInstance()) {
			JOptionPane.showMessageDialog(null, "Program already running");
			System.exit(0);
		}
		try {
			// add lib/, lib/native/ and lib/native/win(32/64) to library path.
			String current = System.getProperty("user.dir") + "\\lib\\";
			addLibraryPath(current);
			current = current + "\\Native\\";
			addLibraryPath(current);
			if (System.getProperty("sun.arch.data.model").equals("64")) {
				addLibraryPath(current + "win64");
				System.out.println(current + "win64");
			} else {
				addLibraryPath(current + "win32");
			}

		} catch (final Exception e) {
			e.printStackTrace();
		}
		if(!Start.debug){
			
		}
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					final GUIStartMenu frame = new GUIStartMenu();
					frame.setVisible(true);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	
	}
	
	public static void main(final String[] args) {
		boolean debug = false;
		for (int i = 0; i < args.length; i++) {
			if(args[i].toLowerCase().equals("debug")){
				debug = true;
				System.out.println("debug");
			}
		}
		new Start(debug);
		
	}


	public static boolean isDebugging() {
		return debug;
	}

}
