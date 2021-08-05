package com.game.module.${module.name?lower_case}.structs;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.game.module.player.component.PlayerBean;
import common.persistence.annotation.DataConverter;
import common.persistence.annotation.TableName;
import com.game.module.${module.name?lower_case}.persistence.${entityName}BeanConverter;

${entity.entityBeans}

/**
* ${entityName}Bean
* @date ${.now?string("yyyy-MM-dd HH:mm:ss")}
*/
@TableName("${entity.tablName}")
@DataConverter(${entity.entityName}BeanConverter.class)
public class ${entity.entityName}Bean implements PlayerBean {

	<#-- filed -->
	<#if entity.properties ? exists>
	<#list entity.properties as prop>
	/**
	* ${prop.desc}
	*/
	<#if prop.type ? contains('Map') || prop.type ? contains('List') || prop.type ? contains('Set')>
	private ${prop.keyword} ${prop.type} ${prop.field} = ${prop.init};
	<#else>
	private ${prop.keyword} ${prop.type} ${prop.field};
	</#if>
	</#list>
	</#if>
    /**
     *boolean 发生改变
     */
    private transient boolean change;
	
	<#-- Constructor -->
	public ${entity.entityName}Bean(){
		<#--<#if entityBeans ? exists>
		<#list entityBeans as bean>
		<#if bean.type == 'String'>
		this.${bean.field} = "";
		<#elseif bean.type ? starts_with('Map')>
		this.${bean.field} = new HashMap<>();
		</#if>
		</#list>
		</#if>-->
	}
	
	<#-- Getter Setter-->
	<#if entity.properties ? exists>
	<#list entity.properties as prop>
	/** ${prop.desc} **/
	public ${prop.type} get${prop.field ? cap_first}(){
		return this.${prop.field};
	}
	<#if !bean.keyword?contains("final")>
	public void set${prop.field ? cap_first}(${prop.type} ${prop.field}){
		this.${prop.field} = ${prop.field};
	}
	</#if>
	</#list>
	</#if>
	
    @Override
    public boolean isChange() {
      return change;
  }

  @Override
  public void setChange(boolean change) {
      this.change = change;
  }
	
  @Override
  public String toString() {
		return ${entity.genToStr()}
	}
	
}
