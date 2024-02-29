/*
 * Copyright (c) 2023. Melvin Wulle
 * All rights reserved.
 */
package de.dhbw.mwulle.jhelp.ui;

import de.dhbw.mwulle.jhelp.api.HelpSet;
import de.dhbw.mwulle.jhelp.api.HelpSetProvider;
import de.dhbw.mwulle.jhelp.api.MapId;
import de.dhbw.mwulle.jhelp.api.View;
import de.dhbw.mwulle.jhelp.helpset.toc.TOCItemNode;
import de.dhbw.mwulle.jhelp.netbeans.impl.ContentManager;
import de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.UiViewFactory;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.TopComponent;

import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//com.mwulle.help.ui//HelpTopComponent//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "HelpTopComponent",
        iconBase = "de/dhbw/mwulle/jhelp/ui/help-16x16.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "com.mwulle.help.ui.HelpTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_HelpTopComponentAction",
        preferredID = "HelpTopComponent"
)
@Messages({
        "CTL_HelpTopComponentAction=Help",
        "CTL_HelpTopComponent=Help",
        "HINT_HelpTopComponent=This is a Help window"
})
public final class HelpTopComponent extends TopComponent {

    private final ContentManager contentManager = new ContentManagerImpl();
    private Lookup tabbedPaneLookup = Lookup.EMPTY;
    // Currently open help set
    private Lookup helpSetLookup = Lookup.EMPTY;
    private HelpSet helpSet = null;

    public HelpTopComponent() {
        initComponents();
        setName(Bundle.CTL_HelpTopComponent());
        setToolTipText(Bundle.HINT_HelpTopComponent());
        contentEditorPane.setContentType("text/html");
        contentEditorPane.setEditable(false);
        contentEditorPane.setOpaque(false);

        contentEditorPane.addHyperlinkListener(hle -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(hle.getEventType())) {
                try {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.browse(hle.getURL().toURI());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        List<Lookup> lookups = new ArrayList<>();
        lookups.add(Lookups.singleton(new ContentManagerImpl()));
        lookups.add(Lookups.proxy(() -> helpSetLookup));
        lookups.add(Lookups.proxy(() -> tabbedPaneLookup));
        associateLookup(new ProxyLookup(lookups.toArray(new Lookup[0])));
    }

    public void setHelpSet(HelpSet helpSet) {
        if (this.helpSet == helpSet) {
            return;
        }

        this.helpSet = helpSet;

        if (helpSet != null) {
            helpSetLookup = Lookups.singleton(helpSet);
            MapId mapId = helpSet.findMapId(helpSet.getHelpSetMap().getHomeId());
            if (mapId != null && mapId.getUrl() != null) {
                contentManager.setContent(mapId);
            } else {
                setEmptyPage();
            }

            setUpPaneTabs();
            helpSetLookup = Lookups.singleton(helpSet);
        } else {
            helpSetLookup = Lookup.EMPTY;
            setEmptyPage();
            clearPaneTabs();
        }
    }

    private void setEmptyPage() {
        contentEditorPane.setText(" <!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h1>404</h1>\n" +
                "<p>No HelpSet present or the home id is not found.</p>\n" +
                "\n" +
                "</body>\n" +
                "</html> ");
    }

    public void setContent(String text) {
        // contentEditorPane.setText(text);
    }

    public void setContentHeader(String text) {
        // contentHeader.setText(text);
    }

    public void setRootContext(TOCItemNode rootContext) {
        // TocItemNode otherRoot = Lookup.getDefault().lookup(de.dhbw.mwulle.jhelp.api.HelpSet.class).getViews().stream().filter(d -> d instanceof TocView).map(d -> (TocView) d).map(TocItemNode::createRootNode).findFirst().get();
        // ((BeanTreeView) tocPane).setRootVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        tabbedPane = new javax.swing.JTabbedPane();
        contentPanel = new javax.swing.JPanel();
        contentHeader = new javax.swing.JLabel();
        contentScrollPane = new javax.swing.JScrollPane();
        contentEditorPane = new javax.swing.JEditorPane();

        tabbedPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabbedPaneStateChanged(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(contentHeader, org.openide.util.NbBundle.getMessage(HelpTopComponent.class, "HelpTopComponent.contentHeader.text")); // NOI18N

        contentScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        contentScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        contentScrollPane.setMinimumSize(new java.awt.Dimension(300, 200));
        contentScrollPane.setPreferredSize(new java.awt.Dimension(300, 200));
        contentScrollPane.setViewportView(contentEditorPane);

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contentHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(contentPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(contentScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 716, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addComponent(contentHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 490, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentPanelLayout.createSequentialGroup()
                    .addGap(0, 38, Short.MAX_VALUE)
                    .addComponent(contentScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(contentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void setUpPaneTabs() {
        clearPaneTabs();
        if (helpSet == null) {
            return;
        }

        Collection<? extends UiViewFactory> uiViewFactories = Lookup.getDefault().lookupAll(UiViewFactory.class);
        List<Lookup> lookups = new ArrayList<>();

        for (View view : helpSet.getViews()) {
            System.out.println("Got View to open: " + view.getClass());
            dance: for (UiViewFactory uiViewFactory : uiViewFactories) {
                if (view.getClass() == uiViewFactory.getViewClass()) {
                    Component component = uiViewFactory.createComponent(view);
                    if (component instanceof Lookup.Provider) { // TODO 2024-02-23: Maybe there is a better way?
                        lookups.add(((Lookup.Provider) component).getLookup());
                    }
                    tabbedPane.addTab(NbBundle.getMessage(HelpTopComponent.class, String.format("HelpTopComponent.tabbedPane.view.%s.tabTitle", view.getClass().getName())), component);
                    break dance;
                }
            }
        }

        tabbedPaneLookup = new ProxyLookup(lookups.toArray(new Lookup[0]));
    }

    private void clearPaneTabs() {
        tabbedPane.removeAll();
    }

    private void tabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabbedPaneStateChanged
        // TODO 2024-02-22: Fill it
        // tabbedPane.getSelectedComponent()
        // TocItemNode otherRoot = Lookup.getDefault().lookup(de.dhbw.mwulle.jhelp.api.HelpSet.class).getViews().stream().filter(d -> d instanceof TocView).map(d -> (TocView) d).map(TocItemNode::createRootNode).findFirst().get();
    }//GEN-LAST:event_tabbedPaneStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane contentEditorPane;
    private javax.swing.JLabel contentHeader;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JScrollPane contentScrollPane;
    private javax.swing.JPanel panel;
    private javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened() {
        if (helpSet == null) {
            // Set help set if not already set
            setHelpSet(Lookup.getDefault().lookup(HelpSetProvider.class).getMasterHelpSet());
        }
    }

    @Override
    public void componentClosed() {
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    private class ContentManagerImpl implements ContentManager {

        @Override
        public void setContent(MapId mapId) {
            if (mapId.getUrl() == null) {
                throw new IllegalArgumentException("Map id url must not be null");
            }
            try {
                contentEditorPane.setPage(mapId.getUrl());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
