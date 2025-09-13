package com.starturtle27.villagermincer;

import java.util.ArrayList;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemMeatCan extends ItemFood {

    private ArrayList<PotionEffect> potionEffect = new ArrayList();

    public ItemMeatCan(int par1, int par2, boolean par3) {
        super(par1, par2, par3);
        this.setMaxStackSize(1);
        this.setUnlocalizedName("meat");
        this.setTextureName("VillagerMincer:meat");
        this.setCreativeTab(CreativeTabs.tabFood);
    }

    public ItemMeatCan(int par1, int par2, float par3, boolean par4, String name) {
        super(6, 0.1F, true);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabFood);
        this.setUnlocalizedName(name);
        this.setTextureName("VillagerMincer:" + name);

    }

    public Item addPotionEffect(PotionEffect... effect) {
        for (PotionEffect element : effect) {
            this.potionEffect.add(element);
        }

        return this;
    }

    @Override
    public ItemStack onEaten(ItemStack item, World world, EntityPlayer player) {
        super.onEaten(item, world, player);
        if (item.getItem() == VillagerMincer.superEmeraldCan) {
            world.createExplosion((Entity) null, player.posX, player.posY, player.posZ, 10.0F, true);
        }

        if (this.potionEffect != null) {
            for (PotionEffect element : this.potionEffect) {
                player.addPotionEffect(element);
            }
        }

        return item.getItem() == VillagerMincer.strangeMeat ? item : new ItemStack(VillagerMincer.emptyCan);
    }
}
