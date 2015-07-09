package Elements;

import static java.lang.Math.*;

/**
 * Created by Matt on 7/1/2015.
 */
public class CollisionFunctions
{
    public static boolean rectBall(int rx, int ry, int rw, int rh, float bx, float by, int d)
    {
        rx+=rw/2;
        ry+=rh/2;
        float circleDistancex = abs(bx - rx);
        float circleDistancey = abs(by - ry);

        if (circleDistancex > (rw/2 + d)) { return false; }
        if (circleDistancey > (rh/2 + d)) { return false; }

        if (circleDistancex <= (rw/2)) { return true; }
        if (circleDistancey <= (rh/2)) { return true; }

        double cornerDistance_sq = Math.pow(circleDistancex - rw/2,2) + Math.pow(circleDistancey - rh/2,2);
        return (cornerDistance_sq <= Math.pow(d,2));
    }

}
