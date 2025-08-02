package com.gamearoosdevelopment.roadageaddon.Handalers;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import java.util.Random;

import com.gamearoosdevelopment.roadageaddon.RoadAgeConfig;

import java.util.Arrays;
import java.util.List;

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
            System.out.println("[RoadAger] Skipping: already at final stage.");
            return false;
        }

        // If transitioning to final stage (right before pothole), add chance logic
        boolean isFinalNormalStage = (index == STAGES.size() - 2);
        boolean goToPothole = false;

        if (isFinalNormalStage) {
            int roll = new Random().nextInt(100); // 0–99
            goToPothole = roll > RoadAgeConfig.potholeChancePercent;
            if (goToPothole && RoadAgeConfig.potholeChancePercent != 0) {
                index++; // force into pothole
            }
        }

        if (index < STAGES.size() - 1) {
            Block nextBlock = Block.REGISTRY.getObject(STAGES.get(index + 1));
            if (nextBlock != null) {
                IBlockState next = nextBlock.getStateFromMeta(meta);
                world.setBlockState(pos, next, 3);
                System.out.println("[RoadAger] Aged block at " + pos + " to " + nextBlock.getRegistryName() + " (meta " + meta + ")");
                return true;
            }
            System.out.println("[RoadAger] Failed to find next block for ID " + id);
        } else {
            System.out.println("[RoadAger] Skipping: already at final stage.");
        }


        System.out.println("[RoadAger] Failed to find next block for ID " + id);
        return false;
    }

}
