/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractal;
import org.lwjgl.opengl.GL11;
/**
 *
 * @author Shtutman
 */
public class PolygonFractal {
    private int num_verts;
    private float mult;
    private float ang_mult;
    private float color_basis = 0f;

    public void setMult(double mult) {
        this.mult = (float)mult;
    }

    public void setNumVerts(int num_verts) {
        this.num_verts = num_verts;
    }

    public void setColor_basis(double color_basis) {
        this.color_basis = (float)color_basis;
    }
    
    public PolygonFractal(int num_verts, double mult, double ang_mult) {
        this.num_verts = num_verts;
        this.mult = (float)mult;
        this.ang_mult = (float)ang_mult;
    }
    
    public void draw(int depth, double size, double angle) {     
        GL11.glPushMatrix();
            GL11.glScalef((float)size, (float)size, (float)size);
            next(depth, angle);
        GL11.glPopMatrix();
    }
    
    public void next(int depth, double angle) {
        GL11.glRotatef((float)angle, 0, 0, 1);
        
        float sector = 2f * (float)Math.PI / (float)num_verts;
        
        /*GL11.glColor3f((float)Math.sin(color_basis) / 2f + 0.5f, 
                       (float)Math.sin(color_basis + Math.PI / 1.5) / 2f + 0.5f,
                       (float)Math.sin(color_basis + Math.PI / 0.75) / 2f + 0.5f);*/
        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (int i = 0; i <= num_verts; i++) {
            float a = sector * i;
            GL11.glVertex2f((float)Math.cos(a), (float)Math.sin(a));
        }
        GL11.glEnd();
        
        if(depth > 0) {
            for (int i = 0; i < num_verts; i++) {
                GL11.glRotatef(sector * 180f / (float)Math.PI, 0, 0, 1);
                GL11.glPushMatrix();
                    GL11.glTranslatef(1, 0, 0);
                    GL11.glScalef(mult, mult, mult);

                    next(depth - 1, angle*ang_mult);
                GL11.glPopMatrix();                
            }
        }
    }
}
