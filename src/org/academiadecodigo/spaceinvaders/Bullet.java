package org.academiadecodigo.spaceinvaders;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Bullet implements Grid{
    private int damage;
    private int posY;
    private int posX;
    public Rectangle rectangle;
    private boolean isDestroyed;

    public Bullet(int posY, int posX) {
        this.posY = posY + cellSize*3 - 5;
        this.posX = posX + cellSize*6;
        rectangle = new Rectangle(this.posX,  this.posY, cellSize, cellSize/4);
        rectangle.fill();
        rectangle.setColor(Color.RED);
        isDestroyed = false;
    }
    public void move(){
        if (posX <= x-BORDER) {
            rectangle.translate(cellSize, 0);
            posX += cellSize;
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
