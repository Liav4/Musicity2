package main;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


// a class to notify the client about certain
// events throughout the servlet lifetime
public class ClientInteractor {

	// a method to get the writer to the response packet
	private static PrintWriter getPrintWriter(HttpServletResponse response) {

		// defining the writer
		PrintWriter writer = null;

		try {

			// getting the writer
			writer = response.getWriter();

		} // handling the exception
		catch (IOException exception) {

			System.err.println(StringConstants.RESPONSE_WRITER_ERROR);
			System.err.println(StringConstants.EXCPETION_MESSAGE + exception.getMessage());

		}

		// returning the writer
		return writer;

	}

	// a method to send a status to the client
	public static void sendStatus(HttpServletResponse response, int status) {

		// getting the object for text output in the packet
		PrintWriter writer = getPrintWriter(response);

		// sending the notification
		writer.print(status);

	}

	// a method to send an array of objects to the client
	public static void sendObject(HttpServletResponse response, Object object) {

		// getting the object for text output in the packet
		PrintWriter writer = getPrintWriter(response);

		// getting a GSON object to format the information to be sent
		// in a JSON format
		Gson gson = new Gson();

		// sending the notification
		
		writer.print(gson.toJson(object));

	}

	// a method to send an array of objects to the client
	public static void sendObjects(HttpServletResponse response, Object[] objects) {
	
		// getting the object for text output in the packet
		PrintWriter writer = getPrintWriter(response);
	
		// getting a GSON object to format the information to be sent
		// in a JSON format
		Gson gson = new Gson();
		
		System.out.println("what is sent: " + gson.toJson(objects));
		
		// sending the notification
		writer.print(gson.toJson(objects));
	
	}
	
	public static void sendData(HttpServletResponse response, String data) {
		
		// getting the object for text output in the packet
		PrintWriter writer = getPrintWriter(response);
		
		System.out.println("the data that will be sent: " + data);
		
		// sending the data
		writer.print(data);
		
	}

}
