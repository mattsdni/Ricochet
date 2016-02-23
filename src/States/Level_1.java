package States;

import Elements.*;
import Main.GUI;
import controlP5.ControlEvent;
import controlP5.ControlFont;
import controlP5.ControlP5;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

import java.util.LinkedList;

/**
 * Created by Matt on 5/25/2015.
 * An empty state
 */
public class Level_1 extends PApplet implements IState
{
    PApplet p;
    ControlP5 ui;
    PFont pfont;
    ControlFont font;
    Player player1;
    boolean[] downKeys;
    LinkedList<Wall> walls;
    public static LinkedList<Projectile> projectiles;

    /**
     * Constructs the state
     * @param _p a referece to the parent PApplet.
     */
    public Level_1(PApplet _p, ControlP5 _ui, PFont _pfont, boolean[] _downKeys)
    {
        p = _p;
        ui = _ui;
        pfont = _pfont;
        downKeys = _downKeys;
    }

    @Override
    /**
     * Calculates physics, animations, and any other actions that happen.
     */
    public void Update(double elapsedTime)
    {
        //******************************************************************//
        //----------------------Update Player Location----------------------//
        //******************************************************************//
        for (int i = 0; i < downKeys.length; i++)
        {
            if (downKeys[i])
            {
                if ((char) i == 'w')
                {
                    player1.move((3*PConstants.PI)/2);
                }
                if ((char) i == 'a')
                {
                    player1.move(PConstants.PI);
                }
                if ((char) i == 's')
                {
                    player1.move(PConstants.PI/2);
                }
                if ((char) i == 'd')
                {
                    player1.move(0);
                }
            }
        }

        //******************************************************************//
        //-------------------Wall vs Projectile Collision-------------------//
        //******************************************************************//
        for (int i = 0; i < projectiles.size(); i++)
        {
            Projectile prj = projectiles.get(i);
            prj.update();

            for (Wall w : walls)
            {
                if (CollisionFunctions.rectBall(w.x, w.y, w.w, w.h, prj.x, prj.y, prj.size))
                {
                    //left
                    if ((w.x > prj.x) && (w.x + w.w > prj.x + prj.size))
                    {
                        prj.v.x*=-1;
                    }
                    //right
                    else if ((w.x < prj.x) && (w.x + w.w < prj.x + prj.size))
                    {
                        prj.v.x*=-1;
                    }
                    //top
                    else if ((w.y > prj.y) && (w.y + w.h > prj.y + prj.size))
                    {
                        prj.v.y*=-1;
                    }
                    //bottom
                    else if ((w.y < prj.y) && (w.y + w.h < prj.y + prj.size))
                    {
                        prj.v.y*=-1;
                    }
                }
            }

            if (prj.offscreen)
            {
                projectiles.remove(i);
            }
        }

        //******************************************************************//
        //---------------------Player vs Wall Collision---------------------//
        //******************************************************************//
//        for (Wall w : walls)
//        {
//            if (CollisionFunctions.rectBall(w.x, w.y, w.w, w.h, player1.x, player1.y, player1.size/2))
//            {
//                if (player1.v.x > 0)
//                    player1.setPosition(player1.prevX, player1.prevY);
//                if (player1.v.y > 0)
//                    player1.setPosition(player1.prevX, player1.prevY);
//                player1.wouldCollide = true;
//                System.out.println("p collildl");
//            }
//        }

    }

    @Override
    /**
     * Displays everything to the screen.
     */
    public void Render()
    {
        p.background(35, 49, 63);

        //Display all walls
        for (Wall w : walls)
        {
            w.display();
        }

        for (Projectile p : projectiles)
        {
            p.display();
        }

        //Display Player
        player1.display();

        //Display UI Text
        p.textSize(36);
        p.textAlign(PConstants.CENTER);
        p.fill(71,119,198);
        p.text("Level: 1", p.width*.04f, p.height*.04f);
        p.text("Movement: " + player1.getMovement(), p.width*.18f, p.height*.04f);
        p.text("Ammo: " + player1.getAmmo(), p.width*.32f, p.height*.04f);
    }

    @Override
    /**
     * Sets up any variables needed for the duration of the state.
     */
    public void OnEnter()
    {
        font = new ControlFont(pfont,48);
        projectiles = new LinkedList<Projectile>();
        walls = new LinkedList<Wall>();
        //******************************************************************//
        //------------------------Create UI Elements------------------------//
        //******************************************************************//
        ui.addButton("mainmenu")
                .setBroadcast(false)
                .setValue(0)
                .setPosition(p.width*.8f,p.height*.8f)
                .setSize(250, 200)
                .setColorBackground(p.color(180))
                .setColorForeground(p.color(255))
                .setColorLabel(p.color(0))
                .setCaptionLabel("Main Menu")
                .setBroadcast(true)
                .getCaptionLabel()
                .setFont(font)
                .toUpperCase(false)
                .setSize(36)
                .align(ControlP5.CENTER, ControlP5.CENTER)
        ;

        //******************************************************************//
        //--------------------------Create Player---------------------------//
        //******************************************************************//
        player1 = new Player(p);
        player1.setPosition(p.width * .05f, p.height * .95f);
        player1.setMovement(10000);
        player1.setAmmo(1000);


        //******************************************************************//
        //---------------------------Create Walls---------------------------//
        //******************************************************************//

        walls.add(new Wall(p, p.width - 50, 0,50,2000,"steel"));
        walls.add(new Wall(p,0,p.height-50,p.width,50, "steel"));
        walls.add(new Wall(p,0,50,p.width,50, "steel"));
        walls.add(new Wall(p,0,50,p.width,50, "steel"));


        walls.add(new Wall(p,0,50,50,800, "steel"));
        walls.add(new Wall(p,250,500,50,200, "steel"));
        walls.add(new Wall(p,550,500,50,200, "steel"));
        walls.add(new Wall(p, 250, 700, 1000, 50, "steel"));

    }

    @Override
    /**
     * Cleans up and executes any final tasks before leaving the state.
     */
    public void OnExit()
    {
        //Set the UI elements to 'invisible'
        ui.getController("mainmenu").setVisible(false);
    }

    public void receiveControlEvents(ControlEvent event)
    {
        if (event.getController().getName().equals("mainmenu"))
        {
            GUI.stateMachine.setState("mainmenu");
        }
    }

    public void mousePressed(int mouseBtn)
    {
        switch (mouseBtn)
        {
            case PConstants.LEFT:
                player1.shoot();
                break;
        }
    }
}
