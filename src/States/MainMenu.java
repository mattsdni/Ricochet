package States;

import Main.GUI;
import controlP5.ControlEvent;
import controlP5.ControlFont;
import controlP5.ControlP5;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;

/**
 * Created by Matt on 7/7/2015.
 */
public class MainMenu extends PApplet implements IState
{
    PApplet p;
    ControlP5 ui;
    PFont pfont;
    ControlFont font;
    boolean[] downKeys;
    PImage logo;

    /**
     * Constructs the state
     * @param _p a referece to the parent PApplet.
     */
    public MainMenu(PApplet _p, ControlP5 _ui, PFont _pfont, boolean[] _downKeys)
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
        //update the x location of the square
        for (int i = 0; i < downKeys.length; i++)
        {
            if (downKeys[i])
            {
                if((char)i == 'w')
                {

                }

            }
        }
    }

    @Override
    /**
     * Displays everything to the screen.
     */
    public void Render()
    {
        //draw the text and square to the screen
        p.background(35, 49, 63);
        p.textSize(96);
        p.textAlign(PConstants.CENTER);
        p.image(logo, p.width/2-logo.width/2, p.height*.08f);
    }

    @Override
    /**
     * Sets up any variables needed for the duration of the state.
     */
    public void OnEnter()
    {
        font = new ControlFont(pfont,48);
        // create a new button with name 'button1'
        ui.addButton("newgame")
                .setBroadcast(false)
                .setValue(0)
                .setPosition(p.width*.5f-175,p.height*.6f)
                .setSize(250, 100)
                .setColorBackground(p.color(71,119,198))
                .setColorForeground(p.color(109,159,220))
                .setColorLabel(p.color(0))
                .setCaptionLabel("New Game")
                .setBroadcast(true)
                .getCaptionLabel()
                .setFont(font)
                .toUpperCase(false)
                .setSize(36)
                .align(ControlP5.CENTER, ControlP5.CENTER)
        ;

        ui.addButton("loadgame")
                .setBroadcast(false)
                .setValue(0)
                .setPosition(p.width * .5f - 175, p.height * .7f)
                .setSize(250, 100)
                .setColorBackground(p.color(71,119,198))
                .setColorForeground(p.color(109,159,220))
                .setColorLabel(p.color(0))
                .setCaptionLabel("Load Game")
                .setBroadcast(true)
                .getCaptionLabel()
                .setFont(font)
                .toUpperCase(false)
                .setSize(36)
                .align(ControlP5.CENTER, ControlP5.CENTER)
        ;

        ui.addButton("exit")
                .setBroadcast(false)
                .setValue(0)
                .setPosition(p.width * .5f - 175, p.height * .8f)
                .setSize(250, 100)
                .setColorBackground(p.color(71,119,198))
                .setColorForeground(p.color(109,159,220))
                .setColorLabel(p.color(0))
                .setCaptionLabel("Exit")
                .setBroadcast(true)
                .getCaptionLabel()
                .setFont(font)
                .toUpperCase(false)
                .setSize(36)
                .align(ControlP5.CENTER, ControlP5.CENTER)
        ;
        logo = loadImage(System.getProperty("user.dir")+"/resources/img/logo.png");
    }

    @Override
    /**
     * Cleans up and executes any final tasks before leaving the state.
     */
    public void OnExit()
    {
        ui.getController("newgame").setVisible(false);
        ui.getController("loadgame").setVisible(false);
        ui.getController("exit").setVisible(false);
    }

    public void receiveControlEvents(ControlEvent event)
    {
        if (event.getController().getName().equals("newgame"))
        {
            Main.GUI.stateMachine.Put("level1", new States.Level_1(p, ui, pfont, downKeys));
        }
        if (event.getController().getName().equals("exit"))
        {
            GUI.stateMachine.stateMap.clear();
        }
    }

    public void mousePressed(int mouseBtn)
    {

    }
}
