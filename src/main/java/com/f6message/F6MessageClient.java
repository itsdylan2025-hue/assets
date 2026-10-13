package com.f6message;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class F6MessageClient implements ClientModInitializer {

    private static final String NAME = "Dylan";
    private static final String MESSAGE = " Toggled Player Outline";
    private static final int BRACKET_COLOR = 0xAAAAAA;
    private static final int NAME_COLOR    = 0xA020F0;
    private static final int MESSAGE_COLOR = 0xDDDDDD;

    private static KeyBinding sendKey;

    @Override
    public void onInitializeClient() {
        sendKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.f6message.send",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F6,
                KeyBinding.Category.create(Identifier.of("f6message", "main"))
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (sendKey.wasPressed()) {
                if (client.player != null) {
                    client.player.sendMessage(buildMessage(), false);
                }
            }
        });
    }

    private static Text buildMessage() {
        MutableText text = Text.literal("[").setStyle(Style.EMPTY.withColor(BRACKET_COLOR));
        text.append(Text.literal(NAME).setStyle(Style.EMPTY.withColor(NAME_COLOR)));
        text.append(Text.literal("]").setStyle(Style.EMPTY.withColor(BRACKET_COLOR)));
        text.append(Text.literal(MESSAGE).setStyle(Style.EMPTY.withColor(MESSAGE_COLOR)));
        return text;
    }
}
