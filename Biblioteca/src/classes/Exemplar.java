/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author vovostudio
 */
public class Exemplar {
    //atributos 
    //-------------------------------------
   private int idExemplar = 0 ;
 
    //-------------------------------------
    
    //-------------------------------------
    //contrutores 
    public Exemplar(){
        
    }
    
    public Exemplar (Exemplar exemplar ){
        idExemplar = exemplar.idExemplar;
    }
    
    public Exemplar (int idExemplar){
        this.idExemplar = idExemplar;
    }

    public Exemplar(int parseInt, String dado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //-------------------------------------
    //geters e seters 
    //-------------------------------------
    public int getIdExemplar() {
        return idExemplar;
    }

    public void setIdExemplar(int idExemplar) {
        this.idExemplar = idExemplar;
    }
    //-------------------------------------
    
    //-------------------------------------
    //toString 
    @Override 
    public String toString(){
        return (idExemplar + "");
    }
    

}
