package com.gamearoosdevelopment.roadageaddon.gui;

import java.util.Set;

import com.gamearoosdevelopment.roadageaddon.RoadAgeConfig;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;

public class RoadAgeGuiFactory implements IModGuiFactory {
    @Override
    public void initialize(Minecraft minecraftInstance) {}

    @Override
    public boolean hasConfigGui() {
        return true;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return new GuiConfig(
            parentScreen,
            new ConfigElement(RoadAgeConfig.config.getCategory("General")).getChildElements(),

            "roadageaddon",
            false,
            false,
            "Road Age Addon Config"
        );
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }
}
