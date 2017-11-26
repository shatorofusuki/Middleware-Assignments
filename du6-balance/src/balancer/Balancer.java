package balancer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Balancer extends Thread {
	private static Map<String, Boolean> URLs;
	private static Iterator last;
	static 
	{
		URLs = new HashMap<String, Boolean>();
		URLs.put("http://147.32.233.18:8888/MI-MDW-LastMinute1/list", true);
		URLs.put("http://147.32.233.18:8888/MI-MDW-LastMinute2/list", true);
		URLs.put("http://147.32.233.18:8888/MI-MDW-LastMinute3/list", true);
		last = URLs.entrySet().iterator();
	};
	
	
	public void run() {
		while(true) {
			Iterator it = URLs.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				String url = (String) pair.getKey();
				HttpURLConnection connection;
				try {
					connection = (HttpURLConnection) (new URL(url)).openConnection();
					connection.setRequestMethod("GET");
					int code = connection.getResponseCode();
					if (code == 200) {
						pair.setValue(true);
						System.out.println("Link "+pair.getKey() + " is available");
					} else {
						pair.setValue(false);
						System.out.println("Link "+pair.getKey() + " is NOT available!");
					}
					
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}				
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static String getURL() {
	
		while (true) {
			if (last.hasNext()) {
				Map.Entry pair = (Map.Entry)last.next();
				if ((boolean) pair.getValue()) {
					return (String) pair.getKey();
				}
			} else {
				last = URLs.entrySet().iterator();
				Map.Entry pair = (Map.Entry)last.next();
				if ((boolean) pair.getValue()) {
					return (String) pair.getKey();
				}
			}
		}
	}
	
}
