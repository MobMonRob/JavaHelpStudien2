/*
 * Copyright (c) 2023. Melvin Wulle
 * All rights reserved.
 */
package de.dhbw.mwulle.jhelp;

import de.dhbw.mwulle.jhelp.api.HelpSet;
import de.dhbw.mwulle.jhelp.api.HelpSetProvider;
import de.dhbw.mwulle.jhelp.api.MapId;
import de.dhbw.mwulle.jhelp.api.MapIdEntry;
import de.dhbw.mwulle.jhelp.netbeans.impl.ContentManager;
import de.dhbw.mwulle.jhelp.ui.HelpTopComponent;
import org.openide.modules.OnStart;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.windows.WindowManager;

import java.net.URL;

/**
 * Help. Initialization of the program and incoming requests of help are handled here.
 *
 * @author Melvin Wulle
 */
@ServiceProvider(service = HelpCtx.Displayer.class)
public class JHelp implements HelpCtx.Displayer {

    @Override
    public boolean display(HelpCtx helpCtx) {
        HelpSetProvider helpSetProvider = Lookup.getDefault().lookup(HelpSetProvider.class);
        HelpTopComponent component = (HelpTopComponent) WindowManager.getDefault().findTopComponent("HelpTopComponent");

        if (helpSetProvider == null || component == null) {
            System.out.println("[JHelp] No HelpSetProvider or component found");
            // TODO 2024-03-05: Maybe log this?
            return false;
        }

        HelpSet helpSet = helpSetProvider.getMasterHelpSet();

        if (helpSet == null) {
            System.out.println("[JHelp] No HelpSet found");
            // TODO 2024-03-05: Maybe log this?
            return false;
        }

        MapIdEntry mapIdEntry = null;
        if (helpCtx != HelpCtx.DEFAULT_HELP) {
            MapId helpID = MapId.fromString(helpCtx.getHelpID());
            if (helpID != null) {
                mapIdEntry = helpSet.findMapIdEntry(helpID);
            } else {
                URL url = helpCtx.getHelp();
                if (url != null) {
                    // TODO 2024-03-05: Search via url
                }
            }

            if (mapIdEntry == null) {
                // TODO 2024-03-05: Maybe log this?
                System.out.println("[JHelp] MapIdEntry not found " + helpID + " context: " + helpCtx);
                return false;
            }
        }

        System.out.println("[JHelp] Open component");
        component.open();
        component.setHelpSet(helpSet);

        if (mapIdEntry != null) {
            // Only set if map id is not null, this happens, when HelpCtx is the default help,
            // in which case we want to open the last open page instead of using the home page
            component.getLookup().lookup(ContentManager.class).setContent(mapIdEntry);
        }

        return true;
    }

    @OnStart
    public static class Initialization implements Runnable {

        @Override
        public void run() {
            SearchEngine.deleteIndex();
        }
    }
}
