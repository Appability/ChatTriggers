package com.chattriggers.ctjs.libs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Collections;

public class MinecraftVars {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static KeyBinding keyLeftArrow = new KeyBinding("left", Keyboard.KEY_LEFT, "CT Controls");
    public static KeyBinding keyRightArrow = new KeyBinding("right", Keyboard.KEY_RIGHT, "CT Controls");
    public static KeyBinding keyUpArrow = new KeyBinding("up", Keyboard.KEY_UP, "CT Controls");
    public static KeyBinding keyDownArrow = new KeyBinding("down", Keyboard.KEY_DOWN, "CT Controls");

    public static Boolean isLeftArrowDown() {
        return keyLeftArrow.isKeyDown();
    }
    public static Boolean isRightArrowDown() {
        return keyRightArrow.isKeyDown();
    }
    public static Boolean isUpArrowDown() {
        return keyUpArrow.isKeyDown();
    }
    public static Boolean isDownArrowDown() {
        return keyDownArrow.isKeyDown();
    }

    /**
     * Gets the player's username.
     * In an import, accessible via the {@code playerName} variable.
     * @return The username of the user.
     */
    public static String getPlayerName() {
        return mc.getSession().getUsername();
    }

    /**
     * Gets the player's UUID.
     * In an import, accessible via the {@code playerUUID} variable.
     * @return The UUID of the user.
     */
    public static String getPlayerUUID() {
        return mc.getSession().getPlayerID();
    }

    /**
     * Gets the player's HP.
     * In an import, accessible via the {@code hp} variable.
     * @return The player's HP.
     */
    public static Float getPlayerHP() {
        return mc.thePlayer == null ? null : mc.thePlayer.getHealth();
    }

    /**
     * Gets the player's hunger level.
     * In an import, accessible via the {@code hunger} variable.
     * @return The player's hunger level.
     */
    public static Integer getPlayerHunger() {
        return mc.thePlayer == null ? null : mc.thePlayer.getFoodStats().getFoodLevel();
    }

    /**
     * Gets the player's saturation level.
     * In an import, accessible via the {@code saturation} variable.
     * @return The player's saturation level.
     */
    public static Float getPlayerSaturation() {
        return mc.thePlayer == null ? null : mc.thePlayer.getFoodStats().getSaturationLevel();
    }

    /**
     * Gets the player's XP level.
     * In an import, accessible via the {@code xpLevel} variable.
     * @return The player's XP level.
     */
    public static Integer getXPLevel() {
        return mc.thePlayer == null ? null : mc.thePlayer.experienceLevel;
    }

    /**
     * Gets the player's XP progress towards the next XP level.
     * In an import, accessible via the {@code xpProgress} variable.
     * @return The player's xp progress.
     */
    public static Float getXPProgress() {
        return mc.thePlayer == null ? null : mc.thePlayer.experience;
    }

    /**
     * Returns true if the player has the chat open.
     * In an import, accessible via the {@code inChat} variable.
     * @return True if the player has the chat open, false otherwise.
     */
    public static boolean isInChat() {
        return mc.currentScreen instanceof GuiChat;
    }

    /**
     * Returns true if the player has the tab list open.
     * In an import, accessible via the {@code inTab} variable.
     * @return True if the player has the tab list open, false otherwise.
     */
    public static boolean isInTab() {
        return mc.gameSettings.keyBindPlayerList.isKeyDown();
    }

    /**
     * Returns true if the player is sneaking.
     * In an import, accessible via the {@code isSneaking} variable.
     * @return True if the player is sneaking, false otherwise.
     */
    public static boolean isSneaking() {
        return mc.thePlayer != null && mc.thePlayer.isSneaking();
    }

    /**
     * Returns true if the player is sprinting.
     * In an import, accessible via the {@code isSprinting} variable.
     * @return True if the player is sprinting, false otherwise.
     */
    public static boolean isSprinting() {
        return mc.thePlayer != null && mc.thePlayer.isSprinting();
    }

    /**
     * Gets the current server's IP.
     * In an import, accessible via the {@code serverIP} variable.
     * @return The IP of the current server, or "localhost" if the player
     *          is in a single player world.
     */
    public static String getServerIP() {
        if (mc.isSingleplayer()) return "localhost";

        return mc.getCurrentServerData() == null ? null : mc.getCurrentServerData().serverIP;
    }

    /**
     * Gets the current server's name.
     * In an import, accessible via the {@code server} variable.
     * @return The name of the current server, or "SinglePlayer" if the player
     *          is in a single player world.
     */
    public static String getServerName() {
        if (mc.isSingleplayer()) return "SinglePlayer";

        return mc.getCurrentServerData() == null ? null : mc.getCurrentServerData().serverName;
    }

    /**
     * Gets the current server's MOTD.
     * In an import, accessible via the {@code serverMOTD} variable.
     * @return The MOTD of the current server, or "SinglePlayer" if the player
     *          is in a single player world.
     */
    public static String getServerMOTD() {
        if (mc.isSingleplayer()) return "SinglePlayer";

        return mc.getCurrentServerData() == null ? null : mc.getCurrentServerData().serverMOTD;
    }

    /**
     * Gets the ping to the current server.
     * In an import, accessible via the {@code ping} variable.
     * @return The ping to the current server, or 5 if the player
     *          is in a single player world.
     */
    public static Long getPing() {
        if (mc.isSingleplayer()) return 5L;

        return mc.getCurrentServerData() == null ? null : mc.getCurrentServerData().pingToServer;
    }

    /**
     * Gets a list of the players in tabs list.
     * @return A string array containing the names of the players in the tabs list.
     *          If the player is in a single player world, returns an array containing
     *          the player's name.
     */
    public static ArrayList<String> getTabList() {
        if (mc.isSingleplayer()) return new ArrayList<>(Collections.singletonList(getPlayerName()));
        if (mc.getNetHandler() == null || mc.getNetHandler().getPlayerInfoMap() == null) return null;

        ArrayList<String> playerNames = new ArrayList<>();

        for (NetworkPlayerInfo playerInfo : mc.getNetHandler().getPlayerInfoMap()) {
            playerNames.add(playerInfo.getGameProfile().getName());
        }

        return playerNames;
    }

    /**
     * Gets the player's X position.
     * In an import, accessible via the {@code posX} variable.
     * @return The player's X position.
     */
    public static Double getPlayerPosX() {
        return mc.thePlayer == null ? null : mc.thePlayer.posX;
    }

    /**
     * Gets the player's Y position.
     * In an import, accessible via the {@code posY} variable.
     * @return The player's Y position.
     */
    public static Double getPlayerPosY() {
        return mc.thePlayer == null ? null : mc.thePlayer.posY;
    }

    /**
     * Gets the player's Z position.
     * In an import, accessible via the {@code posZ} variable.
     * @return The player's Z position.
     */
    public static Double getPlayerPosZ() {
        return mc.thePlayer == null ? null : mc.thePlayer.posZ;
    }

    /**
     * Gets the player's camera pitch.
     * In an import, accessible via the {@code cameraPitch} variable.
     * @return The player's camera pitch.
     */
    public static Float getPlayerPitch() {
        return mc.thePlayer == null ? null : mc.thePlayer.cameraPitch;
    }

    /**
     * Gets the player's camera yaw.
     * In an import, accessible via the {@code cameraYaw} variable.
     * @return The player's camera yaw.
     */
    public static Float getPlayerYaw() {
        return mc.thePlayer == null ? null : mc.thePlayer.cameraYaw;
    }

    /**
     * Gets the direction the player is facing.
     * In an import, accessible via the {@code facing} variable.
     * @return The direction the player is facing, one of the four cardinal directions.
     */
    public static String getPlayerFacing() {
        return mc.thePlayer == null ? null : mc.thePlayer.getHorizontalFacing().toString();
    }

    /**
     * Gets the game's FPS count.
     * In an import, accessible via the {@code fps} variable.
     * @return The game's FPS count.
     */
    public static int getPlayerFPS() {
        return Minecraft.getDebugFPS();
    }
}