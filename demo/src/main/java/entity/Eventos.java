package entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class Eventos {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "tipo")
    private String tipo;
    @Basic
    @Column(name = "data")
    private Date data;
    @Basic
    @Column(name = "endereco")
    private String endereco;
    @Basic
    @Column(name = "descricao")
    private String descricao;
    @Basic
    @Column(name = "qtdpessoas")
    private int qtdpessoas;
    @Basic
    @Column(name = "status")
    private String status;
    @ManyToOne
    @JoinColumn(name = "responsavel_fk", referencedColumnName = "id")
    private Funcionario funcionarioByResponsavelFk;
    @ManyToOne
    @JoinColumn(name = "solicitante_fk", referencedColumnName = "cpf")
    private Cliente clienteBySolicitanteFk;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQtdpessoas() {
        return qtdpessoas;
    }

    public void setQtdpessoas(int qtdpessoas) {
        this.qtdpessoas = qtdpessoas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Eventos eventos = (Eventos) o;

        if (id != eventos.id) return false;
        if (qtdpessoas != eventos.qtdpessoas) return false;
        if (tipo != null ? !tipo.equals(eventos.tipo) : eventos.tipo != null) return false;
        if (data != null ? !data.equals(eventos.data) : eventos.data != null) return false;
        if (endereco != null ? !endereco.equals(eventos.endereco) : eventos.endereco != null) return false;
        if (descricao != null ? !descricao.equals(eventos.descricao) : eventos.descricao != null) return false;
        if (status != null ? !status.equals(eventos.status) : eventos.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (tipo != null ? tipo.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (endereco != null ? endereco.hashCode() : 0);
        result = 31 * result + (descricao != null ? descricao.hashCode() : 0);
        result = 31 * result + qtdpessoas;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    public Funcionario getFuncionarioByResponsavelFk() {
        return funcionarioByResponsavelFk;
    }

    public void setFuncionarioByResponsavelFk(Funcionario funcionarioByResponsavelFk) {
        this.funcionarioByResponsavelFk = funcionarioByResponsavelFk;
    }

    public Cliente getClienteBySolicitanteFk() {
        return clienteBySolicitanteFk;
    }

    public void setClienteBySolicitanteFk(Cliente clienteBySolicitanteFk) {
        this.clienteBySolicitanteFk = clienteBySolicitanteFk;
    }
}
