public class HotAirBalloon extends Aircraft {
    public HotAirBalloon() {
        takeoffTime = 20;
        landingTime = 15;
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
        System.out.println("Hot air balloon: " + message);
    }

    @Override
    public String getType() {
        return "hot air balloon";
    }
}
