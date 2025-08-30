//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "Z:\home\aaron\Files\Minecraft Stuff\Mods\Mod Code Stuff\mcp811\conf"!

package _jx.jxLib.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAIFollow extends EntityAIBase {
   private IFollowingEntity theFollower;
   private EntityLiving follower;
   private EntityLiving theOwner;
   World theWorld;
   private double field_75336_f;
   private PathNavigate petPathfinder;
   private int field_75343_h;
   float maxDist;
   float minDist;
   private boolean field_75344_i;

   public EntityAIFollow(IFollowingEntity follower, double par2, float par4, float par5) {
      this.theFollower = follower;
      this.follower = (EntityLiving)this.theFollower;
      this.theWorld = this.follower.worldObj;
      this.field_75336_f = par2;
      this.petPathfinder = this.follower.getNavigator();
      this.minDist = par4;
      this.maxDist = par5;
      this.setMutexBits(3);
   }

   public boolean shouldExecute() {
      EntityLiving entityliving = this.theFollower.getOwner();
      if (entityliving == null) {
         return false;
      } else if (this.follower.getDistanceSqToEntity(entityliving) < (double)(this.minDist * this.minDist)) {
         return false;
      } else {
         this.theOwner = entityliving;
         return true;
      }
   }

   public boolean continueExecuting() {
      return !this.petPathfinder.noPath() && this.follower.getDistanceSqToEntity(this.theOwner) > (double)(this.maxDist * this.maxDist);
   }

   public void startExecuting() {
      this.field_75343_h = 0;
      this.field_75344_i = this.follower.getNavigator().getAvoidsWater();
      this.follower.getNavigator().setAvoidsWater(false);
   }

   public void resetTask() {
      this.theOwner = null;
      this.petPathfinder.clearPathEntity();
      this.follower.getNavigator().setAvoidsWater(this.field_75344_i);
   }

   public void updateTask() {
      this.follower.getLookHelper().setLookPositionWithEntity(this.theOwner, 10.0F, (float)this.follower.getVerticalFaceSpeed());
      if (--this.field_75343_h <= 0) {
         this.field_75343_h = 10;
         if (!this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.field_75336_f) && !this.follower.getLeashed() && this.follower.getDistanceSqToEntity(this.theOwner) >= 144.0D) {
            int i = MathHelper.floor_double(this.theOwner.posX) - 2;
            int j = MathHelper.floor_double(this.theOwner.posZ) - 2;
            int k = MathHelper.floor_double(this.theOwner.boundingBox.minY);

            for(int l = 0; l <= 4; ++l) {
               for(int i1 = 0; i1 <= 4; ++i1) {
                  if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.theWorld.doesBlockHaveSolidTopSurface(i + l, k - 1, j + i1) && !this.theWorld.isBlockNormalCube(i + l, k, j + i1) && !this.theWorld.isBlockNormalCube(i + l, k + 1, j + i1)) {
                     this.follower.setLocationAndAngles((double)((float)(i + l) + 0.5F), (double)k, (double)((float)(j + i1) + 0.5F), this.follower.rotationYaw, this.follower.rotationPitch);
                     this.petPathfinder.clearPathEntity();
                     return;
                  }
               }
            }
         }
      }

   }
}
