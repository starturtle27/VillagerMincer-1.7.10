//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "Z:\home\aaron\Files\Minecraft Stuff\Mods\Mod Code Stuff\mcp811\conf"!

package _jx.jxLib.entity;

import _jx.PumpkinAPI.item.IItemNeuronerBattery;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class EntityNeuronerGolemBase extends EntityTameable {
   protected float field_70926_e;
   protected float field_70924_f;

   public EntityNeuronerGolemBase(World par1World) {
      super(par1World);
      this.getNavigator().setAvoidsWater(true);
      this.tasks.addTask(1, new EntityAISwimming(this));
      this.tasks.addTask(2, this.aiSit);
      this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
      this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, true));
      this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
      this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
      this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(7, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
      this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
      this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
      this.setTamed(false);
      this.stepHeight = 1.0F;
   }

   public boolean isAIEnabled() {
      if ((double)this.getHealth() <= this.getMaxHealth() / 10.0D) {
         this.setMoveSpeed(0.0D);
         return false;
      } else {
         this.setMoveSpeed(0.3D);
         return true;
      }
   }

   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(18, new Float(this.getHealth()));
      this.dataWatcher.addObject(19, new Byte((byte)0));
      this.dataWatcher.addObject(20, new Byte((byte)BlockColored.getBlockFromDye(1)));
   }

   protected void updateAITick() {
      this.dataWatcher.updateObject(18, this.getHealth());
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setByte("CollarColor", (byte)this.getCollarColor());
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      if (par1NBTTagCompound.hasKey("CollarColor")) {
         this.setCollarColor(par1NBTTagCompound.getByte("CollarColor"));
      }

   }

   protected String getHurtSound() {
      return "mob.irongolem.hit";
   }

   protected String getDeathSound() {
      return "mob.irongolem.death";
   }

   protected boolean interactEventOnItem(EntityPlayer player, ItemStack itemstack) {
      return true;
   }

   public boolean interact(EntityPlayer player) {
      ItemStack itemstack = player.inventory.getCurrentItem();
      if (this.isTamed()) {
         if (itemstack != null) {
            if (this.isEnergyStackItem(itemstack)) {
               Item item = itemstack.getItem();
               if ((double)this.dataWatcher.getWatchableObjectFloat(18) < this.getMaxHealth()) {
                  if (item instanceof IItemNeuronerBattery) {
                     IItemNeuronerBattery en = (IItemNeuronerBattery)item;
                     this.heal((float)en.energyAmount());
                     en.onConsume(itemstack, player, this);
                     if (en.isNormalConsumeable()) {
                        this.consumeItem(player, itemstack);
                     }
                  } else if (item == Item.redstone) {
                     this.heal(5.0F);
                     this.consumeItem(player, itemstack);
                  }

                  if (itemstack.stackSize <= 0) {
                     player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
                  }

                  this.sendChatLog(player, "battery" + String.valueOf((double)this.getHealth() / this.getMaxHealth() * 100.0D) + "%");
                  this.worldObj.playSoundAtEntity(player, "mob.creeper.death", 1.0F, 1.0F);
               } else {
                  this.sendChatLog(player, "battery Full");
               }

               return true;
            }

            if (itemstack.itemID == Item.dyePowder.itemID) {
               int i = BlockColored.getBlockFromDye(itemstack.getItemDamage());
               if (i != this.getCollarColor()) {
                  this.setCollarColor(i);
                  if (!player.capabilities.isCreativeMode && --itemstack.stackSize <= 0) {
                     player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
                  }

                  return true;
               }
            }

            this.interactEventOnItem(player, itemstack);
         }

         if (player.getCommandSenderName().equalsIgnoreCase(this.getOwnerName()) && !this.worldObj.isRemote && !this.isEnergyStackItem(itemstack)) {
            this.aiSit.setSitting(!this.isSitting());
            this.isJumping = false;
            this.setPathToEntity((PathEntity)null);
            this.setTarget((Entity)null);
            this.setAttackTarget((EntityLivingBase)null);
         }
      } else if (itemstack != null && this.isEnergyStackItem(itemstack)) {
         if (!player.capabilities.isCreativeMode) {
            --itemstack.stackSize;
         }

         if (itemstack.stackSize <= 0) {
            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
         }

         if (!this.worldObj.isRemote) {
            if (this.rand.nextInt(3) == 0) {
               this.setTamed(true);
               this.setPathToEntity((PathEntity)null);
               this.setAttackTarget((EntityLivingBase)null);
               this.aiSit.setSitting(true);
               this.setHealth(20.0F);
               this.setOwner(player.getCommandSenderName());
               this.playTameEffect(true);
               this.worldObj.setEntityState(this, (byte)7);
            } else {
               this.playTameEffect(false);
               this.worldObj.setEntityState(this, (byte)6);
            }
         }

         return true;
      }

      return super.interact(player);
   }

   protected void consumeItem(EntityPlayer player, ItemStack itemstack) {
      if (!player.capabilities.isCreativeMode) {
         --itemstack.stackSize;
      }

   }

   public boolean isEnergyStackItem(ItemStack item) {
      if (item != null) {
         Item tag = Item.itemsList[item.itemID];
         boolean flag = tag instanceof IItemNeuronerBattery || tag == Item.redstone;
         if (flag) {
            return true;
         }
      }

      return false;
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
      this.spawnLivingParticle();
   }

   public void spawnLivingParticle() {
      for(int k = 0; k < 2; ++k) {
         this.worldObj.spawnParticle("portal", this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
      }

   }

   public void onUpdate() {
      super.onUpdate();
      this.field_70924_f = this.field_70926_e;
      if (this.canChaseToTarget()) {
         this.field_70926_e += (1.0F - this.field_70926_e) * 0.4F;
      } else {
         this.field_70926_e += (0.0F - this.field_70926_e) * 0.4F;
      }

      if (this.canChaseToTarget()) {
         this.numTicksToChaseTarget = 10;
      }

   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      if (this.isEntityInvulnerable()) {
         return false;
      } else {
         Entity entity = par1DamageSource.getEntity();
         this.aiSit.setSitting(false);
         if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow)) {
            par2 = (par2 + 1.0F) / 2.0F;
         }

         return super.attackEntityFrom(par1DamageSource, par2);
      }
   }

   protected void playTameEffect(boolean par1) {
      String s = "spell";
      if (!par1) {
         s = "smoke";
      }

      for(int i = 0; i < 7; ++i) {
         double d0 = this.rand.nextGaussian() * 0.02D;
         double d1 = this.rand.nextGaussian() * 0.02D;
         double d2 = this.rand.nextGaussian() * 0.02D;
         this.worldObj.spawnParticle(s, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d0, d1, d2);
      }

   }

   protected void fall(float f) {
   }

   protected boolean canDespawn() {
      return !this.isTamed() && this.ticksExisted > 2400;
   }

   protected float getSoundVolume() {
      return 0.4F;
   }

   protected int getDropItemId() {
      return -1;
   }

   public int getVerticalFaceSpeed() {
      return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
   }

   public void sendChatLog(EntityPlayer player, String message) {
      if (!this.worldObj.isRemote && player != null) {
         player.addChatMessage(message);
      }

   }

   public boolean canChaseToTarget() {
      return this.dataWatcher.getWatchableObjectByte(19) == 1;
   }

   public int getCollarColor() {
      return this.dataWatcher.getWatchableObjectByte(20) & 15;
   }

   public void setCollarColor(int par1) {
      this.dataWatcher.updateObject(20, (byte)(par1 & 15));
   }

   public double getMoveSpeed() {
      return this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
   }

   public void setMoveSpeed(double speed) {
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(speed);
   }

   protected double getMaxHealth() {
      return this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
   }

   public float getHealth() {
      return this.getHealth();
   }

   public EntityAgeable createChild(EntityAgeable par1EntityAgeable) {
      return null;
   }
}
