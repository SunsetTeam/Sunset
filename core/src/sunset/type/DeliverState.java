package sunset.type;

public enum DeliverState {
    base,
    target(true),
    ;
    public final boolean needsTarget;

    DeliverState(boolean needsTarget) {
        this.needsTarget = needsTarget;
    }

    DeliverState() {
        this(false);
    }
}
