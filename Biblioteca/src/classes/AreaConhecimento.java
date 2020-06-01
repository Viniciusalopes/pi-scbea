/*
AreaConhecimento
- idAreaConhecimento : int
- cdd : int
- descricaoAreaConhecimento : String
-STERS--GETRS-
+ get() : void
+ set() : void
-TO-STRING-
+ toString() : String
 */
package classes;

public class AreaConhecimento {

    //atributos 
    private int idAreaConhecimento;

    private int cdd;

    private String descricaoAreaConhecimento;

    public AreaConhecimento() {
   //construtor padrao 
    }

    public AreaConhecimento(AreaConhecimento areaComhecimento) {
        idAreaConhecimento = areaComhecimento.idAreaConhecimento;
        cdd = areaComhecimento.cdd;
        descricaoAreaConhecimento = areaComhecimento.descricaoAreaConhecimento;

    }

    public AreaConhecimento(int idAreaConhecimento, int cdd, String descricaoAreaConhecimento) {
        this.idAreaConhecimento = idAreaConhecimento;
        this.cdd = cdd;
        this.descricaoAreaConhecimento = descricaoAreaConhecimento;
    }

    //STERS & GETRS 
    public int getIdAreaConhecimento() {
        return idAreaConhecimento;
    }

    public void setIdAreaConhecimento(int idAreaConhecimento) {
        this.idAreaConhecimento = idAreaConhecimento;
    }

    public int getCdd() {
        return cdd;
    }

    public void setCdd(int cdd) {
        this.cdd = cdd;
    }

    public String getDescricaoAreaConhecimento() {
        return descricaoAreaConhecimento;
    }

    public void setDescricaoAreaConhecimento(String descricaoAreaConhecimento) {
        this.descricaoAreaConhecimento = descricaoAreaConhecimento;
    }
    //TO STRING  
    @Override
    public String toString(){
        return (idAreaConhecimento + ";" + cdd + ";" +  descricaoAreaConhecimento );
    }

}
