package knightminer.simplytea.core;

import knightminer.simplytea.SimplyTea;
import knightminer.simplytea.block.TeaSaplingBlock;
import knightminer.simplytea.block.TeaTrunkBlock;
import knightminer.simplytea.item.CocoaItem;
import knightminer.simplytea.item.HotTeapotItem;
import knightminer.simplytea.item.TeaCupItem;
import knightminer.simplytea.item.TeapotItem;
import knightminer.simplytea.item.TooltipItem;
import knightminer.simplytea.potion.CaffeinatedEffect;
import knightminer.simplytea.potion.EnderfallingEffect;
import knightminer.simplytea.potion.RestfulEffect;
import knightminer.simplytea.worldgen.TeaTreeFeature;
import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(SimplyTea.MOD_ID)
@EventBusSubscriber(modid = SimplyTea.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Registration {

  /* Creative tab */
  public static ItemGroup group = new ItemGroup("simplytea") {
    @Override
    public ItemStack createIcon() {
      return new ItemStack(tea_leaf);
    }
  };

  /* Potions */
  public static final Effect restful = null;
  public static final Effect caffeinated = null;
  public static final Effect enderfalling = null;

  /* Blocks */
  public static final Block tea_sapling = null;
  public static final Block tea_trunk = null;
  public static final Block tea_fence = null;
  public static final Block tea_fence_gate = null;

  /* Items */
  /* Crafting */
  public static final Item tea_leaf = null;
  public static final Item black_tea = null;
  public static final Item tea_stick = null;
  public static final Item chorus_petal = null;

  /* Tea bags */
  public static final Item teabag = null;
  public static final Item teabag_black = null;
  public static final Item teabag_floral = null;
  public static final Item teabag_chorus = null;
  public static final Item teabag_green = null;

  /* Tea bags */
  public static final Item teapot = null;
  public static final Item teapot_water = null;
  public static final Item teapot_milk = null;

  /* Drinks */
  public static final Item cup = null;
  public static final Item cup_tea_black = null;
  public static final Item cup_tea_green = null;
  public static final Item cup_tea_floral = null;
  public static final Item cup_tea_chai = null;
  public static final Item cup_tea_chorus = null;
  public static final Item cup_cocoa = null;

  /* World Gen */
  public static final Feature<NoFeatureConfig> tea_tree = null;

  // TODO: remove once Forge fixes #5859
  public static class HackRegistryOrder {
    public static Effect restful = null;
    public static Effect caffeinated = null;
    public static Effect enderfalling = null;
  }

  @SubscribeEvent
  public static void registerEffects(final RegistryEvent.Register<Effect> event) {
    IForgeRegistry<Effect> r = event.getRegistry();

    register(r, HackRegistryOrder.caffeinated, "caffeinated");
    register(r, HackRegistryOrder.restful, "restful");
    register(r, HackRegistryOrder.enderfalling, "enderfalling");
  }


  @SubscribeEvent
  public static void registerBlocks(final RegistryEvent.Register<Block> event) {
    IForgeRegistry<Block> r = event.getRegistry();

    Block.Properties props;

    props = Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD);
    register(r, new FenceBlock(props), "tea_fence");
    register(r, new FenceGateBlock(props), "tea_fence_gate");

    props = Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0).sound(SoundType.PLANT);
    register(r, new TeaSaplingBlock(props), "tea_sapling");

    props = Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0F).sound(SoundType.WOOD).tickRandomly();
    register(r, new TeaTrunkBlock(props), "tea_trunk");
  }

  @SubscribeEvent
  public static void registerItems(final RegistryEvent.Register<Item> event) {
    IForgeRegistry<Item> r = event.getRegistry();

    Item.Properties base = new Item.Properties().group(group);
    Item.Properties props;

    // crafting
    register(r, new TooltipItem(base), "tea_leaf");
    register(r, new TooltipItem(base), "black_tea");
    register(r, new TooltipItem(base), "tea_stick");
    register(r, new TooltipItem(base), "chorus_petal");

    // tea bags
    register(r, new Item(base), "teabag");
    register(r, new Item(base), "teabag_black");
    register(r, new Item(base), "teabag_floral");
    register(r, new Item(base), "teabag_chorus");
    register(r, new Item(base), "teabag_green");

    // teapots
    props = new Item.Properties().group(group).maxStackSize(16);
    Item teapot = register(r, new TeapotItem(props), "teapot");
    props.containerItem(teapot).maxStackSize(1);
    register(r, new TooltipItem(props), "teapot_water");
    register(r, new TooltipItem(props), "teapot_milk");
    props.setNoRepair().maxDamage(4);
    register(r, new HotTeapotItem(props), "teapot_hot");
    register(r, new HotTeapotItem(props), "teapot_frothed");

    // drinks
    props = new Item.Properties().group(group).maxStackSize(16);
    Item cup = register(r, new Item(props), "cup");
    HackRegistryOrder.caffeinated = new CaffeinatedEffect();
    HackRegistryOrder.restful = new RestfulEffect();
    HackRegistryOrder.enderfalling = new EnderfallingEffect();
    register(r, new TeaCupItem(makeTea(4, 0.8f, new EffectInstance(HackRegistryOrder.caffeinated, 150*20, 1)).containerItem(cup)), "cup_tea_black");
    register(r, new TeaCupItem(makeTea(3, 0.5f, new EffectInstance(HackRegistryOrder.caffeinated, 150*20, 1)).containerItem(cup)), "cup_tea_green");
    register(r, new TeaCupItem(makeTea(2, 0.5f, new EffectInstance(HackRegistryOrder.restful, 20*20, 1)).containerItem(cup)), "cup_tea_floral");
    register(r, new TeaCupItem(makeTea(5, 0.6f, new EffectInstance(HackRegistryOrder.caffeinated, 150*20, 2)).containerItem(cup)), "cup_tea_chai");
    register(r, new TeaCupItem(makeTea(3, 0.8f, new EffectInstance(HackRegistryOrder.enderfalling, 150*20)).containerItem(cup)), "cup_tea_chorus");
    register(r, new CocoaItem(makeTea(4, 0.6f, null).containerItem(cup)), "cup_cocoa");

    // blocks
    registerBlockItem(r, tea_fence);
    registerBlockItem(r, tea_fence_gate);
    registerBlockItem(r, tea_sapling);
  }

  @SubscribeEvent
  public static void registerFeatures(final RegistryEvent.Register<Feature<?>> event) {
    IForgeRegistry<Feature<?>> r = event.getRegistry();

    register(r, new TeaTreeFeature(NoFeatureConfig::deserialize), "tea_tree");
  }

  /* Helper methods */

  public static Item.Properties makeTea(int hunger, float saturation, EffectInstance effect) {
    Item.Properties props = new Item.Properties();
    props.maxStackSize(1);
    props.maxDamage(2);
    props.group(group);
    props.setNoRepair();

    Food.Builder food = new Food.Builder().hunger(hunger).saturation(saturation).fastToEat().setAlwaysEdible();
    if (effect != null) {
      food.effect(effect, 1.0F);
    }
    props.food(food.build());
    return props;
  }

  private static <V extends R, R extends IForgeRegistryEntry<R>> V register(IForgeRegistry<R> registry, V value, ResourceLocation location) {
    value.setRegistryName(location);
    registry.register(value);
    return value;
  }

  private static <V extends R, R extends IForgeRegistryEntry<R>> V register(IForgeRegistry<R> registry, V value, String name) {
    value.setRegistryName(new ResourceLocation(SimplyTea.MOD_ID, name));
    registry.register(value);
    return value;
  }

  private static BlockItem registerBlockItem(IForgeRegistry<Item> registry, Block block) {
    Item.Properties props = new Item.Properties().group(group);
    return register(registry, new BlockItem(block, props), block.getRegistryName());
  }
}
