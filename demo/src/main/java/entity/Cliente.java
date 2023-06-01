package entity;

import jakarta.persistence.*;

import ObserverPD.*;

@Entity
public class Cliente implements Observer{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nome")
    private String nome;
    @Basic
    @Column(name = "cpf")
    private String cpf;
    @Basic
    @Column(name = "endereco")
    private String endereco;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "senha")
    private String senha;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cliente cliente = (Cliente) o;

        if (id != cliente.id) return false;
        if (nome != null ? !nome.equals(cliente.nome) : cliente.nome != null) return false;
        if (cpf != null ? !cpf.equals(cliente.cpf) : cliente.cpf != null) return false;
        if (endereco != null ? !endereco.equals(cliente.endereco) : cliente.endereco != null) return false;
        if (email != null ? !email.equals(cliente.email) : cliente.email != null) return false;
        if (senha != null ? !senha.equals(cliente.senha) : cliente.senha != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (cpf != null ? cpf.hashCode() : 0);
        result = 31 * result + (endereco != null ? endereco.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (senha != null ? senha.hashCode() : 0);
        return result;
    }


    @Override
    public void update(Eventos evento) {
        System.out.println(nome+" Atualização do evento "+evento.getTipo()+ " -Novo Status: "+evento.getStatus());
    }
}
