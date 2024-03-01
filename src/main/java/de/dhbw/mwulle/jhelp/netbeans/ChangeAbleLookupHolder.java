package de.dhbw.mwulle.jhelp.netbeans;

import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

public class ChangeAbleLookupHolder<T> implements Lookup.Provider {

    private T value = null;
    private Lookup lookup = Lookup.EMPTY;

    private final Lookup proxyLookUp;

    public ChangeAbleLookupHolder() {
        this.proxyLookUp = Lookups.proxy(() -> lookup);
    }

    public boolean changeValue(T newValue) {
        if (value == newValue) {
            return false;
        }

        value = newValue;

        if (newValue != null) {
            lookup = Lookups.singleton(newValue);
        } else {
            lookup = Lookup.EMPTY;
        }

        proxyLookUp.lookup((Class<Object>) null); // Causes the change events to be called
        return true;
    }

    public T getValue() {
        return value;
    }

    @Override
    public Lookup getLookup() {
        return proxyLookUp;
    }
}
