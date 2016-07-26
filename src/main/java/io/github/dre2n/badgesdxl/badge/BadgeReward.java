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

import io.github.dre2n.badgesdxl.BadgesDXL;
import io.github.dre2n.caliburn.item.UniversalItem;
import io.github.dre2n.dungeonsxl.reward.Reward;
import io.github.dre2n.dungeonsxl.reward.RewardType;
import org.bukkit.entity.Player;

/**
 * @author Daniel Saukel
 */
public class BadgeReward extends Reward {

    public static final RewardType TYPE;

    static {
        TYPE = new RewardType() {
            @Override
            public String getIdentifier() {
                return "badge";
            }

            @Override
            public Class<? extends Reward> getHandler() {
                return BadgeReward.class;
            }
        };
    }

    private UniversalItem badge;

    /* Getters and setters */
    /**
     * @return
     * the badge the player gets
     */
    public UniversalItem getBadge() {
        return badge;
    }

    /**
     * @param badge
     * the badge the player gets to set
     */
    public void setBadge(UniversalItem badge) {
        this.badge = badge;
    }

    @Override
    public RewardType getType() {
        return TYPE;
    }

    /* Actions */
    @Override
    public void giveTo(Player player) {
        BadgesDXL.getInstance().getDataByPlayer(player).addBadge(badge);
    }

}
