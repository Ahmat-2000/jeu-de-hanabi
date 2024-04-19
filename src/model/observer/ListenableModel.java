package model.observer;

/**
 * Defines the contract for models that can be listened to. Such models can register
 * and notify listeners about changes, allowing for a reactive design where changes in the
 * model can be propagated to observers, typically for UI updates or other forms of state
 * synchronization.
 */
public interface ListenableModel {
    /**
     * Registers a ModelListener to be notified about changes to the model.
     *
     * @param l The listener to be added.
     */
    void addModelListener(ModelListener l);

    /**
     * Removes a previously registered ModelListener so it no longer receives change notifications.
     *
     * @param l The listener to be removed.
     */
    void removeModelListener(ModelListener l);
}
