//Copyright (c) Jared Blackburn 2017
package jaredbgreat.arcade.ui.input;


import jaredbgreat.arcade.entity.IInputController;
import jaredbgreat.arcade.game.BaseGame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Jared Blackburn
 */
public class KeyInput implements KeyListener { 
   private IKeyTranslator pressed, released;
   private IInputController inputController;
   private int commands;
   
   
   public KeyInput(IKeyTranslator press, IKeyTranslator release) {       
        pressed = press;
        released = release;        
   }
   

   @Override
   public void keyPressed(KeyEvent e) {
       if(BaseGame.game.blockInput() || (pressed == null)) {
           return;
       }
       commands = commands | pressed.translate(e);
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
       if(BaseGame.game.blockInput() || (released == null)) {
           return;
       }
       commands &= ~released.translate(e);
    }
    
    
    public void clear() {
        commands = 0;
    }
    
    
    public void update() {
        inputController.update(commands, null);
    }
    
    
    public int getCommands() {
        return commands;
    }
    
    
    public void setTranslators(IKeyTranslator press, 
            IKeyTranslator release) {
        pressed = press;
        released = release;        
    }
    
    
    /*-************************************-*/
    /*          UNUSED METHODS              */
    /*-************************************-*/
    
    
    @Override
    public void keyTyped(KeyEvent e)  {/*Do nothing*/}
    
}
