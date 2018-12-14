package org.stonecipher.PiperClient;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PiperCommand {

	private String raw;
	private InetAddress address;
	private String token;
	private ArrayList<String> command = new ArrayList<String>();

	public PiperCommand(InetAddress address, String json) {
		JSONParser parser = new JSONParser();
		this.address = address;
		try {
			Object obj = parser.parse(json);
			JSONObject object = (JSONObject) obj;
			this.token = (String) object.get("token");
			this.command.clear();
			JSONArray args = (JSONArray) object.get("command");
			Iterator<String> iterator = args.iterator();
			while (iterator.hasNext()) {
				this.command.add(iterator.next());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public PiperCommand(String token, String commandArray) {
		this.token = token;
		this.command = new ArrayList<String>(Arrays.asList(commandArray.split(" ")));
	}

	public PiperCommand(String token, ArrayList<String> command) {
		this.token = token;
		this.command = command;
	}

	public String getPayload() {
		JSONObject obj = new JSONObject();
		obj.put("token", token);
		JSONArray arr = new JSONArray();
		for (int i = 0; i < command.size(); i++) {
			arr.add(i, command.get(i));
		}
		obj.put("command", arr);
		return obj.toJSONString();
	}

	public String getToken() {
		return token;
	}

	public String getArgs() {
		return command.toString();
	}

	public String getAddress() {
		return address.getHostAddress();
	}

}