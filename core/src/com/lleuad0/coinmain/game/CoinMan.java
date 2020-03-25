package com.lleuad0.coinmain.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CoinMan extends ApplicationAdapter {
    SpriteBatch batch;
    Texture background;
    Texture[] coinman;
    int spriteState;
    int pause;
    int coordY;
    float jumpHeight;
    float fallHeightPerFrame;

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("bg.png");
        coinman = new Texture[4];
        coinman[0] = new Texture("frame-1.png");
        coinman[1] = new Texture("frame-2.png");
        coinman[2] = new Texture("frame-3.png");
        coinman[3] = new Texture("frame-4.png");
        spriteState = 0;
        pause = 0;
        coordY = (Gdx.graphics.getHeight() - coinman[spriteState].getHeight()) / 2;
        jumpHeight = coinman[spriteState].getHeight() * 0.325f;
        fallHeightPerFrame = 6.75f;
    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // regulating sprite drawing speed
        if (pause < 8) {
            pause++;
        } else {
            if (spriteState < 3) {
                spriteState++;
            } else {
                spriteState = 0;
            }
            pause = 0;
        }

        if (Gdx.input.justTouched()) {
            coordY += jumpHeight;
        }

        // managing natural falling speed
        coordY -= fallHeightPerFrame;

        // preventing the sprite from going out of bounds
        if (coordY <= 0) {
            coordY = 0;
        } else if (coordY > Gdx.graphics.getHeight() - coinman[spriteState].getHeight()) {
            coordY = Gdx.graphics.getHeight() - coinman[spriteState].getHeight();
        }

        //regular sprite cycle
        batch.draw(coinman[spriteState],
                (Gdx.graphics.getWidth() - coinman[spriteState].getWidth()) / 2,
                coordY);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
