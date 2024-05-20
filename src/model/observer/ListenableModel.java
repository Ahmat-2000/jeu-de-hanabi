package model.observer;

/**
 * Interface ListenableModel pour les modèles qui peuvent être écoutés.
 */
public interface ListenableModel {
    /**
     * Ajoute un écouteur de modèle.
     *
     * @param l L'écouteur de modèle à ajouter.
     */
    void addModelListener(ModelListener l);

    /**
     * Retire un écouteur de modèle.
     *
     * @param l L'écouteur de modèle à retirer.
     */
    void removeModelListener(ModelListener l);
}
