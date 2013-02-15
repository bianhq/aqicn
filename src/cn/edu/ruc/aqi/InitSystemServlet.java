package cn.edu.ruc.aqi;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InitSystemServlet extends HttpServlet {

	private static final long serialVersionUID = 997327024309990327L;

	@Override
	public void init() throws ServletException {
		InitSystem.init();
	}
	
}
