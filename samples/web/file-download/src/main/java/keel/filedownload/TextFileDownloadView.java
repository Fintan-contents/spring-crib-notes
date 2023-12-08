package keel.filedownload;

import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

// example-start
// @Componentを設定して、コンポーネントスキャン対象にします
@Component
public class TextFileDownloadView extends AbstractView {

    public static final String DOWNLOAD_FILE_INFO_KEY = "fileInfo";

    private final ResourceLoader resourceLoader;

    public TextFileDownloadView(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
                                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        FileDownloadAttributes attributes = (FileDownloadAttributes) model.get(DOWNLOAD_FILE_INFO_KEY);

        // レスポンスヘッダを設定します
        response.setContentType("text/plain");
        // 日本語のファイル名に対応するため、Content-DispositionはRFC6266の形式に沿って設定する
        ContentDisposition contentDisposition = ContentDisposition
                .attachment()
                .filename(attributes.getDownloadFileName(), StandardCharsets.UTF_8)
                .build();
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());

        // ダウンロード対象のファイルを読込み、レスポンスボディに書込みます
        try (InputStream in = resourceLoader.getResource(attributes.getTargetFilePath()).getInputStream()) {
            in.transferTo(response.getOutputStream());
        }
    }
}
// example-end
