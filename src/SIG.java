import java.util.Arrays;
import java.util.Objects;

public class SIG {
    SIG dad;
    int q;
    int m;
    int[] levels;
    int cost;

    public SIG(int m, int[] levels, SIG dad, int q, int cost) {
        this.m = m;
        this.levels = levels;
        this.dad = dad;
        this.q = q;
        this. cost = cost;
    }

    public int getM() {
        return m;
    }

    public int[] getLevels() {
        return levels;
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
