/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;
import modelos.classes.Editora;
import modelos.interfaces.ICRUDEditora;
import persistencia.EditoraPersistencia;
/**
 *
 * @author eugeniojulio
 */
public class EditoraControle implements ICRUDEditora{
    private ICRUDEditora objeto = null;
    
    public EditoraControle(){
        objeto = new EditoraPersistencia("Editoras.txt");
    }
    
    @Override
    public void incluir(Editora objeto) {
        System.out.println("Estou na controle verificando e "
                + "mandando incluir os dados");
        //verificar se aditora ja existe;
        this.objeto.incluir(objeto);
    }
    
    
    
}
