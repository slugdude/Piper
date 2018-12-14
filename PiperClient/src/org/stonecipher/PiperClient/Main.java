package org.stonecipher.PiperClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Main {

	public static void main(String[] args) throws IOException {
		PiperCommand tmp = new PiperCommand(args[0], args[1]);
		Socket s = new Socket("127.0.0.1", 46920);
		PrintWriter dOut = new PrintWriter(new DataOutputStream(s.getOutputStream()));
		dOut.println(":" + tmp.getPayload());
		dOut.flush();
		BufferedReader dIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String input;
		while (!(input = dIn.readLine()).equals("!:" + tmp.getToken())) {
			System.out.println(input);
		}
		s.close();
		System.exit(0);
	}
}