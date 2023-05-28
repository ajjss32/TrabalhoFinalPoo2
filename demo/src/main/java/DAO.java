import entity.Funcionario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class DAO {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Funcionario teste = new Funcionario();
            teste.setEmail("teste@gmail.com");
            teste.setCpf("12345678910");
            teste.setEndereco("sla");
            teste.setNome("sssss");
            teste.setMatricula("455545");
            teste.setSenha("socorro");

            entityManager.persist(teste);

            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
