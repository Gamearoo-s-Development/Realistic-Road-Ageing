// RoadAgeReloadCommand.java
package com.gamearoosdevelopment.roadageaddon.Commands;

import com.gamearoosdevelopment.roadageaddon.RoadAgeConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.io.File;

public class RoadAgeReloadCommand extends CommandBase {
    @Override
    public String getName() {
        return "roadage_reload";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/roadage_reload reload";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            File configDir = new File("config"); // You could also use FML paths
            RoadAgeConfig.load(configDir);
            sender.sendMessage(new TextComponentString("§a[RoadAge] Config reloaded successfully."));
        } else {
            sender.sendMessage(new TextComponentString("§cUsage: /roadage_reload reload"));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2; // OP only
    }
}
