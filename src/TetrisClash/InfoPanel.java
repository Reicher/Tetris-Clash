/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TetrisClash;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 *
 * @author regen
 */
public class InfoPanel {
    public InfoPanel(Vector2i screenSize){
        Vector2f size = new Vector2f(screenSize.x - screenSize.y, screenSize.y);        
        m_borders = new RectangleShape(size);
        m_borders.setPosition(size.y, 0);
        
        Color color = new Color(150, 150, 220, 200);
        m_borders.setFillColor(color);
        m_borders.setOutlineColor(new Color(color.r, color.g, color.b, 255));
        m_borders.setOutlineThickness(-10);
    }
    
    public void update(float dt){
        
    }
    
    public void draw(RenderWindow window){
        window.draw(m_borders);
    }
    
    private RectangleShape m_borders;
}
