/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TetrisClash;

import java.util.Arrays;
import java.util.List;
import org.jsfml.graphics.Color;
import org.jsfml.system.Vector2i;

/**
 *
 * @author regen
 */
public class BlockStructure {
    public static List<Block> addPlayerStructure( List<Block> blocks,
                                                int blockWidth){
        Vector2i pos = new Vector2i(blockWidth/2-2, blockWidth/2-2);
               
        Color color = Color.RED;
        
        int[][] structure = new int[][]
                { {0, 0, 0, 0}, 
                  {0, 1, 1, 0}, 
                  {0, 1, 1, 0}, 
                  {0, 0, 0, 0} };
        
        for(int x = 0; x < 4; x++)
            for(int y = 0; y < 4; y++)
                if(structure[x][y] == 1)
                    blocks.add(new Block( pos.x + x, 
                                          pos.y +y, 
                                          color, 
                                          Block.Direction.Player));

        return blocks;
    }
    
    public static List<Block> addRandomStructure( List<Block> blocks,
                                            int blockWidth)
    {
        Vector2i pos = Vector2i.ZERO;
        Block.Direction dir = Block.Direction.values()[(int)(Math.random() * 3.9999)];
        switch(dir){
            case North:
                pos = new Vector2i(blockWidth/2 - 2, blockWidth);
                break;
            case East:
                pos = new Vector2i(- 4, blockWidth/2 -2);
                break;
            case South:
                pos = new Vector2i(blockWidth/2 - 2, -4);
                break;
            case West:
                pos = new Vector2i(blockWidth, blockWidth/2 -2);
                break;
            case Player:
                pos = new Vector2i(blockWidth/2, blockWidth/2);
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
                if(structure[x][y] == 1){
                    Block tmp = new Block(pos.x + x, pos.y + y, color, dir);
                    tmp.setAsPartAsStruct(nextStructId);
                    blocks.add(tmp);
                }       
        
        nextStructId++;
        return blocks;
    }
    
    public static List<Block> structBecamePlayer(List<Block> blocks, int structId){
        for(Block struct: blocks){
            if(struct.getStructId() == structId)
                struct.setAsPartOfPlayer();
        }
        
        return blocks;
    }

    public enum Type{
        O, S, Z, L, J, I;
    }
    
    private static int nextStructId = 1;

}
