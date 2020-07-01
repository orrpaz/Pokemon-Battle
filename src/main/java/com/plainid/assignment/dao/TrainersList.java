package com.plainid.assignment.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TrainersList {
    List<Trainer> trainers;

    public List<Trainer> getTrainers() {
        return trainers;
    }

//    public void setTrainers(Map<String,Trainer> trainersMap) {
////        this.trainers = trainers;
//        this.trainers = new LinkedList<Trainer>(trainersMap.values());
//        System.out.println("vdf");
    public void setTrainers(List<Trainer> trainers) {
        this.trainers = trainers;

    }
}
