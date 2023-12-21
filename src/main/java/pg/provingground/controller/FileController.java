package pg.provingground.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileController {

    @GetMapping("/geofiles/path/{testId}/{carPath}.geojson")
    public ResponseEntity<Resource> getFile(@PathVariable String testId, @PathVariable String carPath) {
        try {
            // 지정된 절대 경로에 testId와 filename을 추가
            Path file = Paths.get("/Users/juyeon/code/ProvingGround/userfiles/test-results")
                    .resolve(testId)
                    .resolve(carPath);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}

