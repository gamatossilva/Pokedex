package com.example.guilh.pokedex;

public class PokeStat {
    private String mStatName;
    private String mStatValue;

    public PokeStat(String statName, String statValue) {
        mStatName = statName;
        mStatValue = statValue;
    }

    public String getStatName() {
        return mStatName;
    }


    public String getStatValue() {
        return mStatValue;
    }
}
