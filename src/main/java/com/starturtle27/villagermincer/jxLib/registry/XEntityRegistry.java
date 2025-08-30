//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "Z:\home\aaron\Files\Minecraft Stuff\Mods\Mod Code Stuff\mcp811\conf"!

package _jx.jxLib.registry;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

public class XEntityRegistry {
   public static void addMonsterSpawn(Class target, int weightedProb, int min, int max) {
      if (weightedProb > 0) {
         BiomeGenBase[] biomes = BiomeGenBase.biomeList;

         for(int i = 0; i < biomes.length; ++i) {
            if (biomes[i] != null && biomes[i] != BiomeGenBase.hell && biomes[i] != BiomeGenBase.sky && biomes[i] != BiomeGenBase.mushroomIsland && biomes[i] != BiomeGenBase.mushroomIslandShore) {
               EntityRegistry.addSpawn(target, weightedProb, min, max, EnumCreatureType.monster, new BiomeGenBase[]{BiomeGenBase.biomeList[i]});
            }
         }
      }

   }

   public static void addMonsterSpawn(Class target, int weightedProb, int min, int max, BiomeGenBase... biomes) {
      if (weightedProb > 0) {
         EntityRegistry.addSpawn(target, weightedProb, min, max, EnumCreatureType.monster, biomes);
      }

   }

   public static void addCreatureSpawn(Class target, int weightedProb, int min, int max) {
      if (weightedProb > 0) {
         for(int i = 0; i < BiomeGenBase.biomeList.length; ++i) {
            if (BiomeGenBase.biomeList[i] != null) {
               BiomeGenBase var10000 = BiomeGenBase.biomeList[i];
               BiomeGenBase var10001 = BiomeGenBase.biomeList[i];
               if (var10000 != BiomeGenBase.hell) {
                  var10000 = BiomeGenBase.biomeList[i];
                  var10001 = BiomeGenBase.biomeList[i];
                  if (var10000 != BiomeGenBase.sky) {
                     EntityRegistry.addSpawn(target, weightedProb, min, max, EnumCreatureType.creature, new BiomeGenBase[]{BiomeGenBase.biomeList[i]});
                  }
               }
            }
         }
      }

   }

   public static void addCreatureSpawn(Class target, int weightedProb, int min, int max, BiomeGenBase... biomes) {
      if (weightedProb > 0) {
         EntityRegistry.addSpawn(target, weightedProb, min, max, EnumCreatureType.creature, biomes);
      }

   }

   public static void registerEntity(Class<? extends Entity> entityClass, String name, int entityNumber, Object instance, int backgroundEggColour, int foregroundEggColour) {
      EntityRegistry.registerGlobalEntityID(entityClass, name, EntityRegistry.findGlobalUniqueEntityId(), backgroundEggColour, foregroundEggColour);
      EntityRegistry.registerModEntity(entityClass, name, entityNumber, instance, 128, 1, true);
   }

   public static void registerEntity(Class<? extends Entity> entityClass, String name, int entityNumber, Object instance) {
      EntityRegistry.registerGlobalEntityID(entityClass, name, EntityRegistry.findGlobalUniqueEntityId());
      EntityRegistry.registerModEntity(entityClass, name, entityNumber, instance, 128, 1, true);
   }
}
