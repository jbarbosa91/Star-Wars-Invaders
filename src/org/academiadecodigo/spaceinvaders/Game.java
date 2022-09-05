package org.academiadecodigo.spaceinvaders;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.graphics.Text;
import org.academiadecodigo.simplegraphics.pictures.Picture;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class Game{
    private int delay;
    private boolean endGame;
    private Spaceship spaceship;
    private LinkedList<Alien> aliens;
    private Grid background;
    private Picture [] heart;
    private LinkedList<Bullet> bullets;
    private LinkedList<AlienBullet> alienBullets;
    private Text scoreText;
    private Picture pressSpace;
    private Sound sound = new Sound();
    private boolean b;
    private Picture starWars;
    private boolean bossDefeated;
    private boolean phase1Completed;
    private DeathStar deathStar;
    private LinkedList<CriticalDS> criticalDS;
    private LinkedList<Beam> beam;
    private BeamEye beamEye;
    private Picture gameOver;
    private Picture victory;

    public Game(int delay){
        this.delay = delay;
        this.endGame = false;
        background = new Background();
        b = false;
        summonLives();
        phase1Completed = false;
        bossDefeated = false;
        spaceship = new Spaceship();
    }

    public void init() throws InterruptedException {
        starWars = new Picture(0, 0, "Resources/starwars.png");
        starWars.draw();
        while (!spaceship.getStart()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            drawPressSpace();
        }
        starWars.delete();
        pressSpace.delete();
        intro();
    }

    public void intro(){
        if (!phase1Completed){
            Timer timer1 = new Timer();
            playSFX(4);
            textIntro();
            TimerTask task2 = new TimerTask() {
                @Override
                public void run() {
                    aliens = new LinkedList<>();
                    alienBullets = new LinkedList<>();
                    bullets = new LinkedList<>();
                    drawText();
                    while (!spaceship.getStart()) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    try {
                        start();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            timer1.schedule(task2, 9450);
            return;
        }
        stopMusic();
    }
    public void drawPressSpace(){
        if(b){
            pressSpace.delete();
            b = false;
            return;
        }
        pressSpace = new Picture(0, 0,"Resources/PressStart.png");
        pressSpace.draw();
        b = true;
    }
    public void phase1() throws InterruptedException{
        int counter = 0;
        int nAliens = 0;
        while (!phase1Completed && !endGame) {

            // Pause for a while
            Thread.sleep(delay);

            if (counter % 3 == 0 ){
                bullets.add(new Bullet(spaceship.getPosY(), spaceship.getPosX()));
                playSFX(2);
            }
            if (counter % 12 == 0 && nAliens < 60){
                aliens.add(new Alien());
                nAliens++;
            }
            if(counter == 48){
                for(int i = 0; i < aliens.size(); i++){
                    alienBullets.add(new AlienBullet(aliens.get(i).getPosY(), aliens.get(i).getMaxPosX()));
                    counter = 0;
                }
            }
            if (nAliens >= 60 && aliens.size() == 0){
                phase1Completed = true;
            }
            // Move all Elements
            moveAliens();
            moveBullets();
            moveAlienBullets();
            catchShip();
            catchAlien();
            counter++;
        }
    }
    public void phase2() throws InterruptedException{
        int counter = 0;
        int imgC = 1;
        deathStar = new DeathStar();
        boolean deathStarSummoned = false;
        //playSFX(4);
        while(!deathStarSummoned){
            moveBullets();
            moveAlienBullets();
            catchShip();
            // Pause for a while
            Thread.sleep(delay);
            if (counter % 6 == 0 && imgC < 10){
                imgC++;
                deathStar.drawDS(imgC);
                counter = 0;
            }
            if(imgC >= 10 && counter > 10){
                deathStarSummoned = true;
            }
            counter++;
        }
    }
    public void phase3() throws InterruptedException {
        int counter = 0;
        criticalDS = new LinkedList<>();
        int nCriticalDS = 0;
        boolean lastStance = false;
        boolean beamFull = false;
        int beamCounter = 0;
        while (!bossDefeated && !endGame) {

            // Pause for a while
            Thread.sleep(delay);

            if (counter % 3 == 0){
                bullets.add(new Bullet(spaceship.getPosY(), spaceship.getPosX()));
                playSFX(2);
            }
            if (counter % 24 == 0 && nCriticalDS < 20){
                criticalDS.add(new CriticalDS(deathStar));
                nCriticalDS++;
            }
            if(counter == 48 && !lastStance){
                for(int i = 0; i < criticalDS.size(); i++){
                    alienBullets.add(new AlienBullet((criticalDS.get(i).getPosY()- (Grid.cellSize + Grid.cellSize)), (criticalDS.get(i).getMaxPosX())));
                    counter = 0;
                }
            }
            if (nCriticalDS >= 20 && criticalDS.size() == 0 && !lastStance){
                beamEye = new BeamEye(deathStar);
                beam = new LinkedList<>();
                beam.add(new Beam(beamEye.getPosY(), beamEye.getPosX()));
                catchBeamEye();
                lastStance = true;
            }
            if(lastStance && beam.size() < 156 && !beamFull){
                beam.add(new Beam(beamEye.getPosY(), beamEye.getPosX()));
                moveBeam();
                catchBeamEye();
                beamOnShip();
            }
            if(lastStance && (beam.size() >= 156 || beamCounter > 1) && beamCounter < 4){
                beamFull = true;
                upBeam();
                catchBeamEye();
                beamCounter++;
                beamOnShip();
            }
            if (beamCounter >= 4){
                while(spaceship.getLives() > 0){
                    reduceLives();
                }
                endGame = true;
            }
            moveBullets();
            moveAlienBullets();
            catchShip();
            catchCriticalDS();
            counter++;
        }
    }
    public void beamOnShip(){
        for (int j = 0; j < beam.size(); j++) {
            if (beam.get(j).getMaxPosY() >= spaceship.getPosY()
                    && beam.get(j).getPosY() <= spaceship.getMaxPosY()
                    && beam.get(j).getPosX() <= spaceship.getMaxPosX()
                    && beam.get(j).getMaxPosX() >= spaceship.getPosX())
            {
                beam.get(j).setIsDestroyed();
                beam.get(j).deleteRectangle();
                beam.remove(j);
                reduceLives();
            }
        }
    }
    public void moveBeam(){
        for (int i= 0; i < beam.size(); i++){
            if(beam.get(i).getIsDestroyed()){
                beam.get(i).deleteRectangle();
                beam.remove(beam.get(i));
                continue;
            }
            beam.get(i).move();
        }
    }
    public void upBeam() {
        int j = 0;
        for (int i = (beam.size() - 1); i > -1; i--) {
            if (beam.get(i).getIsDestroyed()) {
                beam.get(i).deleteRectangle();
                beam.remove(beam.get(i));
                continue;
            }
            beam.get(i).moveUp(j);
            j++;
        }
    }

    public void start() throws InterruptedException {
        phase1();
        if(!endGame){
            phase2();
            phase3();
        }
        if (bossDefeated){
            victory = new Picture(0,0,"Resources/VictoryFull.png");
            victory.draw();
        }
        else{
            gameOver = new Picture(0,0,"Resources/GameOver.png");
            gameOver.draw();
        }
        spaceship.resetStart();
    }
    private void moveAlienBullets() {
        for (int i= 0; i < alienBullets.size(); i++){
            if(alienBullets.get(i).getIsDestroyed()){
                alienBullets.get(i).deleteRectangle();
                alienBullets.remove(alienBullets.get(i));
                continue;
            }
            alienBullets.get(i).move();
        }
    }
    private void moveBullets() {
        for (int i= 0; i <bullets.size(); i++){
            if(bullets.get(i).getIsDestroyed()) {
                bullets.get(i).deleteRectangle();
                bullets.remove(bullets.get(i));
                continue;
            }
            bullets.get(i).move();
        }
    }
    private void moveAliens() {
        for (int i = 0; i < aliens.size(); i++) {
            aliens.get(i).moveAlien();
        }
    }
    private void catchBeamEye(){
        for (int j = 0; j < bullets.size(); j++) {
            if (bullets.get(j).getMaxPosY() >= beamEye.getPosY()
                    && bullets.get(j).getPosY() <= beamEye.getMaxPosY()
                    && bullets.get(j).getMaxPosX() >= beamEye.getPosX()) {
                bullets.get(j).setIsDestroyed();
                bullets.get(j).deleteRectangle();
                bullets.remove(j);
                beamEye.reduceHP();
                spaceship.addScore();
                playSFX(3);
                drawText();
                if (beamEye.getHp() < 1){
                    beamEye.delete();
                    bossDefeated = true;
                }
                break;
            }
        }
    }
    private void catchCriticalDS() {
        for (int i = 0; i < criticalDS.size(); i++) {
            for (int j = 0; j < bullets.size(); j++) {
                if (bullets.get(j).getMaxPosY() >= criticalDS.get(i).getPosY()
                        && bullets.get(j).getPosY() <= criticalDS.get(i).getMaxPosY()
                        && bullets.get(j).getMaxPosX() >= criticalDS.get(i).getPosX()) {

                    bullets.get(j).setIsDestroyed();
                    bullets.get(j).deleteRectangle();
                    bullets.remove(j);
                    criticalDS.get(i).delete();
                    //criticalDS.get(i).setIsDestroyed();
                    criticalDS.remove(i);
                    spaceship.addScore();
                    playSFX(3);
                    drawText();
                    break;
                }
            }
        }
    }
    private void catchAlien() {
        for (int i = 0; i < aliens.size(); i++) {
            if (aliens.get(i).getIsOutside()) {
                aliens.get(i).delete();
                aliens.remove(i);
                reduceLives();
                break;
            }
            if (spaceship.getMaxPosY() >= aliens.get(i).getPosY()
                    && spaceship.getPosY() <= aliens.get(i).getMaxPosY()
                    && spaceship.getMaxPosX() >= aliens.get(i).getPosX()) {
                aliens.get(i).delete();
                aliens.get(i).setIsDestroyed();
                aliens.remove(i);
                reduceLives();
            }
            for (int j = 0; j < bullets.size(); j++) {
                if (bullets.get(j).getMaxPosY() >= aliens.get(i).getPosY()
                        && bullets.get(j).getPosY() <= aliens.get(i).getMaxPosY()
                        && bullets.get(j).getMaxPosX() >= aliens.get(i).getPosX()) {

                    bullets.get(j).setIsDestroyed();
                    bullets.get(j).deleteRectangle();
                    bullets.remove(j);
                    aliens.get(i).delete();
                    aliens.get(i).setIsDestroyed();
                    aliens.remove(i);
                    spaceship.addScore();

                    playSFX(3);

                    drawText();
                    break;
                }
            }
        }
    }
    public void catchShip(){
        for (int j = 0; j < alienBullets.size(); j++) {
            if (alienBullets.get(j).getMaxPosY() >= spaceship.getPosY()
                    && alienBullets.get(j).getPosY() <= spaceship.getMaxPosY()
                    && alienBullets.get(j).getPosX() <= spaceship.getMaxPosX()
                    && alienBullets.get(j).getMaxPosX() >= spaceship.getPosX())
            {
                alienBullets.get(j).setIsDestroyed();
                alienBullets.get(j).deleteRectangle();
                alienBullets.remove(j);
                reduceLives();
            }
        }
    }
    public void reduceLives(){
        spaceship.setLives();
        switch (spaceship.getLives()) {
            case 5:
                heart[5].delete();
                break;
            case 4:
                heart[4].delete();
                break;
            case 3:
                heart[3].delete();
                break;
            case 2:
                heart[2].delete();
                break;
            case 1:
                heart[1].delete();
                break;
            case 0:
                heart[0].delete();
                //spaceship.changePicture();
                endGame = true;
                break;
        }
    }

    public void summonLives(){
        int nHearts = 5;
        int spacing = 20;
        this.heart = new Picture[nHearts];
        for (int i = 0; i < nHearts; i++){
            this.heart[i] = new Picture(spacing,20, "heart.png");
            heart[i].draw();
            spacing += background.getCellSize()*2+5;
        }
    }

    public void drawText(){
        if(scoreText != null){
            scoreText.delete();
        }
        scoreText = new Text(background.getX()- background.getCellSize()*5, background.getCellSize()*2, "Score " + spaceship.getScore());
        scoreText.grow(30, 10);
        scoreText.setColor(Color.RED);
        scoreText.draw();
    }

    public void stopMusic () {
        sound.stop();
    }

    public void playSFX (int i) {
        sound.setFile(i);
        sound.play();
    }

    public void textIntro () {
        final Timer timerIntro = new Timer();

        final Rectangle textBox3 = new Rectangle(background.getX()/4 - 15, background.getY()/2 - 128 - 15, background.getY()/2 + background.getX()/4 + 10, 256 +30);
        final Rectangle textBox = new Rectangle(background.getX()/4 - 10, background.getY()/2 - 128 - 10, background.getY()/2 + background.getX()/4, 256 +20);
        final Rectangle textBox2 = new Rectangle(background.getX()/4 + 180, background.getY()/2 + 30, background.getX()/4, 96);

        final Picture han = new Picture(background.getX()/4, background.getY()/2 - 128, "Resources/hanSolo.gif");
        final Picture chewie = new Picture(background.getY()/2 + background.getX()/4 + 128, background.getY()/2 - 128, "Resources/chewbacca.png");

        final Picture text1 = new Picture(background.getX()/4 + 200, background.getY()/ 2 + 46, "Resources/text1.png");
        final Picture text2 = new Picture(background.getX()/4 + 200, background.getY()/ 2 + 46, "Resources/text2.png");
        final Picture text3 = new Picture(background.getX()/4 + 280, background.getY()/ 2 + 46, "Resources/text3.png");
        final Picture text4 = new Picture(background.getX()/4 + 200, background.getY()/ 2 + 46, "Resources/text4.png");

        TimerTask taskIntro1 = new TimerTask() {

            @Override
            public void run() {
                textBox3.setColor(Color.WHITE);
                textBox3.fill();
                textBox.setColor(Color.LIGHT_GRAY);
                textBox.fill();
                textBox2.setColor(Color.WHITE);
                textBox2.fill();


                text1.draw();
                han.draw();
            }
        };

        TimerTask taskIntro2 = new TimerTask() {

            @Override
            public void run() {
                text1.delete();
                text3.draw();
                chewie.draw();
            }
        };

        TimerTask taskIntro3 = new TimerTask() {

            @Override
            public void run() {
                text3.delete();
                text2.draw();
            }
        };

        TimerTask taskIntro4 = new TimerTask() {

            @Override
            public void run() {
                text2.delete();
                text4.draw();
            }
        };

        TimerTask taskIntro5 = new TimerTask() {

            @Override
            public void run() {
                han.delete();
                chewie.delete();
                text4.delete();
                textBox.delete();
                textBox2.delete();
                textBox3.delete();
                timerIntro.cancel();
            }
        };

        timerIntro.schedule(taskIntro1, 250);
        timerIntro.schedule(taskIntro2, 3500);
        timerIntro.schedule(taskIntro3, 5500);
        timerIntro.schedule(taskIntro4, 8000);
        timerIntro.schedule(taskIntro5, 9500);
    }
}