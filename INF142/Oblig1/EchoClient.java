import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

class EchoClient extends Thread {
    protected Socket socket;

    public EchoClient(Socket clientSocket) {
	this.socket = clientSocket;
    }

    public void run() {
	String clientID;
	String clientInput;
	String serverOutput;
	BufferedReader inFromClient = null;
	DataOutputStream outToClient = null;

	// Avoiding thread syncronization
	try {
	    Thread.currentThread().sleep(1000);
	}
	catch(InterruptedException e) {
	    e.printStackTrace();
	}

	// Creating Input and Output stream readers
	try {
	    inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    outToClient = new DataOutputStream(socket.getOutputStream());
	    clientID = socket.getInetAddress().getHostAddress();
	}
	catch(IOException e){
	    return;
	}

	while(true) {
	    try {
		clientInput = inFromClient.readLine();
	    }
	    catch(IOException e) {
		return;
	    }
	    System.out.println("DATA FROM " + clientID + ": " + clientInput);
	    
	    if(clientInput.equalsIgnoreCase("FULL")) {
		// Return date and time in format "febuary 2017 17:15:16"
		try {
		    DateFormat df = new SimpleDateFormat("MMMMM yyyy HH:mm:ss");
		    Date dateobj = new Date();
		    serverOutput = df.format(dateobj) + '\n';
		    outToClient.writeBytes(serverOutput);
		    System.out.println(clientID + " requested FULL, data sent: " + serverOutput);
		}
		catch(IOException e) {
		    return;
		}
	    }

	    else if(clientInput.equalsIgnoreCase("DATE")) {
		// Return date in format "febuary 2017"
		try {
		    DateFormat df = new SimpleDateFormat("MMMMM yyyy");
		    Date dateobj = new Date();
		    serverOutput = df.format(dateobj) + '\n';
		    outToClient.writeBytes(serverOutput);
		    System.out.println(clientID + " requested DATE, data sent: " + serverOutput);
		}
		catch(IOException e) {
		    return;
		}
	    }
		    
	    else if(clientInput.equalsIgnoreCase("TIME")) {
		// Return time in format 17:15:16
		try {
		    DateFormat df = new SimpleDateFormat("HH:mm:ss");
		    Date dateobj = new Date();
		    serverOutput = df.format(dateobj) + '\n';
		    outToClient.writeBytes(serverOutput);
		    System.out.println(clientID + " requested TIME, data sent: " + serverOutput);
		}
		catch(IOException e) {
		    return;
		}
	    }
	    
	    else if(clientInput.equalsIgnoreCase("CLOSE")) {
		// Close the connection with client
		try {
		    serverOutput = "Closing the connection now" + '\n';
		    outToClient.writeBytes(serverOutput);
		    System.out.println(clientID + " requested to close the connection. Connection with " + clientID + " closed\n");
		    socket.close();
		}
		catch(IOException e) {
		    return;
		}
	    }

	    else if(clientInput.equalsIgnoreCase("SHUTDOWN")) {
		// Close the connection with client, and terminate the server
		try {
		    serverOutput = "Closing the connection now, and shutting down the server. " + '\n';
		    outToClient.writeBytes(serverOutput);
		    System.out.println(clientID + " asked to close the connection and shutdown the server. Connection with " + clientID + " closed\n");
		    socket.close();
		    System.out.println("Server shutting down");
		    System.exit(1);
		}
		catch(IOException e) {
		    return;
		}
	    }
	    
	    else {
		// Input matches none of the above, error is sent to appropriate client
		try {
		    serverOutput = "Unknown input, please try again. Valid inputs are: FULL, DATE, TIME, CLOSE, and SHUTDOWN.\n";
		    outToClient.writeBytes(serverOutput);
		    System.out.println("Server Output: " + serverOutput);
		    System.out.println("Input not valid from " + clientID + " error sent. ");
		}
		catch(IOException e) {
		    e.printStackTrace();
		    return;
		}
	    } // End of else
	} // End of while	
    } // End of run
}
