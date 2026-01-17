package io.github.some_example_name.screens;

import static io.github.some_example_name.Main.SCR_HEIGHT;
import static io.github.some_example_name.Main.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

import io.github.some_example_name.Main;
import io.github.some_example_name.characters.Bird;
import io.github.some_example_name.characters.Tube;
import io.github.some_example_name.components.MovingBackround;
import io.github.some_example_name.components.PointCounter;
import io.github.some_example_name.components.ShieldPickup;
import io.github.some_example_name.components.NitroPickup;
public class ScreenGame implements Screen {

    Main main;

    Bird bird;

    MovingBackround movingbackround;
    NitroPickup nitroPickup;
    Texture nitroIcon;

    int tubeCount = 3;
    Tube[] tubes;
    int baseTubeSpeed = 6;
    int nitroBonusSpeed = 5;

    int gamePoints;
    PointCounter pointCounter;

    int lastShieldSpawnPoints = -999;

    int shieldStep = 10;
    int nextNitroScore = 8;
    float NitroTimer = 0f;
    boolean NitroActive = false;

    final int pointCounterMarginTop = 60;
    final int pointCounterMarginRight = 400;

    float invincibleTimer = 0f;

    boolean isGameOver;

    int tubeSpeed = 6;
    int maxTubeSpeed = 14;
    int speedUp = 10;

    boolean ShieldActive = false;
    float shieldTimer = 0f;

    int nextShieldScore = 10;

    ShieldPickup shieldPickup;
    Texture shieldIcon;

    Random random = new Random();

    public ScreenGame(Main game) {
        this.main = game;

        bird = new Bird(0, 0, 10, 50, 40);
        initTubes();

        pointCounter = new PointCounter(SCR_WIDTH - pointCounterMarginRight, SCR_HEIGHT - pointCounterMarginTop);
        movingbackround = new MovingBackround("backgrounds/game_bg.png");

        shieldPickup = new ShieldPickup("Buffs/Shield.png", 64);
        shieldIcon = new Texture("Buffs/Shield.png");
        nitroIcon = new Texture("Buffs/Energetic.png");
        nitroPickup = new NitroPickup("Buffs/Energetic.png", 64);
    }

    @Override
    public void show() {
        isGameOver = false;
        gamePoints = 0;
        shieldStep = 10;
        nextShieldScore = 10;

        baseTubeSpeed = 6;
        tubeSpeed = baseTubeSpeed;

        ShieldActive = false;
        shieldTimer = 0f;
        NitroActive = false;
        NitroTimer = 0f;
        nextNitroScore = 13;
        nitroPickup.deactivate();

        shieldPickup.deactivate();

        bird.setY(SCR_HEIGHT / 2);
        initTubes();
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.justTouched()) {
            bird.onClick();
        }

        movingbackround.move();
        bird.fly();

        if (invincibleTimer > 0)
        {
            invincibleTimer -= delta;
        }

        if (!bird.isInField()) {
            isGameOver = true;
        }

        if (ShieldActive) {
            shieldTimer -= delta;
            if (shieldTimer <= 0) {
                ShieldActive = false;
                shieldTimer = 0;
            }
        }
        if(NitroActive)
        {
            NitroTimer -= delta;
            if(NitroTimer <= 0)
            {
                NitroActive = false;
                NitroTimer = 0f;

                tubeSpeed = baseTubeSpeed;
                for (Tube tube : tubes)
                {
                    tube.setSpeed(tubeSpeed);
                }
            }
        }

        shieldPickup.update(tubeSpeed);
        nitroPickup.update(tubeSpeed);

        for (Tube tube : tubes) {

            tube.move(tubeSpeed);

            if (tube.isHint(bird)) {
                if (ShieldActive) {
                    ShieldActive = false;
                    shieldTimer = 0;
                    bird.setY(SCR_HEIGHT / 2);
                    invincibleTimer = 4f;
                } else {
                    isGameOver = true;
                }
            }

            if (tube.needAddPoint(bird)) {

                gamePoints ++;
                tube.setPointReceived();
                pointCounter.pulse();
                if (gamePoints % speedUp == 0 && baseTubeSpeed < maxTubeSpeed) {

                    baseTubeSpeed += 1;

                    tubeSpeed = baseTubeSpeed + (NitroActive ? nitroBonusSpeed : 0);

                    for (Tube t : tubes) {
                        t.setSpeed(tubeSpeed);
                    }
                }
                if (gamePoints == nextShieldScore && !shieldPickup.isActive()) {
                    lastShieldSpawnPoints = gamePoints;

                    Tube tube1 = tubes[random.nextInt(tubeCount)];

                    float spawnX = tube1.getX() + tube1.getWidth() / 2f;
                    float spawnY = tube1.getGapY() - 32;

                    shieldPickup.spawn(spawnX, spawnY);
                    nextShieldScore += shieldStep;

                    nextShieldScore += 2;
                }

                    if (gamePoints == nextNitroScore && !nitroPickup.isActive()) {

                        Tube tube1 = tubes[random.nextInt(tubeCount)];

                        float spawnX = tube1.getX() + tube1.getWidth() / 2f;
                        float spawnY = tube1.getGapY() - 32;

                        nitroPickup.spawn(spawnX, spawnY);
                        nextNitroScore += 8 + random.nextInt(13);
                    }
            }
        }

        if (shieldPickup.isActive() && bird.getrect().overlaps(shieldPickup.get_rect()))
        {
            shieldPickup.deactivate();

            ShieldActive = true;
            shieldTimer = 10f;
        }
        if (nitroPickup.isActive() && bird.getrect().overlaps(nitroPickup.get_rect())) {
            nitroPickup.deactivate();

            NitroActive = true;
            NitroTimer = 10f;

            tubeSpeed = baseTubeSpeed + nitroBonusSpeed;

            for (Tube t : tubes) {
                t.setSpeed(tubeSpeed);
            }
        }



            if (isGameOver) {
            main.screenRestart.gamePoints = gamePoints;
            main.setScreen(main.screenRestart);
            return;
        }

        ScreenUtils.clear(0, 0, 0, 1);

        main.camera.update();
        main.batch.
            setProjectionMatrix(main.camera.combined);

        main.batch.begin();

        movingbackround.onDraw(main.batch);

        for (Tube tube : tubes) tube.draw(main.batch);

        shieldPickup.draw(main.batch);
        nitroPickup.draw(main.batch);

        boolean visible = true;
        if(invincibleTimer > 0)
        {
            visible = ((int)(invincibleTimer * 10) % 2 == 0);
        }

        bird.draw(main.batch, true);

        if (ShieldActive) {
            int size = 64;
            main.batch.draw(shieldIcon, 20, SCR_HEIGHT - 80, size, size);
        }

        if (NitroActive)
        {
            int size = 64;
            main.batch.draw(nitroIcon, 100, SCR_HEIGHT - 80, size, size);
        }

        pointCounter.draw(main.batch, gamePoints, delta);

        main.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        bird.dispose();
        pointCounter.dispose();
        shieldPickup.dispose();
        shieldIcon.dispose();
        nitroIcon.dispose();
        nitroPickup.dispose();

        for (int i = 0; i < tubeCount; i++) {
            tubes[i].dispose();
        }
    }

    void initTubes() {
        tubes = new Tube[tubeCount];
        for (int i = 0; i < tubeCount; i++) {
            tubes[i] = new Tube(tubeCount, i, tubeSpeed);
        }
    }
}
