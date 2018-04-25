package com.example.guilh.pokedex;

import com.google.gson.annotations.SerializedName;


public class Stats {

    private Stat stat;

    @SerializedName("base_stat")
    private Integer baseStat;

    public Stat getStat() {
        return stat;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }

    public Integer getBaseStat() {
        return baseStat;
    }

    public void setBaseStat(Integer baseStat) {
        this.baseStat = baseStat;
    }

    static class Stat{

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
