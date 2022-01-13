package styleconstants.imagesvg;

public enum DefaultSize {
    HEIGHT(40),
    WIDTH(40),
    ;
    private double size;
    DefaultSize(double size) {
        this.size = size;
    }
    public double getSize(){
        return size;
    }
    public double getScaleX(double width){
        return width/ WIDTH.size;
    }
    public double getScaleY(double height){
        return height/ HEIGHT.size;
    }
}
