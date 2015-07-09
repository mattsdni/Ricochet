package Main;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import processing.core.PApplet;
import processing.core.PFont;
import ch.aplu.xboxcontroller.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;


/**
 * Created by Matt on 5/25/2015.
 * This is an application template set up with a state machine using a stack.
 * 'States' are pushed onto the stack. The state on the top of the stack is what is shown to the user.
 * (See BlankState for an example)
 *
 * -Component List-
 * Processing Library
 * XBOX Controller Library
 * Framerate independent movement
 * Relative Directory File Loading ('dir' variable contains path to execution directory)
 * Auto Window Scaling
 * Default Font Loaded (arial bold)
 * State Machine Control
 * Does Not Render When Minimized
 * Windows Key Blocked
 *
 * -To Do List-
 * Full Screen Support
 * Buttons and Menus
 * Sound Library
 */
public class GUI extends PApplet
{
    public static void main(String args[])
    {
        PApplet.main(new String[]{"Main.GUI"});
    }

    float SCALE = .8f;
    float ASPECT_RATIO = 1.78f;

    PFont font;
    private XboxController xc;
    public static StateMachine stateMachine;
    ControlP5 ui;
    boolean[] downKeys = new boolean[256];

    public void init()
    {
        if (frame != null)
        {
            frame.removeNotify();// make the frame not displayable
            frame.addWindowStateListener(new WindowStateListener()
            {
                public void windowStateChanged(WindowEvent arg0)
                {
                    frame__windowStateChanged(arg0);
                }
            });
            frame.addNotify();
        }
        super.init();
    }

    public void frame__windowStateChanged(WindowEvent e)
    {
        if ((e.getNewState() & Frame.ICONIFIED) == Frame.ICONIFIED)
        {
            stateMachine.minimized = true;
            KeyHook.unblockWindowsKey();
        }
        else if ((e.getNewState() & Frame.NORMAL) == Frame.NORMAL)
        {
            stateMachine.minimized = false;
            KeyHook.blockWindowsKey();
        }
    }

    public void setup()
    {
        size((int)(displayWidth*SCALE),(int)(displayWidth*SCALE/ASPECT_RATIO), P2D);
        //size(1920,1080);
        frame.setTitle("Morken");
        KeyHook.blockWindowsKey();
        ui = new ControlP5(this);
        String dir = System.getProperty("user.dir");
        font = createFont(dir+"/src//arialbd.ttf",48);
        stateMachine = new StateMachine();
        stateMachine.Put("mainmenu", new States.MainMenu(this, ui, font, downKeys));
        xc = new XboxController(dir+"/xboxcontroller64.dll", 1, 50,50);
        //frameRate(10);
    }

    public void draw()
    {
        stateMachine.Update();
        stateMachine.Render();
    }

    public void keyPressed()
    {
        if(keyCode==ESC || key == ESC)
        {
            key = 0;
            keyCode = 0;
        }
        if (key<256)
        {
            downKeys[key] = true;
        }

    }

    public void keyReleased()
    {
        if(keyCode==ESC || key == ESC)
        {
            key = 0;
            keyCode = 0;
        }
        if (key<256)
        {
            downKeys[key] = false;
        }
    }

    public void keyTyped()
    {
        if(keyCode==ESC || key == ESC)
        {
            key = 0;
            keyCode = 0;
        }
    }

    public void mousePressed()
    {
        stateMachine.stateMap.get(stateMachine.currentState).mousePressed(mouseButton);
    }

    public void controlEvent(ControlEvent e)
    {
        stateMachine.stateMap.get(stateMachine.currentState).receiveControlEvents(e);
    }
}
