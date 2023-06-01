package ObserverPD;

import entity.Eventos;

interface Observable {
    void registrarObserver();
    void removerObserver(Observer observer);
    void notifyObservers(Eventos eventos);
}
