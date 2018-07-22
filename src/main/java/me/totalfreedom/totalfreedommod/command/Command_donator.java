package me.totalfreedom.totalfreedommod.command;

import java.util.ArrayList;
import java.util.List;
import me.totalfreedom.totalfreedommod.config.ConfigEntry;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.util.Vector;

@CommandPermissions(level = Rank.OP, source = SourceType.BOTH)
@CommandParameters(description = "Donor perk command", usage = "/<command>", aliases = "donor")
public class Command_donator extends FreedomCommand
{

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (!ConfigEntry.SERVER_DONATORS.getStringList().contains(sender.getName()))
        {
            msg("This command is for donors only.", ChatColor.RED);
            return true;
        } 
        else
        {
            if (args.length == 0)
            {
                return false;
            }
            switch (args[0])
            {
                case "expel":
                {
                    double radius = 20.0;
                    double strength = 5.0;
                    List<String> pushedPlayers = new ArrayList<>();

                    final Vector senderPos = playerSender.getLocation().toVector();
                    final List<Player> players = playerSender.getWorld().getPlayers();
                    for (final Player player : players)
                    {
                        if (player.equals(playerSender))
                        {
                            continue;
                        }

                        final Location targetPos = player.getLocation();
                        final Vector targetPosVec = targetPos.toVector();

                        boolean inRange = false;
                        try
                        {
                            inRange = targetPosVec.distanceSquared(senderPos) < (radius * radius);
                        } 
                        catch (IllegalArgumentException ex)
                        {
                        }

                        if (inRange)
                        {
                            player.getWorld().createExplosion(targetPos, 0.0f, false);
                            FUtil.setFlying(player, false);
                            player.setVelocity(targetPosVec.subtract(senderPos).normalize().multiply(strength));
                            pushedPlayers.add(player.getName());
                        }
                    }

                    if (pushedPlayers.isEmpty())
                    {
                        msg("No players pushed.");
                    } 
                    else
                    {
                        msg("Pushed " + pushedPlayers.size() + " players: " + StringUtils.join(pushedPlayers, ", "));
                    }
                    return true;
                }
            }
        }
        return true;
    }
}
