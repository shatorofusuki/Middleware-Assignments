package logic;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class BookingFlow
 */
public class BookingFlow extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookingFlow() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String text; 
		
		BookingState bs = (BookingState)session.getAttribute("bookingState");
		if (bs==null) {
			text = "No booking is associated with this session\n";
		} else {
			text = "You are at a state " + bs.name() + "\n";
		}
		response.getWriter().append(text);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String text; 
		
		BookingState bs = (BookingState)session.getAttribute("bookingState");
		if (bs==null) {
			text = "Looks like you want to go somewhere, do you?\n I am creating a NEW trip for you\n";
	        session.setAttribute("bookingState", BookingState.NEW);
		} else {
			switch (bs) {
				case NEW:
					session.setAttribute("bookingState", BookingState.PAYMENT);
					text = "Well done, now please PAY!\n";
					break;
				case PAYMENT:
					session.setAttribute("bookingState", BookingState.COMPLETED);
					session.invalidate();
					text = "Your payment has been received, have a good time!\n";
					break;
				case COMPLETED:
					text = "Your booking has been completed, nothing else to do for now\n";
					break;
				default:
					//when done
					text = "How did I end up here\n";
					break;
			}
		}
		response.getWriter().append(text);
	}

}
