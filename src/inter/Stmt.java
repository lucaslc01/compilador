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
public class Stmt extends Node{
    int after = 0;
    public Stmt(){}
    public static Stmt Null = new Stmt();
    public void gen(int begin, int after){}
    public static Stmt Enclosing = Stmt.Null;
}
