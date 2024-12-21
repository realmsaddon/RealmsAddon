package xyz.telosaddon.yuno.ui.tabs;

import xyz.telosaddon.yuno.TelosAddon;
import xyz.telosaddon.yuno.utils.config.TelosConfig;

public abstract class AbstractTab {



    public void toggle(String name, String btnName, boolean toggled) {

        String toggleText = toggled ? "§6Set §7'§f" + btnName + "§7' §6to §a§lTRUE§7!" : "§6Set §7'§f" + btnName + "§7' §6to §c§lFALSE§7!";
        TelosAddon.getInstance().sendMessage(toggleText);
    }



}
