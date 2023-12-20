package pg.provingground.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.LocalDateTime;

@Embeddable
@Getter
public class FileMetaData {
    private String fileName;
    private String fileType;
    private Long fileSize;
    private String filePath;
    private LocalDateTime uploadTime;

    protected FileMetaData() {}

    public FileMetaData(String name, String type, Long size, String path, LocalDateTime time) {
        this.fileName = name;
        this.fileType = type;
        this.fileSize = size;
        this.filePath = path;
        this.uploadTime = time;
    }
}
