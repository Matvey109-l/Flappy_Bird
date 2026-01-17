package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.screens.ScreenGame;
import io.github.some_example_name.screens.ScreenRestart;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */

public class Main extends Game {

    public ScreenGame screenGame;

    public ScreenRestart screenRestart;

    public SpriteBatch batch;
    public static final int SCR_WIDTH = 1280;
    public static final int SCR_HEIGHT = 720;
    public OrthographicCamera camera;



    @Override
    public void create () {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        screenRestart = new ScreenRestart(this);
        screenGame = new ScreenGame(this);
        setScreen(screenGame);
    }



    @Override
    public void dispose() {
        batch.dispose();
    }

}
