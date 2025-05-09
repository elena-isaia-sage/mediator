public class Helicopter extends Aircraft {
    public Helicopter() {
        takeoffTime = 5;
        landingTime = 5;
    }

    @Override
    public void requestTakeoff() {
        trafficControl.requestTakeoff(this);
    }

    @Override
    public void requestLanding() {
        trafficControl.requestLanding(this);
    }

    @Override
    public void notifyAirTrafficControl(String message) {
        System.out.println("Helicopter: " + message);
    }

    @Override
    public String getType() {
        return "helicopter";
    }
}
