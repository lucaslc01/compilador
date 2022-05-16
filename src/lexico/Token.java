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
public class Token {
    public final Tag tag;
    public Token (Tag tag){
        this.tag = tag;
    }
    @Override
    public String toString() {
        return "Token = " + tag;
    }
}
