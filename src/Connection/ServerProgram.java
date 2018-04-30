package Connection;

import java.io.IOException;
import java.util.Date;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class ServerProgram extends Listener{
	//Server Object
		static  Server server;
		//Ports to listen on
		static int udpPort = 27960, tcpPort = 27960;
		
		public static void main(String[] args) throws Exception {
			//Create the server
			server = new Server();
			
			//Register a package;
			server.getKryo().register(PacketMessage.class);
			//we can only send objects as packets if they are registered
			
			//Bind to a port
			server.bind(tcpPort,udpPort);
			
			//Start the server
			server.start();
			
			System.out.println("Server is optional!");
		}
		
		//this run when connection is received.
		public void connected(Connection c) {
			System.out.println("Received a connection from "+c.getRemoteAddressTCP().getHostString());
			//created a message packet.
			PacketMessage packetMessage = new PacketMessage();
			//Assign the message text
			packetMessage.message = "Hello "+ new Date().toString();
			
			//Send the message
			c.sendTCP(packetMessage); //alternative c.sendUDP(packetMessage);		
		}
		
		//this is run when we receive a packet.
		public void received(Connection c, Object p) {
			
		}
		
		//This is run when client has disconnected.
		public void disconnected(Connection c) {
			System.out.println("a client disconnected.");
		}
}
