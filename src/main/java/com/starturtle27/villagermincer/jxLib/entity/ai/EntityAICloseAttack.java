//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "Z:\home\aaron\Files\Minecraft Stuff\Mods\Mod Code Stuff\mcp811\conf"!

package _jx.jxLib.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAICloseAttack extends EntityAIBase {
   World worldObj;
   IShootingEntity attacker;
   EntityCreature attackerLiving;
   int attackTick;
   double speedTowardsTarget;
   boolean longMemory;
   PathEntity entityPathEntity;
   Class classTarget;
   private int field_75445_i;

   public EntityAICloseAttack(IShootingEntity par1EntityCreature, Class par2Class, double par3, boolean par5) {
      this(par1EntityCreature, par3, par5);
      this.classTarget = par2Class;
   }

   public EntityAICloseAttack(IShootingEntity par1EntityCreature, double par2, boolean par4) {
      this.attacker = par1EntityCreature;
      this.attackerLiving = (EntityCreature)par1EntityCreature;
      this.worldObj = this.attackerLiving.worldObj;
      this.speedTowardsTarget = par2;
      this.longMemory = par4;
      this.setMutexBits(3);
   }

   public boolean shouldExecute() {
      EntityLivingBase entitylivingbase = this.attackerLiving.getAttackTarget();
      if (entitylivingbase == null) {
         return false;
      } else if (!entitylivingbase.isEntityAlive()) {
         return false;
      } else if (this.classTarget != null && !this.classTarget.isAssignableFrom(entitylivingbase.getClass())) {
         return false;
      } else if (!this.attacker.isShootingMode()) {
         this.entityPathEntity = this.attackerLiving.getNavigator().getPathToEntityLiving(entitylivingbase);
         return this.entityPathEntity != null;
      } else {
         return false;
      }
   }

   public boolean continueExecuting() {
      EntityLivingBase entitylivingbase = this.attackerLiving.getAttackTarget();
      return entitylivingbase == null ? false : (!entitylivingbase.isEntityAlive() ? false : (!this.longMemory ? !this.attackerLiving.getNavigator().noPath() : this.attackerLiving.func_110176_b(MathHelper.floor_double(entitylivingbase.posX), MathHelper.floor_double(entitylivingbase.posY), MathHelper.floor_double(entitylivingbase.posZ))));
   }

   public void startExecuting() {
      this.attackerLiving.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
      this.field_75445_i = 0;
   }

   public void resetTask() {
      this.attackerLiving.getNavigator().clearPathEntity();
   }

   public void updateTask() {
      EntityLivingBase entitylivingbase = this.attackerLiving.getAttackTarget();
      this.attackerLiving.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
      if ((this.longMemory || this.attackerLiving.getEntitySenses().canSee(entitylivingbase)) && --this.field_75445_i <= 0) {
         this.field_75445_i = 4 + this.attackerLiving.getRNG().nextInt(7);
         this.attackerLiving.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.speedTowardsTarget);
      }

      this.attackTick = Math.max(this.attackTick - 1, 0);
      double d0 = (double)(this.attackerLiving.width * 2.0F * this.attackerLiving.width * 2.0F + entitylivingbase.width);
      if (this.attackerLiving.getDistanceSq(entitylivingbase.posX, entitylivingbase.boundingBox.minY, entitylivingbase.posZ) <= d0 && this.attackTick <= 0) {
         this.attackTick = 20;
         if (this.attackerLiving.getHeldItem() != null) {
            this.attackerLiving.swingItem();
         }

         this.attackerLiving.attackEntityAsMob(entitylivingbase);
      }

   }
}
