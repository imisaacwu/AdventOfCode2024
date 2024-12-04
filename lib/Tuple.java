package lib;

// Tuples
public class Tuple {
    public Object[] arr;

    public Tuple(Object... values) {
        this.arr = values;
    }

    @Override
    public String toString() {
        String out = "(";
        for (Object o : arr) {
            out += o + ",";
        }
        return out.substring(0, out.length() - 1) + ")";
    }

    public static class Unit<T0> extends Tuple {
        public Unit(T0 v0) {
            super(v0);
        }

        @SuppressWarnings("unchecked")
        public T0 v0() { return (T0) arr[0]; }

        @Override
        public int hashCode() {
            return v0().hashCode();
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object other) {
            if (other == this) { return true; }
            if (other == null || other.getClass() != this.getClass()) { return false; }
            Unit<?> o = (Unit<?>) other;
            try {
                Unit<T0> obj = (Unit<T0>) o;
                return obj.v0().equals(v0());
            } catch (ClassCastException cce) {}
            return false;
        }
    }

    public static class Pair<T0, T1> extends Tuple {
        public Pair(T0 v0, T1 v1) {
            super(v0, v1);
        }

        @SuppressWarnings("unchecked")
        public T0 v0() { return (T0) arr[0]; }

        @SuppressWarnings("unchecked")
        public T1 v1() { return (T1) arr[1]; }

        @Override
        public int hashCode() {
            return v0().hashCode() * 17 + v1().hashCode() * 31;
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof Pair<?, ?>) {
                Pair<?, ?> o = (Pair<?, ?>) other;
                return new Unit<>(v0()).equals(new Unit<>(o.v0())) &&
                       new Unit<>(v1()).equals(new Unit<>(o.v1()));
            }
            return false;
        }
    }
}
