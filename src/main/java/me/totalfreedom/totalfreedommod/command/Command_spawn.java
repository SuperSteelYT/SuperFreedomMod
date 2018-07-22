package me.totalfreedom.totalfreedommod.command;

import me.totalfreedom.totalfreedommod.config.ConfigEntry;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.ChatColor;

// Most or all credit goes to Fleek over at the BariaHQ server for the code!
@CommandPermissions(level = Rank.IMPOSTOR, source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Go to spawn", usage = "/<command> [player]")
public class Command_spawn extends FreedomCommand
{

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String label, String[] args, boolean senderIsConsole)
    {
        if (args.length == 0)
        {
            World world = Bukkit.getWorld(ConfigEntry.SPAWN_WORLD_NAME.getString());
            if (world == null)
            {
                msg("That world does not exist.");
                return true;
            }
            Location location = world.getBlockAt(ConfigEntry.SPAWN_X_POSITION.getInteger(), ConfigEntry.SPAWN_Y_POSITION.getInteger(), ConfigEntry.SPAWN_Z_POSITION.getInteger()).getLocation();
            playerSender.teleport(location);
            msg("Teleporting...", ChatColor.GOLD);
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null)
        {
            msg(PLAYER_NOT_FOUND);
            return true;
        }
        World world = Bukkit.getWorld(ConfigEntry.SPAWN_WORLD_NAME.getString());
        if (world == null)
        {
            msg("That world does not exist.");
            return true;
        }
        Location location = world.getBlockAt(ConfigEntry.SPAWN_X_POSITION.getInteger(), ConfigEntry.SPAWN_Y_POSITION.getInteger(), ConfigEntry.SPAWN_Z_POSITION.getInteger()).getLocation();
        target.teleport(location);
        msg(target, "Teleporting...", ChatColor.GOLD);
        return true;
    }
    
}
