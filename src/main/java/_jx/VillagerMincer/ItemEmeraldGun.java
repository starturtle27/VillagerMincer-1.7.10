//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "Z:\home\aaron\Files\Minecraft Stuff\Mods\Mod Code Stuff\mcp811\conf"!

package _jx.VillagerMincer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemEmeraldGun extends ItemBow {
   public ItemEmeraldGun(int par1) {
      super(par1);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IconRegister par1IconRegister) {
      this.itemIcon = par1IconRegister.registerIcon(this.getIconString());
   }

   @SideOnly(Side.CLIENT)
   public Icon getItemIconForUseDuration(int par1) {
      return this.itemIcon;
   }

   public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) {
      int j = this.getMaxItemUseDuration(par1ItemStack) - par4;
      boolean flag = par3EntityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;
      if (flag || par3EntityPlayer.inventory.hasItem(Item.emerald.itemID)) {
         float f = (float)j / 20.0F;
         f = (f * f + f * 2.0F) / 3.0F;
         if ((double)f < 0.1D) {
            return;
         }

         if (f > 1.0F) {
            f = 1.0F;
         }

         EntityEmeraldGunBullet entity = new EntityEmeraldGunBullet(par2World, par3EntityPlayer, 5.0F);
         if (f == 1.0F) {
            entity.setIsCritical(true);
         }

         int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);
         if (k > 0) {
            entity.setDamage(entity.getDamage() + (double)k * 0.5D + 0.5D);
         }

         int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);
         if (l > 0) {
            entity.setKnockbackStrength(l);
         }

         if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0) {
            entity.setFire(100);
         }

         par1ItemStack.damageItem(1, par3EntityPlayer);
         par2World.playSoundAtEntity(par3EntityPlayer, "random.explode", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
         if (flag) {
            entity.canBePickedUp = 2;
         } else {
            par3EntityPlayer.inventory.consumeInventoryItem(Item.emerald.itemID);
         }

         this.generateRandomParticles(par3EntityPlayer);
         if (!par2World.isRemote) {
            par2World.spawnEntityInWorld(entity);
         }
      }

   }

   @SideOnly(Side.CLIENT)
   private void generateRandomParticles(Entity entity) {
      for(int i = 0; i < 5; ++i) {
         double d0 = itemRand.nextGaussian() * 0.02D;
         double d1 = itemRand.nextGaussian() * 0.02D;
         double d2 = itemRand.nextGaussian() * 0.02D;
         entity.worldObj.spawnParticle("angryVillager", entity.posX + (double)(itemRand.nextFloat() * entity.width * 2.0F) - (double)entity.width, entity.posY + (double)(itemRand.nextFloat() * entity.height), entity.posZ + (double)(itemRand.nextFloat() * entity.width * 2.0F) - (double)entity.width, d0, d1, d2);
      }

   }

   public ItemStack onItemRightClick(ItemStack item, World par2World, EntityPlayer player) {
      if (player.capabilities.isCreativeMode || player.inventory.hasItem(Item.emerald.itemID)) {
         player.setItemInUse(item, this.getMaxItemUseDuration(item));
      }

      return item;
   }
}
