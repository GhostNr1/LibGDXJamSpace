package net.corpwar.game.libgdxjam.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import net.corpwar.game.libgdxjam.LibGDXJamSpace;

/**
 * LibGDXJam
 * Created by Ghost on 2015-12-19.
 */
public class GameScreen implements Screen {

    private LibGDXJamSpace jamSpace;

    public GameScreen(LibGDXJamSpace jamSpace) {
        this.jamSpace = jamSpace;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        jamSpace.arthWorld.setDelta(Gdx.graphics.getDeltaTime());
        jamSpace.arthWorld.process();
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
