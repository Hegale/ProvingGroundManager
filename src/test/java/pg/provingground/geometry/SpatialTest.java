package pg.provingground.geometry;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.*;

import java.util.List;
import java.util.Random;

public class SpatialTest {

    private static final double BASE_X = 126.33504677811536;
    private static final double BASE_Y = 36.64304710106608;
    private static final double OFFSET = 0.01; // 이 값은 조절 가능

    /** 랜덤 좌표 생성 */
    private static Coordinate generateRandomCoordinate() {
        Random random = new Random();
        double x = BASE_X + (random.nextDouble() * 2 - 1) * OFFSET;
        double y = BASE_Y + (random.nextDouble() * 2 - 1) * OFFSET;
        return new Coordinate(x, y);
    }

    public void lineStringTest() {

        List<Coordinate> coordinates;
        LineString lineString;



    }
}
