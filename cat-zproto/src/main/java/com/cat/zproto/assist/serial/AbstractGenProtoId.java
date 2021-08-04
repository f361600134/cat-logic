package com.cat.zproto.assist.serial;

import com.cat.zproto.domain.proto.ProtocolObject;
import com.cat.zproto.domain.proto.ProtocolStructure;
import com.cat.zproto.domain.system.SettingConfig;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractGenProtoId implements IGenProtoId{

    @Autowired
    SettingConfig setting;

    @Override
    public Map<String, Integer> genProtoIds(int moduleId, ProtocolObject protoObj) {
        Map<String, Integer> result = new LinkedHashMap<>();
        final String reqPrefix = setting.getProto().getReqPrefix();
        final String respPrefix = setting.getProto().getRespPrefix();

        List<ProtocolStructure> reqs = new ArrayList<>();
        Map<String, ProtocolStructure> resps = new LinkedHashMap<>();
        //筛选请求响应协议
        protoObj.getStructures().forEach((protoName, struct)->{
            if (protoName.startsWith(reqPrefix)) {
                reqs.add(struct);
            }
            else if (protoName.startsWith(respPrefix)) {
                resps.put(protoName, struct);
            }
        });
        return generater(moduleId, reqs, resps);
    }

    /**
     * 实际执行方法
     * @param moduleId 模块id
     * @param reqs 请求协议
     * @param resps 响应协议
     */
    public abstract Map<String, Integer> generater(int moduleId, List<ProtocolStructure> reqs, Map<String, ProtocolStructure> resps);

}
