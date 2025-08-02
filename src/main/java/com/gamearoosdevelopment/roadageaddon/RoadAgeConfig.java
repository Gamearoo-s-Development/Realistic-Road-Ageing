package com.gamearoosdevelopment.roadageaddon;

import java.io.File;
import net.minecraftforge.common.config.Configuration;

public class RoadAgeConfig {
    public static Configuration config;

    public static double irlIntervalMinutes;

    public static void load(File configDir) {
        File configFile = new File(configDir, "roadage.cfg");
        config = new Configuration(configFile);

        irlIntervalMinutes = config.get("General", "IntervalMinutes", 60.0, "How often to run road aging (in real-world minutes)").getDouble();

        if (config.hasChanged()) config.save();
    }

}
