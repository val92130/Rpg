package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Characters.Npc;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.PathFinding.Node;
import com.mygdx.game.PathFinding.PathMap;
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
    private Player player;
    private ArrayList<Npc> npcs;

    public GameMap(String fileName, GameScreen game)
    {
        npcs = new ArrayList<Npc>();
        player = new Player(game, new Texture(Gdx.files.internal("player.png")),60,100, new Vector2(5 * 16 * ratio, 20 * 16 * ratio) );
        this.game = game;
        this.fileName = fileName;
        map = new TmxMapLoader().load(fileName);
        mapRenderer = new OrthogonalTiledMapRenderer(getMap(), ratio);
        ShaderProgram.pedantic = false;
        npcs.add(new Npc(game, new Texture(Gdx.files.internal("npc.png")), 60,100,"Lea", new Vector2(40 * 16 * ratio, 17 * 16 * ratio)));
    }

    public void update()
    {
        player.update();
        for(Npc n : npcs)
        {
            n.update();
        }
    }



    public void draw(SpriteBatch batch)
    {
        mapRenderer.setView(game.getCamera().getCamera());
        //mapRenderer.render();
        mapRenderer.getBatch().begin();
        mapRenderer.renderTileLayer(this.getGroundLayer());
        mapRenderer.renderTileLayer(this.getWaterLayer());

        player.render((SpriteBatch)mapRenderer.getBatch());
        this.renderNpcs((SpriteBatch)mapRenderer.getBatch());

        mapRenderer.renderTileLayer(this.getVegetationLayer());
        mapRenderer.renderTileLayer(this.getCollisionLayer());

        mapRenderer.getBatch().end();

    }

    public void renderNpcs(SpriteBatch batch)
    {
        for(Npc n : npcs)
        {
            n.render(batch);
        }
    }

    static public boolean intersect(Rectangle rectangle1, Rectangle rectangle2, Rectangle intersection) {
        if (rectangle1.overlaps(rectangle2)) {
            intersection.x = Math.max(rectangle1.x, rectangle2.x);
            intersection.width = Math.min(rectangle1.x + rectangle1.width, rectangle2.x + rectangle2.width) - intersection.x;
            intersection.y = Math.max(rectangle1.y, rectangle2.y);
            intersection.height = Math.min(rectangle1.y + rectangle1.height, rectangle2.y + rectangle2.height) - intersection.y;
            return true;
        }
        return false;
    }

    public ArrayList<Npc> getNpcs()
    {
        return npcs;
    }


    public Player getPlayer()
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

    public OrthogonalTiledMapRenderer getMapRenderer()
    {
        return this.mapRenderer;
    }

    public boolean isCollision(int x, int y)
    {
        TiledMapTileLayer t = getCollisionLayer();
        return t.getCell(x,y) != null;
    }

    public Vector2 worldToCellCoords(Vector2 worldCoords)
    {
        return new Vector2((int)(worldCoords.x / Constants.TILE_SIZE / ratio), (int)(worldCoords.y / Constants.TILE_SIZE / ratio) );
    }

    public Vector2 cellToWorldCoords(Vector2 cellCoords)
    {
        return new Vector2(cellCoords.x * game.getMap().getScaleRatio() * Constants.TILE_SIZE,cellCoords.y * game.getMap().getScaleRatio() * Constants.TILE_SIZE);
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
    public TiledMapTileLayer getWaterLayer()
    {
        return (TiledMapTileLayer)map.getLayers().get("water");
    }

    public TiledMapTileLayer.Cell getClosestCell(int x, int y, TiledMapTileLayer layer)
    {
        Vector3 worldCoords = game.getCamera().getCamera().unproject(new Vector3(x,y,0));
        Vector2 selectedTile = new Vector2(worldCoords.x / Constants.TILE_SIZE, worldCoords.y / Constants.TILE_SIZE );
        TiledMapTileLayer.Cell cell = layer.getCell((int)selectedTile.x / ratio, (int)selectedTile.y / ratio);
        return cell;
    }

    public TiledMapTileLayer.Cell getClosestCellWorldCoords(int x, int y, TiledMapTileLayer layer)
    {
        Vector2 selectedTile = new Vector2(x / Constants.TILE_SIZE, y / Constants.TILE_SIZE );
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
