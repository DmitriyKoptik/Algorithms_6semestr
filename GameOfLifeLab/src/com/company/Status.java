package com.company;

public enum Status {
    FREE,
    BORN,
    LIVE,
    DIED;

    public Status step1(int around) {
        switch (this) {
            case FREE:
                return (around == 3) ? BORN : FREE;
            case LIVE:
                return (around <= 1 || around >= 4) ? DIED : LIVE;
            default:
                return this;
        }
    }

    public Status step2() {
        switch (this) {
            case BORN:
                return LIVE;
            case DIED:
                return FREE;
            default:
                return this;
        }
    }

    public boolean isCell() {
        return this == LIVE || this == DIED;
    }
}
