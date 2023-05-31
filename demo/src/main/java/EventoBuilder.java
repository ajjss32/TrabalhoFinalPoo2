import entity.Eventos;

import java.sql.Date;

public class EventoBuilder {

    private Eventos evento;

    //id gera automaticamente
    //solicitante

    public EventoBuilder(){
        this.evento = new Eventos();
    }

    public EventoBuilder setTipo(String tipo){
        evento.setTipo(tipo);
        return this;
    }

    public EventoBuilder setData(Date data){
        evento.setData(data);
        return this;
    }

    public EventoBuilder setEndereco(String endereco){
        evento.setEndereco(endereco);
        return this;
    }

    public EventoBuilder setDescricao(String descricao){
        evento.setDescricao(descricao);
        return this;
    }
     public EventoBuilder setQtdPessoas(int pessoas){
        evento.setQtdpessoas(pessoas);
        return this;
     }

     public Eventos builder(){
        evento.setStatus("Pendente");
        evento.setFuncionarioByResponsavelFk(DAO.atribuirReponsavel());
        return evento;
     }
}
