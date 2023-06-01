package ObserverPD;

import entity.Eventos;

interface Observable {
    void registrarObserver(Observer observer);
    void removerObserver(Observer observer);
    void notifyObservers(Eventos eventos);
}
