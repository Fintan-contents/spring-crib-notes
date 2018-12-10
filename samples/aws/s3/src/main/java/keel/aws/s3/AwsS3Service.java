package keel.aws.s3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

@Service
public class AwsS3Service {

    private final Logger logger = LoggerFactory.getLogger(AwsS3Service.class);

    // injection-start
    private final AwsS3Properties properties;

    private final TransferManager transferManager;

    public AwsS3Service(final AwsS3Properties properties,
            final TransferManager transferManager) {
        this.properties = properties;
        this.transferManager = transferManager;
    }
    // injection-end

    // upload-start
    public void uploadFile(final Path path) {
        try (final InputStream stream = Files.newInputStream(path);) {

            // メタデータにファイルのサイズを設定します。
            final ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(Files.size(path));

            if (isPng(path)) {
                // content-typeを設定する場合は、ObjectMetadataに設定します。
                // この例では、アップロード対象ファイルがpng形式の場合のみcontent-typeを設定しています。
                metadata.setContentType("image/png");
            }

            // waitForUploadResultを使用して、ファイルのアップロードが完了するまで待機します。
            final Upload upload = transferManager.upload(
                    properties.getBucketName(), "upload/" + path.getFileName(), stream, metadata);
            upload.waitForUploadResult();

            logger.info("AWS S3にファイルをアップロードしました。");
        } catch (IOException e) {
            throw new RuntimeException("S3へのファイル保存に失敗しました。");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    // upload-end

    // download-start
    public void downloadFile(final String fileName) {

        final File downloadPath = new File("download/pattern1/", fileName);
        final Download download = transferManager.download(
                properties.getBucketName(), "upload/" + fileName, downloadPath);

        try {
            // ダウンロードが完了するまで待機します。
            download.waitForCompletion();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info("{}にファイルを保存しました。", downloadPath.getAbsolutePath());
    }
    // download-end

    // download2-start
    public void downloadFileByStream(final String fileName) {

        final AmazonS3 client = transferManager.getAmazonS3Client();
        final S3Object s3Object = client.getObject(properties.getBucketName(), "upload/" + fileName);

        final File downloadPath = new File("download/pattern2/", fileName);
        try {
            // 出力先のOutputStreamにS3オブジェクトの内容を移送します。
            FileCopyUtils.copy(s3Object.getObjectContent(), new FileOutputStream(downloadPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("{}にファイルを保存しました。", downloadPath.getAbsolutePath());
    }
    // download2-end

    private boolean isPng(final Path path) {
        try (final ImageInputStream inputStream = ImageIO.createImageInputStream(Files.newInputStream(path))) {
            final Iterator<ImageReader> readers = ImageIO.getImageReaders(inputStream);
            if (readers.hasNext()) {
                return readers.next()
                              .getFormatName()
                              .equals("png");
            } else {
                return false;
            }
        } catch (IOException e) {
            return false;
        }

    }

}
