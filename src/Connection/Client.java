package Connection;

import com.lloseng.ocsf.client.AbstractClient;

/**
 * 
 * @author Kwankaew Uttama
 *
 */
public class Client extends AbstractClient{

	public Client(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		// TODO Auto-generated method stub
		
	}

}
