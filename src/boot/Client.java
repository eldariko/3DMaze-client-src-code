package boot;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import algorithms.search.Solution;

// TODO: Auto-generated Javadoc
/**
 * The Class Client.
 */
public class Client {

	/** The socket. */
	private Socket socket;

	/**
	 * Instantiates a new client.
	 *
	 * @param address
	 *            the address
	 * @param port
	 *            the port
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public Client(String address, int port) throws IOException {

		socket = new Socket(address, port);

	}

	/**
	 * Gets the solution from server.
	 *
	 * @param prob
	 *            the prob
	 * @return the solution from server
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public Solution getSolutionFromServer(Problem prob) throws IOException,
			ClassNotFoundException {

		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		Solution s = null;

		oos = new ObjectOutputStream(socket.getOutputStream());
		oos.writeObject(prob);
		ois = new ObjectInputStream(socket.getInputStream());
		s = (Solution) (ois.readObject());

		oos.close();
		ois.close();

		return s;
	}

	/**
	 * Close connettion.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void closeConnettion() throws IOException {
		socket.close();
	}

}
