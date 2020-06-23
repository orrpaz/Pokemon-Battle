package com.plainid.assignment.dao;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.*;

public class Trainer {
    int level;
    String name;
//    List<Pokemon> bag;
    int sizeBag = 3;
    @ManyToMany
    @JoinTable(name = "POKEMON_TRAINER",joinColumns = @JoinColumn(name = "TRAINER_ID"),
            inverseJoinColumns = @JoinColumn(name = "POKEMON_ID"))
    static Deque<Pokemon> bag;
    static HashSet<Pokemon> map;

    public Trainer(String name) {
        this.level = 0;
        this.name = name;
//        this.bag = new ArrayList<>();
        bag = new LinkedList<>();
        map = new HashSet<>();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<Pokemon> getBag() {
//        return bag;
//    }
//
//    public void setBag(List<Pokemon> bag) {
//        this.bag = bag;
//    }
    public void add(Pokemon pokemon){
        if (!map.contains(pokemon)) {
            if (bag.size() == sizeBag) {
                Pokemon last = bag.removeLast();
                map.remove(last);
            }
        }
        else {
            int index = 0, i = 0;
            for (Pokemon value : bag) {
                if (value == pokemon) {
                    index = i;
                    break;
                }
                i++;
            }
            bag.remove(index);
        }
        bag.push(pokemon);
        map.add(pokemon);
    }
}

