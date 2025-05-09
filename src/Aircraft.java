public abstract class Aircraft {
    protected AirTrafficControlTower trafficControl;
    protected Integer takeoffTime;
    protected Integer landingTime;

    public abstract void requestTakeoff();
    public abstract void requestLanding();
    public abstract void notifyAirTrafficControl(String message);
    public abstract String getType();

    public Integer getTakeoffTime() {
        return takeoffTime;
    }

    public Integer getLandingTime() {
        return landingTime;
    }

    public void connectToTrafficControl(AirTrafficControlTower trafficControl) {
        this.trafficControl = trafficControl;
    }
}
