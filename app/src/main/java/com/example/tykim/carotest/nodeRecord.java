package com.example.tykim.carotest;

public class nodeRecord {
    private Move move;
    private int score;

    public nodeRecord(Move move, int score) {
        this.move = move;
        this.score = score;
    }
    public nodeRecord(){
    }
    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
