package pg.provingground.domain;

import lombok.Getter;

@Getter
public enum FuelType {
    GASOLINE("가솔린"),
    DIESEL("디젤"),
    ELECTRIC("전기"),
    HYDROGEN("수소"),
    LPG("LPG");

    private final String displayName;

    FuelType(String displayName) {
        this.displayName = displayName;
    }

}
