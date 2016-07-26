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
import io.github.dre2n.caliburn.item.UniversalItem;
import io.github.dre2n.dungeonsxl.config.PlayerData;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Daniel Saukel
 */
public class BadgeData {

    BadgesDXL plugin;

    private File file;
    private FileConfiguration config;

    private Map<UniversalItem, Long> badges = new HashMap<>();

    public BadgeData(BadgesDXL plugin, PlayerData playerData) {
        this(plugin, playerData.getFile(), playerData.getConfig());
    }

    public BadgeData(BadgesDXL plugin, File file, FileConfiguration config) {
        this.plugin = plugin;

        this.file = file;
        this.config = config;

        ConfigurationSection section = config.getConfigurationSection("badges");
        if (section != null) {
            for (Entry<String, Object> entry : section.getValues(true).entrySet()) {
                badges.put(plugin.getBadges().getById(entry.getKey()), (long) entry.getValue());
            }
        } else {
            config.createSection("badges");
        }
    }

    /* Getters and setters */
    /**
     * @return
     * the badges
     */
    public Map<UniversalItem, Long> getBadges() {
        return badges;
    }

    /**
     * Gives the player a badge and logs current time and date.
     * Fails if the item is not a badge.
     *
     * @param badge
     * the badge to add
     */
    public void addBadge(UniversalItem badge) {
        if (!plugin.getBadges().getBadges().contains(badge)) {
            return;
        }

        badges.put(badge, System.currentTimeMillis());
    }

    /**
     * Removes a badge.
     *
     * @param badge
     * the badge to remove
     */
    public void removeBadge(UniversalItem badge) {
        badges.remove(badge);
    }

    /**
     * @return
     * true if the player has the badge, false if not
     */
    public boolean hasBadge(UniversalItem badge) {
        return badges.containsKey(badge);
    }

    /* Actions */
    /**
     * Saves the data to the config and the config to the file.
     */
    public void save() {
        for (Entry<UniversalItem, Long> entry : badges.entrySet()) {
            config.set("badges." + entry.getKey().getId(), entry.getValue());
        }

        try {
            config.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
