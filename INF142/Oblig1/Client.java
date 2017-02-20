import java.io.*;
import java.net.*;

class Client {
    public static void main(String[] args) throws IOException {
	String serverReply;
	String sentence;
	String hostIP;
	
	BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

	// "localhost" or 127.0.0.1 will connect if settings are default and server is run on same machine as client
	System.out.print("Please type in name, or IP of host: ");
	hostIP = inFromUser.readLine();
	
	// Create clientSocket object of type Socket , connect to server
	Socket clientSocket = new Socket(hostIP,1978);

	// Create output stream attached to socket
	DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

	// Create input stream attached to socket
	BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

	while(true) {
	    // Read line from user
	    sentence = inFromUser.readLine();

	    System.out.println("TO SERVER: '" + sentence + "'");
	       
	    // Send line to server
	    outToServer.writeBytes(sentence + '\n');
	    
	    // Read line from server
	    serverReply = inFromServer.readLine();
	    
	    System.out.println("FROM SERVER: '" + serverReply + "'");

	    // Breaks out of the while after the close or shutdown message has been sent
	    if(sentence.equalsIgnoreCase("CLOSE") || sentence.equalsIgnoreCase("SHUTDOWN")) break;
	}
	
	// Cleanup on aile 6 (Close socket)
	clientSocket.close();
    }
}
