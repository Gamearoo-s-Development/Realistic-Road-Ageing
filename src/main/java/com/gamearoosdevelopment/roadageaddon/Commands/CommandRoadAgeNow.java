package com.gamearoosdevelopment.roadageaddon.Commands;

import com.gamearoosdevelopment.roadageaddon.Managers.RoadAgeManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandRoadAgeNow extends CommandBase {

    @Override
    public String getName() {
        return "roadage_now";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/roadage_now now";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("now")) {
            boolean success = RoadAgeManager.ageAllLoadedRoadBlocks(sender.getEntityWorld());
            sender.sendMessage(new TextComponentString("[RoadAge] Manual aging triggered."));
        } else {
            sender.sendMessage(new TextComponentString("[RoadAge] Usage: /roadage_now now"));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2; // OP only
    }
}
