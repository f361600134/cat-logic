<#--根据数据 生成*.proto文件-->
syntax = "proto3";
package Protocol;
option java_package = "${javaPath}";
option java_outer_classname = "${outClass}";

<#-- 引用 -->
<#if dependenceObjs??&&(dependenceObjs?size > 0)>
<#list dependenceObjs as dependanceObj>
<#if dependanceObj??>
import "${dependanceObj}.proto";
</#if>
</#list>
</#if>

<#list structureList as structure>
// ${structure.comment}
message ${structure.name}{
<#if structure.fields??>
<#list structure.fields as field>
    <#if field.repeated==true>repeated </#if>${field.type} ${field.name} = ${field.index};  //${field.comment}
</#list>
</#if>
}

</#list>