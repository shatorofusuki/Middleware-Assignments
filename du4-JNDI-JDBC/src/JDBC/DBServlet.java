package JDBC;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.naming.*;
import java.util.*;
import javax.sql.*;

/**
 * Servlet implementation class DBServlet
 */
public class DBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DBServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Properties parms = new Properties();
		parms.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		parms.put(Context.PROVIDER_URL, "t3://localhost:7001");		
	
		Connection c;
		try {
			InitialContext ctx = new InitialContext(parms);
			DataSource dataSource = (DataSource) ctx.lookup("SlimShady");
			c = dataSource.getConnection();
			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM records");
			while (rs.next()) {
				
				String res = "Reservation #" + rs.getInt("id") + " ";
				res = (rs.getString("type")!= null) ? res += "is a " + rs.getString("type"): res;
				res = (rs.getString("location")!= null) ? res += " to " + rs.getString("location"): res;
				
				res += ". Capacity = " + rs.getInt("capacity");
				res += ", occupied = " + rs.getInt("occupied");
				res += ", trip = " + rs.getInt("occupied") + ".";
				
				res = (rs.getString("person")!= null) ? res += " Made for " + rs.getString("person") + "." : res;
				
				response.getWriter().append(res + "\n");
			}
			
			
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Redirecting to GET");
		doGet(request, response);
	}

}
