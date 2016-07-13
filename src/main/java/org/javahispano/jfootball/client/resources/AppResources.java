package org.javahispano.jfootball.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.resources.client.TextResource;

public interface AppResources extends ClientBundle {
    interface Normalize extends CssResource {
    }

    interface Style extends CssResource {
    }
    
    interface ShowHint extends CssResource {
    }
    
    interface StyleCodeMirror extends CssResource {
    }
    
    interface ManchesterSyntax extends CssResource {
    }
    
    interface ThemeEclipse extends CssResource {
    }
    
    @Source("css/normalize.gss")
    Normalize normalize();

    @Source("css/style.gss")
    Style style();
    
    @NotStrict
    @Source("css/codemirror/addon/hint/show-hint.css")
    ShowHint showHint();

    @NotStrict
    @Source("css/codemirror/lib/codemirror.css")
    StyleCodeMirror styleCodeMirror();
    
    @NotStrict
    @Source("css/codemirror/mode/manchestersyntax/manchestersyntax.css")
    ManchesterSyntax manchseterSyntax();
    
    @NotStrict
    @Source("css/codemirror/theme/eclipse/eclipse.css")
    ThemeEclipse themeEclipse();
}
