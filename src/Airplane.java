public class Airplane extends Aircraft {
    public Airplane() {
        takeoffTime = 10;
        landingTime = 7;
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
        System.out.println("Airplane: " + message);
    }

    @Override
    public String getType() {
        return "airplane";
    }
}
