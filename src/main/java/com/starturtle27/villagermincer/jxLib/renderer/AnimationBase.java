package _jx.jxLib.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

@SideOnly(Side.CLIENT)
public class AnimationBase {
   private int id;
   private int priority;
   private int animationType = 0;
   private float moveValue;
   private float lastValue;
   private ModelRenderer model;
   private ModelBase modelBase;
   ModelMotionHelper helper = new ModelMotionHelper();

   public AnimationBase(ModelBase model, int id, int priority) {
      this.id = id;
      this.priority = priority;
      this.modelBase = model;
      this.animationType = 0;
   }

   public AnimationBase(ModelRenderer model, int id, int priority, float moveValue, float lastValue, int animationType) {
      this.id = id;
      this.priority = priority;
      this.model = model;
      this.moveValue = moveValue;
      this.lastValue = lastValue;
      this.animationType = animationType;
   }

   public boolean playAnimation() {
      switch(this.getAnimationType()) {
      case 0:
         return this.animation();
      case 1:
         return this.helper.rotationX(this.model, this.moveValue, this.lastValue);
      case 2:
         return this.helper.rotationY(this.model, this.moveValue, this.lastValue);
      case 3:
         return this.helper.rotationZ(this.model, this.moveValue, this.lastValue);
      case 4:
         return this.helper.movePointX(this.model, this.moveValue, this.lastValue);
      case 5:
         return this.helper.movePointY(this.model, this.moveValue, this.lastValue);
      case 6:
         return this.helper.movePointZ(this.model, this.moveValue, this.lastValue);
      default:
         return false;
      }
   }

   public boolean animation() {
      return false;
   }

   public int getAnimationID() {
      return this.id;
   }

   public int getAnimationType() {
      return this.animationType;
   }

   public int getAnimationPriority() {
      return this.priority;
   }
}
