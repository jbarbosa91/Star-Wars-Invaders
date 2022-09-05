package org.academiadecodigo.spaceinvaders;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public class Spaceship implements KeyboardHandler, Grid {
    private int score;
    private Picture spaceship;
    private int posX;
    private int posY;
    private int lives;
    private boolean start;

    public Spaceship() {
        score = 0;
        posX = cellSize / 2;
        posY = (int) Math.floor(y / 2);
        spaceship = new Picture(posX, posY, "Resources/spaceship2.png");
        spaceship.draw();
        keyboardInit();
        lives = 5;
        start = false;
    }

    public int getPosX() {
        return posX;
    }

    public int getMaxPosX() {
        return posX + cellSize * 7;
        // cellSize * 7 = 112 + cellSize = 128;
    }

    public int getPosY() {
        return posY;
    }

    public int getMaxPosY() {
        return posY + cellSize * 6;
    }

    public void resetStart(){
        this.start = false;
    }

    public void addScore() {
        this.score++;
    }

    public int getScore() {
        return score;
    }

    public void setLives() {
        this.lives--;
    }

    public int getLives() {
        return lives;
    }

    public boolean getStart() {
        return start;
    }

    public void setStart() {
        start = true;
    }

    private void keyboardInit() {
        Keyboard keyboard = new Keyboard(this);

        KeyboardEvent upPressed = new KeyboardEvent();
        upPressed.setKey(KeyboardEvent.KEY_UP);
        upPressed.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent rightPressed = new KeyboardEvent();
        rightPressed.setKey(KeyboardEvent.KEY_RIGHT);
        rightPressed.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent leftPressed = new KeyboardEvent();
        leftPressed.setKey(KeyboardEvent.KEY_LEFT);
        leftPressed.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent downPressed = new KeyboardEvent();
        downPressed.setKey(KeyboardEvent.KEY_DOWN);
        downPressed.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent spacePressed = new KeyboardEvent();
        spacePressed.setKey(KeyboardEvent.KEY_SPACE);
        spacePressed.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        keyboard.addEventListener(leftPressed);
        keyboard.addEventListener(rightPressed);
        keyboard.addEventListener(upPressed);
        keyboard.addEventListener(downPressed);
        keyboard.addEventListener(spacePressed);
    }

    public void keyPressed(KeyboardEvent keyboardEvent) {

        if (keyboardEvent.getKey() == KeyboardEvent.KEY_UP && posY > cellSize * 3) {
            spaceship.translate(0, -cellSize);
            posY -= cellSize;
        }
        if (keyboardEvent.getKey() == KeyboardEvent.KEY_DOWN && posY < y - cellSize * 6) {
            spaceship.translate(0, cellSize);
            posY += cellSize;
        }
        if (keyboardEvent.getKey() == KeyboardEvent.KEY_RIGHT && posX + cellSize * 7 < x/2) {
            spaceship.translate(cellSize/2, 0);
            posX += cellSize/2;
        }
        if (keyboardEvent.getKey() == KeyboardEvent.KEY_LEFT && posX > cellSize / 2) {
            spaceship.translate(-cellSize/2, 0);
            posX -= cellSize/2;
        }
        if (keyboardEvent.getKey() == KeyboardEvent.KEY_SPACE) {
            setStart();
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
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
