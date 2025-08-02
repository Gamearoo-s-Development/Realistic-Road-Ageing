package com.gamearoosdevelopment.roadageaddon;

import java.io.File;
import net.minecraftforge.common.config.Configuration;

public class RoadAgeConfig {
    public static Configuration config;

    public static double irlIntervalMinutes;

    public static int potholeChancePercent;

    
   

    public static void load(File configDir) {
        File configFile = new File(configDir, "roadage.cfg");
        config = new Configuration(configFile);

        irlIntervalMinutes = config.get("General", "IntervalMinutes", 60.0,
            "How often to run road aging (in real-world minutes) (0 to disable)").getDouble();
        potholeChancePercent = config.get("General", "PotholeChancePercent", 1,
        	    "Chance (0-100) that the block becomes a pothole when aging to the final stage (0 to disable)").getInt();

       

        

        if (config.hasChanged()) config.save();
    }
}
