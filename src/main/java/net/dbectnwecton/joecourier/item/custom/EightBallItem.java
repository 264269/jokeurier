package net.dbectnwecton.joecourier.item.custom;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
//import com.mojang.datafixers.types.templates.Tag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.minecraft.core.Registry;
import net.minecraftforge.internal.TextComponentMessageFormatHandler;
import org.objectweb.asm.util.Textifier;

import javax.swing.plaf.metal.MetalBorders;
//import java.rmi.registry.Registry;

public class EightBallItem extends Item {
    /*public static final String TOKEN = "0a68a0b60a68a0b60a68a0b6be097c014f00a680a68a0b66efae816d28785d1805ad79f";
    public static final String V_API = "5.131";
    public static final int GROUP_ID = -92876084;
    public static final String GROUP_DOMAIN = "jumoreski";*/

    /*public static String getParamsString(Map<String, String> params) throws Exception {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

    public static String getResponse() throws Exception {
        //  step 1: specify method url, open connection
        URL url = new URL("https://api.vk.com/method/wall.get");
        HttpURLConnection newConnection = (HttpURLConnection) url.openConnection();
        newConnection.setRequestMethod("GET");

        //  step 2: add params
        Map<String, String> parameters = new HashMap<>();
        parameters.put("access_token", TOKEN);
        parameters.put("v", V_API);
        parameters.put("owner_id", Integer.toString(GROUP_ID));
        parameters.put("domain", GROUP_DOMAIN);
        parameters.put("offset", Integer.toString(1));
        parameters.put("count", Integer.toString(1));

        newConnection.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(newConnection.getOutputStream());
        out.writeBytes(getParamsString(parameters));
        out.flush();
        out.close();

        //  step 3: get response
        int status = newConnection.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(newConnection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        newConnection.disconnect();

        return content.toString();
    }

    public class JsonResult {
        ResponseBody response;
    }
    public class ResponseBody {
        int count;
        ArrayList<Post> items;
    }
    public class Post {
        int id;
        String text;
    }*/

    public EightBallItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            outputRandomNumber(player);
            player.getCooldowns().addCooldown(this, 20);
            ItemStack curIS = player.getInventory().getItem(0);
//            player.sendSystemMessage(Component.literal(curIS.getDescriptionId()));
//            player.sendSystemMessage(Component.literal(curIS.getDisplayName().getString()));
//            player.sendSystemMessage(Component.literal(curIS.getOrCreateTag().toString()));
            player.sendSystemMessage(Component.literal(curIS.getOrCreateTag().getAllKeys().toString()));
            ListTag pagesList = curIS.getOrCreateTag().getList("pages", 8);
            String firstPage = pagesList.get(0).getAsString();
//            player.sendSystemMessage(
//                    Component.literal(
////                            curIS.getOrCreateTag().getList("pages", 8).get(0).toString()
//                            firstPage
//                    )
//            );
            for (Tag page : pagesList) {
                player.sendSystemMessage(
                        Component.literal(
//                            curIS.getOrCreateTag().getList("pages", 8).get(0).toString()
                                page.getAsString()
                        )
                );
                player.sendSystemMessage(
                        Component.literal(
//                            curIS.getOrCreateTag().getList("pages", 8).get(0).toString()
                                Integer.toString(Minecraft.getInstance().font.wordWrapHeight(page.getAsString(), 114))
                        )
                );
//                Minecraft.getInstance().font.wordWrapHeight()
            }
//            try {
//                CompoundTag compoundTag = TagParser.parseTag(firstPage.substring(1, firstPage.length() - 1));
//                player.sendSystemMessage(
//                        Component.literal(
////                            curIS.getOrCreateTag().getList("pages", 8).get(0).toString()
//                                compoundTag.toString()
//                        )
//                );
//            } catch (CommandSyntaxException e) {
//                throw new RuntimeException(e);
//            }
//            try {
//                player.sendSystemMessage(
//                        Component.literal(
//                                TagParser.parseTag(curIS.getOrCreateTag().getList("pages", 8).get(0).toString()).toString()
//                        )
//                );
//            } catch (CommandSyntaxException e) {
//                throw new RuntimeException(e);
//            }
//            curIS.getOrCreateTag().getList("pages", curIS.getOrCreateTag().getTagType("pages")).get(0);
//            if (curIS.getOrCreateTag().getAllKeys().contains("pages")) {
//                player.sendSystemMessage(Component.literal(curIS.getOrCreateTag().get("pages").getAsString()));
//            }
            player.sendSystemMessage(Component.literal(Integer.toString(Registry.ITEM.getId(curIS.getItem()))));

//            player.sendSystemMessage(Component.literal(player.getInventory().getItem(0).);
//            Item newBook = Registry.ITEM.byId(986);
            ItemStack newBook = new ItemStack(Registry.ITEM.byId(986), 1);
            player.getInventory().add(newBook);
//            jokeBook
//            player.getInventory().add(jokeBook);
//            jokeBook.getOrCreateTag().
//            player.getInventory().add
        }



        return super.use(level, player, hand);
    }

    private void outputRandomNumber(Player player) {
        player.sendSystemMessage(Component.literal("Ball decides... It's " + getRandomNumber()));
    }

    private int getRandomNumber() {
        return RandomSource.createNewThreadLocalInstance().nextInt(10);
    }
}
