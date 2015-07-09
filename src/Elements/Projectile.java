package Elements;

import Main.GUI;
import Main.StateMachine;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created by Matt on 6/27/2015.
 */
public class Projectile
{
    PApplet p;
    public PVector v;
    public int size;
    public float x,y;
    public boolean offscreen;

    public Projectile(PApplet _p, float vx, float vy, float _x, float _y)
    {
        p = _p;
        size = 10;
        v = new PVector(vx,vy);
        x = _x+v.x*12;
        y = _y+v.y*12;
        offscreen = false;

    }

    public void update()
    {
        x += v.x*400 * GUI.stateMachine.timeElapsed;
        y += v.y*400 * GUI.stateMachine.timeElapsed;
        if (x < -20 || x > p.width+20 || y < -20 || y > p.height+20)
        {
            offscreen = true;
        }
    }
    public void display()
    {
        p.fill(255);
        p.ellipse(x,y,size,size);
    }

}
