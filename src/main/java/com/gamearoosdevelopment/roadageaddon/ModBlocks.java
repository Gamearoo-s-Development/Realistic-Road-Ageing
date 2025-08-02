package com.gamearoosdevelopment.roadageaddon;

import com.silvaniastudios.roads.items.FRItems;
import com.silvaniastudios.roads.items.RoadItemBase;

import com.silvaniastudios.roads.blocks.RoadBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ModBlocks {

    public static final Block road_block_pothole = new RoadBlock("road_block_pothole", Material.ROCK, FRItems.tarmac_fragment_light);
   

    // Register block
    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
    	event.getRegistry().register(road_block_pothole);
       
    }

    // Register item form of the block
    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
   
    }
}
