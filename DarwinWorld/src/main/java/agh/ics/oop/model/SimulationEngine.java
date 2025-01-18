package agh.ics.oop.model;

import agh.ics.oop.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {

    List<Simulation> simulations;
    private final List<Thread> threads = new ArrayList<>();
    ExecutorService executorService = Executors.newFixedThreadPool(4);

    public SimulationEngine(List<Simulation> simulations) {
        this.simulations = simulations;
    }

    public void awaitSimulationsEnd() {
        try{
            for (Thread thread : threads) {
                thread.join();
            }
            executorService.shutdown();
            if(!executorService.awaitTermination(100, TimeUnit.SECONDS)){
                System.out.println("Simulation threads did not terminate");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void runAsyncInThreadPool(){
        for (Simulation simulation : simulations) {
            executorService.submit(simulation);
        }
        awaitSimulationsEnd();
    }

}
