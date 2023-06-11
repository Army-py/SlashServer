package com.minecraftdimensions.slashserver.command;

import java.io.File;

import com.minecraftdimensions.slashserver.SlashServer;
import com.minecraftdimensions.slashserver.util.Config;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class SlashServerReloadCommand extends Command {
    String configpath = File.separator + "plugins" + File.separator + "SlashServer" + File.separator + "config.yml";

    public SlashServerReloadCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    public void execute(CommandSender sender, String[] args) {
        SlashServer.time.clear();
        SlashServer.config = new Config(SlashServer.INSTANCE.getDataFolder(), "config.yml");
        for (String data : ProxyServer.getInstance().getServers().keySet())
            SlashServer.time.put(data, Integer.valueOf(SlashServer.config.getInt(data, 0)));
        SlashServer.ALREADY_ON_SERVER = SlashServer
                .color(SlashServer.config.getString("ALREADY_ON_SERVER", "&cYou are already on that server!"));
        SlashServer.TELEPORTING = SlashServer
                .color(SlashServer.config.getString("TELEPORTING", "&2Teleporting your to the server {name}"));
        SlashServer.ALREADY_TELEPORTING = SlashServer
                .color(SlashServer.config.getString("ALREADY_TELEPORTING", "&cAlready teleporting you to a server"));
        sender.sendMessage(TextComponent.fromLegacyText("Config reloaded"));
    }
}
