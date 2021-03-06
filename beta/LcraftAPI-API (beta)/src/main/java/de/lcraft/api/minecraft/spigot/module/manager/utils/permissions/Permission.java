package de.lcraft.api.minecraft.spigot.module.manager.utils.permissions;

import de.lcraft.api.java_utils.configuration.Config;

public class Permission {

	private final String permission;
	private final Config allPermissionsCfg;

	public Permission(String permission, Config allPermissionsCfg) {
		this.permission = permission;
		this.allPermissionsCfg = allPermissionsCfg;
	}
	public final Permission register() {
		if(!isExiting()) {
			set(true);
		}
		return this;
	}
	/*public final void registerToLuckPerms(boolean isLuckPermsEnabled) {
		RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
		if (Objects.nonNull(provider) && isLuckPermsEnabled) {
			LuckPerms api = provider.getProvider();
			api.getContextManager().registerCalculator(new LuckPermsCalculator(getPermission(), "*", "admin"));
		}
	}*/
	public final void set(boolean isEnabled) {
		String root = getRoot();
		getAllPermissionsCfg().set(root + ".name", getPermission());
		getAllPermissionsCfg().set(root + ".enabled", isEnabled);
	}
	public final boolean isEnabled() {
		String root = getRoot();
		if(isExiting()) {
			return getAllPermissionsCfg().getBoolean(root + ".enabled");
		} else {
			set(true);
		}
		return isEnabled();
	}
	public final boolean isExiting() {
		String root = getRoot();
		return getAllPermissionsCfg().exists(root);
	}

	public final String getPermission() {
		return permission;
	}
	public Config getAllPermissionsCfg() {
		return allPermissionsCfg;
	}
	private final String getRoot() {
		return "permissions." + getPermission().replace(".", "_");
	}

}
