package de.dhbw.mwulle.jhelp.netbeans.impl;

import de.dhbw.mwulle.jhelp.api.HelpSet;
import de.dhbw.mwulle.jhelp.api.HelpSetProvider;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = HelpSetProvider.class)
public class NetbeansHelpSetProvider implements HelpSetProvider {

    @Override
    public HelpSet getMasterHelpSet() {
        // TODO 2024-02-24: Actually lookup all HelpSets and merge them
        return Lookup.getDefault().lookup(HelpSet.class);
    }
}
