package org.polytechtours.performance.tp.fourmispeintre;

import java.awt.*;

public class MyColor {
    private static Color[] colors = new Color[0xFFFFFF + 1];
    public static final Color WHITE = getColor(255, 255, 255);
    public static final Color BLACK = getColor(0, 0, 0);

    public static Color getColor(int hex){
        int key = hex & 0x00FFFFFF;
        Color c = colors[key];
        if(c == null)
        {
            c = new Color(hex);
            colors[key] = c;
        }
        return c;
    }

    public static Color getColor(int R, int G, int B){
        return getColor(0xFF000000 | (R << 16) | (G << 8) | B);
    }
}
