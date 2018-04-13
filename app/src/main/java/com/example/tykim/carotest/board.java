package com.example.tykim.carotest;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class board {
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    private int[][] board;
    private int player;
    private Context context;
    private int bitmapWidth, bitmapHeight, colQty,rowQty;
    private List<Line> lines;
    private int need = 3;
    private Move tempMove;
    private boolean isOver = false;


    private Bitmap playerA, playerB;

    public board(Context context, int bitmapWidth, int bitmapHeight, int colQty, int rowQty) {
        this.context = context;
        this.bitmapWidth = bitmapWidth;
        this.bitmapHeight = bitmapHeight;
        this.colQty = colQty;
        this.rowQty = rowQty;
    }


    public void init(){
        lines = new ArrayList<>();
        bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        board = new int[rowQty][colQty];
        for(int i = 0; i<rowQty; i++){
            for(int j = 0; j < colQty;j++){
                board[i][j] = -1;
            }
        }

        player = 0;
        paint.setStrokeWidth(2);
        int celWidth = bitmapWidth/colQty;
        int celHeight = bitmapHeight/rowQty;
        for(int i = 0; i <= colQty; i++){
            lines.add(new Line(celWidth*i, 0, celWidth*i, bitmapHeight));
        }
        for(int i = 0; i <= rowQty; i++){
            lines.add(new Line(0, i*celHeight, bitmapWidth, i*celHeight));
        }
    }

    public Bitmap drawBoard(){
        for(int i = 0; i < lines.size(); i++){
            canvas.drawLine(
                    lines.get(i).getX1(),
                    lines.get(i).getY1(),
                    lines.get(i).getX2(),
                    lines.get(i).getY2(),
                    paint
            );
        }
        playerA = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_player_a);

        return bitmap;
    }

    public boolean onTouch(final View view, MotionEvent motionEvent){
        int cellWidth = view.getWidth()/colQty;
        int cellHeight = view.getHeight()/rowQty;
        int colIndex = (int) (motionEvent.getX()/cellWidth);
        int rowIndex = (int) (motionEvent.getY()/cellHeight);
        Log.i("DOO", colIndex+"-"+rowIndex);
        if(board[rowIndex][colIndex] != -1){
            return true;
        }




        return true;
    }

    public void onDrawBoard(int colIndex, int rowIndex, View view){
        int cellWidth = view.getWidth()/colQty;
        int cellHeight = view.getHeight()/rowQty;
        board[rowIndex][colIndex] = player;
        int padding = 50;
        if(player == 0){
            canvas.drawBitmap(
                    playerA,
                    new Rect(0,0,playerA.getWidth(), playerA.getHeight()),
                    new Rect(colIndex*cellWidth+padding,rowIndex*cellHeight+padding,(colIndex+1)*cellWidth -padding, (rowIndex+1)*cellHeight -padding),
                    paint);

        }else {
            canvas.drawBitmap(
                    playerB,
                    new Rect(0,0,playerB.getWidth(), playerB.getHeight()),
                    new Rect(colIndex*cellWidth,rowIndex*cellHeight,(colIndex+1)*cellWidth, (rowIndex+1)*cellHeight),
                    paint);

        }
    }

    public boolean isGameOver(){
        if (checkWindiagonalLeftBottom(tempMove.getColIndex(),tempMove.getRowIndex())
                || checkWindiagonalRightBottom(tempMove.getColIndex(),tempMove.getRowIndex())
                || checkWinHorizontal(tempMove.getRowIndex())
                || checkWinVerical(tempMove.getColIndex())
                ) return true;

        int count = 0;
        for (int i = 0; i < rowQty; i++) {
            for (int j = 0; j < colQty; j++) {
                if (board[i][j] == -1) count++;
            }
        }
        if (count == 0){
            return true;
        }

        return false;
    }
    private boolean checkWin(int player) {
        if(board[0][0]==player && board[0][1]==player && board[0][2]==player)
            return true;

        if(board[1][0]==player && board[1][1]==player && board[1][2]==player)
            return true;

        if(board[2][0]==player && board[2][1]==player && board[2][2]==player)
            return true;

        if(board[0][0]==player && board[1][0]==player && board[2][0]==player)
            return true;

        if(board[0][1]==player && board[1][1]==player && board[2][1]==player)
            return true;

        if(board[0][2]==player && board[1][2]==player && board[2][2]==player)
            return true;

        if(board[0][0]==player && board[1][1]==player && board[2][2]==player)
            return true;

        if(board[0][2]==player && board[1][1]==player && board[2][0]==player)
            return true;
        return false;


    }
    private void checkDiagonalFromTopRight (int colQty, int rowQty) {

    }

    public List<Move> getMove() {

        List<Move> moves = new ArrayList<>();
        for (int i = 0; i < rowQty; i++) {
            for (int j = 0; j < colQty; j++) {
                if (board[i][j] == -1) moves.add(new Move(i, j));
            }
        }
        return moves;
    }


    public void makeMove(Move move) {
        tempMove = move;
        board[move.getRowIndex()][move.getColIndex()] = player;
        player = (player + 1) % 2;

    }


    public int evaluate() {
        if (checkWin(player))
            return 1;
        if (checkWin((player + 1) % 2))
            return -1;
        return 0;
    }



    public int[][] getNewBoard(){
        int[][] newBoard = new int[rowQty][colQty];
        for (int i = 0; i < rowQty; i++) {
            for (int j = 0; j < colQty; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
        return newBoard;
    }
    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getBitmapWidth() {
        return bitmapWidth;
    }

    public void setBitmapWidth(int bitmapWidth) {
        this.bitmapWidth = bitmapWidth;
    }

    public int getBitmapHeight() {
        return bitmapHeight;
    }

    public void setBitmapHeight(int bitmapHeight) {
        this.bitmapHeight = bitmapHeight;
    }

    public int getColQty() {
        return colQty;
    }

    public void setColQty(int colQty) {
        this.colQty = colQty;
    }

    public int getRowQty() {
        return rowQty;
    }

    public void setRowQty(int rowQty) {
        this.rowQty = rowQty;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getCurrentDept(){
        int count = 0;
        for (int i = 0; i < rowQty; i++) {
            for (int j = 0; j < colQty; j++) {
                if (board[i][j] == -1) count++;
            }
        }
        return count;
    }


    public boolean checkWinHorizontal(int row){
        int dem = 0;
        for (int i = 1; i < rowQty; i++) {
            if (board[row][i] != board[row][i-1]) {
                dem = 0;
            }else if(board[row][i] != -1){
                dem++;
            }
            if (dem == need) {
                return true;
            }
        }

        return false;
    }
    private boolean checkWinVerical(int col){
        int dem = 0;
        for (int i = 1; i < rowQty; i++) {
            if (board[i][col] != board[i-1][col]) {
                dem = 0;
            }else if(board[i][col] != -1){
                dem++;
            }
            if (dem == need) {
                return true;
            }
        }

        return false;
    }
    private boolean checkWindiagonalLeftBottom(int x, int y){
        int a = 0, b = 0,count  = 0;
        if(x > y){
            a = x - y;
            b = y - y;
        }
        if(x < y){
            a = y -x;
            b = y - y;
        }
        for (int i = a + 1; i < rowQty; i++) {
            if(a < rowQty && b < rowQty){
                if(board[i][b+1] != board[a][b]){
                    count = 0;
                }else if(board[i][b+1] != -1){
                    count++;
                }
                if(count == need) return true;
            }
            a = a+1;
            b = b+1;
        }
        return false;
    }
    private boolean checkWindiagonalRightBottom(int x, int y){
        int a = 0, b = 0, count  = 0;
        if(x > y){
            a = x + y;b = 0;
        }else {
            a = colQty -1;
            b = x +y - (colQty-1);
        }
        for (int i = 0; i < rowQty -1; i++) {
            if(a > 0 && b < colQty  && b >0){
                if(board[a - 1][b+1] != board[a][b]){
                    count = 0;
                }else if(board[a-1][b+1] != -1){
                    count++;
                }

                if(count == need) return true;
            }
            a = a-1;
            b = b+1;
        }
        return false;
    }
}
