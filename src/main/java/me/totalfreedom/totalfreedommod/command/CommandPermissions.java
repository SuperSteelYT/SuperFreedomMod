package me.totalfreedom.totalfreedommod.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.rank.Title;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandPermissions
{

    Rank level();

    SourceType source();

    boolean blockHostConsole() default false;
}