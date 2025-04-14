import java.util.Arrays;
import java.util.Objects;

public class SIG {
    SIG dad;
    int q;
    private final int m;
    private final int[] levels;

    public SIG(int m, int[] levels, SIG dad, int q) {
        this.m = m;
        this.levels = Arrays.copyOf(levels, levels.length);
        this.dad = dad;
        this.q = q;
    }

    public int getM() {
        return m;
    }

    public int[] getLevels() {
        return Arrays.copyOf(levels, levels.length);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SIG)) return false;
        SIG that = (SIG) o;
        return m == that.m && Arrays.equals(levels, that.levels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m, Arrays.hashCode(levels));
    }
 
}
