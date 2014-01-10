/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TetrisClash;

import java.util.ArrayList;
import org.jsfml.graphics.Color;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.KeyEvent;

/**
 *
 * @author regen
 */
public class PlayerStructure extends Structure{
    public PlayerStructure(Vector2i middlePos){
        super(middlePos);
        
        m_playerMove = Vector2i.ZERO;
        
        int size = 3;
        Vector2i pos = Vector2i.add(middlePos, new Vector2i(-size/2, -size/2));
        for(int x = pos.x; x < pos.x + size; x++)
            for(int y = pos.y; y < pos.y+size; y++)
                this.addToStructure(new Block(x, y, Color.RED));
    }
    
    public void update(Structure walls, ArrayList<FreeStructure> enemies){
        if(m_playerMove.equals(Vector2i.ZERO))
            return;

        move(m_playerMove);
        
        // We had a collision, move back and game over
        if( isCollidingWith(walls) ){
            move(Vector2i.mul(m_playerMove, -1));
            System.out.println("GAME OVER MAN");
        }
        else{
            for(FreeStructure enemy : enemies){
                if( isCollidingWith(enemy)){   
                    move(Vector2i.mul(m_playerMove, -1));
                    m_playerMove = Vector2i.ZERO;
                    addToStructure(enemy.getBlocks());
                    enemy.setDead(); // remove the free moving block
                }
            }
        }

        
  
        

            
        m_playerMove = Vector2i.ZERO;
    }

    public void keyPressed(KeyEvent event){
        switch(event.asKeyEvent().key){
            case W:
            case UP:
                m_playerMove = new Vector2i(0, -1);
                break;
            case D:
            case RIGHT:
                m_playerMove = new Vector2i(1, 0);
                break;
                case S:
            case DOWN:
                m_playerMove = new Vector2i(0, 1);
                break;
            case A:
            case LEFT:
                m_playerMove = new Vector2i(-1, 0);
                break;
        }
    }
    
    private Vector2i m_playerMove;
}
