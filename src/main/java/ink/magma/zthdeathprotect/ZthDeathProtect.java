package ink.magma.zthdeathprotect;

import ink.magma.zthdeathprotect.listeners.NearbyProtectListener;
import ink.magma.zthdeathprotect.listeners.PermissionProtectListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZthDeathProtect extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new PermissionProtectListener(), this);
        Bukkit.getPluginManager().registerEvents(new NearbyProtectListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
