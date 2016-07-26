/*
 * Copyright (C) 2016 Daniel Saukel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.dre2n.badgesdxl;

import io.github.dre2n.badgesdxl.badge.BadgeRequirement;
import io.github.dre2n.badgesdxl.badge.BadgeReward;
import io.github.dre2n.badgesdxl.badge.Badges;
import io.github.dre2n.badgesdxl.command.BadgesCommand;
import io.github.dre2n.badgesdxl.command.SetBadgeCommand;
import io.github.dre2n.badgesdxl.config.BMessages;
import io.github.dre2n.badgesdxl.config.BadgeData;
import io.github.dre2n.caliburn.CaliburnAPI;
import io.github.dre2n.dungeonsxl.DungeonsXL;
import io.github.dre2n.dungeonsxl.player.DGlobalPlayer;
import io.github.dre2n.dungeonsxl.util.commons.compatibility.Internals;
import io.github.dre2n.dungeonsxl.util.commons.config.MessageConfig;
import io.github.dre2n.dungeonsxl.util.commons.javaplugin.BRPlugin;
import io.github.dre2n.dungeonsxl.util.commons.javaplugin.BRPluginSettings;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Daniel Saukel
 */
public class BadgesDXL extends BRPlugin implements Listener {

    DungeonsXL dungeonsxl;

    private static BadgesDXL instance;

    /**
     * The /dxl badges command
     */
    public static BadgesCommand BADGES_COMMAND;

    public static SetBadgeCommand SET_BADGE_COMMAND;

    private MessageConfig messageConfig;
    private Badges badges;
    private Map<Player, BadgeData> dataCache = new HashMap<>();

    /* Loading */
    public BadgesDXL() {
        /*
         * ##########################
         * ####~BRPluginSettings~####
         * ##########################
         * #~Internals~##INDEPENDENT#
         * #~SpigotAPI~##~~~false~~~#
         * #~~~~UUID~~~##~~~~true~~~#
         * #~~Economy~~##~~~false~~~#
         * #Permissions##~~~false~~~#
         * #~~Metrics~~##~~~~true~~~#
         * #Resource ID##~~~?????~~~#
         * ##########################
         */

        settings = new BRPluginSettings(false, true, false, false, true, 00000, Internals.INDEPENDENT);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;
        dungeonsxl = DungeonsXL.getInstance();

        loadMessageConfig(new File(dungeonsxl.getDataFolder(), "badges.yml"));
        manager.registerEvents(this, this);
        dungeonsxl.getRewardTypes().addReward(BadgeReward.TYPE);
        dungeonsxl.getRequirementTypes().addRequirement(BadgeRequirement.TYPE);

        BADGES_COMMAND = new BadgesCommand(this);
        SET_BADGE_COMMAND = new SetBadgeCommand(this);
        dungeonsxl.getCommands().addCommand(BADGES_COMMAND);

        loadBadges();
    }

    /* Getters and loaders */
    /**
     * @return the plugin instance
     */
    public static BadgesDXL getInstance() {
        return instance;
    }

    /**
     * @return the loaded instance of MessageConfig
     */
    public MessageConfig getMessageConfig() {
        return messageConfig;
    }

    /**
     * load / reload a new instance of MessageConfig
     */
    public void loadMessageConfig(File file) {
        messageConfig = new MessageConfig(BMessages.class, file);
    }

    /**
     * @return the loaded instance of GlobalData
     */
    public Badges getBadges() {
        return badges;
    }

    /**
     * load / reload a new instance of Badges
     */
    public void loadBadges() {
        badges = new Badges(CaliburnAPI.getInstance());
    }

    /**
     * @param player
     * the player to check
     * @return
     * the player's data
     */
    public BadgeData getDataByPlayer(Player player) {
        return getDataByPlayer(dungeonsxl.getDPlayers().getByPlayer(player));
    }

    /**
     * @param player
     * the player to check
     * @return
     * the player's data
     */
    public BadgeData getDataByPlayer(DGlobalPlayer player) {
        BadgeData data = dataCache.get(player.getPlayer());
        if (data == null) {
            data = new BadgeData(this, player.getData());
            dataCache.put(player.getPlayer(), data);
        }

        return data;
    }

    /* Listener */
    @EventHandler(priority = EventPriority.LOW)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        BadgeData data = dataCache.get(player);
        if (data != null) {
            data.save();
            dataCache.remove(player);
        }
    }

}
