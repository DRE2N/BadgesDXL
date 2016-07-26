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
package io.github.dre2n.badgesdxl.config;

import io.github.dre2n.badgesdxl.BadgesDXL;
import io.github.dre2n.dungeonsxl.DungeonsXL;
import io.github.dre2n.dungeonsxl.util.commons.config.Messages;
import io.github.dre2n.dungeonsxl.util.commons.util.messageutil.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * @author Daniel Saukel
 */
public enum BMessages implements Messages {

    CMD_SET_BADGE_ADDED("cmd.setBadge.added", "&6Successfully added the badge &4&v1&6."),
    CMD_SET_BADGE_REMOVED("cmd.setBadge.removed", "&6Successfully removed the badge &4&v1&6."),
    CMD_SET_BADGE_CHECK_TRUE("cmd.setBadge.check.true", "&6The player &4&v1 &6owns the badge &4&v2 &6since: &4&v3"),
    CMD_SET_BADGE_CHECK_FALSE("cmd.setBadge.check.false", "&6The player &4&v1 &6does not own the badge &4&v2&6."),
    ERROR_NO_SUCH_BADGE("error.noSuchBadge", "&4The badge &6&v1&4 does not exist!"),
    HELP_CMD_BADGES("help.cmd.badges", "/dxl badges - Shows all of your badges"),
    HELP_CMD_SET_BADGE("help.cmd.setBadge", "/dxl setbadge - Give or remove a badge; usage: /dxl setbadge [] ");

    private String identifier;
    private String message;

    BMessages(String identifier, String message) {
        this.identifier = identifier;
        this.message = message;
    }

    /* Getters and setters */
    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getMessage() {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    @Override
    public String getMessage(String... args) {
        return BadgesDXL.getInstance().getMessageConfig().getMessage(this, args);
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    /* Actions */
    /**
     * Sends the message to the console.
     */
    public void debug() {
        MessageUtil.log(DungeonsXL.getInstance(), getMessage());
    }

    /* Statics */
    /**
     * @param identifer
     * the identifer to set
     */
    public static Messages getByIdentifier(String identifier) {
        for (Messages message : values()) {
            if (message.getIdentifier().equals(identifier)) {
                return message;
            }
        }

        return null;
    }

    /**
     * @return
     * a FileConfiguration containing all messages
     */
    public static FileConfiguration toConfig() {
        FileConfiguration config = new YamlConfiguration();
        for (BMessages message : values()) {
            config.set(message.getIdentifier(), message.message);
        }

        return config;
    }

}
