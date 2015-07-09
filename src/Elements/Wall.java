package Elements;

import processing.core.PApplet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Matt on 7/1/2015.
 */
public class Wall
{
    public int w, h, x, y; //TODO: add getters and setters for these variables
    int color;
    int health;
    String material;
    int bounciness;
    boolean heal;
    PApplet p;

    public Wall(PApplet _p, int _x, int _y, int _w, int _h, String _material)
    {
        p = _p;
        w = _w;
        h = _h;
        x = _x;
        y = _y;
        material = _material;
        readProperties();
    }

    public void update()
    {

    }

    public void display()
    {
        p.fill(color);
        p.noStroke();
        p.rect(x,y,w,h);
    }

    private void readProperties()
    {
        HashMap<String, HashMap<String, Integer>> mcMap = new HashMap<String, HashMap<String, Integer>>();
        File file = new File(System.getProperty("user.dir")+"/resources/materials/vanilla.mtrl");
        Scanner inputFile = null;
        try
        {
            inputFile = new Scanner(file);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        while(inputFile.hasNext())
        {
            String temp = inputFile.nextLine();
            String[] first = temp.split(":");
            String firstKey = first[0];
            String[] second = first[1].split(" ");
            HashMap<String, Integer> tmpMap = new HashMap<String, Integer>();

            //fill second hashmap with properties
            for (int i = 0; i < second.length; i++)
            {
                String[] propTemp = second[i].split("=");
                //convert int to color if necessary
                if (propTemp[0].compareTo("color") == 0)
                {
                    String[] colorArrTmp = propTemp[1].split(",");
                    int colorTemp1 = Integer.parseInt(colorArrTmp[0]);
                    int colorTemp2 = Integer.parseInt(colorArrTmp[1]);
                    int colorTemp3 = Integer.parseInt(colorArrTmp[2]);

                    int valTemp = p.color(colorTemp1,colorTemp2,colorTemp3);
                    tmpMap.put(propTemp[0], valTemp);
                }
                else
                {
                    try
                    {
                        int valTemp = Integer.parseInt(propTemp[1]);
                        tmpMap.put(propTemp[0], valTemp);

                    }catch (NumberFormatException e)
                    {
                        int valTemp;
                        if (propTemp[1].equals("true"))
                            valTemp = 1;
                        else
                            valTemp = 0;

                        tmpMap.put(propTemp[0], valTemp);
                    }
                }
            }
            //add second hashmap to main hashmap
            mcMap.put(firstKey, tmpMap);
        }
        HashMap tmpMap = mcMap.get(material);
        Iterator it = tmpMap.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            if (pair.getKey().equals("color"))
                color = (Integer)(pair.getValue());
            if (pair.getKey().equals("health"))
                health = (Integer)(pair.getValue());
            if (pair.getKey().equals("bounce"))
                bounciness = (Integer)(pair.getValue());
            if (pair.getKey().equals("heal"))
                heal = (Integer)pair.getValue()==1;
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

}
