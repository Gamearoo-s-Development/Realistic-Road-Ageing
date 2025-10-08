package com.gamearoosdevelopment.roadageaddon;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;


import org.apache.logging.log4j.Logger;

import com.gamearoosdevelopment.roadageaddon.Commands.CommandRoadAgeNow;
import com.gamearoosdevelopment.roadageaddon.Commands.RoadAgeReloadCommand;
import com.gamearoosdevelopment.roadageaddon.Handalers.ConfigEventHandler;
import com.gamearoosdevelopment.roadageaddon.Managers.RoadAgeManager;

import com.gamearoosdevelopment.roadageaddon.CommonProxy;





@Mod(modid = ModRoadAgeing.MODID, name = ModRoadAgeing.NAME, version = ModRoadAgeing.VERSION, dependencies = "required-after:furenikusroads",  guiFactory = "com.gamearoosdevelopment.roadageaddon.gui.RoadAgeGuiFactory")
public class ModRoadAgeing
{
    public static final String MODID = "roadageaddon";
    public static final String NAME = "Road Ageing Addon";
    public static final String VERSION = "1.1.3";
    @SidedProxy(clientSide = "com.gamearoosdevelopment.roadageaddon.ClientProxy",
            serverSide = "com.gamearoosdevelopment.roadageaddon.CommonProxy")
public static CommonProxy proxy;

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        RoadAgeConfig.load(event.getModConfigurationDirectory());
        

    }
    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandRoadAgeNow());
        event.registerServerCommand(new RoadAgeReloadCommand());
        MinecraftForge.EVENT_BUS.register(new RoadAgeManager());
        MinecraftForge.EVENT_BUS.register(ConfigEventHandler.class);
      
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        proxy.registerItemRenderer(Item.getItemFromBlock(ModBlocks.road_block_pothole), 0, "roadageaddon:road_block_pothole");

        
        logger.info("[RoadAge] RoadAgeManager registered");
    }
}
