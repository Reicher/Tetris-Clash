/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TetrisClash;

import java.util.ArrayList;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 *
 * @author regen
 */

public class FreeStructure extends Structure {
    public FreeStructure( int playAreaWidth){
        super(Vector2i.ZERO);
    
        m_active = false;
        m_dead = false;
        
        m_move = Vector2i.ZERO;
        Vector2i pos = Vector2i.ZERO;
        m_direction = Direction.values()[(int)(Math.random() * 3.9999)];
        switch(m_direction){
            case North:
                pos = new Vector2i(playAreaWidth/2 - 2, playAreaWidth);
                m_move = new Vector2i(0, - 1 );
                break;
            case East:
                pos = new Vector2i(- 4, playAreaWidth/2 -2);
                m_move = new Vector2i(1, 0 );
                break;
            case South:
                pos = new Vector2i(playAreaWidth/2 - 2, -4);
                m_move = new Vector2i(0, 1 );
                break;
            case West:
                pos = new Vector2i(playAreaWidth, playAreaWidth/2 -2);
                m_move = new Vector2i(-1, 0 );                
                break;
        }
        
        // should moved down to be special for each kind of peice
        Color color = new Color( (int)(Math.random() * 255.0f), 
                             (int)(Math.random() * 255.0f), 
                             (int)(Math.random() * 255.0f), 
                             200);
        
        // They will all be transposed, all my work!=/
        int[][] structure = new int[4][4];
        Type type = Type.values()[(int)(Math.random() * 5.999)];
        switch(type){
            case  O:
                structure = new int[][]
                { {0, 0, 0, 0}, 
                  {0, 1, 1, 0}, 
                  {0, 1, 1, 0}, 
                  {0, 0, 0, 0} };
                break;
            case S: 
                structure = new int[][]
                { {0, 0, 0, 0}, 
                  {0, 0, 1, 0}, 
                  {0, 1, 1, 0}, 
                  {0, 1, 0, 0} };
                break;
            case Z:
                structure = new int[][]
                { {0, 0, 0, 0}, 
                  {0, 1, 0, 0}, 
                  {0, 1, 1, 0}, 
                  {0, 0, 1, 0} };
                break;
            case L:
                structure = new int[][]
                { {0, 0, 0, 0}, 
                  {0, 0, 1, 0}, 
                  {1, 1, 1, 0}, 
                  {0, 0, 0, 0} };
                break;
            case J:
                structure = new int[][]
                { {0, 0, 0, 0}, 
                  {1, 1, 1, 0}, 
                  {0, 0, 1, 0}, 
                  {0, 0, 0, 0} };                
                break;
            case I:
                structure = new int[][]
                { {0, 0, 0, 0}, 
                  {1, 1, 1, 1}, 
                  {0, 0, 0, 0}, 
                  {0, 0, 0, 0} };                        
                break;
        }
        
        for(int x = 0; x < 4; x++)
            for(int y = 0; y < 4; y++)
                if(structure[x][y] == 1)
                    addToStructure(new Block(pos.x + x, pos.y + y, color));   
    }
    
    public void setActive(){
        m_active = true;
    }
    
    public void setDead(){
        m_dead = true;
    }
    
    public boolean isDead(){
        return m_dead;
    }
      
    
    void update(PlayerStructure player){
        move(m_move);
        
        if( isCollidingWith(player)){   
            move(Vector2i.mul(m_move, -1));
            player.addToStructure(m_blocks);
            m_dead = true; // remove the free moving block
        }
    }
    
    @Override
    public void draw(RenderWindow window){
        if(m_active)
            super.draw(window);
    }

    private enum Type{
        O, S, Z, L, J, I;
    }
    
    private enum Direction{
        North, East, South, West
    }
    
    private boolean m_active;
    private boolean m_dead;
    private Direction m_direction;
    private Vector2i m_move;
}
