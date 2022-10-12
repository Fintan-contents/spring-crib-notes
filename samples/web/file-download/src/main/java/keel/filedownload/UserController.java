package keel.filedownload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    /** ダウンロード対象のファイルパス */
    @Value("${keel.filedownload.path}")
    private String targetFilePath;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // example-start
    @GetMapping("/download")
    public String download(Model model) {

        // Viewで使用するダウンロードファイルパスをModelに設定します
        // （もしユーザが画面で入力したファイルパス等を使用する場合は、ディレクトリトラバーサル攻撃への対策も考慮してください）
        model.addAttribute(TextFileDownloadView.DOWNLOAD_FILE_INFO_KEY,
                new FileDownloadAttributes(targetFilePath, "download.txt"));

        // ViewのBean名を返します
        return "textFileDownloadView";
    }
    // example-end
}
