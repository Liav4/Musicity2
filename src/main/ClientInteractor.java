package main;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * The ClientInteractor class provides an API for sending data or resources to
 * the client.
 * 
 * @author LIAV
 * @since 2016-02-26
 *
 */
public class ClientInteractor {

	/**
	 * Returns a {@link PrintWriter} object which is used to write data or
	 * resources to the packet which is sent to the client.
	 * 
	 * @param response
	 *            The response that is used to get the object from.
	 * @return The {@link PrintWriter} object.
	 */
	private static PrintWriter getPrintWriter(HttpServletResponse response) {

		// defining the writer
		PrintWriter writer = null;

		try {

			// getting the writer
			writer = response.getWriter();

		} // handling the exception
		catch (IOException exception) {

			System.err.println(StringConstants.RESPONSE_WRITER_ERROR);
			exception.printStackTrace();

		}

		// returning the writer
		return writer;

	}

	/**
	 * Sends a status to the client. Mainly used for a binary response - either
	 * worked or didn't work.
	 * 
	 * @param response
	 *            The response used to send to status.
	 * @param status
	 *            The status to send.
	 */
	public static void sendStatus(HttpServletResponse response, int status) {

		// getting the object for text output in the packet
		PrintWriter writer = getPrintWriter(response);

		// sending the status
		writer.print(status);

	}

	/**
	 * Sends an object to the client, using JSON format that is formatted with a
	 * GSON object.
	 * 
	 * @param response
	 *            A response object used to send the object.
	 * @param object
	 *            The object to send.
	 */
	public static void sendObject(HttpServletResponse response, Object object) {

		// getting the object for text output in the packet
		PrintWriter writer = getPrintWriter(response);

		// getting a GSON object to format the information to be sent
		// in a JSON format
		Gson gson = new Gson();

		// sending the notification

		writer.print(gson.toJson(object));

	}

	/**
	 * Sends an array of objects to the client, using JSON format that is
	 * formatted with a GSON object.
	 * 
	 * @param response
	 *            A response object used to send the objects.
	 * @param objects
	 *            The objects to send.
	 */
	public static void sendObjects(HttpServletResponse response, Object[] objects) {

		// getting the object for text output in the packet
		PrintWriter writer = getPrintWriter(response);

		// getting a GSON object to format the information to be sent
		// in a JSON format
		Gson gson = new Gson();

		// sending the objects in JSON format
		writer.print(gson.toJson(objects));

	}

	/**
	 * Sends a generic data string to the client.
	 * 
	 * @param response
	 *            A response object used to send the data.
	 * @param data
	 *            The data to send.
	 */
	public static void sendData(HttpServletResponse response, String data) {

		// getting the object for text output in the packet
		PrintWriter writer = getPrintWriter(response);

		// sending the data
		writer.print(data);

	}

}
