package com.baumkrieger69.pollinatorplus;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityEnterBlockEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "rawtypes"})
public class PollinatorPlusPlugin extends JavaPlugin implements Listener, CommandExecutor, TabCompleter {

    private boolean debug;
    private boolean handleEntityChangeBlock;
    private boolean handleCreatureSpawn;
    private boolean handleEntityEnterBlock;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("pollinatorplus").setExecutor(this);
        getCommand("pollinatorplus").setTabCompleter(this);
        logInfo("PollinatorPlus enabled");
    }

    @Override
    public void onDisable() {
        logInfo("PollinatorPlus disabled");
    }

    public void loadConfig() {
        debug = getConfig().getBoolean("debug", false);
        handleEntityChangeBlock = getConfig().getBoolean("handle-entity-change-block", true);
        handleCreatureSpawn = getConfig().getBoolean("handle-creature-spawn", true);
        handleEntityEnterBlock = getConfig().getBoolean("handle-entity-enter-block", true);
    }

    public void logInfo(String msg) {
        getLogger().info(msg);
    }

    private void logDebug(String msg) {
        if (debug) {
            getLogger().info(msg);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("pollinatorplus.reload")) {
            sender.sendMessage("§cDu hast keine Berechtigung für diesen Befehl.");
            return true;
        }

        reloadConfig();
        loadConfig();
        sender.sendMessage("§aPollinatorPlus Config erfolgreich neu geladen!");
        logInfo("Config reloaded by " + sender.getName());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (!handleEntityChangeBlock) return;

        if (!event.isCancelled()) {
            return;
        }

        EntityType type = event.getEntity().getType();
        String name = type.name();
        if (!("TURTLE".equals(name) || "FROG".equals(name) || "BEE".equals(name))) {
            return;
        }

        Location blockLoc = event.getBlock().getLocation();

        if ("FROG".equals(name)) {
            logDebug("[DEBUG FROG EntityChangeBlock] Block: " + event.getBlock().getType().name());
        }

        event.setCancelled(false);
        logDebug("Un-cancelled " + name + " EntityChangeBlock");
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (!handleCreatureSpawn) return;

        if (!event.isCancelled()) {
            return;
        }

        String type = event.getEntityType().name();
        String reason = event.getSpawnReason().name();

        if ("FROG".equals(type)) {
            logDebug("[DEBUG FROG CreatureSpawn] reason: " + reason);
        }

        if ("BEE".equals(type) && "BEEHIVE".equals(reason)) {
            event.setCancelled(false);
            logDebug("Un-cancelled BEE CreatureSpawn (BEEHIVE)");
            return;
        }

        if ("FROG".equals(type) || "TURTLE".equals(type)) {
            event.setCancelled(false);
            logDebug("Un-cancelled " + type + " CreatureSpawn");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onEntityEnterBlock(EntityEnterBlockEvent event) {
        if (!handleEntityEnterBlock) return;

        String type = event.getEntity().getType().name();
        if (!"BEE".equals(type)) {
            return;
        }

        if (!event.isCancelled()) {
            return;
        }

        event.setCancelled(false);
        logDebug("Un-cancelled BEE EntityEnterBlock");
    }
}