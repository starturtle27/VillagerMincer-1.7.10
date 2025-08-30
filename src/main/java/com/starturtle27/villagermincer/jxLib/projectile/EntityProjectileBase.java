//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "Z:\home\aaron\Files\Minecraft Stuff\Mods\Mod Code Stuff\mcp811\conf"!

package _jx.jxLib.projectile;

import cpw.mods.fml.common.registry.IThrowableEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet70GameEvent;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityProjectileBase extends Entity implements IProjectile, IThrowableEntity {
   protected int xTile = -1;
   protected int yTile = -1;
   protected int zTile = -1;
   protected int inTile = 0;
   protected int inData = 0;
   protected boolean inGround = false;
   public int canBePickedUp = 0;
   public int arrowShake = 0;
   public Entity shootingEntity;
   protected int ticksInGround;
   protected int ticksInAir = 0;
   protected double damage = 2.0D;
   protected int knockbackStrength;

   public EntityProjectileBase(World par1World) {
      super(par1World);
      this.renderDistanceWeight = 10.0D;
      this.setSize(0.5F, 0.5F);
   }

   public EntityProjectileBase(World par1World, double par2, double par4, double par6) {
      super(par1World);
      this.renderDistanceWeight = 10.0D;
      this.setSize(0.5F, 0.5F);
      this.setPosition(par2, par4, par6);
      this.yOffset = 0.0F;
   }

   public EntityProjectileBase(World par1World, EntityLivingBase user, EntityLivingBase target, float par4, float par5) {
      super(par1World);
      this.renderDistanceWeight = 10.0D;
      this.shootingEntity = user;
      if (user instanceof EntityPlayer) {
         this.canBePickedUp = 1;
      }

      this.posY = user.posY + (double)user.getEyeHeight() - 0.10000000149011612D;
      double d0 = target.posX - user.posX;
      double d1 = target.boundingBox.minY + (double)(target.height / 3.0F) - this.posY;
      double d2 = target.posZ - user.posZ;
      double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);
      if (d3 >= 1.0E-7D) {
         float f2 = (float)(Math.atan2(d2, d0) * 180.0D / 3.141592653589793D) - 90.0F;
         float f3 = (float)(-(Math.atan2(d1, d3) * 180.0D / 3.141592653589793D));
         double d4 = d0 / d3;
         double d5 = d2 / d3;
         this.setLocationAndAngles(user.posX + d4, this.posY, user.posZ + d5, f2, f3);
         this.yOffset = 0.0F;
         float f4 = (float)d3 * 0.2F;
         this.setThrowableHeading(d0, d1 + (double)f4, d2, par4, par5);
      }

   }

   public EntityProjectileBase(World par1World, EntityLivingBase user, float par3) {
      super(par1World);
      this.renderDistanceWeight = 10.0D;
      this.shootingEntity = user;
      if (user instanceof EntityPlayer) {
         this.canBePickedUp = 1;
      }

      this.setSize(0.5F, 0.5F);
      this.setLocationAndAngles(user.posX, user.posY + (double)user.getEyeHeight(), user.posZ, user.rotationYaw, user.rotationPitch);
      this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
      this.posY -= 0.10000000149011612D;
      this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
      this.setPosition(this.posX, this.posY, this.posZ);
      this.yOffset = 0.0F;
      this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F));
      this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F));
      this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * 3.1415927F));
      this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, par3 * 1.5F, 1.0F);
   }

   protected void entityInit() {
      this.dataWatcher.addObject(16, (byte)0);
   }

   public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8) {
      float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
      par1 /= (double)f2;
      par3 /= (double)f2;
      par5 /= (double)f2;
      par1 += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double)par8;
      par3 += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double)par8;
      par5 += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double)par8;
      par1 *= (double)par7;
      par3 *= (double)par7;
      par5 *= (double)par7;
      this.motionX = par1;
      this.motionY = par3;
      this.motionZ = par5;
      float f3 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
      this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / 3.141592653589793D);
      this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, (double)f3) * 180.0D / 3.141592653589793D);
      this.ticksInGround = 0;
   }

   @SideOnly(Side.CLIENT)
   public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
      this.setPosition(par1, par3, par5);
      this.setRotation(par7, par8);
   }

   @SideOnly(Side.CLIENT)
   public void setVelocity(double par1, double par3, double par5) {
      this.motionX = par1;
      this.motionY = par3;
      this.motionZ = par5;
      if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
         float f = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
         this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / 3.141592653589793D);
         this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, (double)f) * 180.0D / 3.141592653589793D);
         this.prevRotationPitch = this.rotationPitch;
         this.prevRotationYaw = this.rotationYaw;
         this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
         this.ticksInGround = 0;
      }

   }

   public static DamageSource causeSimpleArrowDamage(EntityProjectileBase entitySimpleArrow, Entity par1Entity) {
      return (new EntityDamageSourceIndirect("arrow", entitySimpleArrow, par1Entity)).setProjectile();
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
         float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
         this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592653589793D);
         this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f) * 180.0D / 3.141592653589793D);
      }

      int i = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
      if (i > 0) {
         Block.blocksList[i].setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
         AxisAlignedBB axisalignedbb = Block.blocksList[i].getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);
         if (axisalignedbb != null && axisalignedbb.isVecInside(this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ))) {
            this.inGround = true;
         }
      }

      if (this.arrowShake > 0) {
         --this.arrowShake;
      }

      if (this.inGround) {
         int j = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
         int k = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
         if (j == this.inTile && k == this.inData) {
            ++this.ticksInGround;
            if (this.ticksInGround == 1200) {
               this.setDead();
            }
         } else {
            this.inGround = false;
            this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
            this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
            this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
            this.ticksInGround = 0;
            this.ticksInAir = 0;
         }
      } else {
         ++this.ticksInAir;
         Vec3 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
         Vec3 vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
         MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3, vec31, false, true);
         vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
         vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
         if (movingobjectposition != null) {
            vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
         }

         Entity entity = null;
         List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
         double d0 = 0.0D;

         int l;
         float f1;
         for(l = 0; l < list.size(); ++l) {
            Entity entity1 = (Entity)list.get(l);
            if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5)) {
               f1 = 0.3F;
               AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand((double)f1, (double)f1, (double)f1);
               MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec3, vec31);
               if (movingobjectposition1 != null) {
                  double d1 = vec3.distanceTo(movingobjectposition1.hitVec);
                  if (d1 < d0 || d0 == 0.0D) {
                     entity = entity1;
                     d0 = d1;
                  }
               }
            }
         }

         if (entity != null) {
            movingobjectposition = new MovingObjectPosition(entity);
         }

         if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.entityHit;
            if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(entityplayer)) {
               movingobjectposition = null;
            }
         }

         float f2;
         float f3;
         if (movingobjectposition != null) {
            if (movingobjectposition.entityHit != null) {
               f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
               int i1 = MathHelper.ceiling_double_int((double)f2 * this.damage);
               if (this.getIsCritical()) {
                  i1 += this.rand.nextInt(i1 / 2 + 2);
               }

               DamageSource damagesource = null;
               if (this.shootingEntity == null) {
                  damagesource = causeSimpleArrowDamage(this, this);
               } else {
                  damagesource = causeSimpleArrowDamage(this, this.shootingEntity);
               }

               if (this.isBurning() && !(movingobjectposition.entityHit instanceof EntityEnderman)) {
                  movingobjectposition.entityHit.setFire(5);
               }

               if (movingobjectposition.entityHit.attackEntityFrom(damagesource, (float)i1)) {
                  if (movingobjectposition.entityHit instanceof EntityLiving) {
                     EntityLiving entityliving = (EntityLiving)movingobjectposition.entityHit;
                     if (!this.worldObj.isRemote) {
                        entityliving.setArrowCountInEntity(entityliving.getArrowCountInEntity() + 1);
                     }

                     if (this.knockbackStrength > 0) {
                        f3 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
                        if (f3 > 0.0F) {
                           movingobjectposition.entityHit.addVelocity(this.motionX * (double)this.knockbackStrength * 0.6000000238418579D / (double)f3, 0.1D, this.motionZ * (double)this.knockbackStrength * 0.6000000238418579D / (double)f3);
                        }
                     }

                     if (this.shootingEntity != null) {
                        EnchantmentThorns.func_92096_a(this.shootingEntity, entityliving, this.rand);
                     }

                     if (this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity && movingobjectposition.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP) {
                        ((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacket(new Packet70GameEvent(6, 0));
                     }
                  }

                  this.onHit(movingobjectposition.entityHit);
                  this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                  if (!(movingobjectposition.entityHit instanceof EntityEnderman)) {
                     this.setDead();
                  }
               } else {
                  this.motionX *= -0.10000000149011612D;
                  this.motionY *= -0.10000000149011612D;
                  this.motionZ *= -0.10000000149011612D;
                  this.rotationYaw += 180.0F;
                  this.prevRotationYaw += 180.0F;
                  this.ticksInAir = 0;
               }
            } else {
               this.xTile = movingobjectposition.blockX;
               this.yTile = movingobjectposition.blockY;
               this.zTile = movingobjectposition.blockZ;
               this.inTile = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
               this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
               this.motionX = (double)((float)(movingobjectposition.hitVec.xCoord - this.posX));
               this.motionY = (double)((float)(movingobjectposition.hitVec.yCoord - this.posY));
               this.motionZ = (double)((float)(movingobjectposition.hitVec.zCoord - this.posZ));
               f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
               this.posX -= this.motionX / (double)f2 * 0.05000000074505806D;
               this.posY -= this.motionY / (double)f2 * 0.05000000074505806D;
               this.posZ -= this.motionZ / (double)f2 * 0.05000000074505806D;
               this.playSoundOnHit();
               this.inGround = true;
               this.arrowShake = 7;
               this.setIsCritical(false);
               if (this.inTile != 0) {
                  Block.blocksList[this.inTile].onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
               }
            }
         }

         if (this.getIsCritical()) {
            for(l = 0; l < 4; ++l) {
               this.worldObj.spawnParticle("crit", this.posX + this.motionX * (double)l / 4.0D, this.posY + this.motionY * (double)l / 4.0D, this.posZ + this.motionZ * (double)l / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
            }
         }

         this.posX += this.motionX;
         this.posY += this.motionY;
         this.posZ += this.motionZ;
         f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
         this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592653589793D);

         for(this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f2) * 180.0D / 3.141592653589793D); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
         }

         while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
         }

         while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
         }

         while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
            this.prevRotationYaw += 360.0F;
         }

         this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
         this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
         float f4 = 0.99F;
         f1 = 0.05F;
         if (this.isInWater()) {
            f3 = 0.25F;
            f4 = 0.8F;
            this.spawnParticleInWater();
         }

         f3 = 0.25F;
         this.motionX *= (double)f4;
         this.motionY *= (double)f4;
         this.motionZ *= (double)f4;
         this.motionY -= (double)f1;
         this.setPosition(this.posX, this.posY, this.posZ);
         this.doBlockCollisions();
      }

   }

   protected void onHit(Entity target) {
   }

   protected void playSoundOnHit() {
      this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
   }

   protected void spawnParticleInWater() {
      float f3 = 0.25F;

      for(int j1 = 0; j1 < 4; ++j1) {
         this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f3, this.posY - this.motionY * (double)f3, this.posZ - this.motionZ * (double)f3, this.motionX, this.motionY, this.motionZ);
      }

   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      par1NBTTagCompound.setShort("xTile", (short)this.xTile);
      par1NBTTagCompound.setShort("yTile", (short)this.yTile);
      par1NBTTagCompound.setShort("zTile", (short)this.zTile);
      par1NBTTagCompound.setByte("inTile", (byte)this.inTile);
      par1NBTTagCompound.setByte("inData", (byte)this.inData);
      par1NBTTagCompound.setByte("shake", (byte)this.arrowShake);
      par1NBTTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
      par1NBTTagCompound.setByte("pickup", (byte)this.canBePickedUp);
      par1NBTTagCompound.setDouble("damage", this.damage);
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      this.xTile = par1NBTTagCompound.getShort("xTile");
      this.yTile = par1NBTTagCompound.getShort("yTile");
      this.zTile = par1NBTTagCompound.getShort("zTile");
      this.inTile = par1NBTTagCompound.getByte("inTile") & 255;
      this.inData = par1NBTTagCompound.getByte("inData") & 255;
      this.arrowShake = par1NBTTagCompound.getByte("shake") & 255;
      this.inGround = par1NBTTagCompound.getByte("inGround") == 1;
      if (par1NBTTagCompound.hasKey("damage")) {
         this.damage = par1NBTTagCompound.getDouble("damage");
      }

      if (par1NBTTagCompound.hasKey("pickup")) {
         this.canBePickedUp = par1NBTTagCompound.getByte("pickup");
      } else if (par1NBTTagCompound.hasKey("player")) {
         this.canBePickedUp = par1NBTTagCompound.getBoolean("player") ? 1 : 0;
      }

   }

   public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {
      if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0) {
         boolean flag = this.canBePickedUp == 1 || this.canBePickedUp == 2 && par1EntityPlayer.capabilities.isCreativeMode;
         if (this.canBePickedUp == 1 && !par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.arrow, 1))) {
            flag = false;
         }

         if (flag) {
            this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            par1EntityPlayer.onItemPickup(this, 1);
            this.setDead();
         }
      }

   }

   protected boolean canTriggerWalking() {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public float getShadowSize() {
      return 0.0F;
   }

   public void setDamage(double par1) {
      this.damage = par1;
   }

   public double getDamage() {
      return this.damage;
   }

   public void setKnockbackStrength(int par1) {
      this.knockbackStrength = par1;
   }

   public boolean canAttackWithItem() {
      return false;
   }

   public void setIsCritical(boolean par1) {
      byte b0 = this.dataWatcher.getWatchableObjectByte(16);
      if (par1) {
         this.dataWatcher.updateObject(16, (byte)(b0 | 1));
      } else {
         this.dataWatcher.updateObject(16, (byte)(b0 & -2));
      }

   }

   public boolean getIsCritical() {
      byte b0 = this.dataWatcher.getWatchableObjectByte(16);
      return (b0 & 1) != 0;
   }

   public Entity getThrower() {
      return null;
   }

   public void setThrower(Entity entity) {
   }
}
