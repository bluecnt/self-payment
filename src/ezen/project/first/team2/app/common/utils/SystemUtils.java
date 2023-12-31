////////////////////////////////////////////////////////////////////////////////
//
// [SGLEE:20231113MON_014000] Created
//
////////////////////////////////////////////////////////////////////////////////

package ezen.project.first.team2.app.common.utils;

import java.awt.event.ActionListener;

import javax.swing.Timer;

public class SystemUtils {
	public static void setTimeout(int delayInMillis, ActionListener listener) {
		Timer t = new Timer(delayInMillis, e -> {
			listener.actionPerformed(e);
			Timer t2 = (Timer) e.getSource();
			t2.stop();
		});
		t.start();
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void runJarFile(String jarPath, String clsPath) {
		try {
			String cmd = String.format("java -cp %s %s", jarPath, clsPath);
			Process proc = Runtime.getRuntime().exec(cmd);
			proc.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
