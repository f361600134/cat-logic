@echo off
set dir_path=%~dp0
rem proto�ļ����ļ���·��
set proto_path=%dir_path%proto
rem ����JAVA�ļ����ļ���·��
::set java_out=%dir_path%java
set java_out=%dir_path%java
rem ����proto�ļ���ָ�����ļ���
set proto_out="backup"

rem ����C#�ļ����ļ�·��
::set csharp_out=%dir_path%csharp
set csharp_out=%dir_path%csharp
rem C#�����ռ�
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