package com.minecraftdimensions.slashserver.command;

import java.util.concurrent.TimeUnit;

import com.minecraftdimensions.slashserver.SlashServer;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ServerCommand extends Command {
    private final String name;
    private final ServerInfo server;

    public ServerCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
        this.name = name;
        this.server = ProxyServer.getInstance().getServerInfo(name);
    }

    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer))
            return;
        
        final ProxiedPlayer proxiedPlayer = (ProxiedPlayer) sender;
        if (proxiedPlayer.getServer().getInfo().getName().equalsIgnoreCase(this.name)) {
            proxiedPlayer.sendMessage(TextComponent.fromLegacyText(SlashServer.ALREADY_ON_SERVER.replace("{name}", this.name)));
        } else {
            if (SlashServer.tasks.contains(proxiedPlayer)) {
                proxiedPlayer.sendMessage(TextComponent.fromLegacyText(SlashServer.ALREADY_TELEPORTING.replace("{name}", this.name)));
                return;
            }
            proxiedPlayer.sendMessage(TextComponent.fromLegacyText(SlashServer.TELEPORTING.replace("{name}", this.name)
                    .replace("{time}", (((Integer) SlashServer.time.get(this.name)).intValue() / 1000) + "")));
            SlashServer.tasks.add(proxiedPlayer);
            
            ProxyServer.getInstance().getScheduler().schedule(SlashServer.INSTANCE, new Runnable() {
                public void run() {
                    if (SlashServer.tasks.contains(proxiedPlayer)) {
                        if (proxiedPlayer != null)
                            proxiedPlayer.connect(ServerCommand.this.server);
                        SlashServer.tasks.remove(proxiedPlayer);
                    }
                }
            }, ((Integer) SlashServer.time.get(this.name)).intValue(), TimeUnit.MILLISECONDS);
        }
    }
}
