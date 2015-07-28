package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Screens.GameScreen;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by val on 26/07/2015.
 */
public class GameMap {
    private GameScreen game;
    private String fileName;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private int ratio = 4;
    private Character player;

    public GameMap(String fileName, GameScreen game)
    {
        player = new Character(game, new Texture(Gdx.files.internal("player.png")),60,100, new Vector2(5 * 16 * ratio, 20 * 16 * ratio) );
        this.game = game;
        this.fileName = fileName;
        map = new TmxMapLoader().load(fileName);

        mapRenderer = new OrthogonalTiledMapRenderer(getMap(), ratio);
    }

    public void update()
    {
        player.update();
    }

    public void draw(SpriteBatch batch)
    {
        mapRenderer.setView(game.getCamera().getCamera());
        mapRenderer.render();
        batch.begin();

        player.render(batch);
        batch.end();
    }

    public Character getPlayer()
    {
        return player;
    }

    public HashMap<TiledMapTileLayer.Cell, Vector2> getCollisionCells()
    {
        HashMap<TiledMapTileLayer.Cell, Vector2> cells = new HashMap<TiledMapTileLayer.Cell, Vector2>();
        for(int i = 0; i < getCollisionLayer().getWidth(); i++)
        {
            for(int j = 0; j < getCollisionLayer().getHeight(); j++)
            {
                TiledMapTileLayer.Cell c = this.getCollisionLayer().getCell(i, j);
                if(c != null)
                {
                    cells.put(c, new Vector2(i,j));
                }
            }
        }
        return cells;
    }

    public boolean isCollision(int x, int y)
    {
        TiledMapTileLayer t = getCollisionLayer();
        return t.getCell(x,y) != null;
    }

    public int getScaleRatio()
    {
        return ratio;
    }

    public boolean isCollisionClosestCell(int x, int y)
    {
        Vector3 worldCoords = game.getCamera().getCamera().unproject(new Vector3(x,y,0));
        Vector2 selectedTile = new Vector2(worldCoords.x / Constants.TILE_SIZE, worldCoords.y / Constants.TILE_SIZE );
        TiledMapTileLayer t = getCollisionLayer();
        return t.getCell((int)selectedTile.x / ratio ,(int)selectedTile.y / ratio) != null;
    }

    public boolean isCollisionScreenCords(int x, int y)
    {
        Vector3 worldCoords = game.getCamera().getCamera().unproject(new Vector3(x,y,0));

        TiledMapTileLayer t = getCollisionLayer();
        return t.getCell((int)worldCoords.x / ratio ,(int)worldCoords.y / ratio) != null;
    }

    public TiledMapTileLayer.Cell getCellAt(int x, int y, TiledMapTileLayer layer)
    {
        return layer.getCell(x,y);
    }

    public TiledMapTileLayer getCollisionLayer()
    {
        return (TiledMapTileLayer)map.getLayers().get("collision");
    }

    public TiledMapTileLayer getGroundLayer()
    {
        return (TiledMapTileLayer)map.getLayers().get("ground");
    }

    public TiledMapTileLayer getVegetationLayer()
    {
        return (TiledMapTileLayer)map.getLayers().get("vegetation");
    }

    public TiledMapTileLayer.Cell getClosestCell(int x, int y, TiledMapTileLayer layer)
    {
        Vector3 worldCoords = game.getCamera().getCamera().unproject(new Vector3(x,y,0));
        Vector2 selectedTile = new Vector2(worldCoords.x / Constants.TILE_SIZE, worldCoords.y / Constants.TILE_SIZE );
        TiledMapTileLayer.Cell cell = layer.getCell((int)selectedTile.x / ratio, (int)selectedTile.y / ratio);
        return cell;
    }

    public TiledMapTileLayer.Cell getClosestCell(float x, float y, TiledMapTileLayer layer)
    {
        return this.getClosestCell((int)x, (int)y,layer );
    }


    public TiledMap getMap() {
        return map;
    }
}
