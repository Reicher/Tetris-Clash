/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TetrisClash;

import java.util.ArrayList;
import java.util.List;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.KeyEvent;

/**
 *
 * @author regen
 */
public class PlayArea {
    public PlayArea(Vector2i screenSize){

        m_center = new Vector2f(screenSize.y / 2.0f, screenSize.y / 2.0f);

        m_blockWidth = 26; // even number looks nicest.
        Block.Size = screenSize.y / m_blockWidth;        
        m_blocks = new ArrayList<Block>();
        int cornerSize = m_blockWidth/2 - 3;
        
        // Add corner blocks
        for(int x = 0; x < m_blockWidth; x++){
            for(int y = 0; y < m_blockWidth; y++){
                if(  (x < cornerSize - y )
                  || (x >= m_blockWidth - cornerSize + y )
                  || (x <= cornerSize - m_blockWidth + y )
                  || (x >= m_blockWidth*2 - cornerSize - y - 1 )) // why -1?  
                    m_blocks.add(new Block( x, y , Block.Direction.Still));
            }
        }
        //Time
        m_timeSinceTick = 0.0f;
        m_tickFrequency = 0.75f;
        
        m_timeSinceBlock = 0.0f;
        m_blockFrequency = 4.0f;
        
        m_timeSinceMove = 0.0f;
        m_moveFrequency = 0.1f;
        
        m_playerMove = Vector2i.ZERO;
        
        // Initial Player structure
        BlockStructure.addPlayerStructure(m_blocks, m_blockWidth);
    }
    
    public void update(float dt){
        m_timeSinceTick += dt;
        m_timeSinceMove += dt;
        m_timeSinceBlock += dt;
        
        if(m_timeSinceBlock >= m_blockFrequency){
            BlockStructure.addRandomStructure(m_blocks, m_blockWidth);
            m_timeSinceBlock = 0.0f;
        }
            
        // If player have moved (and not to fast)
        if(m_playerMove != Vector2i.ZERO && m_timeSinceMove >= m_moveFrequency){
            for(Block block : m_blocks){
                if(block.getDirection() == Block.Direction.Player)
                    block.move(m_playerMove);
            }
            m_playerMove = Vector2i.ZERO;
            m_timeSinceMove = 0.0f;
        }
        
        // only update if a "tick" has passed
        if(m_timeSinceTick >= m_tickFrequency){
            for(Block block : m_blocks)
                block.update();
            
            m_timeSinceTick = 0.0f;
        }
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
    
    public void draw(RenderWindow window){   
        for(Block block : m_blocks){
            block.draw(window);
        }
    }
    
    private Vector2i m_playerMove;
    // private float m_playerRotate;
    
    private List<Block> m_blocks;
    private int m_blockWidth;
    private Vector2f m_center;
    
    // TIME
    private float m_timeSinceTick;
    private float m_tickFrequency;
    
    private float m_timeSinceBlock;
    private float m_blockFrequency;

    private float m_timeSinceMove;
    private float m_moveFrequency;
}
