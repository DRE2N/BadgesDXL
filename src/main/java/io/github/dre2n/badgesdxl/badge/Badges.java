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
package io.github.dre2n.badgesdxl.badge;

import io.github.dre2n.caliburn.CaliburnAPI;
import io.github.dre2n.caliburn.item.UniversalItem;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.inventory.ItemStack;

/**
 * @author Daniel Saukel
 */
public class Badges {

    CaliburnAPI caliburn;

    public static final String ID_PREFIX = "BDXL-";

    private Set<UniversalItem> badges = new HashSet<>();

    public Badges(CaliburnAPI caliburn) {
        this.caliburn = caliburn;
        for (UniversalItem item : caliburn.getItems().getItems()) {
            if (item.getId().startsWith(ID_PREFIX)) {
                badges.add(item);
            }
        }
    }

    /**
     * @param id
     * the ID of the badge, may be with or without prefix
     * @return
     * the badge that has the ID
     */
    public UniversalItem getById(String id) {
        for (UniversalItem badge : badges) {
            if (badge.getId().equals(id) || badge.getId().equals(ID_PREFIX + id)) {
                return badge;
            }
        }

        return null;
    }

    /**
     * @param itemStack
     * an item stack instance of the badge
     * @return
     * the represented badge
     */
    public UniversalItem getByItemStack(ItemStack itemStack) {
        return getById(caliburn.getItems().getCustomItemId(itemStack));
    }

    /**
     * @return
     * a Set of all badges
     */
    public Set<UniversalItem> getBadges() {
        return badges;
    }

}
