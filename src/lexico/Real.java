/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexico;

/**
 *
 * @author pedro
 */
public class Real extends Token{
    public final float value;
    public Real(float value) {
        super(Tag.FLOAT_CONST);
        this.value = value;
    }
    
    @Override
    public String toString() {
        return super.toString() + " Valor = " + value;
    }
}
