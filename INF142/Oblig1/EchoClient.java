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
	InputStream inp = null;
	BufferedReader inFromClient = null;
	DataOutputStream outToClient = null;
	
	try {
	    Thread.currentThread().sleep(1000);
	}
	catch(InterruptedException e) {
	    e.printStackTrace();
	}
	
	try {
	    inp = socket.getInputStream();
	    inFromClient = new BufferedReader(new InputStreamReader(inp));
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
	    System.out.println("Data received: " + clientInput);
	    //serverOutput = clientInput.toUpperCase() + '\n';
	    
	    if(clientInput.equalsIgnoreCase("FULL")) {
		// Return date and time in format "febuary 2017 17:15:16"
		try {
		    DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		    Date dateobj = new Date();
		    serverOutput = df.format(dateobj) + '\n';
		    outToClient.writeBytes(serverOutput);
		    System.out.println("Sent message to " + clientID);
		}
		catch(IOException e) {
		    return;
		}
	    }

	    else if(clientInput.equalsIgnoreCase("DATE")) {
		// Return date in format "febuary 2017"
		try {
		    DateFormat df = new SimpleDateFormat("dd/MM/yy");
		    Date dateobj = new Date();
		    serverOutput = df.format(dateobj) + '\n';
		    outToClient.writeBytes(serverOutput);
		    System.out.println("Sent message to " + clientID);
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
		    System.out.println("Sent message to " + clientID);
		}
		catch(IOException e) {
		    return;
		}
	    }
	    
	    else if(clientInput.equalsIgnoreCase("CLOSE")) {
		// Close the connection, and terminate the server
		try {
		    serverOutput = "Closing the connection now" + '\n';
		    outToClient.writeBytes(serverOutput);
		    System.out.println("Sent message to " + clientID);
		    socket.close();
		}
		catch(IOException e) {
		    return;
		}
	    }
	    
	    else {
		try {
		    serverOutput = "Unknown input, please try again. Valid inputs are: FULL, DATE, TIME, and CLOSE.";
		    outToClient.writeBytes(serverOutput);
		    System.out.println("Sent message");
		}
		catch(IOException e) {
		    return;
		}
	    } // End of else
	} // End of while
	
    } // End of run
}
