import java.io.*;
import java.net.*;

class Client {
    public static void main(String[] args) throws IOException {
	String sentence;
	String modSentence;

	BufferedReader inFromUser =new BufferedReader(new InputStreamReader(System.in));

	// Create clientSocket object of type Socket , connect to server
	Socket clientSocket = new Socket("localhost",6789);

	// Create output stream attached to socket
	DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

	// Create input stream attached to socket
	BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

	// Read line from user
	sentence = inFromUser.readLine();

	// Send line to server
	outToServer.writeBytes(sentence + '\n');

	// Read line from server
	modSentence = inFromServer.readLine();

	System.out.println("FROM SERVER: " + modSentence);

	// Cleanup on aile 6 (Close socket)
	clientSocket.close();
    }
}
