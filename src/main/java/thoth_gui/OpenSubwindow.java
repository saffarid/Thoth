package thoth_gui;

import thoth_gui.thoth_lite.subwindows.Subwindow;

@FunctionalInterface
public interface OpenSubwindow {

    public void openSubwindow(Subwindow subwindow);

}
