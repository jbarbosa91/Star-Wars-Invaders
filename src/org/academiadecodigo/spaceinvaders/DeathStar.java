package org.academiadecodigo.spaceinvaders;

import org.academiadecodigo.simplegraphics.pictures.Picture;

public class DeathStar implements Grid{
    private int health;
    private int posY;
    private int posX;
    private boolean destroyed;
    private Picture picture;

    public DeathStar(){
        posX = x + BORDER - (250);
        posY = y/2 - (527/2);
        destroyed = false;
        health = 100;
        drawDS(1);
    }

    public boolean isDestroyed(){
        return destroyed;
    }

    public void drawDS(int n){
        if(this.picture != null){
            this.picture.delete();
        }
        this.picture = new Picture(posX, posY, "Resources/DeathStar" + n + ".png");
        this.picture.draw();
    }

    public int getPosY(){
        return posY;
    }
    public int getPosX(){
        return posX;
    }
    public int getMaxPosY(){
        return posY + 527;
    }
    public int getMaxPosX(){
        return posX + 250;
    }
    @Override
    public int getCellSize() {
        return cellSize;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
