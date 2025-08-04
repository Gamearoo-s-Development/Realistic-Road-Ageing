package com.gamearoosdevelopment.roadageaddon.Handalers;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.gamearoosdevelopment.roadageaddon.RoadAgeConfig;

public class RoadAger {
    private static final List<ResourceLocation> STAGES = Arrays.asList(
        new ResourceLocation("furenikusroads", "road_block_dark"),
        new ResourceLocation("furenikusroads", "road_block_fine"),
        new ResourceLocation("furenikusroads", "road_block_standard"),
        new ResourceLocation("furenikusroads", "road_block_light"),
        new ResourceLocation("roadageaddon", "road_block_pothole")
    );

    public static boolean ageBlock(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        int meta = block.getMetaFromState(state);

        ResourceLocation id = Block.REGISTRY.getNameForObject(block);
        System.out.println("[RoadAger] Checking block at " + pos + ": " + id + " (meta: " + meta + ")");

        int index = STAGES.indexOf(id);
        if (index == -1) {
            System.out.println("[RoadAger] Skipping: not a tracked block.");
            return false;
        }

        if (index == STAGES.size() - 1) {
            System.out.println("[RoadAger] Skipping: already at pothole stage.");
            return false;
        }

        boolean isAtLight = (index == STAGES.size() - 2); // road_block_light
        if (isAtLight) {
            if (RoadAgeConfig.potholeChancePercent > 0) {
                int roll = new Random().nextInt(100); // 0–99
                if (roll < RoadAgeConfig.potholeChancePercent) {
                    Block potholeBlock = Block.REGISTRY.getObject(STAGES.get(index + 1));
                    if (potholeBlock != null) {
                        int newMeta = (meta > 1) ? meta - 1 : meta;
                        IBlockState newState = potholeBlock.getStateFromMeta(newMeta);
                        world.setBlockState(pos, newState, 3);
                        System.out.println("[RoadAger] Pothole placed at " + pos + " (meta: " + newMeta + ")");
                        return true;
                    }
                }
            }

            System.out.println("[RoadAger] Skipping: light is final stage and pothole not selected.");
            return false;
        }

        // Proceed with normal aging
        Block nextBlock = Block.REGISTRY.getObject(STAGES.get(index + 1));
        if (nextBlock != null) {
            IBlockState next = nextBlock.getStateFromMeta(meta);
            world.setBlockState(pos, next, 3);
            System.out.println("[RoadAger] Aged block at " + pos + " → " + nextBlock.getRegistryName() + " (meta: " + meta + ")");
            return true;
        }

        System.out.println("[RoadAger] Failed to find next block for ID " + id);
        return false;
    }
}
