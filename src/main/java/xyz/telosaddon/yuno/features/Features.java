package xyz.telosaddon.yuno.features;

import xyz.telosaddon.yuno.TelosAddon;

public class Features {
    public static final BossTrackerFeature BOSS_TRACKER_FEATURE = new BossTrackerFeature(TelosAddon.getInstance().getConfig(), "BossTrackerFeature");
}
