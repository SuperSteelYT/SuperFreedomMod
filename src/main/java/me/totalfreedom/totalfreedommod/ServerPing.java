package me.totalfreedom.totalfreedommod;

import me.totalfreedom.totalfreedommod.config.ConfigEntry;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerPing extends FreedomService
{

    public ServerPing(TotalFreedomMod plugin)
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

    @EventHandler(priority = EventPriority.HIGH)
    public void onServerPing(ServerListPingEvent event)
    {
        final String ip = event.getAddress().getHostAddress().trim();
        
        String ipBanned = FUtil.colorize(ConfigEntry.SERVER_MOTD_IP_BAN.getString());
        String adminOnly = FUtil.colorize(ConfigEntry.SERVER_MOTD_ADMIN_ONLY.getString());
        String lockdownEnabled = FUtil.colorize(ConfigEntry.SERVER_MOTD_LOCKDOWN.getString());
        String whitelistEnabled = FUtil.colorize(ConfigEntry.SERVER_MOTD_WHITELIST.getString());
        String serverFull = FUtil.colorize(ConfigEntry.SERVER_MOTD_SERVER_FULL.getString());

        if (plugin.bm.isIpBanned(ip))
        {
            event.setMotd(ipBanned);
            return;
        }

        if (ConfigEntry.ADMIN_ONLY_MODE.getBoolean())
        {
            event.setMotd(adminOnly);
            return;
        }

        if (LoginProcess.isLockdownEnabled())
        {
            event.setMotd(lockdownEnabled);
            return;
        }

        if (Bukkit.hasWhitelist())
        {
            event.setMotd(whitelistEnabled);
            return;
        }

        if (Bukkit.getOnlinePlayers().size() >= Bukkit.getMaxPlayers())
        {
            event.setMotd(serverFull);
            return;
        }

        String baseMotdOne = ConfigEntry.SERVER_MOTD_LINE_ONE.getString().replace("%mcversion%", plugin.si.getVersion());
        String baseMotdTwo = ConfigEntry.SERVER_MOTD_LINE_TWO.getString().replace("%mcversion%", plugin.si.getVersion());
        String baseMotd = FUtil.colorize(baseMotdOne + "\n" + baseMotdTwo);

        if (!ConfigEntry.SERVER_MOTD_COLORFUL.getBoolean())
        {
            event.setMotd(baseMotd);
            return;
        }

        // Colorful MOTD
        final StringBuilder motd = new StringBuilder();
        for (String word : baseMotd.split(" "))
        {
            motd.append(FUtil.randomChatColor()).append(word).append(" ");
        }

        event.setMotd(motd.toString().trim());
    }

}
