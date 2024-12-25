package xyz.telosaddon.yuno.features;

import io.wispforest.owo.config.Option;
import xyz.telosaddon.yuno.TelosAddon;

import static xyz.telosaddon.yuno.utils.TabListUtils.mc;


public class GammaFeature extends ToggleableFeature{
    GammaFeature() {
        super(TelosAddon.CONFIG.keys.gammaSetting);
    }


    @Override
    public void enable(){
        mc.options.getGamma().setValue(1500D);
    }

    @Override
    public void disable(){
        mc.options.getGamma().setValue(1D);
    }
}


