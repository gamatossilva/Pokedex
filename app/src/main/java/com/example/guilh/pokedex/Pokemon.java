package com.example.guilh.pokedex;

public class Pokemon {

    private int number = 0;
    private String name;
    private String url;

    public Pokemon(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name.substring(0, 1).toUpperCase().concat(name.substring(1));
    }

    public String getUrl() {
        return url;
    }


    public int getNumber() {
        if(this.number == 0) {
            String[] urlPartes = url.split("/");
            this.number = Integer.parseInt(urlPartes[urlPartes.length - 1]);
        }
        return this.number;
    }
}
