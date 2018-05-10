package Connection;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class AClientProgram extends Listener {
	// Client object;
	static Client client;
	// IP to connect
	static String ip = "localhost";
	// Port to connect on.
	static int tcpPort = 27960, udpPort = 27960;
	
	static boolean messageReceived;

	public static void main(String[] args) throws Exception {
		// Create the client
		client = new Client();

		// Register the packet object
		client.getKryo().register(APacketMessage.class);
		
		//Start the client
		client.start();
		//client MUST be started before connecting can take place

		// Connect to the server - wait 5000ms before failing.
		client.connect(5000, ip, tcpPort, udpPort);
		
		//Add a Listener
		client.addListener(new AClientProgram());
		
		System.out.println("Connected! The client program is now waiting for a packet...\n");
		
		while(!messageReceived) {
			Thread.sleep(1000);
		}
		System.out.println("Client will now exit.");
		System.exit(0);
	}
	
	public void received(Connection c, Object p) {
		if(p instanceof APacketMessage) {
			APacketMessage packet = (APacketMessage) p;
			System.out.println("receive a message from the host: "+packet.message);
			
			//we already received the message
		 messageReceived = true;
		}
	}

}
