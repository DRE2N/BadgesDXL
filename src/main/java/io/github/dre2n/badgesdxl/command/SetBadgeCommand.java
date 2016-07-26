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
package io.github.dre2n.badgesdxl.command;

import io.github.dre2n.badgesdxl.BadgesDXL;
import io.github.dre2n.badgesdxl.config.BMessages;
import io.github.dre2n.badgesdxl.config.BadgeData;
import io.github.dre2n.caliburn.item.UniversalItem;
import io.github.dre2n.caliburn.util.CaliConfiguration;
import io.github.dre2n.dungeonsxl.DungeonsXL;
import io.github.dre2n.dungeonsxl.config.DMessages;
import io.github.dre2n.dungeonsxl.util.commons.command.BRCommand;
import io.github.dre2n.dungeonsxl.util.commons.util.messageutil.MessageUtil;
import java.io.File;
import java.util.Date;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Daniel Saukel
 */
public class SetBadgeCommand extends BRCommand {

    BadgesDXL plugin;

    public SetBadgeCommand(BadgesDXL plugin) {
        this.plugin = plugin;
        setCommand("setbadge");
        setMinArgs(2);
        setMaxArgs(3);
        setHelp(BMessages.HELP_CMD_SET_BADGE.getMessage());
        setPermission("dxl.setbadge");
        setPlayerCommand(true);
        setConsoleCommand(true);
    }

    @Override
    public void onExecute(String[] args, CommandSender sender) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);

        BadgeData data = null;
        if (player instanceof Player) {
            data = plugin.getDataByPlayer((Player) player);
        } else {
            File file = new File(DungeonsXL.PLAYERS, player.getUniqueId().toString() + ".yml");
            if (file.exists()) {
                data = new BadgeData(plugin, file, CaliConfiguration.loadConfiguration(file));
            }
        }

        if (data == null) {
            MessageUtil.sendMessage(sender, DMessages.ERROR_NO_SUCH_PLAYER.getMessage(args[1]));
            return;
        }

        UniversalItem badge = plugin.getBadges().getById(args[2]);

        if (badge == null) {
            MessageUtil.sendMessage(sender, BMessages.ERROR_NO_SUCH_BADGE.getMessage(args[2]));
            return;
        }

        if (args.length == 3) {
            if (data.hasBadge(badge)) {
                MessageUtil.sendMessage(sender, BMessages.CMD_SET_BADGE_CHECK_TRUE.getMessage(args[1], args[2], new Date(data.getBadges().get(badge) * 1000).toString()));
            } else {
                MessageUtil.sendMessage(sender, BMessages.CMD_SET_BADGE_CHECK_FALSE.getMessage(args[1], args[2]));
            }
            return;
        }

        if (args[3].equalsIgnoreCase("false")) {
            data.removeBadge(badge);
            MessageUtil.sendMessage(sender, BMessages.CMD_SET_BADGE_REMOVED.getMessage(args[2]));
        } else if (args[3].equalsIgnoreCase("true")) {
            data.addBadge(badge);
            MessageUtil.sendMessage(sender, BMessages.CMD_SET_BADGE_ADDED.getMessage(args[2]));
        }
    }

}
