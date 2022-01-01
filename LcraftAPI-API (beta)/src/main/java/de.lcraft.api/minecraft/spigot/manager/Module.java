package de.lcraft.api.minecraft.spigot.manager;

import de.lcraft.api.java_utils.connection.SpigotMc;
import de.lcraft.api.minecraft.spigot.listeners.ModuleListenerManager;
import de.lcraft.api.minecraft.spigot.manager.command.ModuleCommandManager;
import de.lcraft.api.minecraft.spigot.manager.logger.ModuleLogger;
import de.lcraft.api.minecraft.spigot.manager.logger.ModuleLoggerType;
import de.lcraft.api.minecraft.spigot.manager.util.LanguagesManager;
import de.lcraft.api.minecraft.spigot.manager.configs.ModuleConfig;
import de.lcraft.api.minecraft.spigot.manager.util.PermsManager;
import de.lcraft.api.minecraft.spigot.player.LPlayerManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;

public abstract class Module {

    private ModuleDescriptionFileManager moduleDescriptionFileManager;
    private ModuleLogger moduleLogger;
    private ModuleCommandManager moduleCommandManager;
    private LanguagesManager languagesManager;
    private JavaPlugin plugin;
    private ModuleListenerManager listenerManager;
    private File file;
    private ModuleManager manager;
    private PermsManager permsManager;
    private ModuleConfig config;
    private LPlayerManager lPlayerManager;

    public void load(ModuleManager manager) throws Exception {
        this.manager = manager;
        this.lPlayerManager = manager.getPluginMain().getLPlayerManager();

        moduleDescriptionFileManager = new ModuleDescriptionFileManager(file);
        moduleDescriptionFileManager.load();
        moduleLogger = new ModuleLogger(moduleDescriptionFileManager.getName());
        config = new ModuleConfig(getModuleDescriptionFile().getName(), "config.yml");

        permsManager = new PermsManager();
        languagesManager = new LanguagesManager();
        moduleCommandManager = new ModuleCommandManager(this);
        listenerManager = new ModuleListenerManager(this);

        enableModule();

        listenerManager.registerAllListeners();
        moduleCommandManager.reloadConfigs();

        sendUpdateMessageModule();
    }

    public abstract void onEnable() throws IOException;
    public abstract void onDisable() throws IOException;

    public void enableModule() throws IOException {
        getLogger().send(ModuleLoggerType.INFO, "The Spigot Module " + getModuleDescriptionFile().getName() + " is loaded.");
        onEnable();
    }
    public void sendUpdateMessageModule() {
        if(getModuleDescriptionFile().getUpdate_url() != null) {
            if(new SpigotMc().isUpdated(getModuleDescriptionFile().getUpdate_url(), getModuleDescriptionFile().getVersion())) {
                getLogger().send(ModuleLoggerType.INFO, "The Spigot Module " + getModuleDescriptionFile().getName() + " is up to date.");
            } else {
                getLogger().send(ModuleLoggerType.INFO, "The Spigot Module " + getModuleDescriptionFile().getName() + " is outdated.");
            }
        } else if(getModuleDescriptionFile().getSpigotmc_id() != null) {
            if(new SpigotMc().isUpdated(Integer.valueOf(getModuleDescriptionFile().getSpigotmc_id()), getModuleDescriptionFile().getVersion())) {
                getLogger().send(ModuleLoggerType.INFO, "The Spigot Module " + getModuleDescriptionFile().getName() + " is up to date.");
            } else {
                getLogger().send(ModuleLoggerType.INFO, "The Spigot Module " + getModuleDescriptionFile().getName() + " is outdated.");
            }
        }
    }
    public void disableModule() throws IOException {
        getLogger().send(ModuleLoggerType.INFO, "The Spigot Module " + getModuleDescriptionFile().getName() + " was disabled.");
        onDisable();
    }
    public ModuleLogger getLogger() {
        return moduleLogger;
    }
    public ModuleCommandManager getModuleCommandManager() {
        return moduleCommandManager;
    }
    public JavaPlugin getPlugin() {
        return plugin;
    }
    public void setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    public void setFile(File file) {
        this.file = file;
    }
    public File getFile() {
        return file;
    }
    public ModuleListenerManager getListenerManager() {
        return listenerManager;
    }
    public ModuleDescriptionFileManager getModuleDescriptionFile() {
        return moduleDescriptionFileManager;
    }
    public void setModuleDescriptionFile(ModuleDescriptionFileManager moduleDescriptionFileManager) {
        this.moduleDescriptionFileManager = moduleDescriptionFileManager;
    }
    public LanguagesManager getLanguagesManager() {
        return languagesManager;
    }
    public ModuleManager getModuleManager() {
        return manager;
    }
    public PermsManager getPermsManager() {
        return permsManager;
    }

}
