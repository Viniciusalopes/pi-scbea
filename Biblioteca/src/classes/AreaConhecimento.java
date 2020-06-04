package classes;

public class AreaConhecimento {

//Atributos
    private int idAreaConhecimento;
    private int cdd;
    private String descricaoAreaConhecimento;

//Construtor Padrão
    public AreaConhecimento() {
        idAreaConhecimento = 0;
        cdd = 0;
        descricaoAreaConhecimento = "";
    }

//Construtor passando objeto
    public AreaConhecimento(AreaConhecimento areaComhecimento) {
        idAreaConhecimento = areaComhecimento.idAreaConhecimento;
        cdd = areaComhecimento.cdd;
        descricaoAreaConhecimento = areaComhecimento.descricaoAreaConhecimento;
    }

//Construtor passando parâmetros
    public AreaConhecimento(int idAreaConhecimento, int cdd, String descricaoAreaConhecimento) {
        this.idAreaConhecimento = idAreaConhecimento;
        this.cdd = cdd;
        this.descricaoAreaConhecimento = descricaoAreaConhecimento;
    }

//Get's e Set's
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

//Método responsável por retornar uma string com os dados de objetos AreaConhecimento
//Formato de gravação TXT onde o char ; é o delimitador (para utlização em split's)
    @Override
    public String toString() {
        return (idAreaConhecimento + ";" + cdd + ";" + descricaoAreaConhecimento);
    }
}
