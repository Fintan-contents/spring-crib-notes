package keel.filedownload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    /** ダウンロード対象のファイルパス */
    @Value("${keel.filedownload.path}")
    String targetFilePath;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // example-start
    @GetMapping("/download")
    public String download(Model model) {
        /**
         * {@link TextFileDownloadView}に渡す情報を、Modelに設定します。
         *
         * なお、このサンプルではダウンロード対象のファイルパス({@link targetFilePath})は
         * application.propertiesから取得していますが、ユーザが画面で入力したファイルパス等を使用する場合は、
         * ディレクトリトラバーサル攻撃への対策も実施するようにしてください。
         */
        model.addAttribute(TextFileDownloadView.DOWNLOAD_FILE_INFO_KEY,
                new FileDownloadAttributes(targetFilePath, "download.txt"));
        /**
         * ダウンロード処理は、{@link TextFileDownloadView}で実施するため、
         * ここではそのBean名(textFileDownloadView)を返却します。
         */
        return "textFileDownloadView";
    }
    // example-end
}
