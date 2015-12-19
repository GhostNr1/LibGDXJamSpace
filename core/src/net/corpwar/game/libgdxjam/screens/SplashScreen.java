package net.corpwar.game.libgdxjam.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import net.corpwar.game.libgdxjam.LibGDXJamSpace;

/**
 * LibGDXJam
 * Created by Ghost on 2015-12-19.
 */
public class SplashScreen implements Screen {

    private LibGDXJamSpace jamSpace;

    private Texture logo;
    private float timer;
    private final float timeToWait = 1f;

    public SplashScreen(LibGDXJamSpace jamSpace) {
        this.jamSpace = jamSpace;
    }

    @Override
    public void show() {
        logo = new Texture("corpwar_logo.png");
    }

    @Override
    public void render(float delta) {
        timer += delta;
        jamSpace.batch.setProjectionMatrix(jamSpace.camera.combined);
        jamSpace.batch.begin();
        jamSpace.batch.draw(logo, jamSpace.viewport.getWorldWidth() / 2 - logo.getWidth() / 2, jamSpace.viewport.getWorldHeight() / 2 - logo.getHeight() / 2);
        jamSpace.batch.end();

        if (timer > timeToWait) {
            jamSpace.setScreen(jamSpace.gameScreen);
        }
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

    }
}
