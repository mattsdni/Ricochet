package Elements;

import Main.GUI;
import States.Level_1;
import processing.core.PApplet;

/**
 * Created by Matt on 6/27/2015.
 */
public class Player
{
    PApplet p;
    public float x,y;
    public int size;
    float mid;
    private int movement;
    private int ammo;
    public boolean wouldCollide;

    public Player(PApplet _p)
    {
        p = _p;
        x = p.width/2;
        y = p.height/2;
        size = 50;
        mid = 0;
        movement = 0;
        ammo = 0;
        wouldCollide = false;
    }

    public void update()
    {

    }

    public void display()
    {
        p.fill(255);
        p.ellipse(x, y, size, size);
        p.noFill();
        p.stroke(255);

        p.pushMatrix();
        p.translate(x, y);
        float mid = PApplet.atan2(p.mouseY - y, p.mouseX - x);
        p.popMatrix();

        p.arc(x, y, size * 1.5f, size * 1.5f, mid - .2f, mid + .2f);

    }

    public void shoot()
    {
        if (ammo <= 0)
            return;
        mid = PApplet.atan2(p.mouseY - y, p.mouseX - x);
        Level_1.projectiles.add(new Projectile(p, PApplet.cos(mid), PApplet.sin(mid), x, y));
        ammo--;
    }

    /**
     * Moves the Player
     * @param direction (in radians)
     */
    public void move(float direction)
    {
        if (movement <= 0)
            return;
        if (wouldCollide)
        {
            wouldCollide = false;
            return;
        }
        x += 200*GUI.stateMachine.timeElapsed * Math.cos(direction);
        y += 200*GUI.stateMachine.timeElapsed * Math.sin(direction);
        movement-=1;
    }

    public void setPosition(float _x, float _y)
    {
        x = _x;
        y = _y;
    }

    public int getMovement()
    {
        return movement;
    }

    public void setMovement(int m)
    {
        movement = m;
    }

    public int getAmmo()
    {
        return ammo;
    }

    public void setAmmo(int a)
    {
        ammo = a;
    }

}
