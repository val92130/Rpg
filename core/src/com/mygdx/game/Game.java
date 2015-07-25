package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

/**
 * Created by val on 23/07/2015.
 */
public class Game
{
    Box[][] boxes;
    int mapSizeCm;
    int boxCountPerLine;
    int boxSize;
    TextureManager textureManager;
    Camera camera;
    InputManager inputManager;
    BitmapFont font;
    StopWatch watch;
    FPSLogger fpsLogger = new FPSLogger();

    public Game(int boxCountPerLine, int boxSize)
    {
        inputManager = new InputManager(this);
        Gdx.input.setInputProcessor(inputManager);
        camera = new Camera(this);
        textureManager = new TextureManager();
        textureManager.loadTextures();
        this.mapSizeCm = boxCountPerLine * boxSize;
        this.boxSize = boxSize;
        this.boxCountPerLine = boxCountPerLine;
        this.createBoxes();
        boxes[0][0].setTextureType(EBoxGround.WATER);

        font = new BitmapFont();
        font.setColor(Color.WHITE);

        watch = new StopWatch();
    }

    public void createBoxes()
    {
        boxes = new Box[boxCountPerLine][boxCountPerLine];
        for(int i = 0; i < boxCountPerLine; i++)
        {
            for(int j = 0; j < boxCountPerLine; j++)
            {
                boxes[i][j] = new Box(this,i * boxSize,j * boxSize,boxSize,boxSize);
                if(i % 2 == 0 && j %2 == 0)
                {
                    boxes[i][j].setTextureType(EBoxGround.DIRT);
                }
            }
        }
    }

    public Box getBoxAt(int x, int y)
    {
        if(x >= 0 && y >= 0 && x < boxes.length && y < boxes.length)
        {
            return boxes[x][y];
        }
        return null;
    }

    public Box[][] getBoxes()
    {
        return boxes;
    }

    public void Update()
    {
        UpdateBoxes();
        inputManager.Update();
        camera.Update();
    }

    public void Render(SpriteBatch spriteBatch)
    {
        spriteBatch.setProjectionMatrix(camera.getCamera().combined);
        Gdx.gl.glClearColor(0,0,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        this.RenderBoxes(spriteBatch);
        Vector3 fontPos = new Vector3(0,0,0);
        fontPos = camera.getCamera().unproject(fontPos);
        font.draw(spriteBatch, String.valueOf(Gdx.graphics.getFramesPerSecond()) + " FPS", fontPos.x, fontPos.y);
        spriteBatch.end();
    }


    public void UpdateBoxes()
    {
        for(Box[] box : boxes)
        {
            for(Box b : box)
            {
                b.Update();
            }
        }
    }

    public void RenderBoxes(SpriteBatch spriteBatch)
    {

        Rectangle camRect = new Rectangle(this.camera.getCamera().position.x,this.camera.getCamera().position.y, this.camera.getCamera().viewportWidth, this.camera.getCamera().viewportHeight );
        ArrayList<Box> boxList = this.getOverlappedBoxes();
        for(Box bo : boxList)
        {
            bo.Draw(spriteBatch);
        }

    }

    public ArrayList<Box> getOverlappedBoxes()
    {
        ArrayList boxList = new ArrayList<Box>();
        int left = (int)(camera.getCamera().position.x - (camera.getEffectiveViewportWidth() / 2.0)) / this.boxSize;
        int top = (int)(camera.getCamera().position.y + (camera.getEffectiveViewportHeight() / 2.0) )/ this.boxSize;
        int right = (int)(camera.getCamera().position.x + (camera.getEffectiveViewportWidth() / 2.0))/ this.boxSize ;
        int bottom = (int)(camera.getCamera().position.y - (camera.getEffectiveViewportHeight() / 2.0))/ this.boxSize ;

        for(int i = left; i < right + 1; i++)
        {
            for(int j = top; j > bottom - 1; j--)
            {
                Box b = this.getBoxAt(i,j);
                if(b != null)
                {
                    boxList.add(b);
                }
            }
        }

        return boxList;
    }

    public TextureManager getTextureManager()
    {
        return this.textureManager;
    }

    public Camera getCamera()
    {
        return camera;
    }
}
