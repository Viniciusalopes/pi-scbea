package controle;

import classes.AreaConhecimento;
import classes.Livro;
import interfaces.ICRUDAreaConhecimento;
import java.util.ArrayList;
import persistencia.PersistenciaAreaConhecimento;

public class ControleAreaConhecimento implements ICRUDAreaConhecimento {

    private ICRUDAreaConhecimento persistencia = null;
    private ArrayList<AreaConhecimento> colecao;

    public ControleAreaConhecimento() throws Exception {
        persistencia = new PersistenciaAreaConhecimento();
        colecao = new ArrayList<>();
    }

    @Override
    public ArrayList<AreaConhecimento> listar() throws Exception {
        return persistencia.listar();
    }

    @Override
    public void incluir(AreaConhecimento areaConhecimento) throws Exception {
        int cdd = areaConhecimento.getCdd();
        String descricao = areaConhecimento.getDescricaoAreaConhecimento();
        validarPreenchimento(cdd, descricao);
        colecao = listar();
        validarDuplicidade(areaConhecimento);
        persistencia.incluir(areaConhecimento);
    }

    @Override
    public void alterar(AreaConhecimento areaConhecimento) throws Exception {
        int cdd = areaConhecimento.getCdd();
        String descricao = areaConhecimento.getDescricaoAreaConhecimento();
        validarPreenchimento(cdd, descricao);
        colecao = listar();
        validarDuplicidade(areaConhecimento);
        persistencia.alterar(areaConhecimento);
    }

    @Override
    public void excluir(int idAreaConhecimento) throws Exception {
        ArrayList<Livro> livros = new ControleLivro().listar();
        for (Livro l : livros) {
            if (l.getAreaConhecimento().getIdAreaConhecimento() == idAreaConhecimento) {
                throw new Exception("Está área de conhecimento está vinculada ao livro \n"
                        + String.format("%04d", l.getIdLivro()) + " - "
                        + l.getTitulo() + "!");
            }
        }
        persistencia.excluir(idAreaConhecimento);
    }

    private void validarPreenchimento(int cdd, String descricao) throws Exception {
        // Verificar qtd de caracteres de acordo com padrão de cada campo (CDD e Descrição)
        if (cdd <= 99999) {
            throw new Exception("O código do CDD precisa ter 6 dígitos!");
        }
        if (descricao.trim().length() < 2) {
            throw new Exception("A descrição da área de conhecimento precisa ter no mínimo 2 caracteres!");
        }
    }

    private void validarDuplicidade(AreaConhecimento areaConhecimento) throws Exception {

        for (AreaConhecimento objAreaConhecimento : colecao) {
            int idArea = objAreaConhecimento.getIdAreaConhecimento();
            int cddCadastro = objAreaConhecimento.getCdd();
            String descricaoCadastro = objAreaConhecimento.getDescricaoAreaConhecimento();
            if (cddCadastro == areaConhecimento.getCdd()
                    && idArea != areaConhecimento.getIdAreaConhecimento()) {
                throw new Exception("Já existe uma área de conhecimento cadastrada com esse código CDD!");
            }
            if (descricaoCadastro.equalsIgnoreCase(areaConhecimento.getDescricaoAreaConhecimento())
                    && idArea != areaConhecimento.getIdAreaConhecimento()) {
                throw new Exception("Já existe uma área de conhecimento cadastrada com essa descrição!");
            }
        }
    }

    public AreaConhecimento buscarPeloId(int idAreaConhecimento) throws Exception {
        return persistencia.buscarPeloId(idAreaConhecimento);
    }
}
