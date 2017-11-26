package proxy;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import balancer.Balancer;

/**
 * Servlet implementation class ProxyServlet
 */
public class ProxyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static Thread t;
	private static Balancer b;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProxyServlet() {
        super();
        b = new Balancer();
        Thread t = new Thread(b);
		t.start();
    }
    
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// url
        String url = b.getURL();
        System.out.println("Making request to " + url);
        HttpURLConnection connection = (HttpURLConnection) (new URL(url)).openConnection();
        // HTTP method
        connection.setRequestMethod("GET");
        // copy headers
        Enumeration<String> headers = request.getHeaderNames();
        String header;
        while (headers.hasMoreElements()) {
          header = headers.nextElement();
          connection.setRequestProperty(header, request.getHeader(header));
        }
        // copy body
        BufferedReader inputStream = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        ServletOutputStream sout = response.getOutputStream();
        while ((inputLine = inputStream.readLine()) != null) {
            sout.write(inputLine.getBytes());
        }
        // close
        inputStream.close();
        sout.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
