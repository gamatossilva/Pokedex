package com.example.guilh.pokedex;

import android.support.annotation.NonNull;

public class PokeTotal implements Comparable<PokeTotal> {

    private static final String NO_POKEMON_PROVIDED = "";
    private String pokemon = NO_POKEMON_PROVIDED;
    private String name;
    private Integer value;

    public PokeTotal (String pokemon, String name, int value){

        this.pokemon = pokemon;
        this.name = name;
        this.value = value;

    }

    public PokeTotal(String pokemon) {
        this.pokemon = pokemon;
    }

    public PokeTotal (String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getPokemon() {
        return pokemon;
    }

    public void setPokemon(String pokemon) {
        this.pokemon = pokemon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public int compareTo(@NonNull PokeTotal o) {
        return this.value.compareTo(o.value);
    }
    public boolean hasPokemon() {
        return pokemon != NO_POKEMON_PROVIDED;
    }
}
