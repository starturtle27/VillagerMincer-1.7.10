//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "Z:\home\aaron\Files\Minecraft Stuff\Mods\Mod Code Stuff\mcp811\conf"!

package _jx.VillagerMincer;

import _jx.jxLib.registry.XEntityRegistry;
import _jx.jxLib.registry.XLanguageRegistry;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.File;
import java.util.logging.Level;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.Configuration;

@Mod(
   modid = "VillagerMincer",
   name = "VillagerMincer",
   version = "1.6.2"
)
@NetworkMod(
   clientSideRequired = true,
   serverSideRequired = false
)
public class VillagerMincer {
   public static final CreativeTabs tab = new CreativeTabMincer("Mincer");
   public static Block superMincer;
   public static ItemMeatCan strangeMeat;
   public static ItemMeatCan meatCan;
   public static Item emptyCan;
   public static ItemMeatCan emeraldCan;
   public static Item emeraldGun;
   public static ItemMeatCan superEmeraldCan;
   public static int superMincerID;
   public static int strangeMeatID;
   public static int meatCanID;
   public static int emptyCanID;
   public static int emeraldCanID;
   public static int emeraldGunID;
   public static int superEmeraldCanID;
   @SidedProxy(
      clientSide = "_jx.VillagerMincer.VillagerMincer$ClientProxy",
      serverSide = "_jx.VillagerMincer.VillagerMincer$CommonProxy"
   )
   public static VillagerMincer.CommonProxy proxy;
   @Instance("VillagerMincer")
   public static VillagerMincer instance;

   @PreInit
   public void preInit(FMLPreInitializationEvent event) {
      buildConfiguration(event.getSuggestedConfigurationFile());
   }

   @Init
   public void Init(FMLInitializationEvent event) {
      superMincer = (new BlockSuperMincer(superMincerID)).setHardness(1.5F).setResistance(10.0F);
      setUnLocalizeNameAndIconPass(superMincer, "mincer");
      GameRegistry.registerBlock(superMincer);
      GameRegistry.registerTileEntity(TileEntitySuperMincer.class, "TileEntitySuperMincer");
      strangeMeat = (ItemMeatCan)(new ItemMeatCan(strangeMeatID, 4, 0.1F, true)).setCreativeTab(tab);
      strangeMeat.addPotionEffect(new PotionEffect(Potion.hunger.id, 600, 1), new PotionEffect(Potion.confusion.id, 600, 0));
      strangeMeat.setAlwaysEdible().setMaxStackSize(64);
      setUnLocalizeNameAndIconPass((Item)strangeMeat, "meat");
      meatCan = (ItemMeatCan)(new ItemMeatCan(meatCanID, 6, 0.1F, true)).setCreativeTab(tab);
      meatCan.addPotionEffect(new PotionEffect(Potion.hunger.id, 1200, 2), new PotionEffect(Potion.confusion.id, 1200, 1));
      meatCan.setAlwaysEdible();
      setUnLocalizeNameAndIconPass((Item)meatCan, "meatCan");
      emptyCan = (new Item(emptyCanID)).setCreativeTab(tab);
      setUnLocalizeNameAndIconPass(emptyCan, "emptyCan");
      emeraldCan = (ItemMeatCan)(new ItemMeatCan(emeraldCanID, 6, 0.1F, true)).setPotionEffect(Potion.hunger.id, 60, 1, 1.0F).setPotionEffect(Potion.confusion.id, 60, 0, 1.0F).setCreativeTab(tab);
      emeraldCan.addPotionEffect(new PotionEffect(Potion.hunger.id, 1200, 2), new PotionEffect(Potion.confusion.id, 1200, 1), new PotionEffect(Potion.poison.id, 1200, 1));
      emeraldCan.setAlwaysEdible();
      setUnLocalizeNameAndIconPass((Item)emeraldCan, "emeraldCan");
      emeraldGun = (new ItemEmeraldGun(emeraldGunID)).setCreativeTab(tab);
      setUnLocalizeNameAndIconPass(emeraldGun, "emeraldGun");
      superEmeraldCan = (ItemMeatCan)(new ItemMeatCan(superEmeraldCanID, 120, 0.1F, true)).setPotionEffect(Potion.hunger.id, 2400, 5, 5.0F).setPotionEffect(Potion.wither.id, 600, 50, 5.0F).setPotionEffect(Potion.poison.id, 600, 50, 5.0F).setCreativeTab(tab);
      superEmeraldCan.setAlwaysEdible();
      setUnLocalizeNameAndIconPass((Item)superEmeraldCan, "superEmeraldCan");
      XLanguageRegistry.addLocalize(superMincer, "Super Mincer", "スーパーミン�?マシン");
      XLanguageRegistry.addLocalize(strangeMeat, "Strange Meat", "奇妙�?�肉");
      XLanguageRegistry.addLocalize(meatCan, "Meat Can", "肉�?�缶詰");
      XLanguageRegistry.addLocalize(emptyCan, "Empty Can", "空�?�缶詰");
      XLanguageRegistry.addLocalize(emeraldCan, "Emerald Can", "エメラルド�?��?�缶詰");
      XLanguageRegistry.addLocalize(emeraldGun, "Emerald Rifle", "エメラルドライフル");
      XLanguageRegistry.addLocalize(superEmeraldCan, "Super Emerald Can", "スーパーエメラルド缶");
      XEntityRegistry.registerEntity(EntityEmeraldGunBullet.class, "ItemEmeraldGun", 0, instance);
      this.recipe();
      proxy.init();
   }

   public void recipe() {
      GameRegistry.addRecipe(new ItemStack(emptyCan, 4), new Object[]{"I ", "II", 'I', Item.ingotIron});
      GameRegistry.addRecipe(new ItemStack(meatCan, 1), new Object[]{"S", "S", "I", 'I', emptyCan, 'S', strangeMeat});
      GameRegistry.addRecipe(new ItemStack(emeraldCan, 1), new Object[]{"S", "E", "I", 'I', emptyCan, 'S', strangeMeat, 'E', Item.emerald});
      GameRegistry.addRecipe(new ItemStack(superEmeraldCan, 1), new Object[]{"SMS", "DED", " I ", 'I', emeraldCan, 'M', strangeMeat, 'E', Item.emerald, 'D', Block.dirt, 'S', Item.sugar});
      GameRegistry.addRecipe(new ItemStack(superMincer, 2), new Object[]{"IQI", "IEI", "IQI", 'I', Item.netherQuartz, 'Q', Item.ingotIron, 'E', Item.emerald});
      GameRegistry.addRecipe(new ItemStack(emeraldGun, 1), new Object[]{"IE ", "QIE", " QI", 'I', Item.netherQuartz, 'Q', Item.ingotIron, 'E', Item.emerald});
   }

   public static void buildConfiguration(File cfgFile) {
      Configuration cfg = new Configuration(cfgFile);

      try {
         superMincerID = cfg.getBlock("SuperMincer", 3000).getInt();
         strangeMeatID = cfg.getItem("StrangeMeat", 6000).getInt();
         emptyCanID = cfg.getItem("EmptyCan", 6001).getInt();
         meatCanID = cfg.getItem("MeatCan", 6002).getInt();
         emeraldCanID = cfg.getItem("EmeraldCan", 6003).getInt();
         superEmeraldCanID = cfg.getItem("SuperEmeraldCan", 6004).getInt();
         emeraldGunID = cfg.getItem("EmeraldGun", 6005).getInt();
         cfg.load();
      } catch (Exception var6) {
         FMLLog.log(Level.SEVERE, var6, "Exception", new Object[0]);
      } finally {
         cfg.save();
      }

   }

   public static void setUnLocalizeNameAndIconPass(Block block, String pass) {
      block.setTextureName("VillagerMincer:" + pass);
      block.setUnlocalizedName("VillagerMincer:" + pass);
   }

   public static void setUnLocalizeNameAndIconPass(Item item, String pass) {
      item.setTextureName("VillagerMincer:" + pass);
      item.setUnlocalizedName("VillagerMincer:" + pass);
   }

   @SideOnly(Side.CLIENT)
   public static class ClientProxy extends VillagerMincer.CommonProxy {
      public void init() {
         RenderingRegistry.registerEntityRenderingHandler(EntityEmeraldGunBullet.class, new RenderEmerald());
         ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySuperMincer.class, new RenderSuperMincer());
      }
   }

   public static class CommonProxy {
      public void init() {
      }
   }
}
