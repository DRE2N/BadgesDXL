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
import io.github.dre2n.dungeonsxl.util.commons.command.BRCommand;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * @author Daniel Saukel
 */
public class BadgesCommand extends BRCommand {

    BadgesDXL plugin;

    public BadgesCommand(BadgesDXL plugin) {
        this.plugin = plugin;
        setCommand("badges");
        setMinArgs(0);
        setMaxArgs(1);
        setHelp(BMessages.HELP_CMD_BADGES.getMessage());
        setPermission("dxl.badges");
        setPlayerCommand(true);
        setConsoleCommand(false);
    }

    @Override
    public void onExecute(String[] args, CommandSender sender) {
        Player player = (Player) sender;
        if (args.length == 2 && Bukkit.getPlayer(args[1]) != null) {
            player = Bukkit.getPlayer(args[1]);
        }

        BadgeData data = plugin.getDataByPlayer(player);
        Inventory gui = Bukkit.createInventory(null, 54);
        for (Map.Entry<UniversalItem, Long> entry : data.getBadges().entrySet()) {
            entry.getKey().toItemStack(1).getItemMeta().getLore().add(ChatColor.DARK_RED + new Date(entry.getValue() * 1000).toString());
        }
        ((Player) sender).openInventory(gui);
    }

}
