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
    public Block( int x, int y, Direction direction ){
        m_direction = direction;
        m_gridPos = new Vector2i(x, y);
     
        m_color = new Color( (int)(Math.random() * 255.0f), 
                                (int)(Math.random() * 255.0f), 
                                (int)(Math.random() * 255.0f), 
                                200);
        init();
    }
    
    public Block( int x, int y, Color color, Direction direction ){
        
        m_direction = direction;
        m_gridPos = new Vector2i(x, y);
        m_color = color;
        
        init();
        

    }
    
    public void init(){
        m_shape = new RectangleShape(new Vector2f(Size, Size));
        m_shape.setPosition(Vector2f.mul(new Vector2f(m_gridPos.x, m_gridPos.y), Size));
        m_shape.setOutlineThickness(-Size/5.0f);

        Color OutLine = new Color( m_color.r, m_color.g, m_color.b, 255);
        
        m_shape.setFillColor(m_color);
        m_shape.setOutlineColor(OutLine);
        
        m_structId = 0;
    }
    
    public void update(List<Block> blocks){
        switch(m_direction){
            case North:
                move(new Vector2i(0, - 1 ), blocks);
                break;
            case East:
                move(new Vector2i( 1, 0), blocks);
                break;
            case South:
                move(new Vector2i(0, 1), blocks);
                break;
            case West:
                move(new Vector2i(-1, 0), blocks);
                break;
            case Still:
            case Player:
                break;
            default:
                System.out.println("Block update, unknown enum");
        }
    }
    
    public void move(Vector2i v, List<Block> blocks){
        Vector2i maybeMove = Vector2i.add(m_gridPos, v);
        
        for(Block block: blocks){
            // We have a colision of some sort!
            if( block.getGridPosition().equals(maybeMove) )
            {
                // Player moved into something
                if(isPlayerBlock()){
                    
                   // Another player, should not happen
                   if (block.isPlayerBlock()) {        
                       // Move as usual, then done
                       m_gridPos = maybeMove;
                       m_shape.setPosition(Vector2f.mul(new Vector2f(m_gridPos.x, m_gridPos.y), Size));
                       return; 
                   }
                   // Into wall
                   else if(block.isWallBlock()){
                       System.out.println("Game Over man!");
                       return;
                   }
                   // Player into Moving structure
                   else if(isPlayerBlock()){
                       BlockStructure.structBecamePlayer(blocks, block.getStructId());
                       return;
                   }
                }
                // if a moving object
                else{
                   // player
                   if (block.isPlayerBlock()) {        
                       // Move as usual, then done
                        BlockStructure.structBecamePlayer(blocks, block.getStructId());
                       return;
                   }
                }
                
            }
        }
        m_gridPos = maybeMove;
        m_shape.setPosition(Vector2f.mul(new Vector2f(m_gridPos.x, m_gridPos.y), Size));
    }
    
    public void draw(RenderWindow window){
        window.draw(m_shape);
    }
    
    public void setPosition(Vector2f pos){
        m_shape.setPosition(pos);
    }
    
    public Direction getDirection(){
        return m_direction;
    }
    
    public Vector2i getGridPosition(){
        return m_gridPos;
    }
    
    public void setAsPartAsStruct(int id){
        m_structId = id;
    }
    
    public void setAsPartOfPlayer(){
        m_direction = Direction.Player;
    }
    
    public int getStructId(){
        return m_structId;
    }
    
    public boolean isPlayerBlock(){
        return m_direction == Direction.Player;
    }
    
    public boolean isWallBlock(){
        return m_direction == Direction.Still;
    }

    private Vector2i m_gridPos;
    private RectangleShape m_shape;
    private Color m_color;
    
    public static int Size;
    
    private Direction m_direction;
    
    private int m_structId;
    
    public enum Direction{
        North, East, South, West, Still, Player;
    }
}
