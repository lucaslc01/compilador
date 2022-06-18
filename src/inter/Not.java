/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inter;
import lexico.*;
import tabelaDeSimbolos.*;
/**
 *
 * @author pedro
 */
public class Not extends Logical{
    public Not (Token token, Expr x2){
        super (token, x2, x2);
    }

    public void jumping(int t, int f) {
        expr2.jumping(f, t);
    }
    
    public String toString() {
        return op.toString()+" "+expr2.toString();
    }
    
}
