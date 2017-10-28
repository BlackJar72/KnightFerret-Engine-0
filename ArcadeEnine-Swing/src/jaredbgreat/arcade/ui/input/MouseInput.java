package jaredbgreat.arcade.ui.input;

import jaredbgreat.arcade.game.BaseGame;
import jaredbgreat.arcade.ui.MainWindow;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Jared Blackburn
 */
public class MouseInput implements MouseListener {
    private int commands;
    private IMouseTranslator clicked;
    private IMouseTranslator pressed;
    private IMouseTranslator released;
    
    
    public int getCommands() {
        return commands;
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
        MainWindow.getMainWindow().clickMouse(e);
        if(BaseGame.game.blockInput() || (clicked == null)) {
         return;
        }
        commands = clicked.getCommands(e);
    }
    

    @Override
    public void mousePressed(MouseEvent e) {
        if(BaseGame.game.blockInput() || (pressed == null)) {
         return;
        }
        commands |= pressed.getCommands(e);
    }
    

    @Override
    public void mouseReleased(MouseEvent e) {
        if(BaseGame.game.blockInput() || (released == null)) {
         return;
        }
        commands &= ~released.getCommands(e);
    }
    
    
    public void setTranslators(IMouseTranslator press, 
            IMouseTranslator release, IMouseTranslator click) {
        pressed = press;
        released = release;
        clicked = click;
    }
    

    /*------------------------------------------------------------------------//
    //                           UNUSED METHODS                               //
    //------------------------------------------------------------------------*/
        
    @Override
    public void mouseEntered(MouseEvent e) {/*do nothing*/}
    @Override
    public void mouseExited(MouseEvent e) {/*do nothing*/}
    
}
