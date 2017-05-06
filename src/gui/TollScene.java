package gui;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.Random;
import java.util.ResourceBundle;

public class TollScene extends BorderPane {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private AnchorPane anchor;
    @FXML private Button execute;
    @FXML private Label laneThreeTotalTrucks;
    @FXML private Label laneOneTime;
    @FXML private Rectangle laneOneCar;
    @FXML private Label laneOneTotalCars;
    @FXML private Label laneOneTotalTrucks;
    @FXML private Rectangle laneOneTruck;
    @FXML private Label laneThreeTime;
    @FXML private Rectangle laneThreeCar;
    @FXML private Label laneThreeTotalCars;
    @FXML private Rectangle laneThreeTruck;
    @FXML private Label laneTwoTime;
    @FXML private Rectangle laneTwoCar;
    @FXML private Label laneTwoTotalCars;
    @FXML private Label laneTwoTotalTrucks;
    @FXML private Rectangle laneTwoTruck;
    @FXML private Label laneZeroTime;
    @FXML private Rectangle laneZeroCar;
    @FXML private Label laneZeroTotalCars;
    @FXML private Label laneZeroTotalTrucks;
    @FXML private Rectangle laneZeroTruck;
    @FXML private ChoiceBox<String> timeOptions;
    @FXML private Label totalNumTrucks;
    @FXML private Label totalNumCars;
    @FXML private Label time;
    private final int EZ_PASS_MIN = 2;
    private final int EZ_PASS_MAX = 5;
    private int laneOneCarCounter;
    private int laneOneTruckCounter;
    private int laneTwoCarCounter;
    private int laneTwoTruckCounter;
    private int laneThreeCarCounter;
    private int laneThreeTruckCounter;
    private int laneZeroCarCounter;
    private int laneZeroTruckCounter;
    private int totalCars;
    private int totalTrucks;
    private Timeline t;

    public TollScene() {
        //Loads fxml file into the Scene.
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(
                "gui\\display.fxml"));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println("Error or something");
        }
    }

    @FXML void initialize() {
        try {
            t.stop();
        } catch (Exception e) { }

        timeOptions.getItems().removeAll(timeOptions.getItems());
        timeOptions.getItems().addAll("4pm to 5pm", "5pm to 6pm", "6pm to 7pm");
        timeOptions.getSelectionModel().select("4pm to 5pm");

        execute.setOnAction(event -> {
            laneZeroTotalCars.setText("Total cars: 0");
            laneZeroTotalTrucks.setText("Total trucks: 0");

            laneOneTotalCars.setText("Total cars: 0");
            laneOneTotalTrucks.setText("Total trucks: 0");

            laneTwoTotalCars.setText("Total cars: 0");
            laneTwoTotalTrucks.setText("Total trucks: 0");

            laneThreeTotalCars.setText("Total cars: 0");
            laneThreeTotalTrucks.setText("Total trucks: 0");

            laneOneCarCounter = 0;
            laneOneTruckCounter = 0;
            laneTwoCarCounter = 0;
            laneTwoTruckCounter = 0;
            laneThreeCarCounter = 0;
            laneThreeTruckCounter = 0;
            laneZeroCarCounter = 0;
            laneZeroTruckCounter = 0;
            totalCars = 0;
            totalTrucks = 0;

            int index = timeOptions.getSelectionModel().getSelectedIndex();
            int currentCashMin;
            int currentCashMax;
            double truckProportion;
            double rightProportion;
            double ezProportion;
            double generalTime;
            if(index == 0) {
                currentCashMin = 2;
                currentCashMax = 29;
                rightProportion = .32;
                truckProportion = .129;
                ezProportion = .842;
                generalTime = 4250;
            } else if(index == 1) {
                currentCashMin = 2;
                currentCashMax = 30;
                rightProportion = .29;
                truckProportion = .085;
                ezProportion = .925;
                generalTime = 3000;
            } else {
                currentCashMin = 4;
                currentCashMax = 29;
                rightProportion = .317;
                truckProportion = .132;
                ezProportion = .834;
                generalTime = 4250;
            }

            long startTime = System.nanoTime();

            Timeline count = new Timeline(new KeyFrame(Duration.seconds(1), counter -> {
                time.setText("Elapsed time: " + (System.nanoTime() - startTime) / 1000000000 + "s");
            }));
            count.setCycleCount(Timeline.INDEFINITE);
            count.playFromStart();

            t = new Timeline(new KeyFrame(Duration.millis(generalTime), timer -> {
                Random rand = new Random();
                double right = rand.nextDouble();
                double truck = rand.nextDouble();
                double ez = rand.nextDouble();
                if(right > rightProportion) { //If the car is on the left side
                    if(ez > ezProportion) { //If the car is in the cash lane
                        if(truck > truckProportion) { //If the current vehicle is a car
                            if(!laneOneCar.isVisible() && !laneOneCar.isVisible()) { //Check to make sure there isn't already a car there
                                displayCarLaneOne(rand.nextInt(currentCashMax - currentCashMin) + currentCashMin);
                            }
                        } else { //If the current vehicle is a truck
                            if(!laneOneCar.isVisible() && !laneOneCar.isVisible()) { //Check to make sure there isn't already a car there
                                displayTruckLaneOne(rand.nextInt(currentCashMax - currentCashMin) + currentCashMin);
                            }
                        }
                    } else { //If the car is in EZPass lane
                        if(truck > truckProportion) { //If the current vehicle is a car
                            if(!laneZeroCar.isVisible() && !laneZeroTruck.isVisible()) { //Check to make sure there isn't already a car there
                                displayCarLaneZero(rand.nextInt(EZ_PASS_MAX - EZ_PASS_MIN) + EZ_PASS_MIN);
                            }
                        } else { //If the current vehicle is a truck
                            if(!laneZeroCar.isVisible() && !laneZeroTruck.isVisible()) { //Check to make sure there isn't already a car there
                                displayTruckLaneZero(rand.nextInt(EZ_PASS_MAX - EZ_PASS_MIN) + EZ_PASS_MIN);
                            }
                        }
                    }
                } else { //If the car is on the right side
                    if(ez > ezProportion) { //If the car is in the cash lane
                        if(truck > truckProportion) { //If the current vehicle is a car
                            if(!laneTwoCar.isVisible() && !laneTwoTruck.isVisible()) { //Check to make sure there isn't already a car there
                                displayCarLaneTwo(rand.nextInt(currentCashMax - currentCashMin) + currentCashMin);
                            }
                        } else { //If the current vehicle is a truck
                            if(!laneTwoCar.isVisible() && !laneTwoTruck.isVisible()) { //Check to make sure there isn't already a car there
                                displayTruckLaneTwo(rand.nextInt(currentCashMax - currentCashMin) + currentCashMin);
                            }
                        }
                    } else { //If the car is in EZPass lane
                        if(truck > truckProportion) { //If the current vehicle is a car
                            if(!laneThreeCar.isVisible() && !laneThreeTruck.isVisible()) { //Check to make sure there isn't already a car there
                                displayCarLaneThree(rand.nextInt(EZ_PASS_MAX - EZ_PASS_MIN) + EZ_PASS_MIN);
                            }
                        } else { //If the current vehicle is a truck
                            if(!laneThreeCar.isVisible() && !laneThreeTruck.isVisible()) { //Check to make sure there isn't already a car there
                                displayTruckLaneThree(rand.nextInt(EZ_PASS_MAX - EZ_PASS_MIN) + EZ_PASS_MIN);
                            }
                        }
                    }
                }

            }));
            t.setCycleCount(Timeline.INDEFINITE);
            t.playFromStart();
        });

        this.setCenter(anchor);
    }

    private void displayCarLaneZero(int seconds) {
        laneZeroCarCounter++;
        totalCars++;
        updateCounts();
        laneZeroTime.setText("Time: " + seconds + "s");
        laneZeroCar.setVisible(true);
        Timeline t = new Timeline(new KeyFrame(Duration.seconds(seconds), event -> {}));
        t.setOnFinished(event -> {
            laneZeroCar.setVisible(false);
        });
        t.playFromStart();
    }

    private void displayTruckLaneZero(int seconds) {
        laneZeroTruckCounter++;
        totalTrucks++;
        updateCounts();
        laneZeroTime.setText("Time: " + seconds + "s");
        laneZeroTruck.setVisible(true);
        Timeline t = new Timeline(new KeyFrame(Duration.seconds(seconds), event -> {}));
        t.setOnFinished(event -> {
            laneZeroTruck.setVisible(false);
        });
        t.playFromStart();
    }

    private void displayCarLaneOne(int seconds) {
        laneOneCarCounter++;
        totalCars++;
        updateCounts();
        laneOneTime.setText("Time: " + seconds + "s");
        laneOneCar.setVisible(true);
        Timeline t = new Timeline(new KeyFrame(Duration.seconds(seconds), event -> {}));
        t.setOnFinished(event -> {
            laneOneCar.setVisible(false);
        });
        t.playFromStart();
    }

    private void displayTruckLaneOne(int seconds) {
        laneOneTruckCounter++;
        totalTrucks++;
        updateCounts();
        laneOneTime.setText("Time: " + seconds + "s");
        laneOneTruck.setVisible(true);
        Timeline t = new Timeline(new KeyFrame(Duration.seconds(seconds), event -> {}));
        t.setOnFinished(event -> {
            laneOneTruck.setVisible(false);
        });
        t.playFromStart();
    }

    private void displayCarLaneTwo(int seconds) {
        laneTwoCarCounter++;
        totalCars++;
        updateCounts();
        laneTwoTime.setText("Time: " + seconds + "s");
        laneTwoCar.setVisible(true);
        Timeline t = new Timeline(new KeyFrame(Duration.seconds(seconds), event -> {}));
        t.setOnFinished(event -> {
            laneTwoCar.setVisible(false);
        });
        t.playFromStart();
    }

    private void displayTruckLaneTwo(int seconds) {
        laneTwoTruckCounter++;
        totalTrucks++;
        updateCounts();
        laneTwoTime.setText("Time: " + seconds + "s");
        laneTwoTruck.setVisible(true);
        Timeline t = new Timeline(new KeyFrame(Duration.seconds(seconds), event -> {}));
        t.setOnFinished(event -> {
            laneTwoTruck.setVisible(false);
        });
        t.playFromStart();
    }

    private void displayCarLaneThree(int seconds) {
        laneThreeCarCounter++;
        totalCars++;
        updateCounts();
        laneThreeTime.setText("Time: " + seconds + "s");
        laneThreeCar.setVisible(true);
        Timeline t = new Timeline(new KeyFrame(Duration.seconds(seconds), event -> {}));
        t.setOnFinished(event -> {
            laneThreeCar.setVisible(false);
        });
        t.playFromStart();
    }

    private void displayTruckLaneThree(int seconds) {
        laneThreeTruckCounter++;
        totalTrucks++;
        updateCounts();
        laneThreeTime.setText("Time: " + seconds + "s");
        laneThreeTruck.setVisible(true);
        Timeline t = new Timeline(new KeyFrame(Duration.seconds(seconds), event -> {}));
        t.setOnFinished(event -> {
            laneThreeTruck.setVisible(false);
        });
        t.playFromStart();
    }

    private void updateCounts() {
        laneZeroTotalCars.setText("Total cars: " + laneZeroCarCounter);
        laneZeroTotalTrucks.setText("Total trucks: " + laneZeroTruckCounter);

        laneOneTotalCars.setText("Total cars: " + laneOneCarCounter);
        laneOneTotalTrucks.setText("Total trucks: " + laneOneTruckCounter);

        laneTwoTotalCars.setText("Total cars: " + laneTwoCarCounter);
        laneTwoTotalTrucks.setText("Total trucks: " + laneTwoTruckCounter);

        laneThreeTotalCars.setText("Total cars: " + laneThreeCarCounter);
        laneThreeTotalTrucks.setText("Total trucks: " + laneThreeTruckCounter);

        totalNumCars.setText("Cars: " + totalCars);
        totalNumTrucks.setText("Trucks: " + totalTrucks);
    }
}
