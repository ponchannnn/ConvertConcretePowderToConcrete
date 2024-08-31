package com.ponchannn.converteconcretepowdertoconcrete;

import org.bukkit.plugin.java.JavaPlugin;

public final class ConverteConcretePowderToConcrete extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        ConverteConcretePowderToConcrete converteConcretePowderToConcrete = this;
        getServer().getPluginManager().registerEvents(new Listeners(converteConcretePowderToConcrete), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
