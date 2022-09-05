package org.academiadecodigo.spaceinvaders;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public class Background implements Grid{
    final Rectangle background;
    final Picture stars;

    public Background() {
        this.background = new Rectangle(BORDER, BORDER, x, y);
        this.background.draw();
        this.stars = new Picture(BORDER, BORDER, "Resources/Background.png");
        stars.draw();
    }
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

    public int getBORDER(){
        return this.BORDER + cellSize;
    }
}
