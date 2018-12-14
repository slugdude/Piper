package org.stonecipher.PiperClient;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Main {

	public static void main(String[] args) throws IOException {
		PiperCommand tmp = new PiperCommand(args[0], args[1]);
		System.out.println(tmp.getPayload());
		Socket s = new Socket("127.0.0.1", 46920);
		DataOutputStream dOut = new DataOutputStream(s.getOutputStream());
		dOut.writeByte(1);
		dOut.writeUTF(":" + tmp.getPayload());
		dOut.flush();
		s.close();
		System.exit(0);
	}
}