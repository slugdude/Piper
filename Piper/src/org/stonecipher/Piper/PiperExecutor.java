package org.stonecipher.Piper;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Set;

import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

public class PiperExecutor implements ConsoleCommandSender {

	private final ConsoleCommandSender sender;
	private final Server server;
	private final Socket socket;
	private final String token;

	public PiperExecutor(ConsoleCommandSender sender, Server server, Socket socket, String token) {
		this.server = server;
		this.sender = sender;
		this.socket = socket;
		this.token = token;
	}

	@Override
	public void sendMessage(String arg0) {
		try {
			PrintWriter dOut = new PrintWriter(new DataOutputStream(socket.getOutputStream()));
			dOut.println(arg0);
			dOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMessage(String[] arg) {
		try {
			PrintWriter dOut = new PrintWriter(new DataOutputStream(socket.getOutputStream()));
			for (int i = 0; i < arg.length; i++) {
				dOut.println(arg[i]);
			}
			dOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0) {
		return sender.addAttachment(arg0);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, int arg1) {
		return sender.addAttachment(arg0, arg1);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2) {
		return sender.addAttachment(arg0, arg1, arg2);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2, int arg3) {
		return sender.addAttachment(arg0, arg1, arg2, arg3);
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		return sender.getEffectivePermissions();
	}

	@Override
	public boolean hasPermission(String arg0) {
		return sender.hasPermission(arg0);
	}

	@Override
	public boolean hasPermission(Permission arg0) {
		return sender.hasPermission(arg0);
	}

	@Override
	public boolean isPermissionSet(String arg0) {
		return sender.isPermissionSet(arg0);
	}

	@Override
	public boolean isPermissionSet(Permission arg0) {
		return sender.isPermissionSet(arg0);
	}

	@Override
	public void recalculatePermissions() {
		sender.recalculatePermissions();
	}

	@Override
	public void removeAttachment(PermissionAttachment arg0) {
		sender.removeAttachment(arg0);
	}

	@Override
	public boolean isOp() {
		return sender.isOp();
	}

	@Override
	public void setOp(boolean arg0) {
		sender.setOp(arg0);
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public Server getServer() {
		return server;
	}

	@Override
	public Spigot spigot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void abandonConversation(Conversation arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void abandonConversation(Conversation arg0, ConversationAbandonedEvent arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void acceptConversationInput(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean beginConversation(Conversation arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isConversing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void sendRawMessage(String arg0) {
		// TODO Auto-generated method stub

	}

}
