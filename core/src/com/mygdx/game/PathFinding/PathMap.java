package com.mygdx.game.PathFinding;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameMap;
import com.mygdx.game.Screens.GameScreen;

import java.util.ArrayList;

/**
 * Created by val on 02/08/2015.
 */
public class PathMap {

    public Node[][] nodes;
    int mapWidth, mapHeight, boxPerLines;
    private Node targetNode, startNode;
    private Vector2 targetV, startV;
    private GameMap map;
    PathFinder pathFinder;

    public PathMap(GameMap map, Vector2 start, Vector2 target)
    {
        this.map = map;
        startV = start;
        targetV = target;
        init();
    }

    public void init()
    {
        TiledMapTileLayer groundLayer = map.getGroundLayer();
        TiledMapTileLayer colLayer = map.getCollisionLayer();
        TiledMapTileLayer waterLayer = map.getWaterLayer();
        TiledMapTileLayer vegetationLayer = map.getVegetationLayer();

        boxPerLines = groundLayer.getWidth();
        mapWidth = groundLayer.getWidth();
        mapHeight = groundLayer.getHeight();

        nodes = new Node[groundLayer.getWidth()][groundLayer.getHeight()];

        for(int i = 0; i < groundLayer.getWidth(); i++)
        {
            for(int j = 0; j < groundLayer.getHeight(); j++)
            {
                Node candidate;
                candidate = new Node(i,j, this);
                if(groundLayer.getCell(i,j) == null || colLayer.getCell(i,j) != null || waterLayer.getCell(i,j) != null || vegetationLayer.getCell(i,j) != null)
                {
                    // collision cell !
                    candidate.collision = true;
                }

                nodes[i][j] = candidate;
            }
        }

        targetNode = nodes[(int)targetV.x][(int)targetV.y];
        startNode = nodes[(int)startV.x][(int)startV.y];

        System.out.println("Length "  + nodes.length);

    }

    public ArrayList<Node> findPath()
    {
        PathFinder p = new PathFinder(this, startNode.x, startNode.y, targetNode.x, targetNode.y);
        return p.findPath();
    }


    public Node getNodeAt(int x, int y)
    {
        if(x < 0 || x > boxPerLines - 1 || y < 0 || y > boxPerLines - 1)
        {
            return null;
        }
        return nodes[x][y];
    }

    public float getCost(int x, int y, int destX, int destY)
    {
        float dx = Math.abs(x - destX);
        float dy = Math.abs(y - destY);
        return dx + dy;
    }

    public float getMovementCost(Node a, Node b)
    {
        return getCost(a.x, a.y, b.x, b.y) == 1 ? 1 : 1.5f;
    }


}
