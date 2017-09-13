package pt.isel.ls.control;

public class Ints {
    public static int max(int a, int b) {
        return a >= b ? a : b;
    }

    public static int indexOfBinary(int[] a, int fromIndex, int toIndex, int n) {

        if (fromIndex > toIndex)
            throw new IllegalArgumentException("from(" + fromIndex + ") > to(" + toIndex + ")");

        int low = fromIndex;
        int high = toIndex;
        int mid;
        // if (low==high) return a[low]==n ? a[n] : -1;

        while (low <= high) {
            mid = (high + low) >>> 1;//+ 1;
            if (n > a[mid])
                low = mid + 1;
            else if (n < a[mid])
                high = mid - 1;
            else return mid;
        }
        return -1;
    }
}
