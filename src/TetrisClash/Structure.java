/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TetrisClash;

import java.util.ArrayList;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2i;

/**
 *
 * @author regen
 */
public class Structure {
    public Structure(Vector2i pos){
        m_gridPos = pos;
        m_blocks = new ArrayList<Block>();
    }
    
    public void addToStructure(Block block){
        m_blocks.add(block);
    }
    
    public void addToStructure(ArrayList<Block> blocks){
        m_blocks.addAll(blocks);
    }
    
    public void draw(RenderWindow window){
        for(Block block : m_blocks)
            block.draw(window);
    }
    
    protected void move(Vector2i v){
        m_gridPos = Vector2i.add(m_gridPos, v);
        for(Block block : m_blocks)
            block.move(v);
    }
    
    protected boolean isCollidingWith(Structure structure){
        return isCollidingWith(structure.getBlocks());
    }
    
    protected boolean isCollidingWith(ArrayList<Block> structure){
        for(Block block : m_blocks){
            for(Block other : structure){
                if(block.getPosition().equals(other.getPosition()))
                    return true;
            }
        }
        return false;
    }
    
    public ArrayList<Block> getBlocks(){
        return m_blocks;
    }
    
    protected void RemoveDuplicates(){
        ArrayList<Block> duplicateFree = new ArrayList<Block>();
        for(Block block : m_blocks){
            boolean Special = true;
            for(Block unique : duplicateFree){
                if(block.getPosition().equals(unique.getPosition()))
                    Special = false;
            }
            if(Special)
                duplicateFree.add(block);
        }
        m_blocks.clear();
        m_blocks.addAll(duplicateFree);
    }
    
    protected ArrayList<Block> m_blocks;
    protected Vector2i m_gridPos; // upper left corner of struct
}
