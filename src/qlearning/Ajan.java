/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlearning;

/**
 *
 * @author Bayram
 */
public class Ajan {
    int konumx;
    int konumy;
    
    
    public Ajan(int x,int y){
        konumx=x;
        konumy=y;
    }
    public Ajan(){
        
    }

    public int getKonumx() {
        return konumx;
    }

    public void setKonumx(int konumx) {
        this.konumx = konumx;
    }

    public int getKonumy() {
        return konumy;
    }

    public void setKonumy(int konumy) {
        this.konumy = konumy;
    }
}
