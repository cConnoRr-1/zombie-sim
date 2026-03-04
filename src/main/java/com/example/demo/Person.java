package com.example.demo;

import java.util.ArrayList;

public class Person extends Organisms{
//     1 go to work, 2 run, 3 go home, 4 go help, 5 go to store, 6 go to zombie
    private int routine;
    private int hunger;
    private int battlePower;
    private int fear;
    private long hungerTime;
    public Person(int x,int y){
        //0 = male, 1=female
        super(x, y, 1);
        routine = 0;
        hunger = 0;
        fear = 0;
        hungerTime = System.nanoTime();
        battlePower = 0;
    }
    //this code can also be inside of the controller class if necessary.
    public void setHungerTime(){
        hungerTime = System.nanoTime();
    }
    public long getHungerTime(){
        return hungerTime;
    }
    public int getHunger(){
        return hunger;
    }
    public void setHunger(int i){
        hunger = i;
    }
    public int getFear(){
        return fear;
    }
    public void setFear(int i){
        fear = i;
    }
    public void setBattlePower(int i){
        battlePower = i;
    }
    public int getBattlePower(){
        return battlePower;
    }
    public boolean checkNeighborOpposite(ArrayList<Person> tempAnt,int tempGrid[][]){

        for (int i = 0;i<tempAnt.size();i++){
//                System.out.println(tempAnt.get(i).getX());
            if(tempAnt.get(i).getOrgLoc().getX() >=super.getOrgLoc().getX()-1
                    && tempAnt.get(i).getOrgLoc().getX()<=super.getOrgLoc().getX()+1 &&
                    tempAnt.get(i).getOrgLoc().getY() >=super.getOrgLoc().getY()-1
                    && tempAnt.get(i).getOrgLoc().getY()<=super.getOrgLoc().getY()+1 &&
                    tempAnt.get(i).getOrgLoc().getX()!=super.getOrgLoc().getX()
                    && tempAnt.get(i).getOrgLoc().getY()!=super.getOrgLoc().getY() &&
                    super.getIdentity()!=tempAnt.get(i).getIdentity()){

                return true;
            }
        }

        return false;
    }
    public void runAway(){

    }
    public void setRoutine(int num){
        routine = num;
    }
    public int getRoutine(){
        return routine;
    }
    private ArrayList<Locations> tempLocs = new ArrayList<>();
    public void reproduce(ArrayList<Person> tempAnt, int tempGrid[][]){

        tempLocs.clear();
        for(int i = super.getOrgLoc().getX()-1; i<super.getOrgLoc().getX()+2;i++){
            for(int j = super.getOrgLoc().getY()-1;j<super.getOrgLoc().getY()+2;j++){
                if(super.getOrgLoc().getX()<tempGrid.length && super.getOrgLoc().getY() <tempGrid[0].length){
                    if(checkEmptyAroundMe(i,j,tempGrid)){
                        System.out.println("j:" + j);
                        tempLocs.add(new Locations(i,j));
                    }
                }
            }
        }
        if(tempLocs.size()>0){
            int newLoc = (int)(Math.random()*tempLocs.size());
//            System.out.println("y: " +tempLocs.get(newLoc).getY());
            tempAnt.add(new Person(tempLocs.get(newLoc).getX(),tempLocs.get(newLoc).getY()));
//            System.out.println("y: " +tempLocs.get(newLoc).getY());
            tempGrid[tempAnt.get(tempAnt.size()-1).getOrgLoc().getX()][tempAnt.get(tempAnt.size()-1).getOrgLoc().getY()]=1;
//            System.out.println("blah" + tempAnt.get(tempAnt.size()-1).getY());
            super.setReproduceTime();
        }

    }



}
