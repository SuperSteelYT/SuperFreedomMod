package me.totalfreedom.totalfreedommod;

import com.google.common.base.Strings;
import me.totalfreedom.totalfreedommod.admin.Admin;
import me.totalfreedom.totalfreedommod.config.ConfigEntry;
import me.totalfreedom.totalfreedommod.player.FPlayer;
import me.totalfreedom.totalfreedommod.rank.Displayable;
import me.totalfreedom.totalfreedommod.util.FLog;
import me.totalfreedom.totalfreedommod.util.FSync;
import static me.totalfreedom.totalfreedommod.util.FUtil.playerMsg;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatManager extends FreedomService
{

    public ChatManager(TotalFreedomMod plugin)
    {
        super(plugin);
    }

    @Override
    protected void onStart()
    {
    }

    @Override
    protected void onStop()
    {
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerChatFormat(AsyncPlayerChatEvent event)
    {
        try
        {
            handleChatEvent(event);
        }
        catch (Exception ex)
        {
            FLog.severe(ex);
        }
    }

    private void handleChatEvent(AsyncPlayerChatEvent event)
    {
        final Player player = event.getPlayer();
        String message = event.getMessage().trim();

        // Strip color from messages
        message = ChatColor.stripColor(message);

        // Truncate messages that are too long - 256 characters is vanilla client max
        if (message.length() > 256)
        {
            message = message.substring(0, 256);
            FSync.playerMsg(player, "Message was shortened because it was too long to send.");
        }

        // Check for caps
        if (message.length() >= 6)
        {
            int caps = 0;
            for (char c : message.toCharArray())
            {
                if (Character.isUpperCase(c))
                {
                    caps++;
                }
            }
            if (((float) caps / (float) message.length()) > 0.65) //Compute a ratio so that longer sentences can have more caps.
            {
                if (!plugin.al.isAdmin(player))
                {
                    message = message.toLowerCase();
                }
            }
        }

        // Check for adminchat
        final FPlayer fPlayer = plugin.pl.getPlayerSync(player);
        if (fPlayer.inAdminChat())
        {
            FSync.adminChatMessage(player, message);
            event.setCancelled(true);
            return;
        }
        
        // Check if a blocked free hosts is being sent
        for (String domain : ConfigEntry.BLOCKED_FREE_HOSTS.getStringList())
        {
            if (ChatColor.stripColor(message).toLowerCase().contains(domain))
            {
                playerMsg(player, ChatColor.RED + "Mate, get some real server hosting.");
                event.setCancelled(true);
                return;
            }
        }

        // Finally, set message
        event.setMessage(message);

        // Set the tag
        String tag = fPlayer.getTag();
        if (tag != null && !tag.isEmpty())
        {
            event.setFormat("<" + tag.replace("%", "%%") + " %1$s> %2$s");
        }
    }

    public ChatColor getColor(Admin admin, Displayable display)
    {
        ChatColor color = display.getColor();
        return color;
    }

    public void adminChat(CommandSender sender, String message)
    {
        Displayable display = plugin.rm.getDisplay(sender);
        FLog.info("[ADMIN] " + sender.getName() + " " + display.getTag() + ": " + message);

        for (Player player : server.getOnlinePlayers())
        {
            if (plugin.al.isAdmin(player))
            {
                Admin admin = plugin.al.getAdmin(player);
                if (!Strings.isNullOrEmpty(admin.getAcFormat()))
                {
                   String format = admin.getAcFormat();
                   ChatColor color = getColor(admin, display);
                   String msg = format.replace("%name%", sender.getName()).replace("%rank%", display.getAbbr()).replace("%rankcolor%", color.toString()).replace("%msg%", message);
                   player.sendMessage(FUtil.colorize(msg));
                }
                else
                {
                    player.sendMessage("[" + ChatColor.AQUA + "ADMIN" + ChatColor.WHITE + "] " + ChatColor.DARK_RED + sender.getName() + " " + display.getAbbrTag() + ChatColor.WHITE + ": " + ChatColor.AQUA + FUtil.colorize(message));
                }
            }
        }
    }

    public void reportAction(Player reporter, Player reported, String report)
    {
        for (Player player : server.getOnlinePlayers())
        {
            if (plugin.al.isAdmin(player))
            {
                playerMsg(player, ChatColor.RED + "[REPORTS] " + ChatColor.GOLD + reporter.getName() + " has reported " + reported.getName() + " for " + report);
            }
        }
    }

}
