package me.totalfreedom.totalfreedommod.httpd.module;

import me.totalfreedom.totalfreedommod.TotalFreedomMod;
import me.totalfreedom.totalfreedommod.admin.Admin;
import me.totalfreedom.totalfreedommod.config.ConfigEntry;
import me.totalfreedom.totalfreedommod.httpd.NanoHTTPD;
import me.totalfreedom.totalfreedommod.architect.Architect;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Module_players extends HTTPDModule
{

    public Module_players(TotalFreedomMod plugin, NanoHTTPD.HTTPSession session)
    {
        super(plugin, session);
    }

    @Override
    @SuppressWarnings("unchecked")
    public NanoHTTPD.Response getResponse()
    {
        final JSONObject responseObject = new JSONObject();

        final JSONArray players = new JSONArray();
        final JSONArray onlineadmins = new JSONArray();
        final JSONArray architects = new JSONArray();
        final JSONArray superadmins = new JSONArray();
        final JSONArray senioradmins = new JSONArray();
        final JSONArray tfdevelopers = new JSONArray();
        final JSONArray developers = new JSONArray();

        // All online players
        for (Player player : Bukkit.getOnlinePlayers())
        {
            players.add(player.getName());
            if (plugin.al.isAdmin(player) && !plugin.al.isAdminImpostor(player))
            {
                onlineadmins.add(player.getName());
            }
        }

        // Admins
        for (Admin admin : plugin.al.getActiveAdmins())
        {
            final String username = admin.getName();

            switch (admin.getRank())
            {
                case SUPER:
                    superadmins.add(username);
                    break;
                case SENIOR:
                    senioradmins.add(username);
                    break;
            }
        }

        // Master Builders
        for (Architect architect : plugin.arl.getAllArchitects().values())
        {
            architects.add(architect.getName());
        }

        // Developers
        tfdevelopers.addAll(FUtil.TFDEVELOPERS);
        developers.addAll(ConfigEntry.SERVER_DEVELOPERS.getList());

        responseObject.put("players", players);
        responseObject.put("architects", architects);
        responseObject.put("superadmins", superadmins);
        responseObject.put("senioradmins", senioradmins);
        responseObject.put("tfdevelopers", tfdevelopers);
        responseObject.put("developers", developers);

        final NanoHTTPD.Response response = new NanoHTTPD.Response(NanoHTTPD.Response.Status.OK, NanoHTTPD.MIME_JSON, responseObject.toString());
        response.addHeader("Access-Control-Allow-Origin", "*");
        return response;
    }
}
