//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "Z:\home\aaron\Files\Minecraft Stuff\Mods\Mod Code Stuff\mcp811\conf"!

package _jx.VillagerMincer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;

public class CreativeTabMincer extends CreativeTabs {
   public CreativeTabMincer(String type) {
      super(type);
   }

   @SideOnly(Side.CLIENT)
   public int getTabIconItemIndex() {
      return VillagerMincer.superMincer.blockID;
   }

   @SideOnly(Side.CLIENT)
   public String getTranslatedTabLabel() {
      return "VillagerMincer";
   }
}
