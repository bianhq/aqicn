package cn.edu.ruc.aqi;

import java.util.Timer;

public class InitSystem{
	private static boolean inited = false;
	public static void init() {
		if (inited == false) {
			Timer timer = new Timer();
			timer.schedule(new UpdateDataTask(), 0, 600000);
			inited = true;
		}
	}
}
