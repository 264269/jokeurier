package net.dbectnwecton.joecourier.event;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.dbectnwecton.joecourier.JoeCourier;
import net.dbectnwecton.joecourier.item.ModItems;
import net.dbectnwecton.joecourier.villager.ModVillagers;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = JoeCourier.MOD_ID)
public class ModEvents {

//    static Int2ObjectMap<List<VillagerTrades.ItemListing>> libTrades;

    @SubscribeEvent
    public static void checkNewDay(TickEvent.LevelTickEvent event) {
        if (event.level.getDayTime() % 2000 == 0
                && event.phase.equals(TickEvent.Phase.START)
                && event.level.dimension().equals(ServerLevel.OVERWORLD))  {
            System.out.println("Level registry: " + event.level.dimension().registry()
                    + " || location: " + event.level.dimension().location()
                    + " || tick: " + event.level.getDayTime());

//            ItemStack stack = new ItemStack(ModItems.RAW_ZIRCON.get(), 15);
//            int villagerLevel = 1;
//
//            libTrades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
//                    new ItemStack(Items.EMERALD, 5),
//                    stack,10,8,0.02F));
        }
    }

    @SubscribeEvent
    public static void checkEntityInteraction(PlayerInteractEvent.EntityInteract event) {
        if (!event.getLevel().isClientSide()
                && event.getEntity() != null
                && event.getTarget() instanceof Villager villager) {
            if (villager.getVillagerData().getProfession() == VillagerProfession.LIBRARIAN) {
                MerchantOffers merch = getNewMerchOffers(villager.getOffers());
                event.getEntity().sendSystemMessage(
                        Component.literal(merch.get(0).getResult().toString()));
                villager.setOffers(merch);
            }
        }
    }

    private static MerchantOffers getNewMerchOffers(MerchantOffers oldOffers) {
        MerchantOffers merchOffers = new MerchantOffers();
        ItemStack zirconStack = new ItemStack(ModItems.RAW_ZIRCON.get(), 15);
        ItemStack book = new ItemStack(Items.WRITTEN_BOOK, 1);

        boolean noBook = true;
        for (int i = 0; i < oldOffers.size(); i++) {
            if (oldOffers.get(i).getResult().is(Items.WRITTEN_BOOK)) {
                oldOffers.remove(i);
                noBook = false;
                break;
            }
        }
        if (noBook) {
            merchOffers.add(new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    book,1,8,0.02F));
        }

        merchOffers.add(new MerchantOffer(
                new ItemStack(Items.EMERALD, 5),
                zirconStack,10,8,0.02F));
        return merchOffers;
    }

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if(event.getType() == VillagerProfession.TOOLSMITH) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModItems.EIGHT_BALL.get(), 1);
            int villagerLevel = 1;

            trades.get(villagerLevel).add(
                    (trader, rand) ->
                            new MerchantOffer(
                                    new ItemStack(Items.EMERALD, 2),
                                    stack,10,8,0.02F));
        }

        if(event.getType() == ModVillagers.JUMP_MASTER.get()) {
//            libTrades = event.getTrades();
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModItems.ZIRCON.get(), 15);
            int villagerLevel = 1;

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 5),
                    stack,10,8,0.02F));
        }
    }
}
