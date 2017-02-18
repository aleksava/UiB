import java.net.*;
import java.io.*;

class KnockKnockServer {
    public static void main(String[] args) {
	int portnumber = Integer.parseInt(args[0]);

	try (
	    ServerSocket serverSocket = new ServerSocket(portnumber);
	    Socket clientSocket = serverSocket.accept();
	    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
	    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	    ) {
	}

	try (
	     // ...
	     PrintWriter out =
	     new PrintWriter(clientSocket.getOutputStream(), true);
	     BufferedReader in = new BufferedReader(
						    new InputStreamReader(clientSocket.getInputStream()));
	     ) {
	    String inputLine, outputLine;
            
	    // Initiate conversation with client
	    KnockKnockProtocol kkp = new KnockKnockProtocol();
	    outputLine = kkp.processInput(null);
	    out.println(outputLine);

	    while ((inputLine = in.readLine()) != null) {
		outputLine = kkp.processInput(inputLine);
		out.println(outputLine);
		if (outputLine.equals("Bye."))
		    break;
	    }
	}
    }
}
