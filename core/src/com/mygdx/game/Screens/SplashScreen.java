package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.sun.javafx.applet.Splash;

/**
 * Created by val on 28/07/2015.
 */
public class SplashScreen implements Screen {
    private Texture texture, logo;
    private Image image, imageLogo;
    private Stage stage = new Stage();

    @Override
    public void show() {
        texture = new Texture(Gdx.files.internal("images/background.png"));
        logo = new Texture(Gdx.files.internal("images/logo.png"));



        image = new Image(texture);
        image.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(image);

        imageLogo = new Image(logo);
        imageLogo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(imageLogo);



        image.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f), Actions.delay(1.5f), Actions.fadeOut(0.5f), Actions.run(new Runnable() {
            @Override
            public void run() {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
            }
        })));

        image.toBack();

        imageLogo.addAction(Actions.sequence(Actions.alpha(0), Actions.delay(0.5f), Actions.fadeIn(0.5f), Actions.delay(1), Actions.fadeOut(0.5f), Actions.run(new Runnable() {
            @Override
            public void run() {

            }
        }) ));

        imageLogo.toFront();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1); //sets clear color to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clear the batch
        stage.act(); //update all actors
        stage.draw(); //draw all actors on the Stage.getBatch()
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
