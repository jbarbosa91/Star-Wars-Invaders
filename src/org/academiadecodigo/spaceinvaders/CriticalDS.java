package org.academiadecodigo.spaceinvaders;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class CriticalDS implements Grid{
    private int posY;
    private int posX;
    private Rectangle rectangle;

    public CriticalDS(DeathStar deathStar){
        setPos(deathStar);
        draw();
    }
    public void setPos(DeathStar deathStar){
        int random = (int)Math.floor(Math.random() * 3);
        switch (random) {
            case 0:
                posX = x - (cellSize * 2 + BORDER);
                posY = (int) Math.floor(Math.random() * (deathStar.getMaxPosY() - deathStar.getPosY() - cellSize)) + deathStar.getPosY();
                break;
            case 1:
                posX = x - (cellSize * 6 + BORDER);
                posY = (int) Math.floor(Math.random() * (deathStar.getMaxPosY() - deathStar.getPosY() - (4 * cellSize))) + (deathStar.getPosY() + cellSize*2);
                break;
            case 2:
                posX = x - (cellSize * 10 + BORDER);
                posY = (int) Math.floor(Math.random() * (deathStar.getMaxPosY() - deathStar.getPosY() - (12 * cellSize))) + (deathStar.getPosY() + cellSize*6);
                break;
            default:
        }
    }
    public void draw(){
        this.rectangle = new Rectangle(posX, posY, cellSize, cellSize);
        this.rectangle.setColor(Color.MAGENTA);
        this.rectangle.fill();
    }
    public void delete(){
        this.rectangle.delete();
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
        return posY + cellSize;
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
