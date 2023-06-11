package com.minecraftdimensions.slashserver.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.minecraftdimensions.slashserver.SlashServer;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Config {
    private Configuration config;

    private final File dataFolder;
    private final String fileName;

    public Config(File dataFolder, String fileName) {
        this.dataFolder = dataFolder;
        this.fileName = fileName;
    }

    public void load() {
        createFile();
        try {
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class)
                    .load(new File(dataFolder, fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        createFile();
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.config,
                    new File(dataFolder, fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createFile(){
        if (!dataFolder.exists()) {
            SlashServer.INSTANCE.getLogger().info("Created config folder: " + dataFolder.mkdir());
        }

        File configFile = new File(dataFolder, fileName);

        if (!configFile.exists()) {
            try (FileOutputStream outputStream = new FileOutputStream(configFile)) {
                InputStream in = SlashServer.INSTANCE.getResourceAsStream(fileName); // This file must exist in the jar resources folder
                in.transferTo(outputStream); 
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getString(String key, String def) {
        if (this.config.contains(key)) return this.config.getString(key); 
        this.config.set(key, def); 
        return def;
    }

    public int getInt(String key, int def) {
        if (this.config.contains(key))
            return this.config.getInt(key);
        this.config.set(key, Integer.valueOf(def));
        save();
        return def;
    }
}
