package org.javahispano.jfootball.client.resources;

import javax.inject.Inject;

public class ResourceLoader {
    @Inject
    ResourceLoader(AppResources appResources) {
        appResources.normalize().ensureInjected();
        appResources.style().ensureInjected();
        appResources.styleCodeMirror().ensureInjected();
        appResources.showHint().ensureInjected();
        appResources.manchseterSyntax().ensureInjected();
        appResources.themeEclipse().ensureInjected();
    }
}
