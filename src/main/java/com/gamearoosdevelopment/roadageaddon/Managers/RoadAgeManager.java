package com.gamearoosdevelopment.roadageaddon.Managers;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

import com.gamearoosdevelopment.roadageaddon.RoadAgeConfig;
import com.gamearoosdevelopment.roadageaddon.Handalers.RoadAger;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class RoadAgeManager {

    private static final int TICKS_PER_DAY = 24000;
    private static final int DEFAULT_DAY_INTERVAL = 5; // Default fallback
    private static final long DEFAULT_INTERVAL_MS = 10 * 1000L; // every 10 seconds


    private static int tickCounter = 0;

    
    
    public static boolean ageAllLoadedRoadBlocks(World world) {
        if (world.isRemote || !(world instanceof WorldServer)) return false;

        int radius = 5; // Chunks around each player
        int blocksChecked = 0;

        for (EntityPlayer player : world.playerEntities) {
            int cx = (int) player.posX >> 4;
            int cz = (int) player.posZ >> 4;

            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    Chunk chunk = world.getChunkFromChunkCoords(cx + dx, cz + dz);

                    BlockPos min = new BlockPos(chunk.x << 4, 0, chunk.z << 4);
                    BlockPos max = new BlockPos((chunk.x << 4) + 15, 5, (chunk.z << 4) + 15);


                    for (BlockPos pos : BlockPos.getAllInBoxMutable(min, max)) {
                    	IBlockState state = world.getBlockState(pos);
                    	if (state.getBlock() == Blocks.AIR) continue;
                    	if (!state.getBlock().getRegistryName().getResourcePath().startsWith("road_block")) continue;

                        RoadAger.ageBlock(world, pos);
                        blocksChecked++;
                    }
                }
            }
        }

        System.out.println("[RoadAge] Player-based sweep complete. Blocks checked: " + blocksChecked);
        return true;
    }
    private static long lastRun = System.currentTimeMillis();
    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.world.isRemote) return;
        if(RoadAgeConfig.irlIntervalMinutes == 0 || RoadAgeConfig.irlIntervalMinutes == 0.0) return;
        double intervalMs = RoadAgeConfig.irlIntervalMinutes * 60L * 1000L;
        long now = System.currentTimeMillis();
        if (now - lastRun < intervalMs) return; // Not time yet

        lastRun = now;
        World world = event.world;

        int radius = 5; // chunks
        int blocksChecked = 0;

        for (EntityPlayer player : world.playerEntities) {
            int cx = (int) player.posX >> 4;
            int cz = (int) player.posZ >> 4;

            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos min = new BlockPos((cx + dx) << 4, 0, (cz + dz) << 4);
                    BlockPos max = new BlockPos((cx + dx) << 4 | 15, 5, (cz + dz) << 4 | 15);

                    for (BlockPos pos : BlockPos.getAllInBoxMutable(min, max)) {
                        IBlockState state = world.getBlockState(pos);
                        if (state.getBlock() == Blocks.AIR) continue;
                        if (state.getBlock().getRegistryName() == null) continue;
                        String reg = state.getBlock().getRegistryName().getResourcePath();
                        if (!reg.startsWith("road_block")) continue;

                        RoadAger.ageBlock(world, pos);
                        blocksChecked++;
                    }
                }
            }
        }

        System.out.println("[RoadAge] IRL-based player sweep complete. Blocks aged: " + blocksChecked);
    }
}

 
