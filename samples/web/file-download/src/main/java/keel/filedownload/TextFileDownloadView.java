package keel.filedownload;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

// example-start
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

        final FileDownloadAttributes fileInfo = (FileDownloadAttributes) model.get(DOWNLOAD_FILE_INFO_KEY);

        response.setHeader("Content-Disposition", "attachment; filename=" + fileInfo.getDownloadFileName());
        response.setHeader("Content-Type", "text/plain");

        StreamUtils.copy(
                resourceLoader.getResource(fileInfo.getTargetFilePath()).getInputStream(),
                response.getOutputStream());
    }
}
// example-end