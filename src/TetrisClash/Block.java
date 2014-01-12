package TetrisClash;

import java.util.List;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author regen
 */
public class Block {
    public Block( int x, int y ){
        m_gridPos = new Vector2i(x, y);
     
        m_color = new Color( (int)(Math.random() * 155.0f + 100), 
                             (int)(Math.random() * 155.0f + 100), 
                             (int)(Math.random() * 155.0f + 100), 
                                200);
        init();
    }
    
    public Block( int x, int y, Color color ){
        m_gridPos = new Vector2i(x, y);
        m_color = color;
        
        init();
    }
    
    public void init(){
        m_shape = new RectangleShape(new Vector2f(Size, Size));
        m_shape.setPosition(Vector2f.mul(new Vector2f(m_gridPos.x, m_gridPos.y), Size));
        m_shape.setOutlineThickness(-Size/10.0f);
        m_shape.setFillColor(m_color);
        m_shape.setOutlineColor(Color.BLACK);
    }
    
    public void move(Vector2i v){
        m_gridPos = Vector2i.add(m_gridPos, v);
        m_shape.setPosition(Vector2f.mul(new Vector2f(m_gridPos.x, m_gridPos.y), Size));
    }
    
    public void draw(RenderWindow window){
        window.draw(m_shape);
    }

    public Vector2i getPosition(){
        return m_gridPos;
    }
    
    public Color getColor(){
        return m_color;
    }
    
    public void mergeColor(Color color){
        m_color = new Color((m_color.r + color.r) / 2
                , (m_color.g + color.g) / 2
                , (m_color.b + color.b) / 2
                , (m_color.a + color.a) / 2);
    }
    
    public void animate(float dt){

    }
    
    private Vector2i m_gridPos;
    private Vector2f m_v;
    private float m_totalStepTime;
    private float m_stepMoved;
    
    private RectangleShape m_shape;
    private Color m_color;
    
    public static int Size;
    
    
}
