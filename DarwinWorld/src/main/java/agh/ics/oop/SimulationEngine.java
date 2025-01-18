package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {

    List<Simulation> simulations;
    private final List<Thread> threads = new ArrayList<>();
    ExecutorService executorService = Executors.newFixedThreadPool(4);

    public SimulationEngine() {

    }


    public void runAsyncInThreadPool(Simulation simulation) {
        executorService.submit(simulation);
    }

}
