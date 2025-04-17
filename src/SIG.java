import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class SIG {
    SIG dad;
    int q;
    int m;
    int[] levels;
    ArrayList<SIG> childs;
    int cost;

    public SIG(int m, int[] levels, SIG dad, int q, int cost) {
        this.m = m;
        this.levels = Arrays.copyOf(levels, levels.length);
        this.dad = dad;
        this.q = q;
        this.childs = new ArrayList<SIG>();
        this. cost = cost;
    }

    public void addChild(SIG child) {
        this.childs.add(child);
    }

    public int getM() {
        return m;
    }

    public int[] getLevels() {
        return Arrays.copyOf(levels, levels.length);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SIG)) return false;
        SIG other = (SIG) obj;
        return this.m == other.m && Arrays.equals(this.levels, other.levels);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(m, Arrays.hashCode(levels));
    }
 
}
