package app.maillibrary;

import java.util.ArrayList;

/**
 * Created by sound on 2017/4/25.
 */

public class NewMail implements Runnable {
    private static final String mSender = "sounddashi@163.com";
    private static final String mSenderPass = "dashi88";
    private static final String HostName = "smtp.163.com";
    private static final String HostPort = "465";

    private String mReceiver,mTitle,mMsg;
    private ArrayList<String> mFileName,mFilePath;

    /**
     * 创建发送邮件
     * @param receiver      收件人
     * @param title         邮件标题
     * @param msg           邮件内容
     * @param fileName      附件名称
     * @param filePath      附件地址
     */
    public NewMail(String receiver,String title, String msg, ArrayList<String> fileName, ArrayList<String> filePath) {
        mReceiver = receiver;
        mTitle = title;
        mMsg = msg;
        mFileName = fileName;
        mFilePath = filePath;
    }
    @Override
    public void run() {
        MailUtils sender = new MailUtils().setUser(mSender).setPass(mSenderPass)
                .setFrom(mSender).setTo(mReceiver).setHost(HostName)
                .setPort(HostPort).setSubject(mTitle).setBody(mMsg);
        sender.init();
        try {
            for(int i=0;i<mFilePath.size();i++){
                sender.addAttachment(mFilePath.get(i),mFileName.get(i));//添加附件
            }
            sender.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

