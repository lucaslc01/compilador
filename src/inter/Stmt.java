/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inter;

/**
 *
 * @author pedro
 */

// Construções de comando é implementada por uma subclasse de Stmt
public class Stmt extends Node{
    int after = 0; // Guarda o rótulo after
    public Stmt(){}
    public static Stmt Null = new Stmt();
    // Chamado com rótulos begin e after
    // begin marca o início do código para esse comando
    // after marca a primeira instrução após o código para esse comando
    public void gen(int begin, int after){}
    // Enclosing é usado durante a análise sintática para acompanhar
    // a construção de loops
    public static Stmt Enclosing = Stmt.Null; // usado para comandos break
}
