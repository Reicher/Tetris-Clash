/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TetrisClash;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.KeyEvent;

/**
 *
 * @author regen
 */
public class PlayArea {
    public PlayArea(Vector2i screenSize){

        m_playAreaWidth = 26; // even number looks nicest.
        Block.Size = screenSize.y / m_playAreaWidth;      
        int cornerSize = m_playAreaWidth/2 - 3;
        
        m_center = new Vector2i(m_playAreaWidth / 2 - 1, m_playAreaWidth / 2 -1);
        
        m_wallBlocks = new Structure(Vector2i.ZERO);
        m_playerStruct = new PlayerStructure(m_center);
        m_enemies = new ArrayList<FreeStructure>();
        
        // Add corner blocks
        for(int x = 0; x < m_playAreaWidth; x++){
            for(int y = 0; y < m_playAreaWidth; y++){
                if(  (x < cornerSize - y )
                  || (x >= m_playAreaWidth - cornerSize + y )
                  || (x <= cornerSize - m_playAreaWidth + y )
                  || (x >= m_playAreaWidth*2 - cornerSize - y - 1 )) // why -1?  
                    m_wallBlocks.addToStructure(new Block( x, y));
            }
        }
        
        //Time
        m_timeSinceTick = 0.0f;
        m_tickFrequency = 0.75f;
        
        m_timeSinceBlock = 0.0f;
        m_blockFrequency = 4.0f;
        
        m_timeSinceMove = 0.0f;
        m_moveFrequency = 0.1f;
        
        // Add starting enemy
        m_enemies.add(new FreeStructure(m_playAreaWidth));
    }
    
    public void update(float dt){
        m_timeSinceTick += dt;
        m_timeSinceMove += dt;
        m_timeSinceBlock += dt;
        
        // make the last added enemy active and add a new one
        if(m_timeSinceBlock >= m_blockFrequency){
            if(!m_enemies.isEmpty())
                m_enemies.get(m_enemies.size() - 1).setActive();
                
            m_enemies.add(new FreeStructure(m_playAreaWidth));
            m_timeSinceBlock = 0.0f;
        }
        
         // If player have moved (and not to fast)
        if(m_timeSinceMove >= m_moveFrequency){
            m_playerStruct.update(m_wallBlocks, m_enemies);
            m_timeSinceMove = 0.0f;
        }
        
        // only update if a "tick" has passed
        else if(m_timeSinceTick >= m_tickFrequency){
            for(FreeStructure enemy : m_enemies)
                enemy.update(m_playerStruct);
                
            m_timeSinceTick = 0.0f;
        }
        
        // Remove dead
        Iterator<FreeStructure> enemyIt = m_enemies.iterator();
        while (enemyIt.hasNext()) {
            FreeStructure enemyTmp = enemyIt.next();
            if(enemyTmp.isDead())
                enemyIt.remove();
        }
    }
    
    public void keyPressed(KeyEvent event){
        m_playerStruct.keyPressed(event);
    }
    
    
    public void draw(RenderWindow window){   
        m_wallBlocks.draw(window);
        m_playerStruct.draw(window);
        
        for(FreeStructure enemy : m_enemies)
            enemy.draw(window);
    }

    private int m_playAreaWidth;
    private Vector2i m_center;
    
    // Block structures
    Structure m_wallBlocks;
    PlayerStructure m_playerStruct;
    ArrayList<FreeStructure> m_enemies; // höhö
    
    // TIME
    private float m_timeSinceTick;
    private float m_tickFrequency;
    
    private float m_timeSinceBlock;
    private float m_blockFrequency;

    private float m_timeSinceMove;
    private float m_moveFrequency;
}
