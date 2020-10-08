java核心技术（第10版）学习  
[原书代码下载](https://horstmann.com/corejava/corejava10.zip)

---
#### 卷2 高级特性
- **第1章** [Java SE 8 的流库]()  
v2ch01 书籍配套代码  
**stream包**  
CountLongWords（1.1 从迭代到流的操作）  
CreatingStreams（1.2 流的创建）  
FilterMapTest（1.3 filter、map和flatMap方法）  
LimitStreamTest（1.4 抽取子流和连接流）  
OtherStreamConvert（1.5 其他的流转换）  
SimpleReduce（1.6 简单约简）  
**optional包**  
OptionalTest（1.7 Optional类型）  
**collecting包**  
OptionalTest（1.8 收集结果）  
CollectingIntoMaps（1.9 收集到映射表中）  
GroupByPartition（1.10 群组和分区）  
DownstreamCollectors（1.11 下游收集器）  
ReduceTest（1.12 约简操作）  
PrimitiveTypeStreams（1.13 基本类型流）  
ParallelStreams（1.14 并行流）  

- **第2章** [输入与输出]()  
v2ch02 书籍配套代码  
**randomAccess、randomAccess2包**  
RandomAccessTest、RandomAccessTest2（2.3.2 随机访问文件）  
**zip包（2.3.3 ZIP文档）**  
ZipTest（压缩单个文件到zip包中，查看zip包中的内容）  
ZipTest2（压缩多个文件文件到zip包中，查看zip包中的内容）  
ZipTest3（将目录压缩到zip包中，将zip包解压至指定位置，查看zip包中的内容）  
**objectStream包（2.4 对象输入/输出流与序列化）**  
ObjectStreamTest（2.4.1 保存和加载序列化对象）finish  
ModifySerialTest（2.4.3 修改默认的序列化机制）finish  
SerialSingletonTest（2.4.4 序列化单例和类型安全的枚举）finish  
**serialClone包**  
SerialCloneTest（2.4.6 为克隆使用序列化）  
**operateFile包**  
PathTest（2.5.1 Path）finish  
FilesTest（2.5.2 读写文件）  
CreateFileDirectoryTest（2.5.3 创建文件和目录）  
CopyDeleteFileTest（2.5.4 复制、移动和删除文件）  
FileInfoTest（2.5.5 获取文件信息）  
DirectoryItemTest（2.5.6 访问目录中的项）  
DirectoryStreamTest（2.5.7 使用目录流）这个后续有时间再看  
ZipFileSystemTest（2.5.8 ZIP文件系统）  
**memoryMap包**  
MemoryMapTest、MemoryMapTest2（2.6.1 内存映射文件的性能）  
2.7 正则表达式（暂时先跳过）  

- **第4章** [网络]()  
v2ch04 书籍配套代码  
**socket包**  
SocketTest（4.1.2 用Java连接到服务器；4.1.3 套接字超时；4.1.4 因特网地址）  
ThreadedEchoServer（4.2.2 为多个客户端服务）  
HalfCloseServer、HalfCloseClient（4.2.3 半关闭）  
InterruptibleSocketTest（4.3 可中断套接字）  
**webdata包**  
URLTest（4.4.1 URL和URI）  
URLConnectionTest（4.4.2 使用URLConnection获取信息）  
**post包**  
PostTest（4.4.3 提交表单数据 这个没验证成功，后续再弄）  
**mail包**  
MailTest（4.5 发送E-mail）  

- **第5章** [数据库编程]()  
v2ch05 书籍配套代码  
**test包**  
TestDB（5.3.5 连接到数据库）  
**exec包**  
ExecSQL（5.4.4 组装数据库）  
**query包**  
QueryTest（5.5.1 预备语句）  
LobTest（5.5.2 读写LOB字段）  
GetGeneratedKeys（5.5.5 获取自动生成的键）  
**resultset包**  
ScrollResultSet（5.6.1 可滚动的结果集）  
UpdatableResultSet（5.6.2 可更新的结果集）  
**view包**  
ViewDB（5.8 元数据）  
**transaction包**  
JDBCTransaction（5.9.1 用JDBC对事务编程）  
SavePoint（5.9.2 保存点）  
BatchUpdate（5.9.3 批量更新）  

- **第8章** [脚本、编译与注解处理]()  
v2ch08 书籍配套代码
**compiler、buttons2包**  
CompilerTest（8.2.3 动态Java代码生成）  
**buttons2_1包**  
Buttons2_1Test（为了便于理解8.2.3的内容，把不使用动态Java代码生成时的写法写了下）  
**mycompiler_test包**  
网上找了一些例子来辅助理解JavaCompiler的相关api  
JavaCompilerTest（test1~test5）  
**runtimeAnnotations、buttons3包**  
ButtonTest（8.3.2 一个示例：注解事件处理器）  
**buttons3_1包**  
Buttons3_1Test（8.3.2的辅助理解代码）  
**annotationGrammar包（8.4 注解语法）**  
BugReport（8.4.1 注解接口）  
AnnotationDeclaration（8.4.3 注解各类声明）  
**standardAnnotations包**  
TestCase（8.5.3 元注解）  
**sourceAnnotations、rect包**  
ToStringAnnotationProcessor、SourceLevelAnnotationDemo（8.6 源码级注解处理）  
**bytecodeAnnotations、set包**  
下面两节都是在操作Item类的字节码，8.7.1是直接操作类文件，8.7.2是在类加载时操作字节码  
如何测试：通过运行SetTest看是否打印了日志  
EntryLogger（8.7.1 修改类文件）  
EntryLoggingAgent、EntryLogger、EntryLoggingAgent.mf（8.7.2 在加载时修改字节码）  

- **第9章** [安全]()  
v2ch08 书籍配套代码
**classLoader包**  
ClassLoaderTest（9.1.4 编写你自己的类加载器 这个只是照着书里的代码敲了一遍，没有图9-3的效果）  
ClassLoader2Test（结合书籍中关于加密class文件的基本思路，去掉了JFrame相关东西，重新写了下，见test4）
**policyFile包**  
PolicyFileTest（9.2.3 安全策略文件）  
**permissions包**  
PermissionTest（9.2.5 实现权限类）  
**auth包**  
AuthTest（9.3.1 JAAS框架）  
JAASTest（9.3.2 JAAS登录模块 代码敲了一遍，但是图9-10的效果）  
**hash包**  
Digest（9.4.1 消息摘要）  
**aes包**  
AESTest（9.5.2 密钥生成）  
**rsa包**  
RSATest（9.5.4 公共密钥密码）  