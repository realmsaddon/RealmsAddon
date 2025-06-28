package xyz.telosaddon.yuno.features;


import static xyz.telosaddon.yuno.TelosAddon.CONFIG;
import static xyz.telosaddon.yuno.utils.config.ConfigUtils.addConfigBinding;

public abstract class AbstractFeature {

	private boolean enabled;

	protected AbstractFeature(Option.Key configKey){
		addConfigBinding(configKey, this::setToggle);
	}


	public boolean isEnabled() {
		return enabled;
	}

	protected void disable() {

	}

	protected void enable() {

	}

	public void setToggle(boolean enabled) {
		this.enabled = enabled;
		System.out.println("Feature " + this.getClass().getSimpleName() + " is now " + (enabled ? "enabled" : "disabled"));
		if (enabled) {
			enable();
		} else {
			disable();
		}


	}
}
