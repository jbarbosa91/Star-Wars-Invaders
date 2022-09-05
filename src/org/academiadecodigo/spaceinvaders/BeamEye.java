package org.academiadecodigo.spaceinvaders;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class BeamEye implements Grid{
    private int posY;
    private int posX;
    private Rectangle rectangle;
    private int hp;

    public BeamEye(DeathStar deathStar){
        posX = deathStar.getPosX() + (9 * cellSize);
        posY = deathStar.getPosY() + (7 * cellSize);
        hp = 5;
        draw();
    }
    public void reduceHP(){
        this.hp--;
    }
    public int getHp(){
        return hp;
    }

    public void draw(){
        this.rectangle = new Rectangle(posX, posY, cellSize, cellSize);
        this.rectangle.setColor(Color.GREEN);
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
