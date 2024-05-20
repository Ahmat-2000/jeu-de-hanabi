package model.observer;

/**
 * Interface ModelListener pour écouter les changements dans les modèles.
 */
public interface ModelListener {
    /**
     * Méthode appelée lorsqu'un changement se produit dans le modèle.
     *
     * @param source La source du changement.
     */
    public void somethinHasChanged(Object source);
}
