package ObserverPD;

import entity.Eventos;

import java.util.ArrayList;
import java.util.List;

public class EventManeger implements Observable{
    //lista ou um cliente
    private List<Observer> observers;
    private List<Eventos> eventos;

    public EventManeger() {
        observers = new ArrayList<>();
        eventos = new ArrayList<>();
    }

    @Override
    public void registrarObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removerObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Eventos eventos) {
        for (Observer observer:observers) {
            observer.update(eventos);
        }
    }
    public void addEvento(Eventos evento){
        //notificar o funcionario
        eventos.add(evento);
        notifyObservers(evento);
    }
    public void atualizarStatusEvento(int id,String status){
        for (Eventos evento: eventos){
            if (evento.getId() == id){
                evento.setStatus(status);
                notifyObservers(evento);
                break;
            }
        }
    }
}
