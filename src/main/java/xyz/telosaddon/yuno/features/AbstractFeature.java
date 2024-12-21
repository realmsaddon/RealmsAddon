package xyz.telosaddon.yuno.features;

import xyz.telosaddon.yuno.utils.config.TelosConfig;

public abstract class AbstractFeature {

	private final TelosConfig config;
	private final String featureName;
	private boolean enabled;

	protected AbstractFeature(TelosConfig config, String featureName){
		this.config = config;
		this.featureName = featureName;
	}

	private String getEnabledKey() {
		return featureName;
	}

	protected String getFeatureName() {
		return this.featureName;
	}

	protected TelosConfig getConfig() {
		return this.config;
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
		if (enabled) {
			enable();
		} else {
			disable();
		}


	}
}
