//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "Z:\home\aaron\Files\Minecraft Stuff\Mods\Mod Code Stuff\mcp811\conf"!

package _jx.jxLib.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIGettableLookIdle extends EntityAIBase {
   private EntityLiving idleEntity;
   private double lookX;
   private double lookZ;
   private int idleTime;

   public EntityAIGettableLookIdle(EntityLiving par1EntityLiving) {
      this.idleEntity = par1EntityLiving;
      this.setMutexBits(3);
   }

   public EntityLiving getIdleEntity() {
      return this.idleEntity;
   }

   public boolean shouldExecute() {
      return this.idleEntity.getRNG().nextFloat() < 0.02F;
   }

   public boolean continueExecuting() {
      return this.idleTime >= 0;
   }

   public void startExecuting() {
      double d0 = 6.283185307179586D * this.idleEntity.getRNG().nextDouble();
      this.lookX = Math.cos(d0);
      this.lookZ = Math.sin(d0);
      this.idleTime = 20 + this.idleEntity.getRNG().nextInt(20);
   }

   public void updateTask() {
      --this.idleTime;
      this.idleEntity.getLookHelper().setLookPosition(this.idleEntity.posX + this.lookX, this.idleEntity.posY + (double)this.idleEntity.getEyeHeight(), this.idleEntity.posZ + this.lookZ, 10.0F, (float)this.idleEntity.getVerticalFaceSpeed());
   }
}
