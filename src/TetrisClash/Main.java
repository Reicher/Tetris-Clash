/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TetrisClash;


import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
/**
 *
 * @author regen
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args ) {
        
        //Create the window
        RenderWindow window = new RenderWindow();
        window.create(new VideoMode(800, 600), "Tetris Clash");

        //Limit the framerate
        window.setFramerateLimit(30);
        Clock gameClock = new Clock();
        float gameSpeed = 1.0f;
        float dt = 0.0f;
        
        PlayArea playArea = new PlayArea(window.getSize());
        InfoPanel infoPanel = new InfoPanel(window.getSize());

        //Main loop
        while(window.isOpen()) {
            //Handle events
            for(Event event : window.pollEvents()) {
                switch(event.type)
                {
                    case CLOSED:
                        window.close();
                        break;
                    case KEY_PRESSED:
                        if(event.asKeyEvent().key.equals(Keyboard.Key.ESCAPE))
                            window.close();
                        else 
                            playArea.keyPressed(event.asKeyEvent());
                        break;  
                }
            }
        
            
            // Update things
            dt = gameClock.restart().asSeconds() * gameSpeed;
            playArea.update(dt);
            infoPanel.update(dt);

            // Draw things
            window.clear(Color.BLACK);
            playArea.draw(window);
            infoPanel.draw(window);

            //Display what was drawn
            window.display();

        }
    }
}
