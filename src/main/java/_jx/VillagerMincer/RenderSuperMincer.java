//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "Z:\home\aaron\Files\Minecraft Stuff\Mods\Mod Code Stuff\mcp811\conf"!

package _jx.VillagerMincer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderSuperMincer extends TileEntitySpecialRenderer {
   public void renderSuperMincer(TileEntitySuperMincer tile, double par2, double par4, double par6, float par8) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)par2 + 0.5F, (float)par4, (float)par6 + 0.5F);
      func_98144_a(tile, par2, par4, par6, par8);
      GL11.glPopMatrix();
   }

   public static void func_98144_a(TileEntitySuperMincer tile, double par1, double par3, double par5, float par7) {
      EntityVillager entity = tile.getVillager();
      float mod = (float)tile.getRunTime();
      if (mod <= 20.0F) {
         mod = 0.0F;
      }

      if (entity != null) {
         float val;
         if (mod < 20.0F) {
            val = mod * mod;
         } else {
            val = (float)Math.sin((double)mod) * 360.0F;
         }

         float f1 = 0.4375F;
         GL11.glTranslatef(0.0F, 0.4F, 0.0F);
         if (entity.deathTime == 0) {
            GL11.glRotatef(val, 0.0F, 1.0F, 0.0F);
         }

         GL11.glTranslatef(0.0F, -mod / 80.0F, 0.0F);
         entity.setLocationAndAngles(par1, par3, par5, 0.0F, 0.0F);
         RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, par7);
      }

   }

   public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8) {
      this.renderSuperMincer((TileEntitySuperMincer)par1TileEntity, par2, par4, par6, par8);
   }
}
