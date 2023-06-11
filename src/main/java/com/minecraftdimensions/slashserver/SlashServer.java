package com.minecraftdimensions.slashserver;

import java.util.ArrayList;
import java.util.HashMap;

import com.minecraftdimensions.slashserver.command.ServerCommand;
import com.minecraftdimensions.slashserver.command.SlashServerReloadCommand;
import com.minecraftdimensions.slashserver.util.Config;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public class SlashServer extends Plugin {
    ProxyServer proxy;

    public static Config config;

    public static HashMap<String, Integer> time = new HashMap<>();

    public static ArrayList<ProxiedPlayer> tasks = new ArrayList<>();

    public static SlashServer INSTANCE;

    public static String ALREADY_ON_SERVER;

    public static String TELEPORTING;

    public static String ALREADY_TELEPORTING;

    public void onEnable() {
        INSTANCE = this;
        this.proxy = ProxyServer.getInstance();
        registerCommands();
        setupConfig();
    }

    private void setupConfig() {
        config = new Config(getDataFolder(), "config.yml");
        ALREADY_ON_SERVER = color(config.getString("ALREADY_ON_SERVER", "&cYou are already on that server!"));
        TELEPORTING = color(config.getString("TELEPORTING", "&2Teleporting your to the server {name}"));
        ALREADY_TELEPORTING = color(config.getString("ALREADY_TELEPORTING", "&cAlready teleporting you to a server"));

        for (String data : this.proxy.getServers().keySet())
            time.put(data, Integer.valueOf(config.getInt(data, 0)));
    }

    public static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    private void registerCommands() {
        for (String data : this.proxy.getServers().keySet()) {
            ProxyServer.getInstance().getPluginManager()
                .registerCommand(this, new ServerCommand(data, "slashserver." + data, new String[0]));
        }
        ProxyServer.getInstance().getPluginManager()
            .registerCommand(this, new SlashServerReloadCommand("reloadss", "slashserver.reload", new String[] { "reloadslashserver", "slashserverreload", "ssreload" }));
    }
}
