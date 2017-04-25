# soundmail

接入该库主要是让你的项目有自动发送邮件的功能

接入方式很简单：

1、在项目根目下的 build.gradle 文件中，找到 allprojects/repositories 节点加入：maven { url 'https://jitpack.io' }

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
2、在项目的 app/build.gradle 文件中，找到 dependencies 节点加入：compile 'com.github.SoundChina:soundmail:2.0'
  
	  dependencies {
			compile 'com.github.SoundChina:soundmail:2.0'
		}

完成接入！

在代码中调用方法发送邮件：

	new Thread(new NewMail("sounddashi@163.com",    //收件人邮箱
		  "邮件标题",                         //邮件标题
		  "邮件内容",                         //邮件内容
		  new ArrayList<String>(),              //邮件附件名称
		  new ArrayList<String>())              //附件路径（如：sd/img/test.png）
		  ).start();  
          

--------------------------------------------------------------------------------------------------

注意：只测试了
华为：          HUAWEI GRA-CLOO手机 <br> 
系统版本：       EMUI 系统 4.0.2  <br> 
android 版本：  6.0 <br> 
