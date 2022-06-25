package husseinm.opdss;

public class Util {
    public static Point getMidPoint(Point point1, Point point2){
        double diff_x = Math.abs((point1.getX()-point2.getX())/2);
        double diff_y = Math.abs((point1.getY()-point2.getY())/2);
        double x;
        if (point1.compareX(point2))
            x = point2.getX()+diff_x;
        else
            x = point1.getX()+diff_x;

        double y;
        if (point1.compareY(point2))
            y = point2.getY()+diff_y;
        else
            y = point1.getY()+diff_y;

        return new Point(x, y);
    }

    public static double calculateDistance(
            double x1,
            double y1,
            double x2,
            double y2) {

        double ac = Math.abs(y2 - y1);
        double cb = Math.abs(x2 - x1);

        return Math.hypot(ac, cb);
    }
}
