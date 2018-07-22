package me.totalfreedom.totalfreedommod.rank;

import lombok.Getter;
import org.bukkit.ChatColor;

public enum Title implements Displayable
{

    DONATOR("a", "Donator", ChatColor.AQUA, "Donor"),
    ARCHITECT("a", "Architect", ChatColor.DARK_AQUA, "Arc"),
    EXECUTIVE("an", "Executive", ChatColor.RED, "Exec"),
    DEVELOPER("a", "Developer", ChatColor.DARK_PURPLE, "Dev"),
    TFDEVELOPER("a", "TotalFreedom Developer", ChatColor.DARK_PURPLE, "TF-Dev"),
    OWNER("the", "Owner", ChatColor.BLUE, "Owner");

    private final String determiner;
    @Getter
    private final String name;
    @Getter
    private final String abbr;
    @Getter
    private final String tag;
    @Getter
    private final String abbrTag;
    @Getter
    private final String publicTag;
    @Getter
    private final ChatColor color;

    private Title(String determiner, String name, ChatColor color, String tag)
    {
        this.determiner = determiner;
        this.name = name;
        this.abbrTag = color + "[" + tag + "]";
        this.publicTag = color + "[" + name + "]";
        this.abbr = tag;
        this.tag = "[" + tag + "]";
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

}
