 package com.game.module.${module.name?lower_case}.persistence;


import java.util.Collection;
import com.game.persistence.AbstractCommonDataConverter;
import common.utils.buffer.BufferWrapper;
import common.utils.buffer.DefaultBufferWrapper;
import com.game.module.${module.name?lower_case}.structs.*;

/**
* ${entityName}BeanConverter
* @date ${.now?string("yyyy-MM-dd HH:mm:ss")}
*/
public class ${entity.entityName}BeanConverter extends AbstractCommonDataConverter<${entity.entityName}Bean>{

		private final static int VERSION_1 = 1;
		private final static int CUR_VERSION = VERSION_1;
		private static final ${entity.entityName}BeanConverter INSTANCE = new ${entity.entityName}BeanConverter();
	
		public static ${entity.entityName}BeanConverter getInstance() {
        return INSTANCE;
    }
		
  	private ${entity.entityName}BeanConverter() {
        super(${entity.entityName}Bean.class);
    }

		@Override
    protected byte[] toBytes(${entity.entityName}Bean bean) {
        return toBytes1(bean);
    }
    
    @Override
    protected ${entity.entityName}Bean fromBytes(long id, byte[] bytes) {
        ${entity.entityName}Bean bean = new ${entity.entityName}Bean();
        bean.setId(id);
        if (bytes == null || bytes.length <= 4) {
            return bean;
        }
        BufferWrapper buffer = new DefaultBufferWrapper(bytes);
        int version = buffer.readInt();
        if (version == VERSION_1) {
            fromBytes1(bean, buffer);
        } else {
            fromBytes1(bean, buffer);
        }
        return bean;
    }
    
    private byte[] toBytes1(${entity.entityName}Bean bean) {
        BufferWrapper buffer = new DefaultBufferWrapper();
        buffer.writeInt(CUR_VERSION);
        <#if entity.properties ? exists>
		<#list entity.properties as bean>
		<#if bean.keyword != "transient">
		<#if bean.type == 'String'>
		buffer.writeString(bean.get${bean.field?cap_first}());
		<#elseif bean.type == 'int' || bean.type == "Integer">
		buffer.writeInt(bean.get${bean.field?cap_first}());
		<#elseif bean.type == 'boolean' || bean.type == "Boolean">
		buffer.writeBoolean(bean.get${bean.field?cap_first}());
		<#elseif bean.type == 'double' || bean.type == "Double">
		buffer.writeDouble(bean.get${bean.field?cap_first}());
		<#elseif bean.type == 'float' || bean.type == "Float">
		buffer.writeFloat(bean.get${bean.field?cap_first}());
		<#elseif bean.type == 'long' || bean.type == "Long">
		buffer.writeLong(bean.get${bean.field?cap_first}());
		<#elseif bean.type == 'short' || bean.type == "Short">
		buffer.writeShort(bean.get${bean.field?cap_first}());
		<#elseif bean.type ? contains('List<Long>') || bean.type ? contains('Set<Long>')>
		buffer.writeLongs(bean.get${bean.field?cap_first}());
		<#elseif bean.type ? contains('List<Integer>') || bean.type ? contains('Set<Integer>')>
		buffer.writeInts(bean.get${bean.field?cap_first}());
		<#elseif bean.type ? contains('List<String>') || bean.type ? contains('Set<String>')>
		buffer.writeStrings(bean.get${bean.field?cap_first}());
		<#elseif bean.type ? contains('List<') || bean.type ? contains('Set<')>
		<#-- 复杂类型的List-->
		buffer.writeDatas(bean.get${bean.field?cap_first}());
		<#elseif bean.type ? contains('Map<Integer, Integer>')>
		buffer.writeIIMap(bean.get${bean.field?cap_first}());
		<#elseif bean.type ? contains('Map<Integer, Long>')>
		buffer.writeILMap(bean.get${bean.field?cap_first}());
		<#elseif bean.type ? contains('Map<Long, Integer>')>
		buffer.writeLIMap(bean.get${bean.field?cap_first}());
		<#elseif bean.type ? contains('Map<Long, Long>')>
		buffer.writeLLMap(bean.get${bean.field?cap_first}());
		<#elseif bean.type ? contains('Map<')>
		<#-- 复杂类型的Map-->
		buffer.writeDatas(bean.get${bean.field?cap_first}().values());
		<#else>
		buffer.writeUnSupportObject(bean.get${bean.field?cap_first}());
		</#if>
		</#if>
        </#list>
        </#if>
        return buffer.getData();
    }
    
	private void fromBytes1(${entity.entityName}Bean bean, BufferWrapper buffer) {
		<#if entity.properties ? exists>
		<#list entity.properties as bean>
		<#if bean.keyword != "transient">
		<#if bean.type == 'String'>
		bean.set${bean.field?cap_first}(buffer.readString());
		<#elseif bean.type == 'int' || bean.type == "Integer">
		bean.set${bean.field?cap_first}(buffer.readInt());
		<#elseif bean.type == 'boolean' || bean.type == "Boolean">
		bean.set${bean.field?cap_first}(buffer.readBoolean());
		<#elseif bean.type == 'double' || bean.type == "Double">
		bean.set${bean.field?cap_first}(buffer.readDouble());
		<#elseif bean.type == 'float' || bean.type == "Float">
		bean.set${bean.field?cap_first}(buffer.readFloat());
		<#elseif bean.type == 'long' || bean.type == "Long">
		bean.set${bean.field?cap_first}(buffer.readLong());
		<#elseif bean.type == 'short' || bean.type == "Short">
		bean.set${bean.field?cap_first}(buffer.readShort());
		<#elseif bean.type ? contains('List<Long>') || bean.type ? contains('Set<Long>')>
		bean.get${bean.field?cap_first}().addAll(buffer.readLongs());
		<#elseif bean.type ? contains('List<Integer>') || bean.type ? contains('Set<Integer>')>
		bean.get${bean.field?cap_first}().addAll(buffer.readInts());
		<#elseif bean.type ? contains('List<String>') || bean.type ? contains('Set<String>')>
		bean.get${bean.field?cap_first}().addAll(buffer.readStrings());
		<#elseif bean.type ? contains('List<') || bean.type ? contains('Set<')>
		<#-- 复杂类型的List, Set-->
		bean.get${bean.field?cap_first}().putAll(buffer.readDatas(${bean.genericType}.class));
		<#elseif bean.type ? contains('Map<Integer, Integer>')>
		bean.get${bean.field?cap_first}().putAll(buffer.readIIMap());
		<#elseif bean.type ? contains('Map<Integer, Long>')>
		bean.get${bean.field?cap_first}().putAll(buffer.readILMap());
		<#elseif bean.type ? contains('Map<Long, Integer>')>
		bean.get${bean.field?cap_first}().putAll(buffer.readLIMap());
		<#elseif bean.type ? contains('Map<Long, Long>')>
		bean.get${bean.field?cap_first}().putAll(buffer.readLLMap());
		<#elseif bean.type ? contains('Map<')>
		<#-- 复杂类型的Map-->
		Collection<${bean.genericType}> ${bean.genericType?uncap_first}s = buffer.readDatas(${bean.genericType}.class);
		${bean.genericType?uncap_first}s.forEach(m -> bean.get${bean.field?cap_first}().put(m.getIIIIIId(), m));
		<#else>
		buffer.writeUnSupportObject(bean.get${bean.field?cap_first}());
		</#if>
		</#if>
        </#list>
        </#if>
	}
    
}
