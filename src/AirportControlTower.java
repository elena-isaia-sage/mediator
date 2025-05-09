import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AirportControlTower implements AirTrafficControlTower {
    private final Integer laneNumber;
    private final Map<Integer, Aircraft> lanes;
    private final SimpleLock lock;

    public AirportControlTower(Integer laneNumber) {
        this.laneNumber = laneNumber;
        this.lanes = new ConcurrentHashMap<>();
        this.lock = new SimpleLock();
    }

    @Override
    public void requestTakeoff(Aircraft aircraft) {
        aircraft.notifyAirTrafficControl("Requesting takeoff clearance.");
        try {
            boolean tookOff = false;
            for (Integer key = 1; key <= laneNumber; key++) {
                if (lanes.get(key) == aircraft) {
                    try {
                        boolean locked = lock.tryLock(key);
                        if (locked) {
                            Thread.sleep(aircraft.getTakeoffTime() * 1000);
                            lanes.remove(key);
                            System.out.println("Takeoff completed on lane: " + key +
                                    " by aircraft: " + aircraft.getType() + ".");
                            tookOff = true;
                        }
                    } finally {
                        lock.unlock(key);
                    }

                    if (tookOff) {
                        break;
                    }
                }
            }
            if (!tookOff) {
                System.out.println("Error: Aircraft: " + aircraft.getType() +
                        " couldn't take off because it is not in this airport.");
            }
        } catch (InterruptedException e) {
            System.out.println("Error: Aircraft: " + aircraft.getType() +
                    " couldn't take off. \nError message: " + e.getMessage());
        }
    }

    @Override
    public void requestLanding(Aircraft aircraft) {
        aircraft.notifyAirTrafficControl("Requesting landing clearance.");
        try {
            boolean landed = false;
            while (!landed) {
                for (Integer key = 1; key <= laneNumber; key++) {
                    if (!lanes.containsKey(key)) {
                        try {
                            boolean locked = lock.tryLock(key);
                            if (locked) {
                                Thread.sleep(aircraft.getLandingTime() * 1000);
                                lanes.put(key, aircraft);
                                System.out.println("Landing completed on lane: " + key +
                                        " by aircraft: " + aircraft.getType() + ".");
                                landed = true;
                            }
                        } finally {
                            lock.unlock(key);
                        }
                    }
                    if (landed) {
                        break;
                    }
                }

//                if (!landed) {
//                    System.out.println("Error: Aircraft: " + aircraft.getType() +
//                            " couldn't land. Trying again...");
//                }
            }
        } catch (InterruptedException e) {
            System.out.println("Error: Aircraft: " + aircraft.getType() +
                    " couldn't land. \nError message: " + e.getMessage());
        }
    }
}
