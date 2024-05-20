package model.observer;

import java.util.*;

/**
 * Classe abstraite AbstractListenableModel pour les modèles qui peuvent notifier les écouteurs de changement.
 */
public abstract class AbstractListenableModel implements ListenableModel {
    /**
     * La liste des écouteurs de modèle.
     */
    private List<ModelListener> listeners;

    /**
     * Constructeur pour créer un modèle écoutable.
     */
    public AbstractListenableModel(){
        listeners = new ArrayList<>();
    }

    /**
     * Ajoute un écouteur de modèle.
     *
     * @param l L'écouteur de modèle à ajouter.
     */
    @Override
    public void addModelListener(ModelListener l){
        listeners.add(l);
    }

    /**
     * Retire un écouteur de modèle.
     *
     * @param l L'écouteur de modèle à retirer.
     */
    @Override
    public void removeModelListener(ModelListener l){
        listeners.remove(l);
    }

    /**
     * Notifie tous les écouteurs d'un changement.
     */
    protected void fireChange(){
        for(ModelListener l : listeners){
            l.somethinHasChanged(this);
        }
    }
}
