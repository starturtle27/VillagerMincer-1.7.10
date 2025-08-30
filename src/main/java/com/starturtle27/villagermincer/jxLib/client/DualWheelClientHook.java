package _jx.jxLib.client;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent.Specials.Post;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.Event.Result;

public class DualWheelClientHook {
   @SideOnly(Side.CLIENT)
   @ForgeSubscribe
   public void renderSpecial(Post event) {
      DualWheelItemRenderHelper.renderSpecials(event.entityPlayer, this.getRenderManager(event.renderer), getModelBiped(event.renderer, 0), getModelBiped(event.renderer, 1), getModelBiped(event.renderer, 2), (double)event.partialTicks);
      event.setResult(Result.ALLOW);
   }

   private RenderManager getRenderManager(RenderPlayer renderer) {
      return (RenderManager)ObfuscationReflectionHelper.getPrivateValue(Render.class, renderer, 1);
   }

   public static ModelBiped getModelBiped(RenderPlayer renderPlayer, int i) {
      return (ModelBiped)ObfuscationReflectionHelper.getPrivateValue(RenderPlayer.class, renderPlayer, 1 + i);
   }
}
