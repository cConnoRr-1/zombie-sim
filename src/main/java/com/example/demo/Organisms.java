package com.example.demo;

import java.util.ArrayList;

public class Organisms {
    private Locations orgLoc;
    private long ageTime;
    String loc;
    private long moveTime;
    private long reproduceTime;
    private Long fightTime;
    private int identity;
    private long deathTime;
    private long inBuildingTime;
    private Zombies zombieAttack;
    private Person pEaten;
    private Building goToBuild;
    private boolean decided;
    private int tempIdent;
    private long stayTime;
    public Organisms(int x,int y, int id){
        orgLoc = new Locations(x, y);
        reproduceTime = System.nanoTime();
        ageTime = System.nanoTime();
        fightTime = System.nanoTime();
        moveTime = System.nanoTime();
        identity = id;
        deathTime = System.nanoTime();
    }
    public void changeIdentVisual(int[][] grid){
        grid[orgLoc.getX()][orgLoc.getY()] = identity;
    }
    public void setGoToBuild(Building b){
        goToBuild = b;
    }
    public Building getGoToBuild(){
        return goToBuild;
    }
    public void setStayTime(){
        stayTime = System.nanoTime();
    }
    public long getStayTime(){
        return stayTime;
    }
    public boolean getDecided(){
        return decided;
    }
    public void setDecided(boolean n){
        decided = n;
    }
    public int getIdentity() {
        return identity;
    }
    public void changeIdentity(){
        identity++;
    }
    public void setIdentity(int n){
        identity = n;
    }
    public int getTempIdent(){
        return tempIdent;
    }
    public void setTempIdent(int n){
        tempIdent = n;
    }
    public long getMoveTime(){
        return moveTime;
    }
    public void resetMoveTime(){
        moveTime = System.nanoTime();
    }
    public boolean checkEmptyAroundMe(int i, int j, int tempGrid[][]){
        return tempGrid[i][j] ==0;
    }
    public Locations getOrgLoc(){
        return orgLoc;
    }
    public long getDeathTime(){
        return deathTime;
    }
    public void resetDeathTime(){
        deathTime = System.nanoTime();
    }
    public long getAgeTime(){
        return ageTime;
    }
    public void resetAgeTime(){
        ageTime = System.nanoTime();
    }
    public void resetFightTime(){
        fightTime = System.nanoTime();
    }
    public long getFightTime(){
        return fightTime;
    }
    public long getReproduceTime() {
        return reproduceTime;
    }
    public void setReproduceTime(){
        reproduceTime = System.nanoTime();
    }
    public long getInBuildingTime(){
        return inBuildingTime;
    }
    public void setInBuildingTime(){
        inBuildingTime = System.nanoTime();
    }
    public Zombies getZombieAttack(){
        return zombieAttack;
    }
    public Person personEaten(){
        return pEaten;
    }
    public void forgetPersonEaten(){
        pEaten = null;
    }
    public void forgetZombieAttack(){
        zombieAttack = null;
    }
    public boolean checkZombieAlive(ArrayList<Zombies> z){
        if (zombieAttack == null) {
            return false;
        }
        for (int i = 0; i < z.size(); i++){
            if(zombieAttack.equals(z.get(i))){
                return true;
            }
        }
        return false;
    }
    public boolean checkNeighborZombies(ArrayList<Zombies> tempPeople, int tempGrid[][], int radius){
//        System.out.println("hi");
        for (int i = 0;i<tempPeople.size();i++){
//                System.out.println(tempAnt.get(i).getX());
            if(tempPeople.get(i).getOrgLoc().getX() >=getOrgLoc().getX()-radius &&
                    tempPeople.get(i).getOrgLoc().getX()<=getOrgLoc().getX()+radius &&
                    tempPeople.get(i).getOrgLoc().getY() >=getOrgLoc().getY()-radius
                    && tempPeople.get(i).getOrgLoc().getY()<=getOrgLoc().getY()+radius&& tempPeople.get(i).getIdentity()>9){
                System.out.println("p to z");
//                tempGrid[tempPeople.get(i).getOrgLoc().getX()][tempPeople.get(i).getOrgLoc().getY()]=0;
                zombieAttack = tempPeople.get(i);


                return true;
            }
        }

        return false;
    }
    public boolean checkNeighborPerson(ArrayList<Person> tempPeople, int tempGrid[][], int range){
//        System.out.println("hi");
        for (int i = 0;i<tempPeople.size();i++){
//                System.out.println(tempAnt.get(i).getX());
            if(tempPeople.get(i).getOrgLoc().getX() >=getOrgLoc().getX()-range &&
                    tempPeople.get(i).getOrgLoc().getX()<=getOrgLoc().getX()+range &&
                    tempPeople.get(i).getOrgLoc().getY() >=getOrgLoc().getY()-range
                    && tempPeople.get(i).getOrgLoc().getY()<=getOrgLoc().getY()+range && tempPeople.get(i).getIdentity()>0){
                System.out.println("z to p");
//                tempGrid[tempPeople.get(i).getOrgLoc().getX()][tempPeople.get(i).getOrgLoc().getY()]=0;
                pEaten = tempPeople.get(i);


                return true;
            }
        }

        return false;
    }
    public boolean checkNeighborBuilding(ArrayList<Locations> tempPeople, int tempGrid[][], int range){
//        System.out.println("hi");
        for (int i = 0;i<tempPeople.size();i++){
//                System.out.println(tempAnt.get(i).getX());
            if(tempPeople.get(i).getX() >=getOrgLoc().getX()-range &&
                    tempPeople.get(i).getX()<=getOrgLoc().getX()+range &&
                    tempPeople.get(i).getY() >=getOrgLoc().getY()-range
                    && tempPeople.get(i).getY()<=getOrgLoc().getY()+range){
                System.out.println("z to p");
//                tempGrid[tempPeople.get(i).getOrgLoc().getX()][tempPeople.get(i).getOrgLoc().getY()]=0;

                return true;
            }
        }

        return false;
    }
    public void changeLoc(int[][] gameGrid, int [][] envir){
        System.out.println("test1");
        boolean check = false;
        int count = 0;
        while(!check){
//            System.out.println("y in while: " + y);
//            System.out.println("x in while: " + x);
//            System.out.println("while");
            count++;
            if(count==7){
                break;
            }

            int tempx = getOrgLoc().getX();
            int tempy = getOrgLoc().getY();

            if(Math.random()>.5){
                if(tempx < gameGrid.length-1) {
                    tempx++;
                }
            }else {
                if(tempx > 0) {
                    tempx--;
                }
            }
            if(Math.random()>.5){
                if(tempy < gameGrid[0].length-1) {
                    tempy++;
                }
            }else {
                if(tempy > 0) {
                    tempy--;
                }
            }
            System.out.println(tempx);
            System.out.println("tempy:" + tempy);
            if (gameGrid[tempx][tempy]==0 && envir[tempx][tempy] == 0){

                check=true;
//                if(maleFemale==0) {
//                    gameGrid[tempx][tempy] = 1;
//                }else if(maleFemale==1) {
//                    gameGrid[tempx][tempy] = 2;
//                }
//                if (growthPhase == 0){
//                    gameGrid[tempx][tempy] = 1;
//                }else{
//                    gameGrid[tempx][tempy] = 2;
//                }
                gameGrid[tempx][tempy] = getIdentity();
                gameGrid[getOrgLoc().getX()][getOrgLoc().getY()] = 0;
                getOrgLoc().setLocation(tempx, tempy);
            }
        }

//          System.out.println("x: " + x);

    }
    public void moveTo(int x, int y, int[][] gameGrid, int[][] envir) {
        int tempx = 0;
        int tempY = 0;
        if (x > this.orgLoc.getX()) {
            tempx = this.orgLoc.getX() + 1;
        } else if (x < this.orgLoc.getX()) {
            tempx = this.orgLoc.getX() - 1;
        }else{
            tempx = this.orgLoc.getX();
        }
        if (y > this.orgLoc.getY()) {
            tempY = this.orgLoc.getY() + 1;
        } else if (y < this.orgLoc.getY()) {
            tempY = this.orgLoc.getY() - 1;
        }else{
            tempY = this.orgLoc.getY();
        }
        if(tempx < gameGrid.length-1&&tempY < gameGrid[0].length-1 && tempx >= 0&&tempY >= 0) {
            if(gameGrid[tempx][tempY] == 0&& envir[tempx][tempY] == 0)  {
                gameGrid[this.orgLoc.getX()][this.orgLoc.getY()] = 0;
                gameGrid[tempx][tempY] = getIdentity();
                orgLoc.setLocation(tempx, tempY);
            }else{
                changeLoc(gameGrid, envir);
            }
        }else{
            changeLoc(gameGrid, envir);
        }
    }
    public void moveAway(int x, int y, int[][] gameGrid, int[][] envir) {
        int tempx;
        int tempY;
        if (x > this.orgLoc.getX()) {
            tempx = this.orgLoc.getX() - 1;
        } else if (x < this.orgLoc.getX()) {
            tempx = this.orgLoc.getX() + 1;
        }else {
            tempx = this.orgLoc.getX();
        }
        if (y > this.orgLoc.getY()) {
            tempY = this.orgLoc.getY() -1;
        } else if (y < this.orgLoc.getY()) {
            tempY = this.orgLoc.getY() + 1;
        }else{
            tempY = this.orgLoc.getY();
        }
        if(tempx < gameGrid.length-1&&tempY < gameGrid[0].length-1 && tempx >= 0&&tempY >= 0) {
            if(gameGrid[tempx][tempY] == 0&& envir[tempx][tempY] == 0)  {
                gameGrid[this.orgLoc.getX()][this.orgLoc.getY()] = 0;
                gameGrid[tempx][tempY] = getIdentity();
                orgLoc.setLocation(tempx, tempY);
            }else{
                changeLoc(gameGrid, envir);
            }
        }else{
            changeLoc(gameGrid, envir);
        }
    }
    public Person findClosestPerson(ArrayList<Person> people){
        if (people == null || people.isEmpty()) return null;
        Person closest = people.get(0);
        int minDist = manhattanDist(closest.getOrgLoc().getX(), closest.getOrgLoc().getY(), orgLoc.getX(), orgLoc.getY());
        for (Person p: people){
            int d = manhattanDist(p.getOrgLoc().getX(), p.getOrgLoc().getY(), orgLoc.getX(), orgLoc.getY());
            if (d < minDist){
                minDist = d;
                closest = p;
            }
        }
        return closest;
    }
    private int manhattanDist(int x1, int y1, int x2, int y2){
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
    public Building findClosestBuildingSpec(ArrayList<Building> building, int spec ){
        if (building == null || building.isEmpty()) return null;
        Building closest = null;
        int minDist = Integer.MAX_VALUE;
        for (Building p: building){
            if(p.getBuildingType() == spec) {
                int d = manhattanDist(p.getBLoc().getX(), p.getBLoc().getY(), orgLoc.getX(), orgLoc.getY());
                if (closest == null || d < minDist){
                    minDist = d;
                    closest = p;
                }
            }
        }
        return closest != null ? closest : (building.isEmpty() ? null : building.get(0));
    }
    public Building findClosestBuilding(ArrayList<Building> building){
        if (building == null || building.isEmpty()) return null;
        Building closest = building.get(0);
        int minDist = manhattanDist(closest.getBLoc().getX(), closest.getBLoc().getY(), orgLoc.getX(), orgLoc.getY());
        for (Building p: building){
            int d = manhattanDist(p.getBLoc().getX(), p.getBLoc().getY(), orgLoc.getX(), orgLoc.getY());
            if (d < minDist){
                minDist = d;
                closest = p;
            }
        }
        return closest;
    }
    public Locations getClosestEntrance(ArrayList<Locations> b){
        if (b == null || b.isEmpty()) return null;
        Locations closest = b.get(0);
        int minDist = manhattanDist(closest.getX(), closest.getY(), orgLoc.getX(), orgLoc.getY());
        for (Locations p: b){
            int d = manhattanDist(p.getX(), p.getY(), orgLoc.getX(), orgLoc.getY());
            if (d < minDist){
                minDist = d;
                closest = p;
            }
        }
        return closest;
    }
}
