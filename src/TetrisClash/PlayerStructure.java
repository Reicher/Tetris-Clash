/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TetrisClash;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import org.jsfml.graphics.Color;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.KeyEvent;

/**
 *
 * @author regen
 */
public class PlayerStructure extends Structure{
    public PlayerStructure(Vector2i pos){
        super(Vector2i.sub(pos, new Vector2i(2, 2)));
        
        m_playerMove = Vector2i.ZERO;
        m_size = 4; // ALWAYS
        m_core = new ArrayList<Block>();
        for(int x = m_gridPos.x; x < m_gridPos.x + m_size; x++)
            for(int y = m_gridPos.y; y < m_gridPos.y+m_size; y++)
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
            //Collapse();
            // "merge" douubles
            // update multiplier score
            // check again for frame, if have! multiplier+1 and redo
        }
    }
    
    public Vector2i getClosestCorePos(Vector2i pos){
        float bestDist = Float.MAX_VALUE;
        Block best =  m_core.get(m_core.size() - 1);
        for(Block core : m_core){
            Vector2i moveVec = Vector2i.sub(core.getPosition(), pos);
            float dist = (float)Math.sqrt(Math.pow(moveVec.x, 2.) 
                                        + Math.pow(moveVec.y, 2));
            if(dist < bestDist){
                bestDist = dist;
                best = core;
            }
        }
        return best.getPosition();
    }
    
    private void collapse(ArrayList<Block> blocks){
        for(Block block: blocks)
        {
           Vector2i target = getClosestCorePos(block.getPosition());
           Vector2i moveVec = Vector2i.sub(target, block.getPosition());
           Vector2i move = Vector2i.ZERO;
           
           int x = moveVec.x;
           int y = moveVec.y;
           
           if(moveVec.x != 0)
               x /= Math.abs(moveVec.x);
          if(moveVec.y != 0)
               y /= Math.abs(moveVec.y);
           
           if(Math.abs(moveVec.x) > Math.abs(moveVec.y))
               move = new Vector2i(x, 0);
           else
               move = new Vector2i(0, y); 
           
            block.move(move);
        }
    }
    

   
    // Returns true if there is a frame, also removes it.
    private boolean isHavingAFrame(){
        // a bit....cumbersome, like...tripple nested..RLY?
        
        ArrayList<Block> frame = new ArrayList<Block>();
        ArrayList<Block> insideFrame =  new ArrayList<Block>();
        insideFrame.addAll(m_core);
        int level = 0;
        
        boolean frameFound = false;
        
        do{
            level++;    
            frame.clear();
            // Add all blocks in and inside the fram.
            for(int x = m_gridPos.x-level; x < m_gridPos.x + m_size +level; x++)
                for(int y = m_gridPos.y-level; y < m_gridPos.y + m_size +level; y++)
                    for(Block block : m_blocks)
                        if(block.getPosition().x == x && block.getPosition().y == y)
                            frame.add(block);
            
            // remove all blocks inside the frame
            frame.removeAll(insideFrame);
            
            // Check if frame is "full"
            int FrameCount = m_size *(4 + (int)Math.pow(level, 2));
            if(frame.size() == FrameCount){
                frameFound = true;
                // Remove the frame
                m_blocks.removeAll(frame);
                // ADD POINTS AND MULTIPLIER
                
                // collapse all outside
                ArrayList<Block> tmp =  new ArrayList<Block>();
                tmp.addAll(m_blocks);
                tmp.removeAll(insideFrame);
                collapse(tmp);
                
                // Remove duplicates
                RemoveDuplicates();
                level = 0;
            }
            else // there COULD be a frame outside this unfinished one
                insideFrame.addAll(frame);
            
        }while(!frame.isEmpty()); // The edge of the player structure is reached
        
        return frameFound;
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
    private final int m_size;
}
