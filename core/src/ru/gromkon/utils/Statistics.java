package ru.gromkon.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

import ru.gromkon.base.Font;
import ru.gromkon.math.Rect;
import ru.gromkon.sprite.PlayerShip;

public class Statistics {

    private final static float FONT_HEIGHT = 0.02f;

    private final static String FRAGS = "Frags: ";
    private final static float FRAGS_OFFSET_TOP = 0.01f;
    private final static float FRAGS_OFFSET_RIGHT = 0.01f;

    private final static String HP = "HP: ";
    private final static float HP_OFFSET_BOTTOM = 0.01f;
    private final static float HP_OFFSET_RIGHT = 0.01f;

    private final static String GAME_POINTS = "";
    private final static float GAME_POINTS_OFFSET_TOP = 0.01f;
    private final static float GAME_POINTS_OFFSET_LEFT = 0.01f;


    private Rect worldBounds;

    private Font font;

    private int frags;
    private int gamePoints;
    private int hp;

    private StringBuilder sbFrags;
    private StringBuilder sbGamePoints;
    private StringBuilder sbHP;

    public Statistics() {
        font = new Font("font/font.fnt", "font/font.png");

        frags = 0;
        gamePoints = 0;
        hp = PlayerShip.getHP();

        sbFrags = new StringBuilder();
        sbGamePoints = new StringBuilder();
        sbHP = new StringBuilder();
    }

    public void resize(Rect worldBounds) {
        font.setHeight(FONT_HEIGHT);
        this.worldBounds = worldBounds;
    }

    public void setStartOptions() {
        frags = 0;
        gamePoints = 0;
        hp = PlayerShip.getHP();
    }

    public void frag(String type, boolean isDeathFromOutOfScreen) {
        if (!isDeathFromOutOfScreen) {
            frags += 1;
            if (type.equals(EnemyEmitter.getEnemySmallType())) {
                gamePoints += 10;
            } else if (type.equals(EnemyEmitter.getEnemyMediumType())) {
                gamePoints += 25;
            } else if (type.equals(EnemyEmitter.getEnemyBigType())) {
                gamePoints += 100;
            }
        }
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void drawStat(SpriteBatch batch) {
        drawFrags(batch);
        drawGamePoints(batch);
        drawHP(batch);
    }

    private void drawFrags(SpriteBatch batch) {
        sbFrags.setLength(0);
        sbFrags.append(FRAGS).append(frags);
        font.draw(
                batch,
                sbFrags,
                worldBounds.getRight() - FRAGS_OFFSET_RIGHT,
                worldBounds.getTop() - FRAGS_OFFSET_TOP,
                Align.right
        );
    }

    private void drawGamePoints(SpriteBatch batch) {
        sbGamePoints.setLength(0);
        sbGamePoints.append(GAME_POINTS);
        int length = String.valueOf(Math.abs(gamePoints)).length();
        for (int i = length; i < 7; i++) {
            sbGamePoints.append("0");
        }
        sbGamePoints.append(gamePoints);
        font.draw(
                batch,
                sbGamePoints,
                worldBounds.getLeft() + GAME_POINTS_OFFSET_LEFT,
                worldBounds.getTop() - GAME_POINTS_OFFSET_TOP
        );
    }


    private void drawHP(SpriteBatch batch) {
        sbHP.setLength(0);
        sbHP.append(HP).append(hp);
        font.draw(
                batch,
                sbHP,
                worldBounds.getRight() - HP_OFFSET_RIGHT,
                worldBounds.getBottom() + HP_OFFSET_BOTTOM + FONT_HEIGHT,
                Align.right
        );
    }

    public void dispose() {
        font.dispose();
    }



}
