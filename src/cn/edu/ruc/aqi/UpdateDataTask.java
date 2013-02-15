package cn.edu.ruc.aqi;

import java.util.*;

public class UpdateDataTask extends TimerTask{

	@Override
	public void run() {
		DataFactory.getInstance().updateData();
		System.out.println("updated");
	}
}
