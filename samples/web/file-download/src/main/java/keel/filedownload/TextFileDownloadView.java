package keel.filedownload;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

// example-start
// @Componentを設定して、コンポーネントスキャン対象にします
@Component
public class TextFileDownloadView extends AbstractView {

    public static final String DOWNLOAD_FILE_INFO_KEY = "fileInfo";

    private final ResourceLoader resourceLoader;

    public TextFileDownloadView(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        setContentType("text/plain");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
                                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        // ダウンロード対象のファイルを読込み、レスポンスボディに書込みます
        FileDownloadAttributes attributes = (FileDownloadAttributes) model.get(DOWNLOAD_FILE_INFO_KEY);
        long size = writeFileContentToResponse(attributes.getTargetFilePath(), response);

        // レスポンスヘッダを設定します
        response.setContentType(getContentType());
        response.setHeader("Content-Disposition", "attachment; filename=" + attributes.getDownloadFileName());
        response.setHeader("Content-Length", String.valueOf(size));
    }

    private long writeFileContentToResponse(String filePath, HttpServletResponse response) throws IOException {
        try (InputStream in = resourceLoader.getResource(filePath).getInputStream()) {
            return in.transferTo(response.getOutputStream());
        }
    }
}
// example-end
