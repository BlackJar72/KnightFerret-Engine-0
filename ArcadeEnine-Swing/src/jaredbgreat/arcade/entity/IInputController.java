/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.arcade.entity;

/**
 *
 * @author jared
 */
public interface IInputController extends IController {
    /**
     * Receive a set of bit-flag encoded commands aggregated from all input 
     * devices which are supported.
     * 
     * @param commands Discrete commands from input devices
     * @param fvector An n-dimension vector for non-discrete input (e.g., mouse)
     */
    public void update(int commands);
}
