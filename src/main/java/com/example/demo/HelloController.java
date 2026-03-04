package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class HelloController {
    Random randNum = new Random();
    int x = 40;
    int y = 40;
    private int infected = 0;
    Button[][] btn = new Button[x][y];
    int[][] gameGrid = new int[x][y];
    int[][] environmentGrid = new int[x][y];
    ArrayList<Person> people = new ArrayList<>();
    ArrayList<Zombies> zHorde = new ArrayList<>();
    ArrayList<Building> buildings = new ArrayList<>();
    private Boolean nightDay = false;
    private long timeOfDay;
    private int clickedX, clickedY;
    //GridPane gPane = new GridPane();
//    Image k = new Image("resources/Koala.jpg");
    @FXML
    private AnchorPane aPane;
    @FXML
    private TextField amountOfHumText, amountOfZomText;
    @FXML
    private GridPane gPane;
    @FXML
    private ListView<String> showStatsListView;
    @FXML
    private BarChart<String, Number> showPopulationBC;
    public HelloController(){
        nightDay = true;
        timeOfDay = System.nanoTime();
    }
    @FXML
    public void handleAddZomb(){
        int count;
        try {
            count = Integer.parseInt(amountOfZomText.getText().trim());
            if (count < 0) count = 0;
        } catch (NumberFormatException e) {
            return;
        }
        int maxAttempts = x * y * 2;
        for (int i = 0; i < count; i++){
            int rX = randNum.nextInt(x);
            int rY = randNum.nextInt(y);
            int attempts = 0;
            while ((gameGrid[rX][rY] != 0 || environmentGrid[rX][rY] != 0) && attempts < maxAttempts){
                rX = randNum.nextInt(x);
                rY = randNum.nextInt(y);
                attempts++;
            }
            if (gameGrid[rX][rY] == 0 && environmentGrid[rX][rY] == 0) {
                zHorde.add(new Zombies(rX, rY));
                gameGrid[rX][rY] = zHorde.get(zHorde.size() - 1).getIdentity();
            }
        }
    }
    @FXML
    public void handleSetBomb(){
        // Clear all people from grid and list
        for (Person a : people) {
            gameGrid[a.getOrgLoc().getX()][a.getOrgLoc().getY()] = 0;
        }
        people.clear();
        // Clear all zombies from grid and list
        for (Zombies z : zHorde) {
            gameGrid[z.getOrgLoc().getX()][z.getOrgLoc().getY()] = 0;
        }
        zHorde.clear();
        // Clear people inside buildings
        for (Building b : buildings) {
            b.getPeopleInBuilding().clear();
        }
        infected = 0;
        updateScreen();
        updateInfo();
    }
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    @FXML
    private void handleStart(ActionEvent event) {
        showPopulationBC.setAnimated(false);
        //after adding the grdipane in scenebuilder, modify the fxml manually to eliminate
        // rows and columns


        for (int i = 0; i < btn.length; i++) {
            for (int j = 0; j < btn[0].length; j++) {

                //Initializing 2D buttons with values i,j
                btn[i][j] = new Button();
                btn[i][j].setStyle("-fx-background-color:#d3d3d3");

                btn[i][j].setMinHeight(20);
                btn[i][j].setMinWidth(20);
                btn[i][j].setPrefHeight(20);
                btn[i][j].setPrefWidth(20);
//                btn[i][j].setPrefSize(25, 5);
                //Paramters:  object, columns, rows
                gPane.add(btn[i][j], j, i);
                gameGrid[i][j] = 0;


            }
        }

        gPane.setGridLinesVisible(true);

        gPane.setVisible(true);

        EventHandler z = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                //ObservableList<Node> children = gPane.getChildren();
                //used to get a list of all children in gridpane
//                System.out.println("hello");
//                ((ImageView) t.getSource()).setImage(new Image("resources/Penguins.jpg"));
//
//                System.out.println("Row:    " + GridPane.getRowIndex(((ImageView) t.getSource())));
//                System.out.println("Column: " + GridPane.getColumnIndex(((ImageView) t.getSource())));
                Integer row = GridPane.getRowIndex(((Button) t.getSource()));
                Integer col = GridPane.getColumnIndex(((Button) t.getSource()));
                clickedX = (row != null) ? row : 0;
                clickedY = (col != null) ? col : 0;
                System.out.println(clickedX);
                System.out.println(clickedY);
            }

        };
        for (int i = 0; i < btn.length; i++) {
            for (int j = 0; j < btn[0].length; j++) {
                btn[i][j].setOnMouseClicked(z);

            }
        }
        for (int i = 0; i < environmentGrid.length; i++) {
            for (int j = 0; j < environmentGrid[0].length; j++) {
                environmentGrid[i][j] = 0;//default
            }
        }
        buildings.clear();
        buildings.add(new Building(15, 4, 5, 3, 10, 1, 5));
        buildings.add(new Building(15, 20, 4, 6, 10, 1, 5));
        buildings.add(new Building(15, 4, 5, 3, 10, 1, 5));
        buildings.add(new Building(30, 18, 4, 6, 10, 1, 5));
        buildings.add(new Building(33, 8, 3, 5, 10, 2, 3));
        buildings.add(new Building(9, 12, 3, 5, 10, 2, 3));
        buildings.add(new Building(9, 28, 3, 5, 10, 2, 3));
        buildings.add(new Building(22, 17, 3, 5, 10, 2, 3));
        buildings.add(new Building(9, 28, 3, 5, 10, 2, 3));
        buildings.add(new Building(7, 1, 4, 4, 10, 3, 7));
        buildings.add(new Building(35, 35, 4, 4, 10, 3, 7));
        buildings.add(new Building(30, 35, 4, 4, 10, 3, 7));
        buildings.add(new Building(1, 35, 4, 4, 10, 3, 7));
        buildings.add(new Building(6, 35, 4, 4, 10, 3, 7));
        buildings.add(new Building(1, 1, 4, 4, 10, 3, 7));
        buildings.add(new Building(35, 1, 4, 4, 10, 3, 7));
        buildings.add(new Building(30, 1, 4, 4, 10, 3, 7));
        buildings.add(new Building(23, 8, 5, 6, 10, 4, 5 ));
        buildings.add(new Building(21, 28, 5, 6, 10, 4, 5));
        buildings.add(new Building(9, 12, 5, 6, 10, 4, 5));
        int maxRow = environmentGrid.length;
        int maxCol = environmentGrid[0].length;
        for (Building b: buildings) {
            for (int row = b.getBLoc().getX(); row < b.getBLoc().getX()+b.getLength() && row < maxRow; row++) {
                for (int col = b.getBLoc().getY(); col < b.getBLoc().getY()+b.getWidth() && col < maxCol; col++){
                    if (row >= 0 && col >= 0) {
                        environmentGrid[row][col] = b.getBuildingType();
                    }
                }
            }
        }

//

        start();
    }
    @FXML
    public void handleAddHuman()
    {
        int count;
        try {
            count = Integer.parseInt(amountOfHumText.getText().trim());
            if (count < 0) count = 0;
        } catch (NumberFormatException e) {
            return;
        }
        int maxAttempts = x * y * 2;
        for (int i = 0; i < count; i++){
            int rX = randNum.nextInt(x);
            int rY = randNum.nextInt(y);
            int attempts = 0;
            while ((gameGrid[rX][rY] != 0 || environmentGrid[rX][rY] != 0) && attempts < maxAttempts){
                rX = randNum.nextInt(x);
                rY = randNum.nextInt(y);
                attempts++;
            }
            if (gameGrid[rX][rY] == 0 && environmentGrid[rX][rY] == 0) {
                people.add(new Person(rX, rY));
                gameGrid[rX][rY] = people.get(people.size() - 1).getIdentity();
            }
        }
        updateScreen();
    }
    @FXML
    public void handleReset(){
        for (Person a: people){
            gameGrid[a.getOrgLoc().getX()][a.getOrgLoc().getY()] = 0;
        }
        for (Zombies z: zHorde){
            gameGrid[z.getOrgLoc().getX()][z.getOrgLoc().getY()] = 0;
        }
        people.clear();
        zHorde.clear();
        infected = 0;
        for (int i = 0; i < environmentGrid.length; i++) {
            for (int j = 0; j < environmentGrid[0].length; j++) {
                environmentGrid[i][j] = 0;
            }
        }
        int maxR = environmentGrid.length;
        int maxC = environmentGrid[0].length;
        for (Building b : buildings) {
            for (int row = b.getBLoc().getX(); row < b.getBLoc().getX() + b.getLength() && row < maxR; row++) {
                for (int col = b.getBLoc().getY(); col < b.getBLoc().getY() + b.getWidth() && col < maxC; col++) {
                    if (row >= 0 && col >= 0) {
                        environmentGrid[row][col] = b.getBuildingType();
                    }
                }
            }
        }
        updateScreen();
        updateInfo();
    }
    public String showNightOfDay(){
        if(nightDay == true){
            return "day";
        }
        return "night";
    }
    public void updateInfo(){
        if (showStatsListView == null || showPopulationBC == null) return;
        showStatsListView.getItems().clear();
        showStatsListView.getItems().add("Day or Night: "+ showNightOfDay());
        showStatsListView.getItems().add("-------------------------------");
        showStatsListView.getItems().add("Number Of Humans: " + people.size());
        showStatsListView.getItems().add("Number Of Zombies: " + zHorde.size());
        showStatsListView.getItems().add("-------------------------------");
        showStatsListView.getItems().add("Organisms:");
        showStatsListView.getItems().add("Pink - DarkRed = Humans");
        showStatsListView.getItems().add("Purple = Zombies");
        showStatsListView.getItems().add("--------------------------------");
        showStatsListView.getItems().add("Buildings:");
        showStatsListView.getItems().add("Yellow = School");
        showStatsListView.getItems().add("White = Work");
        showStatsListView.getItems().add("Green = Store");
        showStatsListView.getItems().add("Blue = House");
        showPopulationBC.getData().clear();
        showPopulationBC.setTitle("Sim Population");
        series.getData().clear();
        series.setName("Number of population");
        series.getData().add(new XYChart.Data("Humans", people.size()));
        series.getData().add(new XYChart.Data("Zombies", zHorde.size()));
        series.getData().add(new XYChart.Data("got infected", infected));
        showPopulationBC.getData().add(series);
    }

    public void updateScreen(){
//environment grid first, then overide with objects
        //1=water, 2 = grass
        for(int i=0; i<btn.length; i++) {
            for (int j = 0; j < btn[0].length; j++) {
                if (environmentGrid[i][j]==0){
                    if(nightDay == false) {
                        btn[i][j].setStyle("-fx-background-color:grey");
                    }else{
                        btn[i][j].setStyle("-fx-background-color:lightgrey");
                    }
                }
                else if (environmentGrid[i][j]==1){
                    btn[i][j].setStyle("-fx-background-color:white");
                }
                else if (environmentGrid[i][j]==2){
                    btn[i][j].setStyle("-fx-background-color:yellow");
                }
                else if (environmentGrid[i][j]==3){
                    btn[i][j].setStyle("-fx-background-color:lightblue");
                }
                else if (environmentGrid[i][j]==4){
                    btn[i][j].setStyle("-fx-background-color:lightgreen");
                }
            }
        }
        for(int i=0; i<btn.length; i++) {
            for (int j = 0; j < btn[0].length; j++) {
                if (gameGrid[i][j]>0&&gameGrid[i][j]<=3){
                    btn[i][j].setStyle("-fx-background-color:lightpink");
                }else
                if (gameGrid[i][j]>=4 && gameGrid[i][j]<=7){
                    btn[i][j].setStyle("-fx-background-color: red");
                }else
                if (gameGrid[i][j]>=8 && gameGrid[i][j]<10){
                    btn[i][j].setStyle("-fx-background-color:darkred");
                }
                else if (gameGrid[i][j]==10){
                    btn[i][j].setStyle("-fx-background-color:purple");
                }
            }
        }


    }
    public void start() {

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - timeOfDay > 20000000000.0){
                    if (showNightOfDay().equals("day")){
                        nightDay = false;
                    }else{
                        nightDay = true;
                    }
                    timeOfDay = System.nanoTime();
                }
                if(people.size()>0){
                    for (int i = 0;i<people.size();i++){
                        System.out.println(people.get(i).getRoutine());
                        if (people.get(i).checkNeighborZombies(zHorde, gameGrid, 5)) {
                                if (decideIfHappens(5)) {
                                    people.get(i).setRoutine(6);
                                    people.get(i).setDecided(true);
                                    System.out.println(people.get(i).getRoutine());
                                } else {
                                    people.get(i).setRoutine(5);
                                    people.get(i).setFear(people.get(i).getFear()-1);
                                    people.get(i).setBattlePower(people.get(i).getBattlePower()-1);
                                    people.get(i).setDecided(true);
                                }
                        }
                        if(zHorde.size()==0 ){
                            if(nightDay == false){
                                people.get(i).setRoutine(3);
                                people.get(i).setGoToBuild(people.get(i).findClosestBuildingSpec(buildings, 3));
                                people.get(i).setDecided(true);
                            }else {
                                if (people.get(i).getDecided() == false) {
                                    if (people.get(i).getIdentity() > 3) {
                                        if (decideIfHappens(people.get(i).getHunger())) {
                                            people.get(i).setRoutine(2);
                                            people.get(i).setGoToBuild(people.get(i).findClosestBuildingSpec(buildings, 2));
                                            people.get(i).setDecided(true);
                                        } else {
                                            people.get(i).setRoutine(1);
                                            people.get(i).setGoToBuild(people.get(i).findClosestBuildingSpec(buildings, 1));
                                            people.get(i).setDecided(true);
                                        }
                                    } else {
                                        people.get(i).setRoutine(4);
                                        people.get(i).setGoToBuild(people.get(i).findClosestBuildingSpec(buildings, 4));
                                        people.get(i).setDecided(true);
                                    }
                                }
                            }
                            System.out.println(people.get(i).getRoutine());
                        }else{
                            if (people.get(i).getDecided() == false){
                                if(nightDay == false) {
                                    if (decideIfHappens(people.get(i).getHunger())) {
                                        people.get(i).setRoutine(2);
                                        people.get(i).setGoToBuild(people.get(i).findClosestBuildingSpec(buildings, 2));
                                        people.get(i).setDecided(true);
                                    } else {
                                        people.get(i).setRoutine(3);
                                        people.get(i).setGoToBuild(people.get(i).findClosestBuildingSpec(buildings, 3));
                                        people.get(i).setDecided(true);
                                    }
                                }
                            }
                        }
                    }
                    for ( int i = 0; i < people.size(); i++){
                        if (now - people.get(i).getMoveTime() > 300000000.0 && people.get(i).getRoutine() == 6) {
                            people.get(i).moveTo(people.get(i).getZombieAttack().getOrgLoc().getX(),
                                    people.get(i).getZombieAttack().getOrgLoc().getY(), gameGrid, environmentGrid);
                            people.get(i).resetMoveTime();
                        }else
                        if (now - people.get(i).getMoveTime() > 300000000.0 && people.get(i).getRoutine() == 5) {
                            people.get(i).moveAway(people.get(i).getZombieAttack().getOrgLoc().getX(),
                                    people.get(i).getZombieAttack().getOrgLoc().getY(), gameGrid, environmentGrid);
                            people.get(i).resetMoveTime();
                        }else
                        if (now - people.get(i).getMoveTime() > 300000000.0 && (people.get(i).getRoutine() == 1||people.get(i).getRoutine() == 4||
                                people.get(i).getRoutine() == 3) && people.get(i).getGoToBuild() != null) {
                            people.get(i).moveTo(people.get(i).getClosestEntrance(people.get(i).getGoToBuild().getEntrance()).getX(),
                                    people.get(i).getClosestEntrance(people.get(i).getGoToBuild().getEntrance()).getY(), gameGrid,environmentGrid);
                            people.get(i).resetMoveTime();
                        }else
                        if (now - people.get(i).getMoveTime() > 300000000.0 && people.get(i).getRoutine() == 2 && people.get(i).getGoToBuild() != null) {
                            people.get(i).moveTo(people.get(i).getClosestEntrance(people.get(i).getGoToBuild().getEntrance()).getX(),
                                    people.get(i).getClosestEntrance(people.get(i).getGoToBuild().getEntrance()).getY(), gameGrid,environmentGrid);
                            people.get(i).setHunger(0);
                            people.get(i).resetMoveTime();
                        }else
                        if (now - people.get(i).getMoveTime() > 300000000.0 && people.get(i).getRoutine() == 0) {
                            people.get(i).changeLoc(gameGrid, environmentGrid);
                            people.get(i).resetMoveTime();
                        }

                        if(now - people.get(i).getHungerTime() > 3000000000.0){
                            people.get(i).setHunger(people.get(i).getHunger()+1);
                            people.get(i).setHungerTime();
                        }
                        if(now - people.get(i).getAgeTime() > 3000000000.0){
                            if(people.get(i).getIdentity() < 7) {
                                people.get(i).changeIdentity();
                                people.get(i).resetAgeTime();
                            }else{
                                people.get(i).resetAgeTime();
                            }
                        }
                    }
                    for (int i = 0; i< people.size(); i++){
                        if(people.get(i).getRoutine() == 10 && people.get(i).getGoToBuild() != null) {
                            if (now - people.get(i).getStayTime() > people.get(i).getGoToBuild().getStayTime()){
                                people.get(i).setStayTime();
                                people.get(i).setRoutine(0);
                                people.get(i).setIdentity(people.get(i).getTempIdent());
                                people.get(i).setDecided(false);
                                people.get(i).getGoToBuild().removePfromB(people.get(i));
                                people.get(i).setGoToBuild(null);
                                people.get(i).changeIdentVisual(gameGrid);
                            }
                        }
                    }
                    for (Person p: people) {
                        if (p.checkNeighborZombies(zHorde, gameGrid, 1) && (decideIfHappens(p.getFear()) || decideIfHappens(p.getBattlePower()))){
                            gameGrid[p.getZombieAttack().getOrgLoc().getX()][p.getZombieAttack().getOrgLoc().getY()]=0;
                            zHorde.remove(p.getZombieAttack());
                            p.resetFightTime();
                            System.out.println("hit");
                            p.setDecided(false);
                            p.setRoutine(0);
                        }
                        if (p.checkNeighborZombies(zHorde, gameGrid, 8) != true && p.getRoutine() == 5) {
                            p.setRoutine(0);
                            p.forgetZombieAttack();
                            p.setDecided(false);
                        }
                        if(p.getRoutine() == 6||p.getRoutine() == 5){
                            if(p.checkZombieAlive(zHorde)==false){
                                p.forgetZombieAttack();
                                p.setRoutine(0);
                            }
                        }
                        if(p.getRoutine() == 1 || p.getRoutine() == 4 || p.getRoutine() == 3 || p.getRoutine() == 2){
                            if(p.getGoToBuild() != null && p.checkNeighborBuilding(p.getGoToBuild().getEntrance(), environmentGrid, 1)){
                                p.setStayTime();
                                p.setTempIdent(p.getIdentity());
                                p.setIdentity(0);
                                p.setRoutine(10);
                                p.setFear(9);
                                p.setBattlePower(9);
                                p.getGoToBuild().addPtoB(p);
                                p.changeIdentVisual(gameGrid);
                            }
                        }
                    }
                    for (int i = 0; i< people.size(); i++){
//                        if((now - people.get(i).getAge() > 10000000000.0)){
//                            if(decideIfHappens(9)){
//                                gameGrid[people.get(i).getX()][people.get(i).getY()]=0;
//                                people.remove(i);
//                            }
//                        }
//                        if(now - people.get(i).getDeathTime() > 4000000000.0){
//                            people.get(i).resetDeathTime();
//                            if(people.get(i).getIdentity()>0 && people.get(i).getIdentity()<4) {
//                                if (decideIfHappens(3)) {
//                                    gameGrid[people.get(i).getOrgLoc().getX()][people.get(i).getOrgLoc().getY()] = 0;
//                                    people.remove(people.get(i));
//                                }
//                            }else
//                            if(people.get(i).getIdentity()>3 && people.get(i).getIdentity()<7) {
//                                if (decideIfHappens(2)) {
//                                    gameGrid[people.get(i).getOrgLoc().getX()][people.get(i).getOrgLoc().getY()] = 0;
//                                    people.remove(people.get(i));
//                                }
//                            }else
//                            if(people.get(i).getIdentity()>6 && people.get(i).getIdentity()<10) {
//                                if (decideIfHappens(5)) {
//                                    gameGrid[people.get(i).getOrgLoc().getX()][people.get(i).getOrgLoc().getY()] = 0;
//                                    people.remove(people.get(i));
//                                }
//                            }
//                        }

                    }
                }
                // Zombies in buildings: leave when stay time up, or try to infect (throttled to once per second)
                for (int i = 0; i<zHorde.size(); i++){
                    if(zHorde.get(i).getEnteredBuilding() == true && zHorde.get(i).getGoToBuild() != null) {
                        if (now - zHorde.get(i).getStayTime() > zHorde.get(i).getGoToBuild().getStayTime()){
                            zHorde.get(i).setStayTime();
                            zHorde.get(i).setIdentity(zHorde.get(i).getTempIdent());
                            zHorde.get(i).setEnteredBuilding(false);
                            zHorde.get(i).setDoingSomething(false);
                            zHorde.get(i).setGoToBuild(null);
                            zHorde.get(i).changeIdentVisual(gameGrid);

                        }else {
                            // Only infect if person still in people list (prevents same person infected by multiple zombies in one frame)
                            if (decideIfHappens(5)) {
                                ArrayList<Person> inBuilding = zHorde.get(i).getGoToBuild().getPeopleInBuilding();
                                if (!inBuilding.isEmpty()) {
                                    Person p = inBuilding.get(0);
                                    if (people.contains(p)) {
                                        zHorde.get(i).infectPerson(zHorde, p);
                                        Zombies newZombie = zHorde.get(zHorde.size() - 1);
                                        gameGrid[newZombie.getOrgLoc().getX()][newZombie.getOrgLoc().getY()] = newZombie.getIdentity();
                                        people.remove(p);
                                        zHorde.get(i).getGoToBuild().removePfromB(p);
                                        infected++;
                                    }
                                }
                            }
                        }
                    }
                }
                for (int i = 0; i< zHorde.size(); i++) {
                    if (zHorde.get(i).checkNeighborPerson(people, gameGrid, 1) && decideIfHappens(5)) {
                        Person eaten = zHorde.get(i).personEaten();
                        // Only infect if still in people list - prevents multiple zombies infecting same person in one frame
                        if (!people.contains(eaten)) continue;
                        gameGrid[eaten.getOrgLoc().getX()][eaten.getOrgLoc().getY()] = 0;
                        zHorde.get(i).infectPerson(zHorde, eaten);
                        Zombies newZombie = zHorde.get(zHorde.size() - 1);
                        gameGrid[newZombie.getOrgLoc().getX()][newZombie.getOrgLoc().getY()] = newZombie.getIdentity();
                        people.remove(eaten);
                        infected++;
                        zHorde.get(i).resetFightTime();
                    }
                    for (int j = 0; j< buildings.size(); j++){
                        if(zHorde.get(i).checkNeighborBuilding(buildings.get(j).getEntrance(), environmentGrid, 1) && decideIfHappens(2)
                                &&zHorde.get(i).getDoingSomething() == false){
                            zHorde.get(i).setStayTime();
                            zHorde.get(i).setGoToBuild(buildings.get(j));
                            zHorde.get(i).setEnteredBuilding(true);
                            zHorde.get(i).setDoingSomething(true);
                            zHorde.get(i).setTempIdent(zHorde.get(i).getIdentity());
                            zHorde.get(i).setIdentity(0);
                            zHorde.get(i).changeIdentVisual(gameGrid);
                        }
                    }
                    if(now - zHorde.get(i).getMoveTime() > 300000000.0) {
                        if(people.size()!=0 && zHorde.get(i).checkNeighborPerson(people, gameGrid, 3) && decideIfHappens(5)) {
                            zHorde.get(i).moveTo(zHorde.get(i).personEaten().getOrgLoc().getX(), zHorde.get(i).personEaten().getOrgLoc().getY(), gameGrid, environmentGrid);
                            zHorde.get(i).resetMoveTime();
                        }else if (now - zHorde.get(i).getMoveTime() > 500000000.0) {

                            //code to see if the current ant has a neighbor.
                            //Can be moved depending on how often you want to check.
//                            if(people.get(i).checkNeighborOpposite(people,gameGrid)){
//                                System.out.println("found opposite");
//                                if(people.get(i).getMaleFemale()==1)
//                                    people.get(i).reproduce(people,gameGrid);
//                            }
                            System.out.println(zHorde.size());
                            zHorde.get(i).changeLoc(gameGrid, environmentGrid);
                            zHorde.get(i).resetMoveTime();
                        }
                    }

                }
                updateScreen();
                updateInfo();
//                System.out.println("test");
            }
        }.start();
    }
    public boolean decideIfHappens(int chance){
        //can add as many cases as you would like with whatever probabilities you want
        //I made the numbers large, but that is because I am running the code every nanosecond.
        //If you run the code less often then change the numbers.  Or you can add a variable
        //multiplier to make it more universal.
        switch (chance){
            case 0://Almost no chance of happening
                System.out.println("blah");
                return randNum.nextInt(100000)>99990;
            case 1://slight chance of happening

                return randNum.nextInt(100000)>85000;

            case 2://slightly greater chance of happening etc...
                System.out.println("blah2");
                return randNum.nextInt(100000)>95000;
            case 3:
                return randNum.nextInt(100000)>60000;
            case 4:
                return randNum.nextInt(100000)>40000;
            case 5:
                return randNum.nextInt(100000)>35000;
            case 6:
                return randNum.nextInt(100000)>30000;
            case 7:
                return randNum.nextInt(100000)>20000;
            case 8:
                return randNum.nextInt(100000)>10000;
            case 9://95% chance of happening
                return randNum.nextInt(1000)>50;
            default://always happens
                return randNum.nextInt(1000)>0;
        }


    }

}