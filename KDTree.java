package bearmaps;

import java.util.List;

public class KDTree implements PointSet{

    private final PointComparatorNS NScomp = new PointComparatorNS();
    private final PointComparatorEW EScomp = new PointComparatorEW();
    private TreeNode root;

    private class TreeNode{
        Point pos;
        TreeNode Left = null;
        TreeNode Right = null;

        public TreeNode(Point p){
            pos = p;
        }
        public double distance(Point p){
            return Point.distance(pos, p);
        }
    }


    public KDTree(List<Point> points){
        Boolean boo = false;
        root = helpInsert(points.get(0), boo, root);
        for (int i = 1; i<points.size();i++){
            helpInsert(points.get(i), boo, root);
        }
        }

        private TreeNode helpInsert(Point p, boolean depth, TreeNode current){
            if(current == null){
                return new TreeNode(p);
            }
            if(current.pos.equals(p)){
                return current;
            }
            int child;
            if(depth){
                child = NScomp.compare(current.pos, p);
                if(0 < child){
                    current.Left = helpInsert(p, !depth, current.Left);
                }else{
                    current.Right= helpInsert(p, !depth, current.Right);
                }
            }else{
                child = EScomp.compare(current.pos, p);
                if(child > 0){
                    current.Left= helpInsert(p,!depth, current.Left);
                }else{
                   current.Right= helpInsert(p,!depth, current.Right);
                }
            }
                return current;
        }

    @Override
    public Point nearest(double x, double y){
        Point look = new Point(x, y);
        TreeNode start = new TreeNode(root.pos);
        Point p = nearhelp(root, look, start, false);
        return p;

    }


    private Point nearhelp(TreeNode current, Point look4, TreeNode best, boolean boo){
        if(current == null){
            return best.pos;
        }
        TreeNode goodside;
        TreeNode badside;
        if(current.distance(look4) < best.distance(look4)){
            best = new TreeNode(current.pos);

        }
        if(boo){
            if(NScomp.compare(look4, current.pos) < 0){
             goodside = current.Left;
             badside = current.Right;
            }
            else {
                 goodside = current.Right;
                 badside = current.Left;
            }
        }
        else{
            if(EScomp.compare(look4, current.pos) < 0){
                 goodside = current.Left;
                 badside = current.Right;
            }
            else {
                 goodside = current.Right;
                 badside = current.Left;
            }
        }
         best.pos = nearhelp(goodside, look4, best, !boo);
        if(boo) {
            if (Math.pow(look4.getY() - current.pos.getY(), 2) < Point.distance(best.pos, look4)) {
                best.pos = nearhelp(badside, look4, best, !boo);
            }
        }
       else{
           if (Math.pow(look4.getX() - current.pos.getX(), 2) < Point.distance(best.pos, look4)) {
               best.pos = nearhelp(badside, look4, best, !boo);
                    }
                }
            return best.pos;
        }
    }

