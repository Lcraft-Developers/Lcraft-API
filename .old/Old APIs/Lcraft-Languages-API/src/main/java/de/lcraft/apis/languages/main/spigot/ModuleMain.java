package de.lcraft.apis.languages.main.spigot;

import de.lcraft.api.plugin.utils.minecraft.spigot.manager.Module;
import de.lcraft.apis.languages.system.spigot.ModuleCommandManager;
import de.lcraft.apis.languages.system.spigot.filesystem.LanguagesManager;

import java.io.IOException;

public class ModuleMain extends Module {

    private static LanguagesManager languagesManager;

    @Override
    public void onLoad() throws IOException {
        languagesManager = new LanguagesManager(this, new ModuleCommandManager(new de.lcraft.api.plugin.utils.minecraft.spigot.module.commands.ModuleCommandManager(this)));
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public static LanguagesManager getLanguagesManager() {
        return languagesManager;
    }

}
