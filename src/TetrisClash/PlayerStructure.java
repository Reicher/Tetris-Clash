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
        m_core = new ArrayList<Block>();
        for(int x = pos.x; x < pos.x + m_size; x++)
            for(int y = pos.y; y < pos.y+m_size; y++)
                m_core.add(new Block(x, y, Color.WHITE));
        this.addToStructure(m_core);
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
            Collapse();
            // "merge" douubles
            // update multiplier score
            // check again for frame, if have! multiplier+1 and redo
        }
    }
    
    private void Collapse(){
        for(Block block: m_blocks)
        {
            Vector2i pos = block.getGridPosition();
            
            // right
            if(pos.x > m_gridPos.x + m_size - 1){
                block.move(new Vector2i(-1, 0));
            }
            // Left
            else if(pos.x < m_gridPos.x){
                block.move(new Vector2i(1, 0));
            }
            // Under
            else if(pos.y > m_gridPos.y + m_size){
                block.move(new Vector2i(0, -1));
            }
            // Above
            else if(pos.y < m_gridPos.y){
                block.move(new Vector2i(0, 1));
            }
        }
    }
    
    // Returns true if there is a frame, also removes it.
    private boolean isHavingAFrame(){
        // a bit....cumbersome, like...tripple nested..RLY?
        ArrayList<Block> frame = new ArrayList<Block>();
        for(int x = m_gridPos.x - 1; x < m_gridPos.x + m_size+1 ; x++){
            for(int y = m_gridPos.y - 1; y < m_gridPos.y + m_size+1 ; y++){                
                for(Block block : m_blocks){
                    if(block.getGridPosition().x == x 
                            && block.getGridPosition().y == y){
                        frame.add(block);
                        break;
                    }
                }
            }
        }
        frame.removeAll(m_core);
        if(frame.size() < (m_size*4 + 4))
            return false;
        
        m_blocks.removeAll(frame);
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
    ArrayList<Block> m_core;
    private int m_size;
}
