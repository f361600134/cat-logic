syntax = "proto3";
package Protocol;
option java_package = "${javaPath}";
option java_outer_classname = "${outClass}";

/*
*生成规则
*请求协议号=模块id*1000+请求协议号
*返回协议号=模块id*1000+100+返回协议号
*/
enum PBProtocol {
   DEFAULTID = 0;
<#if protoMap??>
   <#list protoMap?keys as moduleName>

   //${moduleName}
   <#list protoMap[moduleName].structures?values as struct>
   <#if protoIdMap[struct.name]??>
   ${struct.name}=${protoIdMap[struct.name]?c};
   </#if>
   </#list>
   </#list>
</#if>
}
