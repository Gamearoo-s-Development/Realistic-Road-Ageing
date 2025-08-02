package com.gamearoosdevelopment.roadageaddon.Handalers;

import com.gamearoosdevelopment.roadageaddon.RoadAgeConfig;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = "roadageaddon")
public class ConfigEventHandler {

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals("roadageaddon")) {
            RoadAgeConfig.config.save();
            System.out.println("[RoadAge] Config saved by GUI.");
        }
    }
}
