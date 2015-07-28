package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by val on 28/07/2015.
 */
public class MainMenuScreen implements Screen {

    private Stage stage = new Stage();
    private Skin skin;
    private TextureAtlas textureAtlas;
    private Table table;
    private TextButton buttonExit, buttonPlay;
    private BitmapFont white, black;
    private Label heading;
    private Texture backGround;
    private SpriteBatch spriteBatch;
    @Override
    public void show() {

        spriteBatch = new SpriteBatch();
        backGround = new Texture(Gdx.files.internal("images/menu-background.png"));
        Gdx.input.setInputProcessor(stage);
        textureAtlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(textureAtlas);
        table = new Table(skin);
        table.setBounds(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        white = new BitmapFont(Gdx.files.internal("fonts/white.fnt"));
        black = new BitmapFont(Gdx.files.internal("fonts/black.fnt"));


        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button.up");
        textButtonStyle.down = skin.getDrawable("button.down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = white;

        buttonExit = new TextButton("Exit", textButtonStyle);
        buttonExit.pad(20);
        buttonExit.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                super.clicked(event, x, y);
            }
        });

        buttonPlay = new TextButton("Play", textButtonStyle);
        buttonPlay.pad(20);
        buttonPlay.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });

        buttonPlay.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1.5f)));
        buttonExit.addAction(Actions.sequence(Actions.alpha(0), Actions.delay(0.5f), Actions.fadeIn(1.5f)));


        Label.LabelStyle headingStyle = new Label.LabelStyle();
        headingStyle.font=white;


        heading=new Label("Gold Skies",headingStyle);

        heading.setFontScale(3);
        heading.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(2.5f, Interpolation.sine)));

        table.add(heading);
        table.row();
        table.add(buttonPlay);
        table.row();
        table.row();
        table.add(buttonExit);

        table.row();


        //table.debug();
        stage.addActor(table);


        }

        @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(backGround, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.end();
        stage.act();
        stage.draw();
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
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
