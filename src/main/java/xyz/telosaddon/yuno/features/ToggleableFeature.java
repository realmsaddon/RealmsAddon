package xyz.telosaddon.yuno.features;

import io.wispforest.owo.config.Option;
import xyz.telosaddon.yuno.TelosAddon;

import java.util.Objects;

public class ToggleableFeature extends AbstractFeature{
    Option.Key configKey;

    protected ToggleableFeature(Option.Key configKey) {
        super(configKey);
        setToggle((Boolean) Objects.requireNonNull(TelosAddon.CONFIG.optionForKey(configKey)).value());
    }


}
