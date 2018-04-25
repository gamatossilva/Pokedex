package com.example.guilh.pokedex;

public class Moves {

    private Move move;

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    static class Move{

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
