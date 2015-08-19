package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Screens.GameScreen;

/**
 * Created by val on 06/08/2015.
 */
public class Npc extends Character {

    private String name;
    public Npc(GameScreen game, Texture texture, int width, int height, String name)
    {
        super(game,texture,width,height);
        this.name = name;
    }

    public Npc(GameScreen game, Texture texture, int width, int height,String name, Vector2 position)
    {
        this(game,texture,width,height, name);
        this.position = position;
    }

    public String getName()
    {
        return this.name;
    }

}
