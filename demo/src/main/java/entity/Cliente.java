package entity;

import ObserverPD.Observer;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Cliente extends Pessoa implements Observer {
    @Id
    @Column(name = "cpf")
    private String cpf;
    @Basic
    @Column(name = "nome")
    private String nome;
    @Basic
    @Column(name = "endereco")
    private String endereco;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "senha")
    private String senha;

    public Cliente(String cpf, String nome, String endereco, String email, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
        this.email = email;
        this.senha = senha;
    }

    public Cliente() {
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
        return Objects.equals(cpf, cliente.cpf) && Objects.equals(nome, cliente.nome) && Objects.equals(endereco, cliente.endereco) && Objects.equals(email, cliente.email) && Objects.equals(senha, cliente.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf, nome, endereco, email, senha);
    }

    @Override
    public void update(Eventos evento) {
        System.out.println(nome+" :Atualização do evento do tipo "+evento.getTipo()+" -Novo Status: "+evento.getStatus());
    }
}
