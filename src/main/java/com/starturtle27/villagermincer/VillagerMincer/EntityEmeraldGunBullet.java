//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "Z:\home\aaron\Files\Minecraft Stuff\Mods\Mod Code Stuff\mcp811\conf"!

package _jx.VillagerMincer;

import _jx.jxLib.projectile.EntityProjectileBase;
import _jx.jxLib.world.SafeExplosion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityEmeraldGunBullet extends EntityProjectileBase {
   public EntityEmeraldGunBullet(World par1World) {
      super(par1World);
   }

   public EntityEmeraldGunBullet(World par1World, EntityLivingBase user, float par3) {
      super(par1World, user, par3);
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.inGround) {
         this.setDead();
      }

   }

   public void onHit(Entity target) {
      if (target instanceof EntityVillager) {
         target.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this.shootingEntity), 100.0F);
         this.worldObj.createExplosion(this.shootingEntity, this.posX, this.posY, this.posZ, 1.5F, false);
         this.itemDrop();
      }

      this.setDead();
   }

   public void makeExplode(double x, double y, double z, float size) {
      SafeExplosion SafeExplosion = new SafeExplosion(this.worldObj, this.shootingEntity, x, y, z, size, true, true);
      SafeExplosion.isFlaming = false;
      SafeExplosion.doExplosionA();
      SafeExplosion.doExplosionB(true);
   }

   public void itemDrop() {
      if (!this.worldObj.isRemote) {
         for(int i = 0; i < 20; ++i) {
            EntityItem item = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(VillagerMincer.strangeMeat));
            EntityItem em = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Item.emerald));
            item.motionX = item.motionX * 5.0D + this.rand.nextGaussian();
            item.motionY = item.motionY * 5.0D + this.rand.nextGaussian();
            item.motionZ = item.motionZ * 5.0D + this.rand.nextGaussian();
            this.worldObj.spawnEntityInWorld(item);
            if (this.rand.nextInt(5) == 0) {
               this.worldObj.spawnEntityInWorld(em);
            }
         }
      }

   }
}
