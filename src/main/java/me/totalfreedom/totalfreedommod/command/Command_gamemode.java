package me.totalfreedom.totalfreedommod.command;

import me.totalfreedom.totalfreedommod.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.OP, source = SourceType.BOTH, blockHostConsole = true)
@CommandParameters(description = "Change your gamemode.", usage = "/<command> [creative | survival | adventure | spectator", aliases = "gm")
public class Command_gamemode extends FreedomCommand
{

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 0)
        {
            return false;
        }
        // (reserved for future use) Player target = Bukkit.getPlayer(args[1]);
        if (args[0].equals("survival") || args[0].equals("s") || args[0].equals("0"))
        {
            playerSender.setGameMode(GameMode.SURVIVAL);
            msg("Gamemode set to survival.");
        } 
        if (args[0].equals("creative") || args[0].equals("c") || args[0].equals("1"))
        {
            playerSender.setGameMode(GameMode.CREATIVE);
            msg("Gamemode set to creative.");
        }
        if (args[0].equals("adventure") || args[0].equals("a") || args[0].equals("2"))
        {
            playerSender.setGameMode(GameMode.ADVENTURE);
            msg("Gamemode set to adventure.");
        }
        if (args[0].equals("spectator") || args[0].equals("sp") || args[0].equals("3"))
        {
            if (plugin.al.isAdmin(sender))
            {
                playerSender.setGameMode(GameMode.SPECTATOR);
                msg("Gamemode set to spectator.");
            }
            else
            {
                noPerms();
            }
        }
        return false;
    }

}
