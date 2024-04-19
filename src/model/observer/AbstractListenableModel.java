package model.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class that provides a framework for observable models. It allows
 * listeners to be added or removed and notifies them when the model changes.
 */
public abstract class AbstractListenableModel implements ListenableModel {
    // A list of listeners that are interested in changes to the model.
    private List<ModelListener> listeners;

    /**
     * Constructs an AbstractListenableModel and initializes the listener list.
     */
    public AbstractListenableModel() {
        listeners = new ArrayList<>();
    }

    /**
     * Registers a listener to be notified of changes to the model.
     *
     * @param l The listener to add.
     */
    @Override
    public void addModelListener(ModelListener l) {
        listeners.add(l);
    }

    /**
     * Removes a registered listener so that it no longer receives change notifications.
     *
     * @param l The listener to remove.
     */
    @Override
    public void removeModelListener(ModelListener l) {
        listeners.remove(l);
    }

    /**
     * Notifies all registered listeners of a change in the model. This method is typically
     * called by subclasses whenever the model's state changes in a way that should be
     * visible to the outside world.
     */
    protected void fireChange() {
        for (ModelListener l : listeners) {
            l.somethingHasChanged(this); // Notify each listener of the change.
        }
    }
}
