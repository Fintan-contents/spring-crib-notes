package keel.filedownload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // example-start
    @GetMapping("/download")
    public String download(Model model, @Value("${keel.filedownload.path}") String targetFilePath) {
        model.addAttribute(TextFileDownloadView.DOWNLOAD_FILE_INFO_KEY,
                new FileDownloadAttributes(targetFilePath, "download.txt"));
        return "textFileDownloadView";
    }
    // example-end
}
