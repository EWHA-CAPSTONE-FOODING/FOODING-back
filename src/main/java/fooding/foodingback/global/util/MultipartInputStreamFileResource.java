package fooding.foodingback.global.util;

import org.springframework.core.io.InputStreamResource;

import java.io.InputStream;

/**
 * MultipartFile을 서버 전송용 Resource 객체로 감싸주는 유틸 클래스
 */
public class MultipartInputStreamFileResource extends InputStreamResource {

    private final String filename;

    public MultipartInputStreamFileResource(InputStream inputStream, String filename) {
        super(inputStream);
        this.filename = filename;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public long contentLength() {
        return -1; //파일 길이를 알 수 없을 때 -1 반환
    }
}