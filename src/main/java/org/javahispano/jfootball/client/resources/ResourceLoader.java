package org.javahispano.jfootball.client.resources;

import javax.inject.Inject;

import com.google.gwt.core.client.ScriptInjector;

public class ResourceLoader {
    @Inject
    ResourceLoader(AppResources appResources) {
        appResources.normalize().ensureInjected();
        appResources.style().ensureInjected();
        appResources.styleCodeMirror().ensureInjected();
        appResources.showHint().ensureInjected();
        appResources.manchseterSyntax().ensureInjected();
        appResources.themeEclipse().ensureInjected();
        
        ScriptInjector.fromString(appResources.statsMin().getText());
        ScriptInjector.fromString(appResources.threeMin().getText());
        ScriptInjector.fromString(appResources.threexCellshader().getText());
    }
}
