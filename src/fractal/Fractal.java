package fractal;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Fractal {    
    PolygonFractal frac;
    float angle = 0;
    
    public void start() {
        try {
            Display.setDisplayMode(new DisplayMode(800, 800));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        // init OpenGL here
        initParams();

        long last_millis = System.currentTimeMillis();
        while (!Display.isCloseRequested()) {
            long current_millis = System.currentTimeMillis();
            
            render((int)(current_millis - last_millis));
            
            last_millis = current_millis;
            Display.update();
        }

        Display.destroy();
    }

    void initParams() {
        frac = new PolygonFractal(5, 0.435, -1.7);
        
        GL11.glClearColor(0.1f, 0.1f, 0.1f, 1f);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glOrtho(-1, 1, -1, 1, -1, 1);
    }
    void render(int dmillis) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glLineWidth(1);
        
        int ms_per_rotate = 3000;
        int ms_per_puls = 500;
        double puls_center = 0.425;
        double puls = 0.018 * 5;
        int ms_per_verts = 1500;
        
        int verts = (int)((System.currentTimeMillis() / ms_per_verts) % 3) + 3;
        frac.setNumVerts(verts);
        frac.setMult(Math.sin((double)(System.currentTimeMillis() % ms_per_puls) / (double) ms_per_puls * 2.0 * Math.PI) * 
                puls / verts + puls_center);
        angle += (double)dmillis / (double)ms_per_rotate * 360.0;
        frac.draw(4, 0.55, angle);
    }

    public static void main(String[] argv) {
        Fractal displayExample = new Fractal();
        displayExample.start();
    }
}