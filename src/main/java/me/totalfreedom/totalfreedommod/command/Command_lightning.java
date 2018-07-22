package me.totalfreedom.totalfreedommod.command;

import me.totalfreedom.totalfreedommod.rank.Rank;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.SENIOR, source = SourceType.BOTH)
@CommandParameters(description = "Lightning! (Senior only)", usage = "/<command> [player]")
public class Command_lightning extends FreedomCommand
{
    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 0)
        {
            if (senderIsConsole)
            {
                msg("When executing this command from console, you must specify a player.");
                return true;
            }
            final Location targetPos = playerSender.getLocation();
            final World world = playerSender.getWorld();
            for (int x = -1; x <= 1; x++)
            {
                for (int z = -1; z <= 1; z++)
                {
                    final Location strike_pos = new Location(world, targetPos.getBlockX()+ x, targetPos.getBlockY(), targetPos.getBlockZ() + z);
                    world.strikeLightning(strike_pos);
                }
            }
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null)
        {
            msg(PLAYER_NOT_FOUND);
            return true;
        }
        if (target == sender)
        {
            msg("Correct usage: /lightning");
            return true;
        }
        final Location targetPos = target.getLocation();
        final World world = target.getWorld();
        for (int x = -1; x <= 1; x++)
        {
            for (int z = -1; z <= 1; z++)
            {
                final Location strike_pos = new Location(world, targetPos.getBlockX() + x, targetPos.getBlockY(), targetPos.getBlockZ() + z);
                world.strikeLightning(strike_pos);
            }
        }
        return true;
    }
    
}
