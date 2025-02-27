public interface Deadline {
    boolean isDeadlineMet(long currentDay);
    void setDeadline(long deadline);
}

class VirtualClock {
    private long currentDay;

    public VirtualClock() {
        currentDay = 0;
    }

    public void tick() {
        currentDay++;
    }

    public long getCurrentDay() {
        return currentDay;
    }
}

class TimeManager {
    private VirtualClock virtualClock;

    public TimeManager() {
        virtualClock = new VirtualClock();
        startClock();
    }

    private void startClock() {
        Thread clockThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10000);
                } 
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                virtualClock.tick();
            }
        });
        clockThread.start();
    }

    public long getCurrentDay() {
        return virtualClock.getCurrentDay();
    }

    public VirtualClock getVirtualClock() {
        return virtualClock;
    }
}
