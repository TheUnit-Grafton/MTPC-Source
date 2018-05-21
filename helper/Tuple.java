package appguru.helper;

public class Tuple {
    public Object value1;
    public Object value2;
    
    public Tuple(Object a, Object a0) {
        super();
        this.value1 = a;
        this.value2 = a0;
    }
    
    public String toString() {
        return new StringBuilder().append("(").append(this.value1.toString()).append("|").append(this.value2.toString()).append(")").toString();
    }
}
