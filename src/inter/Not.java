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
    // Por ser operador unário, 
    // se passa as X2 como as duas expressões de Logical
    public Not (Token token, Expr x2){
        super (token, x2, x2);
    }
    // Chama a segunda Expressão de Logical 
    // com as saídas verdadeiras e falsas invertidas
    // B := not B_1
    public void jumping(int t, int f) {
        expr2.jumping(f, t);
    }
    
    public String toString() {
        return op.toString()+" "+expr2.toString();
    }
    
}
