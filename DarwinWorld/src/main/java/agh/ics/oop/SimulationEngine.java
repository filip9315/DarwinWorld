package agh.ics.oop;

import agh.ics.oop.presenter.SimulationPresenter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
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

    public void createNewSimulation(Simulation simulation) throws IOException {
        Stage newStage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));

            BorderPane root = loader.load();

            SimulationPresenter simulationPresenter = loader.getController();

            simulationPresenter.setSimulation(simulation);

            runAsyncInThreadPool(simulation);

            Scene newScene = new Scene(root);
            newStage.setScene(newScene);

            newStage.setTitle("Simulation");
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runAsyncInThreadPool(Simulation simulation) {
        executorService.submit(simulation);
    }

}
