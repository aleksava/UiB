import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

class Server {
    public static void main(String[] args) throws IOException {
	String clientInput;
	String serverOutput;

	// Create welcoming socket at port 6789
	ServerSocket welcomeSocket = new ServerSocket(6789);

	// Wait, on welcoming socket accept() method
	// for client contact create new socket on return
	while(true) {

	    Socket connectionSocket = welcomeSocket.accept();
	    System.out.println("Connection received from: " + connectionSocket.getInetAddress().getHostName());

	    // Create input stream, attached to socket
	    BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

	    // Create output stream, attached to socket
	    DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

	    // Read in line from socket
	    clientInput = inFromClient.readLine();
	    System.out.println("Data received: " + clientInput);
	    //serverOutput = clientInput.toUpperCase() + '\n';

	    handleInput(connectionSocket.getInetAddress().getHostName(), clientInput);
	    
	} // End of while
    } // End of main

    private static void handleInput(String clientID, String clientData) {
	String severOutput;
	
	if(clientData.equalsIgnoreCase("FULL")) {
	    // Return date and time in format "febuary 2017 17:15:16"
	    DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	    Date dateobj = new Date();
	    serverOutput = df.format(dateobj) + '\n';
	    outToClient.writeBytes(serverOutput);
	    System.out.println("Sent message");
	}

	else if(clientData.equalsIgnoreCase("DATE")) {
	    // Return date in format "febuary 2017"
	    DateFormat df = new SimpleDateFormat("dd/MM/yy");
	    Date dateobj = new Date();
	    serverOutput = df.format(dateobj) + '\n';
	    outToClient.writeBytes(serverOutput);
	    System.out.println("Sent message");
	}
		    
	else if(clientData.equalsIgnoreCase("TIME")) {
	    // Return time in format 17:15:16
	    DateFormat df = new SimpleDateFormat("HH:mm:ss");
	    Date dateobj = new Date();
	    serverOutput = df.format(dateobj) + '\n';
	    outToClient.writeBytes(serverOutput);
	    System.out.println("Sent message");
	}
	    
	else if(clientData.equalsIgnoreCase("CLOSE")) {
	    // Close the connection, and terminate the server
	    serverOutput = "Closing the connection now" + '\n';
	    outToClient.writeBytes(serverOutput);
	    System.out.println("Sent message");
	    connectionSocket.close();
	}
	    
	else {
	    serverOutput = "Unknown input, please try again.\nValid inputs are: FULL, DATE, TIME, and CLOSE.";
	    outToClient.writeBytes(serverOutput);
	    System.out.println("Sent message");
	} // End of else
    }
}
