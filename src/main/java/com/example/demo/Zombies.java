package com.example.demo;

import java.util.ArrayList;

public class Zombies extends Organisms {
    private int hunger;
    private boolean doingSomething;
    private boolean enteredBuilding;
//    0: normal, 1: buffZombie, 2: smartZombie, 3: fastZombie
    private int typeOfZombie;
    public Zombies(int x, int y){
        super(x, y, 10);
        hunger = 3;
    }
    public void setEnteredBuilding(Boolean b){
        enteredBuilding = b;
    }
    public boolean getEnteredBuilding(){
        return enteredBuilding;
    }
    public void setDoingSomething(Boolean b){
        doingSomething = b;
    }
    public boolean getDoingSomething(){
        return doingSomething;
    }
    public void infectPerson(ArrayList<Zombies> z, Person p){
        z.add(new Zombies(p.getOrgLoc().getX(), p.getOrgLoc().getY()));
    }

}
