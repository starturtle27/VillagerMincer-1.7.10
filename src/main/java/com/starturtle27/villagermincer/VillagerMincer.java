package com.starturtle27.villagermincer;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = "VillagerMincer", name = "VillagerMincer", version = "1.7.10"

)
public class VillagerMincer {

    public static Block superMincer;
    public static ItemMeatCan strangeMeat;
    public static ItemMeatCan meatCan;
    public static Item emptyCan;
    public static ItemMeatCan emeraldCan;
    public static ItemMeatCan superEmeraldCan;
    public static int superMincerID;
    public static int strangeMeatID;
    public static int meatCanID;
    public static int emptyCanID;
    public static int emeraldCanID;
    public static int superEmeraldCanID;
    @SidedProxy(
        clientSide = "com.starturtle27.villagermincer.VillagerMincer$ClientProxy",
        serverSide = "com.starturtle27.villagermincer.VillagerMincer$CommonProxy")

    public static VillagerMincer.CommonProxy proxy;
    @Instance("VillagerMincer")
    public static VillagerMincer instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event) {
        superMincer = (new BlockSuperMincer(superMincerID)).setHardness(1.5F)
            .setResistance(10.0F)
            .setCreativeTab(CreativeTabs.tabDecorations);
        setUnLocalizeNameAndIconPass(superMincer, "mincer");
        GameRegistry.registerBlock(superMincer, "superMincer");
        GameRegistry.registerTileEntity(TileEntitySuperMincer.class, "TileEntitySuperMincer");
        strangeMeat = (ItemMeatCan) (new ItemMeatCan(strangeMeatID, 4, 0.1F, true))
            .setCreativeTab(CreativeTabs.tabFood);
        strangeMeat
            .addPotionEffect(new PotionEffect(Potion.hunger.id, 600, 1), new PotionEffect(Potion.confusion.id, 600, 0));
        strangeMeat.setAlwaysEdible()
            .setMaxStackSize(64);
        setUnLocalizeNameAndIconPass(strangeMeat, "meat");
        meatCan = (ItemMeatCan) (new ItemMeatCan(meatCanID, 6, 0.1F, true)).setCreativeTab(CreativeTabs.tabFood);
        meatCan.addPotionEffect(
            new PotionEffect(Potion.hunger.id, 1200, 2),
            new PotionEffect(Potion.confusion.id, 1200, 1));
        meatCan.setAlwaysEdible();
        setUnLocalizeNameAndIconPass(meatCan, "meatCan");
        emptyCan = (new Item()).setCreativeTab(CreativeTabs.tabFood);
        setUnLocalizeNameAndIconPass(emptyCan, "emptyCan");
        emeraldCan = (ItemMeatCan) (new ItemMeatCan(emeraldCanID, 6, 0.1F, true))
            .setPotionEffect(Potion.hunger.id, 60, 1, 1.0F)
            .setPotionEffect(Potion.confusion.id, 60, 0, 1.0F)
            .setCreativeTab(CreativeTabs.tabFood);
        emeraldCan.addPotionEffect(
            new PotionEffect(Potion.hunger.id, 1200, 2),
            new PotionEffect(Potion.confusion.id, 1200, 1),
            new PotionEffect(Potion.poison.id, 1200, 1));
        emeraldCan.setAlwaysEdible();
        setUnLocalizeNameAndIconPass(emeraldCan, "emeraldCan");
        superEmeraldCan = (ItemMeatCan) (new ItemMeatCan(superEmeraldCanID, 120, 0.1F, true))
            .setPotionEffect(Potion.hunger.id, 2400, 5, 5.0F)
            .setPotionEffect(Potion.wither.id, 600, 50, 5.0F)
            .setPotionEffect(Potion.poison.id, 600, 50, 5.0F)
            .setCreativeTab(CreativeTabs.tabFood);
        superEmeraldCan.setAlwaysEdible();
        setUnLocalizeNameAndIconPass(superEmeraldCan, "superEmeraldCan");
        this.recipe();
        proxy.init();
    }

    public void recipe() {
        GameRegistry.addRecipe(new ItemStack(emptyCan, 4), new Object[] { "I ", "II", 'I', Items.iron_ingot });
        GameRegistry
            .addRecipe(new ItemStack(meatCan, 1), new Object[] { "S", "S", "I", 'I', emptyCan, 'S', strangeMeat });
        GameRegistry.addRecipe(
            new ItemStack(emeraldCan, 1),
            new Object[] { "S", "E", "I", 'I', emptyCan, 'S', strangeMeat, 'E', Items.emerald });
        GameRegistry.addRecipe(
            new ItemStack(superEmeraldCan, 1),
            new Object[] { "SMS", "DED", " I ", 'I', emeraldCan, 'M', strangeMeat, 'E', Items.emerald, 'D', Blocks.dirt,
                'S', Items.sugar });
        GameRegistry.addRecipe(
            new ItemStack(superMincer, 2),
            new Object[] { "IQI", "IEI", "IQI", 'I', Items.quartz, 'Q', Items.iron_ingot, 'E', Items.emerald });
    }

    public static void setUnLocalizeNameAndIconPass(Block block, String pass) {
        block.setBlockTextureName("VillagerMincer:" + pass);
        block.setBlockName("VillagerMincer:" + pass);
    }

    public static void setUnLocalizeNameAndIconPass(Item item, String pass) {
        item.setTextureName("VillagerMincer:" + pass);
        item.setUnlocalizedName("VillagerMincer:" + pass);
    }

    @SideOnly(Side.CLIENT)
    public static class ClientProxy extends VillagerMincer.CommonProxy {

        public void init() {
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySuperMincer.class, new RenderSuperMincer());
        }
    }

    public static class CommonProxy {

        public void init() {}
    }
}
