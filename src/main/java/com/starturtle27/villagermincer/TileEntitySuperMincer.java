package com.starturtle27.villagermincer;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;

public class TileEntitySuperMincer extends TileEntity {

    Random rnd = new Random();
    private EntityVillager villager = null;
    private int runTime;
    private int damageCount;

    public void setVillager(EntityVillager entity) {
        this.villager = entity;
    }

    public EntityVillager getVillager() {
        return this.villager;
    }

    public int getRunTime() {
        return this.runTime;
    }

    public void setRunTime(int time) {
        this.runTime = time;
    }

    public void updateEntity() {
        super.updateEntity();
        if (this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord) == VillagerMincer.superMincer) {
            if (this.getVillager() != null) {
                ++this.runTime;
                if (this.getVillager().deathTime > 0) {
                    this.villager.setDead();
                    this.villager = null;
                }
            } else {
                this.villager = this.getOnVillagerWalking();
                this.runTime = 0;
                this.damageCount = 0;
            }

            if (this.runTime > 20) {
                this.startMince();
            }
        } else {
            this.villager = null;
            this.runTime = 0;
            this.damageCount = 0;
        }

    }

    public EntityVillager getOnVillagerWalking() {
        AxisAlignedBB aa = AxisAlignedBB
            .getBoundingBox(
                (double) this.xCoord,
                (double) this.yCoord,
                (double) this.zCoord,
                (double) this.xCoord,
                (double) (this.yCoord + 1),
                (double) this.zCoord)
            .expand(1.5D, 1.0D, 1.5D);
        List list = this.worldObj.getEntitiesWithinAABB(EntityVillager.class, aa);
        Iterator i = list.iterator();

        Entity entity;
        do {
            if (!i.hasNext()) {
                return null;
            }

            entity = (Entity) i.next();
        } while (!(entity instanceof EntityVillager));

        return (EntityVillager) entity;
    }

    public void startMince() {
        ++this.damageCount;
        if (this.damageCount == 5 && this.villager != null) {
            EntityItem item = new EntityItem(
                this.worldObj,
                (double) this.xCoord,
                (double) this.yCoord - 1.5D,
                (double) this.zCoord,
                new ItemStack(VillagerMincer.strangeMeat));
            EntityItem em = new EntityItem(
                this.worldObj,
                (double) this.xCoord,
                (double) this.yCoord - 1.5D,
                (double) this.zCoord,
                new ItemStack(Items.emerald));
            if (!this.worldObj.isRemote) {
                this.worldObj.spawnEntityInWorld(item);
                if (this.rnd.nextInt(3) == 0) {
                    this.worldObj.spawnEntityInWorld(em);
                }
            }

            this.villager.attackEntityFrom(DamageSource.cactus, 2.0F);
            this.damageCount = 0;
        }

    }
}
