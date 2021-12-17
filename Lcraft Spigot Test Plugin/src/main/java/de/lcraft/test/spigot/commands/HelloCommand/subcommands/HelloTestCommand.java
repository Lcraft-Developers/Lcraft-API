package de.lcraft.test.spigot.commands.HelloCommand.subcommands;

import de.lcraft.api.minecraft.spigot.manager.Module;
import de.lcraft.api.minecraft.spigot.manager.command.SubModuleCommand;
import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.util.ArrayList;

public class HelloTestCommand extends SubModuleCommand {

	public HelloTestCommand(Module m) throws IOException {
		super("test", "test", m, false);
	}

	@Override
	public boolean onCommandExecute(CommandSender s, String[] args) {
		s.sendMessage("test hello");
		return super.onCommandExecute(s, args);
	}

	@Override
	public ArrayList<String> getAllPermissions(ArrayList<String> allPermissions) {
		return allPermissions;
	}

	@Override
	public ArrayList<String> getAllTranslations(ArrayList<String> allTranslations) {
		return allTranslations;
	}
}
