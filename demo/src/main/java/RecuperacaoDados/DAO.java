package RecuperacaoDados;

import entity.Cliente;
import entity.Eventos;
import entity.Funcionario;
import entity.Pessoa;
import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Random;

public class DAO {
    public static void salvarDados(Object o){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            if(o instanceof Cliente){
                Cliente cliente = (Cliente) o;
                entityManager.persist(cliente);
            } else if(o instanceof Eventos) {
                Eventos eventos = (Eventos) o;
                entityManager.persist(eventos);
            } else if(o instanceof Funcionario) {
                Funcionario funcionario = (Funcionario) o;
                entityManager.persist(funcionario);
            }

            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    public static Funcionario atribuirReponsavel() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            Long quantidadeTuplas = entityManager.createQuery("SELECT COUNT(f) FROM Funcionario f", Long.class)
                    .getSingleResult();

            Random random = new Random();
            int indiceAleatorio = random.nextInt(quantidadeTuplas.intValue()) + 1;

            TypedQuery<Funcionario> query = entityManager.createQuery("SELECT f FROM Funcionario f WHERE f.id = :indiceAleatorio", Funcionario.class);
            query.setParameter("indiceAleatorio", indiceAleatorio);

            return query.getSingleResult();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    public static Cliente procurarCliente(String cpf){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            TypedQuery<Cliente> query = entityManager.createQuery("SELECT c FROM Cliente c WHERE c.cpf = :cpf", Cliente.class);
            query.setParameter("cpf", cpf);

            return query.getSingleResult();

        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    public static boolean verificarEmailCadastrado(String email) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(c) FROM Cliente c WHERE c.email = :email", Long.class);
            query.setParameter("email", email);

            Long count = query.getSingleResult();
            return count > 0;

        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    public static boolean verificarCpfCadastrado(String cpf) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(c) FROM Cliente c WHERE c.cpf = :cpf", Long.class);
            query.setParameter("cpf", cpf);

            Long count = query.getSingleResult();
            return count > 0;

        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    public static Eventos procurarEvento(int id){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            TypedQuery<Eventos> query = entityManager.createQuery("SELECT e FROM Eventos e WHERE e.id= :id", Eventos.class);
            query.setParameter("id", id);

            return query.getSingleResult();

        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
    public static List<Cliente> listaClientes(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            TypedQuery<Cliente> query = entityManager.createQuery("SELECT c FROM Cliente c ", Cliente.class);

            return query.getResultList();

        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
    public static Pessoa login(String email, String senha) {
        try {
            return loginCliente(email, senha);
        }catch (NoResultException erro){
            return loginFuncionario(email, senha);
        }
    }

    private static Cliente loginCliente(String email,String senha){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            TypedQuery<Cliente> query = entityManager.createQuery("SELECT c FROM Cliente c WHERE c.email = :email", Cliente.class);
            query.setParameter("email", email);

            Cliente cliente = query.getSingleResult();

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String senhaCriptografada = cliente.getSenha();

            if(encoder.matches(senha, senhaCriptografada)){
                return cliente;
            } else {
                throw new NoResultException("Senha incorreta");
            }

        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    private static Funcionario loginFuncionario(String email,String senha){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            TypedQuery<Funcionario> queryFuncionario = entityManager.createQuery("SELECT f FROM Funcionario f WHERE f.email = :email", Funcionario.class);
            queryFuncionario.setParameter("email", email);

            Funcionario funcionario = queryFuncionario.getSingleResult();

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String senhaCriptografada = funcionario.getSenha();

            if(encoder.matches(senha, senhaCriptografada)){
                return funcionario;
            } else {
                throw new NoResultException("Senha incorreta");
            }

        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    public static List<Eventos> listaEventos(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            TypedQuery<Eventos> query = entityManager.createQuery("SELECT e FROM Eventos e ", Eventos.class);

            return query.getResultList();

        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
