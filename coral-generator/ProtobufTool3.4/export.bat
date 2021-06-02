@echo off
set dir_path=%~dp0
rem proto文件的文件夹路径
set proto_path=%dir_path%proto
rem 导出JAVA文件的文件夹路径
::set java_out=%dir_path%java
set java_out=%dir_path%java
rem 拷贝proto文件到指定的文件夹
set proto_out="backup"

rem 导出C#文件的文件路径
::set csharp_out=%dir_path%csharp
set csharp_out=%dir_path%csharp
rem C#命名空间
set csharp_ns=ProtoVO


echo #################################
echo ## dir_path 	= %dir_path%	
echo ## proto_path 	= %proto_path%	
echo ## java_out	= %java_out%	
echo ## csharp_out	= %csharp_out%
echo ## csharp_ns	= %csharp_ns%
echo ## proto_out	= %proto_out%
echo #################################

for /R %proto_path% %%i in (*.proto) do (
   echo gen:%%i
   CALL "bin/protoc.exe" --java_out=%java_out% --proto_path=%proto_path% %%i
   CALL "bin/protoc.exe" --csharp_out=%csharp_out% --proto_path=%proto_path% %%i
)



pause;