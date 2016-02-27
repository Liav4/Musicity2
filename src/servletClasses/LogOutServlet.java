package servletClasses;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.*;

/**
 * The Log Out Servlet provides support for logging a user out of the website.
 * 
 * @author LIAV
 * @since 2016-02-26
 * @see main.MusicityServlet
 * @see main.StringConstants
 * @see main.DatabaseInteractor
 */
public class LogOutServlet extends MusicityServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Provides support for a GET request - sending the parameters to the super
	 * class.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		super.doGet(request, response);

	}

	/**
	 * Provides support for a POST request.
	 * 
	 * @param request
	 *            The request from the client.
	 * @param response
	 *            The response to the client.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// invalidate the user session
		request.getSession().invalidate();

	}

}
