package com.starturtle27.villagermincer;

import java.util.ArrayList;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemEmptyCan extends Item {

    public ItemEmptyCan() {
        this.setMaxStackSize(1);
        this.setUnlocalizedName("emptyCan");
        this.setTextureName("VillagerMincer:emptyCan");
        this.setCreativeTab(CreativeTabs.tabFood);
    }

    public ItemEmptyCan(String name) {
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabFood);
        this.setUnlocalizedName(name);
        this.setTextureName("VillagerMincer:" + name);

    }

       // return item.getItem() == VillagerMincer.strangeMeat ? item : new ItemStack(VillagerMincer.emptyCan);
    }
