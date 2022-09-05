package org.academiadecodigo.spaceinvaders;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Beam implements Grid{
    private int posY;
    private int posX;
    public Rectangle rectangle;
    private boolean isDestroyed;

    public Beam(int posY, int posX) {
        this.posY = posY;
        this.posX = posX;
        rectangle = new Rectangle(this.posX,  this.posY, cellSize/2, cellSize/2);
        rectangle.fill();
        rectangle.setColor(Color.GREEN);
        isDestroyed = false;
    }
    public void move(){
        if (posX >= BORDER) {
            rectangle.translate(-cellSize/2, 3);
            posX -= cellSize/2;
            posY += 3;
            return;
        }

    }
    public void moveUp(int n){
        if (posY >= BORDER){
            rectangle.translate(0,-n);
            posY -=n;
            return;
        }
        isDestroyed = true;
    }

    public void deleteRectangle(){
        this.rectangle.delete();
    }
    public boolean getIsDestroyed(){
        return isDestroyed;
    }
    public void setIsDestroyed(){
        this.isDestroyed = true;
    }
    public int getPosX(){
        return posX;
    }
    public int getMaxPosX(){
        return posX + cellSize;
    }
    public int getPosY(){
        return posY;
    }
    public int getMaxPosY(){
        return posY + cellSize/4;
    }

    @Override
    public int getCellSize() {
        return 0;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }
}
