import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

class Server {

    static final int PORT = 1978;
    
    public static void main(String[] args) throws IOException {
	ServerSocket serverSocket = null;
	Socket connectionSocket = null;

	// Create welcoming socket at port PORT
	try {
	    serverSocket = new ServerSocket(PORT);
	}
	catch(IOException e) {
	    e.printStackTrace();
	}
	
	// Wait, on welcoming socket accept() method for client contact create new socket on return
	while(true) {
	    try {
		connectionSocket = serverSocket.accept();
		String clientID =  connectionSocket.getInetAddress().getHostName();
		System.out.println("Connection received from: " + clientID + "\n");
	    }
	    catch(IOException e) {
		e.printStackTrace();
	    }

	    // New thread for a client
	    new EchoClient(connectionSocket).start();

	} // End of while
    } // End of main
}
