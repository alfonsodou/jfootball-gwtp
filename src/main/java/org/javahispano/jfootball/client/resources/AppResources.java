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
    
    interface StatsMin extends TextResource {
    }
    
    interface ThreeMin extends TextResource {
    }
    
    interface ThreexCellshader extends TextResource {
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
    
    @Source("js/threejs/stats.min.js")
    StatsMin statsMin();
    
    @Source("js/threejs/three.min.js")
    ThreeMin threeMin();
    
    @Source("js/threejs/threex.cellshader.js")
    ThreexCellshader threexCellshader();
}
