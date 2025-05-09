import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AirTrafficControlTower controlTower = new AirportControlTower(2);
        Aircraft airplane = new Airplane();
        Aircraft helicopter = new Helicopter();
        Aircraft hotAirBalloon = new HotAirBalloon();

        airplane.connectToTrafficControl(controlTower);
        helicopter.connectToTrafficControl(controlTower);
        hotAirBalloon.connectToTrafficControl(controlTower);


        List<Thread> threads = new ArrayList<>();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                airplane.requestLanding();
                airplane.requestTakeoff();
                helicopter.requestLanding();
            }
        });
        thread1.start();
        threads.add(thread1);

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                hotAirBalloon.requestLanding();
                hotAirBalloon.requestTakeoff();
                airplane.requestLanding();
            }
        });
        thread2.start();
        threads.add(thread2);

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}