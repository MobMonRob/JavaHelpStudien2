/*
 * Copyright (c) 2023. Melvin Wulle
 * All rights reserved.
 */
package de.dhbw.mwulle.jhelp.netbeans.impl.ui;

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;

import javax.swing.FocusManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@ActionID(
        category = "Edit",
        id = "com.mwulle.help.ui.Shortcut"
)
@ActionRegistration(
        iconBase = "de/dhbw/mwulle/jhelp/netbeans/impl/ui/help-16x16.png",
        displayName = "#CTL_Shortcut"
)
@ActionReferences({
        @ActionReference(path = "Menu/Help", position = 100, separatorAfter = 150),
        @ActionReference(path = "Shortcuts", name = "F1")
})
@Messages("CTL_Shortcut=Get Help")
public final class Shortcut implements ActionListener {

    private HelpCtx getHelpCtx() {
        Component focusOwner = FocusManager.getCurrentManager().getFocusOwner();
        HelpCtx helpCtx = HelpCtx.findHelp(focusOwner);
        if (helpCtx != null) {
            return helpCtx;
        }
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO 2024-03-05: Check if we should use the default HelpCtx, if we use the menu item instead of the the F1 shortcut, since the menu item always returns the Main window help ctx
        // TODO 2024-03-05: Note: modifier=0 -> F1, modifier=16 -> menu item
        HelpCtx.Displayer displayer = Lookup.getDefault().lookup(HelpCtx.Displayer.class);
        displayer.display(getHelpCtx());
    }
}
