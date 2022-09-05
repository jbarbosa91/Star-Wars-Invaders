package org.academiadecodigo.spaceinvaders;

import org.academiadecodigo.simplegraphics.pictures.Picture;

public class Alien implements Grid{
    private int random;
    private int posY;
    private int posX;
    private boolean isOutside;
    private boolean destroyed;
    private int speed;
    private Picture picture;

    public Alien(){
        random = (int)Math.floor(Math.random()* (y - cellSize*8) + 10) +cellSize*3;
        posX = x - (cellSize*2 + BORDER);
        posY = random;
        isOutside = false;
        destroyed = false;
        speed = cellSize/2;
        summonAlien();
    }
    public void moveAlien() {
        if (posX >= cellSize) {
            picture.translate(-speed, 0);
            posX -= speed;
            return;
        }
        isOutside = true;
    }

    public void summonAlien(){
        this.picture = new Picture(posX, posY, "Resources/alien.png");
        picture.draw();
    }
    public int getPosX(){
        return posX;
    }

    public int getMaxPosX(){
        return posX + 55;
    }

    public int getPosY(){
        return posY;
    }
    public int getMaxPosY(){
        return posY + cellSize*4;
    }
    public boolean getIsOutside(){
        return isOutside;
    }
    public void setIsDestroyed(){
        this.destroyed = true;
    }
    public boolean isDestroyed(){
        return destroyed;
    }
    public void delete(){
        picture.delete();
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
