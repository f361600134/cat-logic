package ${javaPackageName}.base;

<#if importJavaTypes??>
<#list importJavaTypes as importType>
<#if importType??>
import ${importType};
<#else>
</#if>
</#list>

</#if>

/**
 * ${fileName}<br>
 * ${name}.json<br>
 * @author auto gen
 */
public class ${javaClazzSimpleName}Base implements ${javaSuperSimpleClazz} {

<#if javaParams??>
<#list javaParams as param>
<#if param.comments??>
    /**
<#list param.comments as comment>
     * ${comment}
</#list>
     */
</#if>
    private ${param.type.fieldClazz} ${param.key};
</#list>

<#list javaParams as param>
    /** @return ${param.name}*/
<#if param.key=='id'>
    @Override
</#if>
    public ${param.type.fieldClazz} get${param.key?cap_first}() {
        return this.${param.key};
    }

    /** @param ${param.key} ${param.name}*/
    public void set${param.key?cap_first}(${param.type.fieldClazz} ${param.key}) {
        this.${param.key} = ${param.key};
    }

</#list>
</#if>
}
