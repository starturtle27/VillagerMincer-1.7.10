package com.starturtle27.villagermincer;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSuperMincer extends BlockContainer {

    @SideOnly(Side.CLIENT)
    private IIcon top;
    @SideOnly(Side.CLIENT)
    private IIcon side;
    @SideOnly(Side.CLIENT)
    private IIcon bottom;

    public BlockSuperMincer(int par1) {
        super(Material.rock);
        this.setTickRandomly(true);
        this.setBlockName("mincer");
        this.setBlockTextureName("VillagerMincer:mincer");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        this.side = par1IconRegister.registerIcon(this.getTextureName());
        this.top = par1IconRegister.registerIcon(this.getTextureName() + "_top");
        this.bottom = par1IconRegister.registerIcon(this.getTextureName() + "_bottom");
    }

    @Override
    public IIcon getIcon(int par1, int par2) {
        if (par1 == 1)
            return this.top;
        else
            return par1 == 0 ? this.bottom : this.side;
    }


    @Override
    public int tickRate(World par1World) {
        return 30;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TileEntitySuperMincer && entity.posY > y) {
            TileEntitySuperMincer mincer = (TileEntitySuperMincer) tile;
            if (mincer.getVillager() == null && entity instanceof EntityVillager) {
                entity.posX = (double) x;
                entity.posY = (double) y + 1;
                entity.posZ = (double) z;
                mincer.setVillager((EntityVillager) entity);
            }
        }

    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random rnd) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TileEntitySuperMincer) {
            TileEntitySuperMincer mincer = (TileEntitySuperMincer) tile;
            if (mincer.getVillager() != null && mincer.getRunTime() > 5 && rnd.nextInt(2) == 0) {
                double d1 = x + rnd.nextFloat();
                double d2 = y + 2.0D;
                double d3 = z + rnd.nextFloat();
                world.spawnParticle("lava", d1, d2, d3, 0.0D, 0.0D, 0.0D);
            }
        }

        double d4 = x + rnd.nextFloat();
        double d5 = y - 1.05D;
        double d6 = z + rnd.nextFloat();
        if (world.getBlock(x, y - 1, z) == this) {
            world.spawnParticle("dripLava", d4, d5, d6, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntitySuperMincer();
    }
}
