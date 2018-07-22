package me.superischroma.threatpanel;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TPLog
{
    // Console Logging //
    private static final Logger log = Logger.getLogger("Minecraft");
    private static final String prefix = "[ToggleMod] ";
    public static void info(String string)
    {
        log.log(Level.INFO, "{0}{1}", new Object[]
        {
            prefix, string
        });
    }
    public static void warning(String string)
    {
        log.log(Level.WARNING, "{0}{1}", new Object[]
        {
            prefix, string
        });
    }
    public static void severe(String string)
    {
        log.log(Level.SEVERE, "{0}{1}", new Object[]
        {
            prefix, string
        });
    }
    public static void ninfo(String string)
    {
        log.log(Level.INFO, "{0}", new Object[]
        {
            string
        });
    }
    public static void nwarning(String string)
    {
        log.log(Level.WARNING, "{0}", new Object[]
        {
            string
        });
    }
    public static void nsevere(String string)
    {
        log.log(Level.SEVERE, "{0}", new Object[]
        {
            string
        });
    }
}
