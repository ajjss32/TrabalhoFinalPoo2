package RecuperacaoDados;

import entity.Cliente;
import entity.Eventos;
import entity.Funcionario;
import jakarta.persistence.*;

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
    public static Cliente login(String email,String senha){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            TypedQuery<Cliente> query = entityManager.createQuery("SELECT c FROM Cliente c WHERE c.email= :email AND c.senha = :senha", Cliente.class);
            query.setParameter("email", email);
            query.setParameter("senha", senha);
            return query.getSingleResult();

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
