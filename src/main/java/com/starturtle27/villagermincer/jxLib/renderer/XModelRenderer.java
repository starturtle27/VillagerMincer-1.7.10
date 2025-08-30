//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "Z:\home\aaron\Files\Minecraft Stuff\Mods\Mod Code Stuff\mcp811\conf"!

package _jx.jxLib.renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class XModelRenderer extends ModelRenderer {
   public float baseRotateAngleX;
   public float baseRotateAngleY;
   public float baseRotateAngleZ;

   public XModelRenderer(ModelBase par1ModelBase, String par2Str) {
      super(par1ModelBase, par2Str);
   }

   public XModelRenderer(ModelBase par1ModelBase) {
      this(par1ModelBase, (String)null);
   }

   public XModelRenderer(ModelBase par1ModelBase, int par2, int par3) {
      this(par1ModelBase);
      this.setTextureOffset(par2, par3);
   }

   public void setBaseRotation(float par1, float par2, float par3) {
      this.baseRotateAngleX = par1;
      this.baseRotateAngleY = par2;
      this.baseRotateAngleZ = par3;
   }
}
