package _jx.jxLib.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

@SideOnly(Side.CLIENT)
public class AnimationManager {
   public List animatonEntries = new ArrayList();
   private List executingAnimationTask = new ArrayList();
   private int playingAnimationID = 0;
   private int playingAnimationPriority = 0;

   public void addAnimationTask(ModelBase model, int id, int priority) {
      this.animatonEntries.add(new AnimationBase(model, id, priority));
   }

   public void addAnimationTask(ModelRenderer model, int id, int priority, float value, float lastValue, int type) {
      this.animatonEntries.add(new AnimationBase(model, id, priority, value, lastValue, type));
   }

   public void onAnimationUpdate(IAnimationEntity entity) {
      Iterator iterator;
      if (IAnimationEntity.requestAnimatonID != null) {
         iterator = IAnimationEntity.requestAnimatonID.iterator();

         while(iterator.hasNext()) {
            int id = (Integer)iterator.next();
            AnimationBase anim = (AnimationBase)this.animatonEntries.get(id - 1);
            int priority = anim.getAnimationPriority();
            boolean flag = this.playingAnimationPriority <= priority;
            if (flag) {
               this.updateExecutingAnimation(anim);
               iterator.remove();
            }
         }
      }

      if (this.executingAnimationTask != null) {
         iterator = this.executingAnimationTask.iterator();

         while(iterator.hasNext()) {
            AnimationBase anim = (AnimationBase)iterator.next();
            int pri = anim.getAnimationPriority();
            if (pri > this.playingAnimationPriority) {
               this.updatePlayingAnimationPriority(pri);
            }

            if (anim.playAnimation()) {
               this.updatePlayingAnimationPriority(0);
               iterator.remove();
            }
         }
      } else {
         this.updatePlayingAnimationPriority(0);
      }

   }

   private void removeLowerPriorityTasks(int basePriority) {
      for(Iterator iterator = this.executingAnimationTask.iterator(); iterator.hasNext(); iterator.remove()) {
         AnimationBase anim = (AnimationBase)iterator.next();
         int priority = anim.getAnimationPriority();
         if (this.executingAnimationTask.contains(anim) & priority < basePriority) {
            this.executingAnimationTask.remove(anim);
         }
      }

   }

   private void updateExecutingAnimation(AnimationBase anim) {
      this.executingAnimationTask.add(anim);
   }

   private void updatePlayingAnimationID(int id) {
      this.playingAnimationID = id;
   }

   private void updatePlayingAnimationPriority(int priority) {
      this.playingAnimationPriority = priority;
   }
}
