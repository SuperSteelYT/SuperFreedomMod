package me.totalfreedom.totalfreedommod.command;

import me.totalfreedom.totalfreedommod.config.ConfigEntry;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Most or all credit goes to Fleek over at the BariaHQ server for the code!
@CommandPermissions(level = Rank.SUPER, source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Set the spawn at your current location.", usage = "/<command>")
public class Command_setspawn extends FreedomCommand
{

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length > 0)
        {
            return false;
        }

        ConfigEntry.SPAWN_WORLD_NAME.setString(playerSender.getLocation().getWorld().getName());
        ConfigEntry.SPAWN_X_POSITION.setInteger(playerSender.getLocation().getBlockX());
        ConfigEntry.SPAWN_Y_POSITION.setInteger(playerSender.getLocation().getBlockY());
        ConfigEntry.SPAWN_Z_POSITION.setInteger(playerSender.getLocation().getBlockZ());
        msg("Spawn successfully set.");
        return true;
    }
}
