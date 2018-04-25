package com.example.guilh.pokedex;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class PokeDetails {

    private String name;
    private Integer weight;
    private Integer height;

    @SerializedName("base_experience")
    private Integer baseExperience;

    private Sprite sprites;

    private ArrayList<Stats> stats;

    private ArrayList<Abilities> abilities;

    private ArrayList<Moves> moves;

    private ArrayList<Types> types;

    public PokeDetails (){

    }

    public PokeDetails(String name) {
        this.name = name;
    }

    public String getName() {
        return name.substring(0, 1).toUpperCase().concat(name.substring(1));
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getBaseExperience() {
        return baseExperience;
    }

    public void setBaseExperience(Integer baseExperience) {
        this.baseExperience = baseExperience;
    }

    public Sprite getSprites() {
        return sprites;
    }

    public void setSprites(Sprite sprites) {
        this.sprites = sprites;
    }

    public ArrayList<Stats> getStats() {
        return stats;
    }

    public void setStats(ArrayList<Stats> stats) {
        this.stats = stats;
    }

    public ArrayList<Abilities> getAbilities() {
        return abilities;
    }

    public void setAbilities(ArrayList<Abilities> abilities) {
        this.abilities = abilities;
    }

    public ArrayList<Moves> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<Moves> moves) {
        this.moves = moves;
    }

    public ArrayList<Types> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<Types> types) {
        this.types = types;
    }

    static class Sprite{

        @SerializedName("front_default")
        private String imageUrl;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

}
