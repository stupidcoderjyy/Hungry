package hungry.init;

import net.minecraft.entity.player.HungerManager;

public class ProgressTracker {
    private long bleedTime;
    private long startTime;
    private boolean active;
    private boolean isBleeding;

    public ProgressTracker() {
        this.active = false;
    }

    public void reset(HungerManager hg) {
        this.startTime = System.currentTimeMillis();
        this.bleedTime = startTime + (80 + Math.round((hg.getFoodLevel() + hg.getSaturationLevel()) * 2.77)) * 50;
        this.isBleeding = false;
    }

    public void startBleeding() {
        startTime = System.currentTimeMillis();
        bleedTime = startTime + 4000;
        isBleeding = true;
    }

    public long getBleedTime() {
        return bleedTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
        this.isBleeding = false;
    }

    public boolean isBleeding() {
        return isBleeding;
    }
}