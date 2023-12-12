package pg.provingground.domain;

public enum FuelType {
    GASOLINE("가솔린"),
    DIESEL("디젤"),
    ELECTRIC("전기"),
    HYBRID("하이브리드"),
    LPG("LPG"),
    CNG("CNG");

    private final String displayName;

    FuelType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
