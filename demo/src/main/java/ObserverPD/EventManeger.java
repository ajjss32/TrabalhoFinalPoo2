package ObserverPD;

import RecuperacaoDados.DAO;
import entity.Cliente;
import entity.Eventos;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public class EventManeger implements Observable{
    private List<Observer> observers;
    public EventManeger() {
        observers = new ArrayList<>();
    }
    @Override
    public void registrarObserver() {
        observers.addAll(DAO.listaClientes());
    }

    @Override
    public void removerObserver(Observer observer) {
        //QUANDO MEU STATUS DO EVENTO FOR "FINALIZADO"
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Eventos evento) {
        for (Observer observer: observers){
            Cliente cliente = (Cliente) observer;
            if ((evento.getClienteBySolicitanteFk().getCpf()).equals(cliente.getCpf())){
                observer.update(evento);
                break;
            }
        }
    }

    public void atualizarStatusEvento(int id,String status){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Eventos evento =entityManager.find(Eventos.class,id);
            evento.setStatus(status);
            Eventos eventoAtualizado = entityManager.merge(evento);
            entityManager.flush();
            entityManager.refresh(evento);
            transaction.commit();
            notifyObservers(evento);

        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
