package com.plainid.assignment.dao;

import java.util.*;

/**
 * this class describe trainer
 */
public class Trainer {
    static int sizeBag = 3;
    String name;
    int level;
    int id;
    Queue<String> Bag;


    public Trainer(String name) {
        this.name = name;
        this.level = 0;
        this.Bag = new PriorityQueue<>(3);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }



//
//    public void setPokemonBag(Queue<Pokemon> pokemonBag) {
//        this.pokemonBag = pokemonBag;
//    }
    public void addPokemon(Pokemon pokemon){
//        pokemonBag.add(pokemon);
        this.Bag.add(pokemon.getName());
    }
    public Queue<String> getBag() {
        return this.Bag;
    }

    public void setBag(Queue<String> Bag) {
        this.Bag = Bag;
    }
}

