package org.stonecipher.Piper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	Random rand = new Random();

	private ServerSocket listener;
	private FileConfiguration config = getConfig();

	@Override
	public void onEnable() {
		setupConfig();
		getLogger().info("Listening on port " + config.getString("port") + " with access token \""
				+ config.getString("accesstoken") + "\".");
		initializeListener();
	}

	@Override
	public void onDisable() {

	}

	private void initializeListener() {
		Thread commandListener = new Thread(() -> {
			try {
				listener = new ServerSocket(Integer.valueOf(config.getString("port")));
				while (true) {
					Socket socket = listener.accept();
					socket.setSoTimeout(500);
					BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String fromSocket = br.readLine();
					PiperCommand inputCommand = new PiperCommand(socket.getInetAddress(),
							fromSocket.substring(fromSocket.indexOf(":") + 1));
					if (inputCommand.getToken().equals(config.getString("accesstoken"))) {
						getLogger().info("Client \'" + inputCommand.getAddress() + "\' executed \'"
								+ inputCommand.getFormattedCommand() + "\'.");
						boolean success = Bukkit.getScheduler().callSyncMethod(this, new Callable<Boolean>() {
							@Override
							public Boolean call() {
								PiperExecutor wrapper = new PiperExecutor(
										(ConsoleCommandSender) Bukkit.getConsoleSender(), Bukkit.getServer(), socket,
										config.getString("accesstoken"));
								return Bukkit.getServer().dispatchCommand(wrapper, inputCommand.getFormattedCommand());
							}
						}).get();

					} else {
						getLogger().info("Denied access for client \'" + inputCommand.getAddress()
								+ "\'. Invalid access token!");
					}

				}
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		});
		commandListener.start();
	}

	private void setupConfig() {
		try {
			if (!getDataFolder().exists()) {
				getDataFolder().mkdir();
			}
			File file = new File(getDataFolder(), "config.yml");

			if (!file.exists()) {
				getLogger().info("Config.yml not found, creating.");
				String token = generateToken(32);
				getLogger().info("Generated suggested token: " + token);
				saveDefaultConfig();
				config.addDefault("accesstoken", token);
				config.addDefault("port", "46920");
				config.options().copyDefaults(true);
				saveConfig();
			} else {
				getLogger().info("Config.yml found, loading.");
				String token = generateToken(32);
				config.addDefault("accesstoken", token);
				config.addDefault("port", "46920");
				config.options().copyDefaults(true);
				saveConfig();
				if ((config.getString("accesstoken").equals("token"))
						|| (config.getString("accesstoken").length() < 32)) {
					getLogger().info("Improper token found. Generating a suggested token");
					config.set("accesstoken", token);
					config.save(file);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String generateToken(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder random = new StringBuilder();
		for (int i = 0; i < length; i++) {
			random.append(chars.charAt(rand.nextInt(52)));
		}
		return random.toString();
	}
}