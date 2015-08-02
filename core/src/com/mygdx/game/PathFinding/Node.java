package com.mygdx.game.PathFinding;

import java.util.ArrayList;

/**
 * Created by val on 02/08/2015.
 */
public class Node {

    int x,y;
    PathMap map;
    Node parent = null;
    private int gCost;
    private float hCost;
    boolean collision = false;
    public int cost = 0;
    public Node(int x, int y, PathMap map)
    {
        this.x = x;
        this.y = y;
        this.map = map;
    }

    public boolean isCollision()
    {
        return collision;
    }

    public void setCollision(boolean col)
    {
        this.collision = col;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public Node getParent()
    {
        return this.parent;
    }

    public void setParent(Node node)
    {
        this.parent = node;
    }

    public Node topNeighbour()
    {
        return this.map.getNodeAt(x, y+1);
    }
    public Node bottomNeighbour()
    {
        return this.map.getNodeAt(x, y-1);
    }
    public Node leftNeighbour()
    {
        return this.map.getNodeAt(x - 1, y);
    }
    public Node rightNeighbour()
    {
        return this.map.getNodeAt(x + 1, y);
    }

    public ArrayList<Node> getAllNeighbours()
    {
        ArrayList<Node> list = new ArrayList<Node>();

        if(topNeighbour() != null)
        {
            list.add(topNeighbour());
        }

        if(bottomNeighbour() != null)
        {
            list.add(bottomNeighbour());
        }

        if(leftNeighbour() != null)
        {
            list.add(leftNeighbour());
        }
        if(rightNeighbour() != null)
        {
            list.add(rightNeighbour());
        }


        /* if wanna allow diagonal, uncomment
        list.clear();
        for (int x2=-1;x2<2;x2++) {
            for (int y2 = -1; y2 < 2; y2++) {
                if ((x2 == 0) && (y2 == 0)) {
                    continue;
                }

                Node t = map.getNodeAt(this.x + x2,this.y + y2);

                if(t != null)
                {
                    list.add(t);
                }
            }
        }
        */


        return list;
    }

    public int getgCost() {
        return gCost;
    }

    public void setgCost(int gCost) {
        this.gCost = gCost;
    }

    public float gethCost() {
        return hCost;
    }

    public void sethCost(float hCost) {
        this.hCost = hCost;
    }

    public float getFValue()
    {
        return hCost + gCost;
    }
}
