package Proxy;

import entity.Cliente;
import entity.Pessoa;

public class TelaEscolhida implements TelaInicial{
    private ClienteTela clienteTela;
    private FuncionarioTela funcionarioTela;
    private Pessoa pessoa;

    public TelaEscolhida() {
        clienteTela = new ClienteTela();
        funcionarioTela = new FuncionarioTela();
    }

    @Override
    public String tela() {
        String tela;

        if(pessoa instanceof Cliente){
            tela = clienteTela.tela();
        } else tela = funcionarioTela.tela();

        return tela;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
