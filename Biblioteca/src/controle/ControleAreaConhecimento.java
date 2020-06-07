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
    public AreaConhecimento buscarPeloId(int idAreaConhecimento) throws Exception {
        return persistencia.buscarPeloId(idAreaConhecimento);
    }

    @Override
    public void incluir(AreaConhecimento areaConhecimento) throws Exception {
        validarPreenchimento(areaConhecimento.getCdd(), areaConhecimento.getDescricaoAreaConhecimento());
        colecao = listar();
        validarDuplicidade(areaConhecimento);
        persistencia.incluir(areaConhecimento);
    }

    @Override
    public void alterar(AreaConhecimento areaConhecimento) throws Exception {
        validarPreenchimento(areaConhecimento.getCdd(), areaConhecimento.getDescricaoAreaConhecimento());
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

//        if (String.format("%07d", cdd).charAt(0) == '0') {
//            throw new Exception("O código do CDD não pode iniciar com 0!");
//        }
        if (cdd < 99 && cdd >= 9999999) {
            throw new Exception("O código do CDD precisa ter nó mínimo 3 e no máximo 7 números!");
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

}
