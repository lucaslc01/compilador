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
public class Temp extends Expr {
    static int count = 0;
    int number = 0;

    public Temp(Tipo tipo) {
        super(Palavra.TEMP, tipo);
        number = ++count;
    }
    public String toString(){
        return "t" + number;
    }
    
}
