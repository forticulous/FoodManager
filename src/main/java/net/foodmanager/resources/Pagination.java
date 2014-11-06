package net.foodmanager.resources;

/**
 * Immutable container for pagination bounds
 *
 * @author fort
 */
public class Pagination {

    private static final int MAX_LIMIT = 10;

    private final int offset;
    private final int limit;

    private Pagination(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    /**
     * Create new pagination
     *
     * @param offset position of the first result, numbered from 1
     * @param limit total number of results
     */
    public static Pagination valueOf(int offset, int limit) {
        if (offset < 1) {
            throw new IllegalArgumentException("Offset must be greater than zero");
        }
        if (limit < 1) {
            throw new IllegalArgumentException("Limit must be greater than zero");
        } else if (limit > MAX_LIMIT) {
            throw new IllegalArgumentException("Limit cannot be greater than " + MAX_LIMIT);
        }
        return new Pagination(offset, limit);
    }

    public int getFirstResult() {
        return offset - 1;
    }

    public int getMaxResult() {
        return limit;
    }

}
