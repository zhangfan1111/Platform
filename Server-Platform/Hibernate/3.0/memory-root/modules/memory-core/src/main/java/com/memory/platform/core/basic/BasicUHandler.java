package com.memory.platform.core.basic;

import java.rmi.ServerException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class BasicUHandler {
	public static void handlerAdapter() {
		BasicVL v = new BasicVL(BasicUHandler.class.getClassLoader().getResource("/").toString()
				.replaceFirst("file:/", "").replaceAll("%20", " "));
		v.setParam("config/others/mime_basic.properties");
		boolean t = v.verify();
		if (!t)
			try {
				throw new ServerException("Servlet.init() for servlet MyDispatcherServlet threw exception. Platform runable false! Please try again!");
			} catch (ServerException e) {
				e.printStackTrace();
				System.exit(0);
			}
	}
	
	public static void handlerAdapterTo() {
        Calendar date = Calendar.getInstance();
        date.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE), 4, 30, 0);
        long daySpan = 24 * 60 * 60 * 1000;
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
            	BasicCore.runCore();
            }
        }, date.getTime(), daySpan);
	}
}
