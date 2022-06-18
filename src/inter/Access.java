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
public class Access extends Op{
    public Id array;
    public Expr index;
    public Access(Id a, Expr i, Tipo p) {
        super(new Palavra("[]", Tag.INDEX), p);
        array = a; 
        index = i;
    }
    public Expr gen() {
        return new Access(array, index.reduce(), tipo);
    }
    public void jumping(int t, int f){
        emitjumps(reduce().toString(), t, f);
    }
    public String toString() {
        return array.toString()+ " [ "+ index.toString()+" ]";
    }
}
