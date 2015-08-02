package com.mygdx.game.PathFinding;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by val on 02/08/2015.
 */
public class PathFinder {

    private ArrayList<Node> closed = new ArrayList<Node>();
    private ArrayList<Node> open = new ArrayList<Node>();
    private PathMap map;

    private int sx,sy,tx,ty;

    public PathFinder(PathMap map, int sx, int sy, int tx, int ty)
    {
        this.map = map;
        this.sx = sx;
        this.sy = sy;
        this.tx = tx;
        this.ty = ty;

        map.nodes[sx][sy].cost = 0;
        open.clear();
        closed.clear();

        open.add(map.nodes[sx][sy]);
        map.nodes[tx][ty].parent = null;
    }

    public ArrayList<Node> findPath()
    {
        while(open.size() != 0)
        {
            Node current = getFirstInOpen();

            if(current == map.nodes[tx][ty])
            {
                break;
            }

            open.remove(current);
            closed.add(current);

            for(Node neighbour : current.getAllNeighbours())
            {
                if(!neighbour.isCollision() )
                {
                    int nextStepCost = (int)(current.cost + map.getMovementCost(current, neighbour));

                    if (nextStepCost < neighbour.cost) {
                        if (open.contains(neighbour)) {
                            open.remove(neighbour);
                        }
                        if (closed.contains(neighbour)) {
                            closed.remove(neighbour);
                        }
                    }

                    if (!open.contains(neighbour) && !(closed.contains(neighbour))) {
                        neighbour.cost = nextStepCost;
                        neighbour.setParent(current);
                        neighbour.sethCost(map.getCost(neighbour.x, neighbour.y, tx, ty));
                        open.add(neighbour);
                    }
                }
            }

        }

        if(map.nodes[tx][ty].parent == null)
        {
            return null;
        }

        Node target = map.nodes[tx][ty];
        ArrayList<Node> path = new ArrayList<Node>();
        while(target != map.nodes[sx][sy])
        {
            path.add(target);
            target = target.parent;
        }
        Collections.reverse(path);

        return path;

    }

    private Node getFirstInOpen() {
        Node start = open.get(0);

        for(Node node : open )
        {
            if(map.getCost(node.x, node.y, tx,ty) < map.getCost(start.x, start.y, tx,ty))
            {
                start = node;
            }
        }

        return start;

    }
}
