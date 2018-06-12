package keel.filedownload;

public class FileDownloadAttributes {

    private final String targetFilePath;

    private final String downloadFileName;

    public FileDownloadAttributes(String targetFilePath, String downloadFileName) {
        this.targetFilePath = targetFilePath;
        this.downloadFileName = downloadFileName;
    }

    public String getTargetFilePath() {
        return targetFilePath;
    }

    public String getDownloadFileName() {
        return downloadFileName;
    }
}
