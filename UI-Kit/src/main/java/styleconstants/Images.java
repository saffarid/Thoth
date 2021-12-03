package styleconstants;

public enum Images {

    URL_CLOSE ("/image/icons/close.png"),
    URL_SQUARE ("/image/icons/square.png"),
    URL_MINIFY ("/image/icons/minify.png"),
    URL_MINIFY_TO_TASK_BAR ("/image/icons/minify-to-task-bar.png"),
    THREE_POINT_H("/image/icons/more-horizontal.png"),
    ;

    private String url;
    Images(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }
}
