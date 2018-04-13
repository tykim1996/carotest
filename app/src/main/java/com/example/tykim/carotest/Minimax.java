package com.example.tykim.carotest;

public class Minimax {


    public nodeRecord minimaxRecode(board chessBoard, int player,int currentDept, int maxDept) {
        Move bestMove=null;//
        int bestScore;
        if(chessBoard.isGameOver() || currentDept==maxDept) {

            return new nodeRecord(null,chessBoard.evaluate());
        }
        if(chessBoard.getPlayer()==player){
            bestScore=Integer.MIN_VALUE;
        }else {
            bestScore=Integer.MAX_VALUE;
        }

        for(Move move:chessBoard.getMove()){

            board newChess = new board(chessBoard.getContext(),chessBoard.getBitmapWidth(), chessBoard.getBitmapHeight(),chessBoard.getColQty(),chessBoard.getRowQty());
            newChess.setBoard(chessBoard.getNewBoard());
            newChess.setPlayer(chessBoard.getPlayer());

            newChess.makeMove(move);
            nodeRecord record = minimaxRecode(newChess,player,currentDept++,maxDept);

            if(chessBoard.getPlayer()==player){
                if(record.getScore() > bestScore) {
                    bestScore = record.getScore();
                    bestMove = move;


                }
            }else{
                if(record.getScore() < bestScore){
                    bestScore = record.getScore();
                    bestMove = move;
                }
            }
        }

        return new nodeRecord(bestMove,bestScore);
    }
}
