package ${javaPackageName};

<#if importJavaTypes??>
<#list importJavaTypes as importType>
<#if importType??>
import ${importType};
<#else>
</#if>
</#list>
import com.cat.server.game.data.config.local.base.${javaClazzSimpleName}Base;

</#if>

/**
 * ${fileName}<br>
 * ${name}.json<br>
 * 
 * @author auto gen
 *
 */
@ConfigPath("${name}.json")
public class ${javaClazzSimpleName} extends ${javaClazzSimpleName}Base {

}
