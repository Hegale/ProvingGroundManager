package pg.provingground.domain;

public enum FuelType {
    GASOLINE("가솔린"),
    DIESEL("디젤"),
    ELECTRIC("전기"),
    HYDROGEN("수소"),
    HYBRID("하이브리드"),
    LPG("LPG");

    private final String displayName;

    FuelType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
