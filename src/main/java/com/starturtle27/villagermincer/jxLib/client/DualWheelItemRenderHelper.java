//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "Z:\home\aaron\Files\Minecraft Stuff\Mods\Mod Code Stuff\mcp811\conf"!

package _jx.jxLib.client;

import _jx.jxLib.renderer.IDualWheelItem;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class DualWheelItemRenderHelper {
   public static void renderSpecials(EntityPlayer player, RenderManager manager, ModelBiped modelBipedMain, ModelBiped modelArmour, ModelBiped modelArmorChestplate, double frame) {
      ItemStack itemstack = player.inventory.getCurrentItem();
      if (itemstack != null && itemstack.getItem() instanceof IDualWheelItem) {
         IDualWheelItem dual = (IDualWheelItem)itemstack.getItem();
         if (dual.onDualWheel() && dual.leftHandItem() != null) {
            itemstack = new ItemStack(dual.leftHandItem());
            GL11.glPushMatrix();
            modelBipedMain.bipedLeftArm.postRender(0.0625F);
            GL11.glTranslatef(0.0125F, 0.4375F, 0.0625F);
            if (player.fishEntity != null) {
               itemstack = new ItemStack(Item.stick);
            }

            EnumAction enumaction = null;
            if (player.getItemInUseCount() > 0) {
               enumaction = itemstack.getItemUseAction();
            }

            IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, ItemRenderType.EQUIPPED);
            boolean is3D = customRenderer != null && customRenderer.shouldUseRenderHelper(ItemRenderType.EQUIPPED, itemstack, ItemRendererHelper.BLOCK_3D);
            boolean isBlock = itemstack.itemID < Block.blocksList.length && itemstack.getItemSpriteNumber() == 0;
            float f11;
            if (!is3D && (!isBlock || !RenderBlocks.renderItemIn3d(Block.blocksList[itemstack.itemID].getRenderType()))) {
               if (itemstack.itemID == Item.bow.itemID) {
                  f11 = 0.625F;
                  GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
                  GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
                  GL11.glScalef(f11, -f11, f11);
                  GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                  GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
               } else if (Item.itemsList[itemstack.itemID].isFull3D()) {
                  f11 = 0.625F;
                  if (Item.itemsList[itemstack.itemID].shouldRotateAroundWhenRendering()) {
                     GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                     GL11.glTranslatef(0.0F, -0.125F, 0.0F);
                  }

                  if (player.getItemInUseCount() > 0 && enumaction == EnumAction.block) {
                     GL11.glTranslatef(0.05F, 0.0F, -0.1F);
                     GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
                     GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
                     GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
                  }

                  GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
                  GL11.glScalef(f11, -f11, f11);
                  GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                  GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
               } else {
                  f11 = 0.375F;
                  GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
                  GL11.glScalef(f11, f11, f11);
                  GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
                  GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                  GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
               }
            } else {
               f11 = 0.5F;
               GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
               f11 *= 0.75F;
               GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
               GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
               GL11.glScalef(-f11, -f11, f11);
            }

            float f12;
            float f13;
            int j;
            if (itemstack.getItem().requiresMultipleRenderPasses()) {
               for(j = 0; j < itemstack.getItem().getRenderPasses(itemstack.getItemDamage()); ++j) {
                  int k = itemstack.getItem().getColorFromItemStack(itemstack, j);
                  f13 = (float)(k >> 16 & 255) / 255.0F;
                  f12 = (float)(k >> 8 & 255) / 255.0F;
                  float f6 = (float)(k & 255) / 255.0F;
                  GL11.glColor4f(f13, f12, f6, 1.0F);
                  manager.itemRenderer.renderItem(player, itemstack, j);
               }
            } else {
               j = itemstack.getItem().getColorFromItemStack(itemstack, 0);
               float f14 = (float)(j >> 16 & 255) / 255.0F;
               f13 = (float)(j >> 8 & 255) / 255.0F;
               f12 = (float)(j & 255) / 255.0F;
               GL11.glColor4f(f14, f13, f12, 1.0F);
               manager.itemRenderer.renderItem(player, itemstack, 0);
            }

            GL11.glPopMatrix();
         }
      }

   }
}
