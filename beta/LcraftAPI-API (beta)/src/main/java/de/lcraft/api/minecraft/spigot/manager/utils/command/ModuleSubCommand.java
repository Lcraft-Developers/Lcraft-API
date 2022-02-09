package de.lcraft.api.minecraft.spigot.manager.utils.command;

import de.lcraft.api.minecraft.spigot.manager.Module;
import de.lcraft.api.minecraft.spigot.manager.utils.language.StandardMessages;

public abstract class ModuleSubCommand extends ModuleCommand {

	public ModuleSubCommand(StandardMessages standardMessages, String subLabel, String desc, Module m, boolean splitting) {
		super(standardMessages, subLabel, desc, m, splitting);
	}
	public ModuleSubCommand(String subLabel, String desc, Module m, boolean splitting) {
		super(subLabel, desc, m, splitting);
	}

}
