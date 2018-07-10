package keel.filedownload;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

// example-start
// @Componentを設定して、コンポーネントスキャン対象にします。
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

        final FileDownloadAttributes attributes = (FileDownloadAttributes) model.get(DOWNLOAD_FILE_INFO_KEY);

        // レスポンスヘッダを設定します
        response.setHeader("Content-Disposition", "attachment; filename=" + attributes.getDownloadFileName());
        response.setHeader("Content-Type", "text/plain");

        // ダウンロード対象のファイルを読込み、レスポンスボディに書込みます
        StreamUtils.copy(
                resourceLoader.getResource(attributes.getTargetFilePath()).getInputStream(),
                response.getOutputStream());
    }
}
// example-end