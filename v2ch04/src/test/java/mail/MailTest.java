package mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

/**
 * 4.5 发送Email
 *
 * 使用JavaMail发送信息
 * Created by shucheng on 2020/9/27 10:09
 */
public class MailTest {

    public static void main(String[] args) throws URISyntaxException, IOException, MessagingException {
        /// 发邮件需要的相关信息
        Properties props = new Properties();
        URL mail = ClassLoader.getSystemResource("mail/mail.properties");

        Path mailPath = Paths.get(mail.toURI());
        try (InputStream in = Files.newInputStream(mailPath)) {
            props.load(in);
        }

        // 邮件内容
        String messageFileName;
        Path messagePath;
        if (args.length > 0) {
            messageFileName = args[0];
            messagePath = Paths.get(messageFileName);
        } else {
            URL message = ClassLoader.getSystemResource("mail/message.txt");
            messagePath = Paths.get(message.toURI());
        }
        List<String> lines = Files.readAllLines(messagePath, Charset.forName("UTF-8"));
        // 注意：from和mail.smtp.user必须一样，不然邮件发不出去
        // String from = lines.get(0);
        String from = "shucheng2015@outlook.com";
        String to = lines.get(1);
        String subject = lines.get(2);

        StringBuilder builder = new StringBuilder();
        for (int i = 3; i < lines.size(); i++) {
            builder.append(lines.get(i));
            builder.append("\n");
        }

        // 要运行的话，用maven时有点麻烦，java -classpath .;D:\\maven\Repository\javax\mail\javax.mail-api\1.6.2\javax.mail-api-1.6.2.jar;D:\readBookProjects\core-java-10\v2ch04\target\classes mail.MailTest
        // 贴jar的话，些的命令可能相对简单，因为只是测试，下面改成直接从配置文件里取
        // 下面是读取命令行里输入的密码
        /*Console console = System.console();
        String password = new String(console.readPassword("Password: "));*/
        String password = (String) props.remove("mail.password");

        Session mailSession = Session.getDefaultInstance(props);
        // mailSession.setDebug(true);
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setText(builder.toString());
        Transport tr = mailSession.getTransport();
        try {
            tr.connect(null, password);
            tr.sendMessage(message, message.getAllRecipients());
        } finally {
            tr.close();
        }
    }
}