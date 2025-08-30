//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "Z:\home\aaron\Files\Minecraft Stuff\Mods\Mod Code Stuff\mcp811\conf"!

package _jx.jxLib.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelRenderer;

@SideOnly(Side.CLIENT)
public class ModelMotionHelper {
   private static ModelMotionHelper instance = new ModelMotionHelper();

   public static ModelMotionHelper getInstance() {
      return instance;
   }

   public boolean rotationX(ModelRenderer parts, float rotateValue, float lastPosition) {
      float rotateX = parts.rotateAngleX;
      if (rotateValue > 0.0F && rotateX > lastPosition) {
         parts.rotateAngleX = lastPosition;
         return true;
      } else if (rotateValue < 0.0F && rotateX < lastPosition) {
         parts.rotateAngleX = lastPosition;
         return true;
      } else {
         if (lastPosition != rotateX) {
            parts.rotateAngleX += rotateValue;
         }

         return false;
      }
   }

   public boolean rotationY(ModelRenderer parts, float rotateValue, float lastPosition) {
      float rotateY = parts.rotateAngleY;
      boolean reachEnd = false;
      if (rotateValue > 0.0F && rotateY > lastPosition) {
         parts.rotateAngleY = lastPosition;
         reachEnd = true;
         return true;
      } else if (rotateValue < 0.0F && rotateY < lastPosition) {
         parts.rotateAngleY = lastPosition;
         reachEnd = true;
         return true;
      } else {
         if (lastPosition != rotateY && !reachEnd) {
            parts.rotateAngleY += rotateValue;
         }

         return false;
      }
   }

   public boolean rotationZ(ModelRenderer parts, float rotateValue, float lastPosition) {
      float rotateZ = parts.rotateAngleZ;
      boolean reachEnd = false;
      if (rotateValue > 0.0F && rotateZ > lastPosition) {
         parts.rotateAngleZ = lastPosition;
         reachEnd = true;
         return true;
      } else if (rotateValue < 0.0F && rotateZ < lastPosition) {
         parts.rotateAngleZ = lastPosition;
         reachEnd = true;
         return true;
      } else {
         if (lastPosition != rotateZ && !reachEnd) {
            parts.rotateAngleZ += rotateValue;
         }

         return false;
      }
   }

   public boolean movePointX(ModelRenderer parts, float moveValue, float lastPosition) {
      float pointX = parts.rotationPointX;
      if (moveValue > 0.0F && pointX > lastPosition) {
         parts.rotationPointX = lastPosition;
         return true;
      } else if (moveValue < 0.0F && pointX < lastPosition) {
         parts.rotationPointX = lastPosition;
         return true;
      } else {
         if (lastPosition != pointX) {
            parts.rotationPointX += moveValue;
         }

         return false;
      }
   }

   public boolean movePointY(ModelRenderer parts, float moveValue, float lastPosition) {
      float pointY = parts.rotationPointY;
      boolean reachEnd = false;
      if (moveValue > 0.0F && parts.rotationPointY > lastPosition) {
         parts.rotationPointY = lastPosition;
         reachEnd = true;
         return true;
      } else if (moveValue < 0.0F && parts.rotationPointY < lastPosition) {
         parts.rotationPointY = lastPosition;
         reachEnd = true;
         return true;
      } else {
         if (lastPosition != parts.rotationPointY && !reachEnd) {
            parts.rotationPointY += moveValue;
         }

         return false;
      }
   }

   public boolean movePointZ(ModelRenderer parts, float moveValue, float lastPosition) {
      float pointZ = parts.rotationPointZ;
      if (moveValue > 0.0F && pointZ > lastPosition) {
         parts.rotationPointZ = lastPosition;
         return true;
      } else if (moveValue < 0.0F && pointZ < lastPosition) {
         parts.rotationPointZ = lastPosition;
         return true;
      } else {
         if (lastPosition != pointZ) {
            parts.rotationPointZ += moveValue;
         }

         return false;
      }
   }
}
