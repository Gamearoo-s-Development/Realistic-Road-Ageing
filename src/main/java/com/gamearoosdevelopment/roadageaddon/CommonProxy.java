package com.gamearoosdevelopment.roadageaddon;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

/**
 * Base proxy for RoadAge Addon.
 * Used for separating client and server side code.
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        // Register blocks, items, tile entities, etc.
    }

    public void init(FMLInitializationEvent event) {
        // Common initialization logic
    }

    public void postInit(FMLPostInitializationEvent event) {
        // Cross-mod interactions or cleanup
    }

    public void registerItemRenderer(Item item, int meta, String id) {
        // Server side does nothing here — overridden on client.
    }
}
