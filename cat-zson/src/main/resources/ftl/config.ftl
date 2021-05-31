package ${javaPackageName};

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
 * 
 * @author auto gen
 *
 */
@ConfigPath("${name}.json")
public class ${javaClazzSimpleName} implements ${javaSuperSimpleClazz} {

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
    /**
     * get ${param.name}
     *
     * @return
     */
<#if param.key=='id'>
    @Override
</#if>
    public ${param.type.fieldClazz} get${param.key?cap_first}() {
        return this.${param.key};
    }

    /**
     * set ${param.name}
     *
     * @param ${param.key}
     */
    public void set${param.key?cap_first}(${param.type.fieldClazz} ${param.key}) {
        this.${param.key} = ${param.key};
    }

</#list>
</#if>
}
