/*
 * Copyright 2015 arne van der Lei
 */
package me.houdhakker2.testplugin;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author arne
 */
public class PluginBukkit extends JavaPlugin {
    
    @Override
    public void onEnable() {
        System.out.print("hallo");

        PluginDescriptionFile pdfFile = getDiscription();
    }

}
