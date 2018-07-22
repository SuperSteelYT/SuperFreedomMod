package me.totalfreedom.totalfreedommod.command;

import me.totalfreedom.totalfreedommod.config.ConfigEntry;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

@CommandPermissions(level = Rank.NON_OP, source = SourceType.BOTH)
@CommandParameters(description = "Information about applying for admin", usage = "/<command>", aliases = "ai")
public class Command_admininfo extends FreedomCommand
{

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        msg("How to apply for admin on the " + ConfigEntry.SERVER_NAME.getString() + " server:", ChatColor.DARK_GREEN);
        msg(" - " + ConfigEntry.ADMININFO_LINE_ONE.getString(), ChatColor.GREEN);
        msg(" - " + ConfigEntry.ADMININFO_LINE_TWO.getString(), ChatColor.GREEN);
        msg(" - " + ConfigEntry.ADMININFO_LINE_THREE.getString(), ChatColor.GREEN);
        msg(" - " + ConfigEntry.ADMININFO_LINE_FOUR.getString(), ChatColor.GREEN);
        return true;
    }
}