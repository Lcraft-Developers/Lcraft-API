package de.lcraft.api.minecraft.spigot.module.manager.loaders;

import de.lcraft.api.java_utils.CodeHelper;
import de.lcraft.api.minecraft.spigot.module.manager.Module;
import de.lcraft.api.minecraft.spigot.module.manager.ModuleDescriptionFileManager;
import de.lcraft.api.minecraft.spigot.module.manager.ModuleManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ModuleFileLoader {

    private final ModuleManager moduleManager;
    private final ArrayList<Module> modules;

    public ModuleFileLoader(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
        modules = new ArrayList<>();
    }

    public final void loadModules(JavaPlugin plugin) {
        File dir = new File("lmodules/");
        if(!dir.exists()) dir.mkdir();
        List<File> files = new CodeHelper().getAllFilesFromADirectory("lmodules/");
        List<File> goodFiles = new ArrayList<>();
        if(Objects.nonNull(files)) {
            for(File file : files) {
                if(file.getName().endsWith(".jar")) {
                    ModuleDescriptionFileManager descriptionFile = new ModuleDescriptionFileManager(file);
                    descriptionFile.load();
                    if(descriptionFile.hasEnoughInformation()) {
                        goodFiles.add(file);
                    }
                }
                continue;
            }
        }
        queuedModule(goodFiles, plugin);
    }
    public final void queuedModule(List<File> files, JavaPlugin plugin) {
        HashMap<ModuleDescriptionFileManager, Boolean> newModules = new HashMap<>();

        // Add Module to ArrayList
        for(File file : files) {
            ModuleDescriptionFileManager moduleDescriptionFileManager = new ModuleDescriptionFileManager(file);
            moduleDescriptionFileManager.load();
            newModules.put(moduleDescriptionFileManager, false);

            continue;
        }

        // Look if Module it has no requiements
        for(ModuleDescriptionFileManager m : newModules.keySet()) {
            boolean hasNoRequriement = false;
            if(m.getRequiredStringModules().length != 0) {
                for(String c : m.getRequiredStringModules()) {
                    if((c.equalsIgnoreCase("LcraftAPI")  || c.equalsIgnoreCase("Lcraft-API") || c.equalsIgnoreCase("Lcraft Permissions API") || c.equalsIgnoreCase("Lcraft Languages API")) && m.getRequiredStringModules().length == 1) {
                        hasNoRequriement = true;
                    }
                    continue;
                }
            } else {
                hasNoRequriement = true;
            }
            if(hasNoRequriement) {
                newModules.put(m, true);
                modules.add(getModule(m.getFile(), plugin, m));
            }
            continue;
        }

        // Load Requiered
        boolean allHasBeenLoaded = false;
        while(!allHasBeenLoaded) {
            for(ModuleDescriptionFileManager m : newModules.keySet()) {
                boolean isLoaded = newModules.get(m);
                if(!isLoaded) {
                    boolean allRequiredModulesHasBeenLoaded = true;
                    for(String allModules : m.getRequiredStringModules()) {
                        for(ModuleDescriptionFileManager c : newModules.keySet()) {
                            boolean isLoadedc = newModules.get(c);
                            if(allModules.equalsIgnoreCase(c.getName())) {
                                if(!isLoadedc) allRequiredModulesHasBeenLoaded = false;
                            }
                        }
                        continue;
                    }

                    if(allRequiredModulesHasBeenLoaded) {
                        newModules.put(m, true);
                        modules.add(getModule(m.getFile(), plugin, m));
                    }
                }
                continue;
            }


            boolean hasBeenLoaded = true;
            for(ModuleDescriptionFileManager m : newModules.keySet()) {
                if(!newModules.get(m)) hasBeenLoaded = false;
            }
            if(hasBeenLoaded) {
                allHasBeenLoaded = true;
            }
        }
    }
    public final Module getModule(File file, JavaPlugin plugin, ModuleDescriptionFileManager descriptionFile) {
        try {
            URLClassLoader moduleClassLoader = new ModuleClassLoader(descriptionFile.getFile());
            Class<?> main = moduleClassLoader.loadClass(descriptionFile.getSpigot_main());
            Module module = (Module) main.newInstance();

            module.setPlugin(plugin);
            module.setFile(file);
            module.load(moduleManager);
            loadModule(module);
            module.enableModule();

            return module;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    public final void loadModule(Module module) {
        module.getModuleDescriptionFile().load();
        module.getModuleDescriptionFile().reloadRequiredModules(moduleManager);

        getModuleManager().getModules().add(module);
    }

    public final ModuleManager getModuleManager() {
        return moduleManager;
    }
    public ArrayList<Module> getModules() {
        return modules;
    }

}
