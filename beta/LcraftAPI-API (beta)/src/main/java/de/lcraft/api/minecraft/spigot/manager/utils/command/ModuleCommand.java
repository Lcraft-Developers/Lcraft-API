package de.lcraft.api.minecraft.spigot.manager.utils.command;

import de.lcraft.api.minecraft.spigot.manager.Module;
import de.lcraft.api.minecraft.spigot.manager.logger.ModuleLogger;
import de.lcraft.api.minecraft.spigot.manager.logger.ModuleLoggerType;
import de.lcraft.api.minecraft.spigot.utils.command.Command;

import java.util.ArrayList;
import java.util.Objects;

public abstract class ModuleCommand extends Command {

	private Module module;
	private ArrayList<ModuleSubCommand> subModuleCommands;

	public ModuleCommand(String label, String desc, Module m, boolean splitting) {
		super(label, desc, m.getPermsManager(), m.getModuleManager().getPluginMain().getLPlayerManager(), m.getLanguagesManager(), splitting);
		this.module = m;
		subModuleCommands = new ArrayList<>();
	}

	public final void addModuleSubCommand(ModuleSubCommand subModuleCommand) {
		subModuleCommands.add(subModuleCommand);
	}
	public final ModuleSubCommand getModuleSubCommand(String name) {
		for(ModuleSubCommand m : subModuleCommands) {
			if(m.getName().equalsIgnoreCase(name)) {
				return m;
			}
			continue;
		}
		return null;
	}
	public final boolean existsModuleSubCommand(String name) {
		if(Objects.nonNull(getSubCommand(name))) {
			return true;
		}
		return false;
	}

	public final void setModule(Module module) {
		this.module = module;
	}
	public final Module getModule() {
		return module;
	}
	public final ModuleLogger getLogger() {
		return module.getLogger();
	}

	public void sendInfoMessage(String message) {
		getLogger().sendModule(ModuleLoggerType.INFO, message);
	}
	public void sendWarningMessage(String message) {
		getLogger().sendModule(ModuleLoggerType.WARNING, message);
	}
	public void sendErrorMessage(String message) {
		getLogger().sendModule(ModuleLoggerType.ERROR, message);
	}
	public void sendMessage(String message) {
		getLogger().sendModule(ModuleLoggerType.NOTHING, message);
	}

}