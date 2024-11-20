package hungry.init;

import net.minecraft.entity.player.HungerManager;

public class ProgressTracker {
    private int progress;
    private int maxProgress;
    private boolean active;
    private boolean isBleeding;

    public ProgressTracker(int maxProgress) {
        this.maxProgress = maxProgress;
        this.progress = maxProgress;
        this.active = false;
    }

    public void reset(HungerManager hg) {
        this.maxProgress = 80 + Math.round((hg.getFoodLevel() + hg.getSaturationLevel()) * 2.77f);
        this.progress = maxProgress;
        this.isBleeding = false;
    }

    public void reduceProgress(int amount) {
        if (active) {
            this.progress = Math.max(this.progress - amount, 0);
            if (progress == 0) {
                startBleeding();
            }
        }
    }

    public void startBleeding() {
        maxProgress = 80;
        progress = maxProgress;
        isBleeding = true;
    }

    public int getProgress() {
        return progress;
    }

    public int getMaxProgress() {
        return maxProgress;
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