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
    public PlayerStructure(Vector2i pos){
        super(pos);
        
        m_playerMove = Vector2i.ZERO;
        
        m_size = 3;
        for(int x = pos.x; x < pos.x + m_size; x++)
            for(int y = pos.y; y < pos.y+m_size; y++)
                this.addToStructure(new Block(x, y, Color.WHITE));
    }
    
    public void update(Structure walls, ArrayList<FreeStructure> enemies){
        if(m_playerMove.equals(Vector2i.ZERO))
            return;

        move(m_playerMove);
        
        // We had a collision with a wall, game over
        if( isCollidingWith(walls) ){
            move(Vector2i.mul(m_playerMove, -1));
            System.out.println("GAME OVER MAN");
        }
        else{
            // Have we collided with anything else?
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
    
    @Override
    public void addToStructure(ArrayList<Block> blocks){
        super.addToStructure(blocks);
        
        // Check if any block is outside
        // ToDo!!
        
        // check if we have a frame around our starting block
        if(isHavingAFrame()){
            // Remove blocks in frame
            // move all in
            // "merge" douubles
            // update multiplier score
            // check again for frame, if have! multiplier+1 and redo
        }
    }
    
    private boolean isHavingAFrame(){
        // a bit....cumbersome, like...tripple nested..RLY?
        for(int x = m_gridPos.x - 1; x < m_gridPos.x + m_size+1 ; x++){
            for(int y = m_gridPos.y - 1; y < m_gridPos.y + m_size+1 ; y++){
                boolean haveBlock = false;
                for(Block block : m_blocks){
                    if(block.getGridPosition().x == x 
                            && block.getGridPosition().y == y){
                        haveBlock = true;
                    }
                }
                if(!haveBlock)
                    return false;
            }
        }
        
        return true;
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
    private int m_size;
}
