import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;


public class ObjectRequestInputStream{
	ObjectInputStream input;
	
	public ObjectRequestInputStream(Socket socket) throws IOException{
		input = new ObjectInputStream(socket.getInputStream());
	}


	public Event readRequest() {
		Event o = null;
		try {
			o = (Event) input.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

}