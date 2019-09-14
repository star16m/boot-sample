package star16m.bootsample;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;
import org.mockftpserver.fake.filesystem.WindowsFakeFileSystem;
import star16m.bootsample.core.resource.ftp.FtpClient;

import java.io.*;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class FtpTest {

    private static final String TEST_FTP_USER = "ftp-test-user";
    private static final String TEST_FTP_PASS = "ftp-test-pass";
    private static final String TEST_FTP_HOME_DIR = "/Users/star16m/Downloads/fakeftpfiles";//System.getProperty("java.io.tmpdir");
    private FakeFtpServer fakeFtpServer;

    private FtpClient ftpClient;

    @BeforeEach
    public void setup() throws IOException {
        FileSystem fileSystem;
        boolean isWindow = System.getProperty("os.name").toLowerCase().contains("win");
        if (isWindow) {
            fileSystem = new WindowsFakeFileSystem();
        } else {
            fileSystem = new UnixFakeFileSystem();
        }
        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.addUserAccount(new UserAccount(TEST_FTP_USER, TEST_FTP_PASS, TEST_FTP_HOME_DIR));

        fileSystem.add(new DirectoryEntry("/Users/star16m/Downloads/fakeftpfiles"));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.setServerControlPort(0);

        fakeFtpServer.start();

        ftpClient = new FtpClient("localhost", fakeFtpServer.getServerControlPort(), TEST_FTP_USER, TEST_FTP_PASS);
        ftpClient.open();
    }

    @AfterEach
    public void tearDown() throws IOException {
        ftpClient.close();
        fakeFtpServer.stop();
    }

    @Test
    public void test업로드파일() throws IOException {
        File file = new File("/Users/star16m/Downloads/fakeftpfiles/haha.txt");
        ftpClient.putFileToPath(file, "/buz.txt");
        assertThat(fakeFtpServer.getFileSystem().exists("/buz.txt")).isTrue();
        assertThat(fakeFtpServer.getFileSystem().exists("/buz2.txt")).isFalse();
    }

    @Disabled
    @Test
    public void test다은이사진가져오기() throws Exception {
        String baseUrl = "http://stak.iptime.org/01033900282/23244-%EC%9D%B4%ED%98%84%EC%A0%95(%EA%B9%80%EB%8B%A4%EC%9D%80)-01-%EC%B4%AC%EC%98%81-50%EC%9D%BC/";
        URL base = new URL(baseUrl);
        Connection.Response response = Jsoup.connect(baseUrl)
                .method(Connection.Method.GET)
                .execute();
        Document doc = response.parse();
        AtomicInteger index = new AtomicInteger();
        doc.select("div.fileInfo a").stream().map(f->f.attr("href")).forEach(f -> {
            URL url = null;
            InputStream in = null;
            BufferedOutputStream out = null;
            byte[] buffer = new byte[4096];
            try {
                url = new URL(base, f);
                in = url.openConnection().getInputStream();
                out = new BufferedOutputStream(new FileOutputStream(new File(String.format("/Users/star16m/Downloads/de100-2/day100_%02d.jpg", index.incrementAndGet()))));
                int b;
                while ((b = in.read(buffer)) != -1) {
                    out.write(buffer, 0, b);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
    }
}
