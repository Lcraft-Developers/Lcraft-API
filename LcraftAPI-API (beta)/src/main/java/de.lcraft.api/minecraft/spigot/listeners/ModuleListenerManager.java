package de.lcraft.api.minecraft.spigot.listeners;

import de.lcraft.api.minecraft.spigot.manager.Module;

public class ModuleListenerManager extends ListenerManager {

	private Module module;

	public ModuleListenerManager(Module module) {
		super(module.getPlugin());
		this.module = module;
	}
	public Module getModule() {
		return module;
	}

}