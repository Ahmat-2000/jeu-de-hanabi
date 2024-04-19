package model.observer;

/**
 * Interface to be implemented by views or components that need to listen to model changes.
 * Implementing this interface allows the component to be notified whenever the state of the model
 * it is interested in changes, enabling the component to update its representation accordingly.
 */
public interface ModelListener {
    /**
     * Called when a change occurs in the model.
     * Implementing classes should define how they react to model changes, typically by updating their visual representation.
     *
     * @param source The object that originated the change, often the model itself.
     */
    public void somethingHasChanged(Object source);
}
