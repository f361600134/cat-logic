<#--根据数据 生成*.proto文件-->
// 如果使用此注释，则使用proto3; 否则使用proto2
syntax = "proto3";

// 生成包名（服务器用）
option java_package = "${javaProtocolPackage}";
// 生成类名（服务器用）
option java_outer_classname = "${javaProtoClazzName}";
<#-- 引用 -->
<#if importProtoFiles??&&(importProtoFiles?size > 0)>
// 引用列表
<#list importProtoFiles as importFile>
<#if importFile??>
import "${importFile}";
</#if>
</#list>
</#if>
// 客户端要求配置
package ${protoPackage};

// 协议列表
<#list protocols as protocol>
// ${protocol.comment}		msgId=#{id*1000+protocol.insideId}
message ${protocol.protocolName}{
	<#-- 参数列表 -->
	<#if protocol.fields??>
	<#list protocol.fields as field>
	<#if field.repeated==true>repeated </#if><#if field.getTypeImportProtoPackage(version)??&&field.getTypeImportProtoPackage(version)!=protoPackage>${field.getTypeImportProtoPackage(version)}.</#if>${field.protocolType} ${field.name} = ${field.index};<#if field.comment??>	//${field.comment}</#if>
	</#list>
	</#if>
}

</#list>