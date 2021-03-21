package io.sandbox.minecarts;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import io.sandbox.helpers.OutputHelper;

public class Main extends JavaPlugin {
	Server server;
	OutputHelper output;

    @Override
    public void onEnable() {
    	server = this.getServer();
		output = new OutputHelper(server);
		
		output.consoleInfo("Sandbox.Minecarts is loading its config...");
		this.loadConfig();
		
		server.getPluginManager().registerEvents(new EventListener(output), this);

        output.consoleSuccess("Sandbox.Minecarts has been initiated!");
    }

    @Override
    public void onDisable() {
    	output.consoleError("Sandbox.Minecarts has been disabled!");
    }
    
    private void loadConfig() {
    	
    }
}
