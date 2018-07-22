package me.totalfreedom.totalfreedommod.rank;

import lombok.Getter;
import org.bukkit.ChatColor;

public enum Rank implements Displayable
{

    IMPOSTOR("an", "Impostor", Type.PLAYER, "Imp", ChatColor.YELLOW),
    NON_OP("a", "Non-Op", Type.PLAYER, "", ChatColor.GREEN),
    OP("an", "Op", Type.PLAYER, "OP", ChatColor.RED),
    SUPER("a", "Super Admin", Type.ADMIN, "SA", ChatColor.GOLD),
    SENIOR("a", "Senior Admin", Type.ADMIN, "SrA", ChatColor.LIGHT_PURPLE),
    CONSOLE("the", "Console", Type.CONSOLE, "Console", ChatColor.DARK_PURPLE);
    @Getter
    private final Type type;
    @Getter
    private final String name;
    private final String abbr;
    private final String determiner;
    @Getter
    private final String tag;
    @Getter
    private final String abbrTag;
    @Getter
    private final String publicTag;
    @Getter
    private final ChatColor color;

    private Rank(String determiner, String name, Type type, String abbr, ChatColor color)
    {
        this.type = type;
        this.name = name;
        this.abbr = abbr;
        this.determiner = determiner;
        this.tag = abbr.isEmpty() ? "" : "[" + abbr + "]";
        this.abbrTag = abbr.isEmpty() ? "" : color + "[" + abbr + "]";
        this.publicTag = color + "[" + name + "]";
        this.color = color;
    }

    @Override
    public String getColoredName()
    {
        return color + name;
    }

    @Override
    public String getColoredLoginMessage()
    {
        return determiner + " " + color + name;
    }

    @Override
    public String getAbbr()
    {
        return abbr;
    }

    public boolean isConsole()
    {
        return getType() == Type.CONSOLE;
    }

    public int getLevel()
    {
        return ordinal();
    }

    public boolean isAtLeast(Rank rank)
    {
        if (getLevel() < rank.getLevel())
        {
            return false;
        }

        if (!hasConsoleVariant() || !rank.hasConsoleVariant())
        {
            return true;
        }

        return getConsoleVariant().getLevel() >= rank.getConsoleVariant().getLevel();
    }

    public boolean isAdmin()
    {
        return getType() == Type.ADMIN || getType() == Type.CONSOLE;
    }

    public boolean hasConsoleVariant()
    {
        return getConsoleVariant() != null;
    }

    public Rank getConsoleVariant()
    {
        switch (this)
        {
            case SENIOR:
            case CONSOLE:
                return CONSOLE;
            default:
                return null;
        }
    }

    public static Rank findRank(String string)
    {
        try
        {
            return Rank.valueOf(string.toUpperCase());
        }
        catch (Exception ignored)
        {
        }

        return Rank.NON_OP;
    }

    public static enum Type
    {

        PLAYER,
        ADMIN,
        CONSOLE;

        public boolean isAdmin()
        {
            return this != PLAYER;
        }
    }

}
