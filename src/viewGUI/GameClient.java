package viewGUI;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import org.json.JSONArray;
import org.json.JSONObject;

import logic.GameObserver;
import logic.exceptions.AlreadyDiscoveredException;

public class GameClient {

	private Socket socket;
	private DataInputStream dataIn;
	private DataOutputStream dataOut;
	private ObjectInputStream objectIn;
	private ObjectOutputStream objectOut;
	
	public GameClient() {
		
		try {
			socket = new Socket("localhost", 17071);
			dataIn = new DataInputStream(socket.getInputStream());
			dataOut = new DataOutputStream(socket.getOutputStream());
			objectIn = new ObjectInputStream(socket.getInputStream());
			objectOut = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException e) {
			System.out.println("IOException");
		}
		
	}
	
	public void setMenu1(int num, int d, String g) {
		JSONObject jo = new JSONObject();
		jo.put("method", "setMenu1");
		
		JSONObject data = new JSONObject();
		data.put("num", num);
		data.put("d", d);
		data.put("g", g);
		
		jo.put("data", data);
		
		send(jo.toString());
	}
	
	public void setMenu2(String[] names, String[] diff) throws Exception {
		JSONObject jo = new JSONObject();
		jo.put("method", "setMenu2");
		
		JSONArray arrayNames = new JSONArray(), arrayDiff = new JSONArray();
		
		for (String s : names)
			arrayNames.put(s);
		
		for (String s : diff)
			arrayDiff.put(s);
		
		JSONObject data = new JSONObject();
		data.put("names", arrayNames);
		data.put("diff", arrayDiff);
		
		send(jo.toString());
	}
	
	// setGame se hará desde el lado del servidor al recibir los datos de setMenu2
	
	public void startGame() throws AlreadyDiscoveredException {
		JSONObject jo = new JSONObject(), data = new JSONObject();
		jo.put("method", "startGame");
		jo.put("data", data);
		send(jo.toString());
	}
	
	public void pick(int card) throws AlreadyDiscoveredException{
		JSONObject jo = new JSONObject(), data = new JSONObject();
		jo.put("method", "pick");
		data.put("card", card);
		jo.put("data", data);
		send(jo.toString());
	}
	
	public void load(FileInputStream file) throws Exception {
		JSONObject jo = new JSONObject(), data = new JSONObject();
		jo.put("method", "load");
		data.put("file", file);
		jo.put("data", data);
		send(jo.toString());
	}
	
	public void save(String file) {
		JSONObject jo = new JSONObject(), data = new JSONObject();
		jo.put("method", "save");
		data.put("file", file);
		jo.put("data", data);
		send(jo.toString());
	}
	
	public void addObserver(GameObserver o) {
		// No sé lol
	}
	
	private void send(String s) {
		try {
			objectOut.writeObject(s);
		}
		catch (IOException e) {
			System.out.println("IOException");
		}
	}
	
}
