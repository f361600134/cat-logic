1.使用前，需先修改export.bat中的配置

2.整个ProtobufTool文件夹的路径不带中文或空格

3.使用文本工具或Notepad打开bat，不要用写字板，会修改掉bat文件的编码

4.修改 proto_path 变量为本地proto文件所存放的文件夹路径，默认值为 ProtobufTool/proto

5.修改 java_out 为导出的java文件所在目录，可以直接指向工程或svn目录

6.修改 proto_out 为输出的proto文件目录，将会把proto_path下的所有proto文件复制到proto_out

7.修改 csharp_out 为导出的cs文件所在目录，可以直接指向工程或svn目录

8.修改 csharp_ns 为默认cs文件命名空间

9.如果不需要拷贝proto文件，请注释掉 xcopy /S/Y %proto_path% %proto_out% 行
