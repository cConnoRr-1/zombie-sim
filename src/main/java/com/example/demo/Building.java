package com.example.demo;

import java.util.ArrayList;
import java.util.Random;

public class Building {
    private static final int MAX_GRID = 39; // 0-based index for 40x40 grid
    private Random coord = new Random();
    private Locations bLoc;
    private int amountOfPeople;
    private int peopleLimit;
    private int length;
    private int width;
    private int buildingType;
    private long stayTime;
    private ArrayList<Locations> entrance = new ArrayList<>();
    private ArrayList<Person> peopleInBuilding = new ArrayList<Person>();

    private static int clamp(int value) {
        return Math.max(0, Math.min(MAX_GRID, value));
    }

    public Building(int x, int y, int length, int width, int pL, int bT, int st){
        bLoc = new Locations(x, y);
        this.length = length;
        this.width = width;
        this.peopleLimit = pL;
        this.buildingType = bT;
        stayTime = (long)st * 1000000000;
        entrance.add(new Locations(clamp(x), clamp(coord.nextInt(width) + y)));
        entrance.add(new Locations(clamp(coord.nextInt(length) + x), clamp(y)));
        entrance.add(new Locations(clamp(x + length), clamp(coord.nextInt(width) + y)));
        entrance.add(new Locations(clamp(coord.nextInt(length) + x), clamp(y + width)));
    }
    public ArrayList<Locations> getEntrance (){
        return entrance;
    }
    public long getStayTime(){
        return stayTime;
    }
    public int getBuildingType() {
        return buildingType;
    }
    public void addPtoB(Person a){
        peopleInBuilding.add(a);
    }
    public ArrayList<Person> getPeopleInBuilding(){
        return peopleInBuilding;
    }
    public void removePfromB(Person a){
        peopleInBuilding.remove(a);
    }
    public Locations getBLoc(){
        return bLoc;
    }
    public int getLength(){
        return length;
    }
    public int getWidth(){
        return width;
    }

}
