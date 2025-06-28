package xyz.telosaddon.yuno.features;


import xyz.telosaddon.yuno.TelosAddon;

import static xyz.telosaddon.yuno.utils.TabListUtils.mc;


public class GammaFeature extends ToggleableFeature{
    GammaFeature() {
        if (TelosAddon.CONFIG.gammaSetting){
            mc.options.getGamma().setValue(1500D);
        }
        else{
            mc.options.getGamma().setValue(100D);
        }
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


