package com.cat.zson.param;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.cat.zson.common.Config;
import com.cat.zson.exception.ParamParseException;
import com.cat.zson.util.JsonUtil;

public enum ParamTypes implements ParamType {
    /**
     * 不需要解析<br>
     * 用于不进行输出的参数
     */
    NULL(new String[] { "-", "none", "no" }, 0, "") {
        @Override
        public Object parseValue0(String param) {
            return StringUtils.EMPTY;
        }
    },
    BOOLEAN(new String[] { "bool", "boolean" }, false, "boolean") {
        @Override
        public Object parseValue0(String param) {
            String tmpValue = param.toLowerCase();
            // 兼容策划乱填
            if (StringUtils.equals("n", tmpValue)//
                    || StringUtils.equals("no", tmpValue)//
                    || StringUtils.equals("f", tmpValue)//
                    || StringUtils.equals("0", tmpValue)//
            ) {
                return false;
            }
            if (StringUtils.equals("y", tmpValue)//
                    || StringUtils.equals("yes", tmpValue)//
                    || StringUtils.equals("t", tmpValue)//
                    || StringUtils.equals("1", tmpValue)//
            ) {
                return true;
            }
            return Boolean.parseBoolean(tmpValue);
        }

    },
    /**
     * int类型
     */
    INT(new String[] { "int", "num", "int32" }, 0, "int") {
        @Override
        public Object parseValue0(String param) {
            return Integer.parseInt(param);
        }

    },
    /**
     * 长整形
     */
    LONG(new String[] { "long", "bignum", "bigint", "int64" }, 0, "long") {
        @Override
        public Object parseValue0(String param) {
            return Long.parseLong(param);
        }

    },
    /**
     * string类型
     */
    STRING(new String[] { "str", "string", "varchar" }, "", "String") {
        @Override
        public Object parseValue0(String param) {
            return param;
        }

    },
    /**
     * 数值一维数组
     */
    NUM_ARRAY(new String[] { "intarray", "int[]", "num[]" }, new int[0], "int[]") {
        @Override
        public Object parseValue0(String param) throws ParamParseException {
            // 强行兼容， ;
            if (param.indexOf("，") >= 0 || param.indexOf(";") >= 0) {
                logger.warn("错误的数字1维数组: {}", param);
                param = param.replaceAll("，", ",");
                param = param.replaceAll(";", ",");
            }
            if (param.indexOf("[") < 0) {
                // 1,3,5
                String[] strings = param.split(",");
                int[] result = new int[strings.length];
                for (int i = 0; i < strings.length; i++) {
                    result[i] = Integer.parseInt(strings[i]);
                }
                return result;
            }
            try {
                // [1,3,5]
                int[] value = JsonUtil.toObject(param, int[].class);
                return value;
            } catch (Exception e) {
                throw new ParamParseException();
            }
        }

    },
    /**
     * int二维数组
     */
    NUM_DIMENSIONAL_ARRAY(new String[] { "int2array", "num2array", "int[][]", "num[][]" }, new int[0][], "int[][]") {
        @Override
        public Object parseValue0(String param) throws ParamParseException {
            try {
                // 强行兼容， ;
                if (param.indexOf("，") >= 0 || param.indexOf(";") >= 0) {
                    logger.warn("错误的数字2维数组: {}", param);
                    param = param.replaceAll("，", ",");
                    param = param.replaceAll(";", ",");
                }
                // [[1,3,5],[3,6]]
                int[][] value = JsonUtil.toObject(param, int[][].class);
                return value;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    },
    /**
     * 字符串数组类型
     */
    STRING_ARRAY(new String[] { "stringarray", "strarray", "str[]", "string[]" }, "", "String[]") {
        @Override
        public Object parseValue0(String param) {
            try {
                String[] value = JsonUtil.toObject(param, String[].class);
                return value;
            } catch (Exception e) {
                throw new ParamParseException();
            }
        }

    },

//    TIME_POINT(new String[] { "timepoint" }, "", "TimePoint", "com.game.common.utils.time.TimePoint") {
//        @Override
//        public Object parseValue0(String param) {
//            return param;
//        }
//
//    },
//    TIME_PERIOD(new String[] { "timeperiod" }, "", "TimePeriod", "com.game.common.utils.time.TimePeriod") {
//        @Override
//        public Object parseValue0(String param) {
//            return param;
//        }
//
//    },
//    RESOURCE(new String[] { "item", "resource" }, new long[0], "Resource", "com.game.module.resource.structs.Resource") {
//        @Override
//        public Object parseValue0(String param) {
//            // 强行兼容， ;
//            if (param.indexOf("，") >= 0 || param.indexOf(";") >= 0) {
//                logger.warn("错误的数字1维数组: {}", param);
//                param = param.replaceAll("，", ",");
//                param = param.replaceAll(";", ",");
//            }
//            if (param.indexOf("[") < 0) {
//                // 1,3,5
//                String[] strings = param.split(",");
//                long[] result = new long[strings.length];
//                for (int i = 0; i < strings.length; i++) {
//                    result[i] = Long.parseLong(strings[i]);
//                }
//                return result;
//            }
//            try {
//                // [1,3,5]
//                long[] value = JsonUtil.toObject(param, long[].class);
//                return value;
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//    },
//    RESOURCE_GROUP(new String[] { "itemlist", "resourcegroup", "itemgroup", "cost", "reward" }, new long[0][], "ResourceGroup",
//            "com.game.module.resource.structs.ResourceGroup") {
//        @Override
//        public Object parseValue0(String param) throws ParamParseException {
//            try {
//                // 强行兼容， ;
//                if (param.indexOf("，") >= 0 || param.indexOf(";") >= 0) {
//                    logger.warn("错误的数字2维数组: {}", param);
//                    param = param.replaceAll("，", ",");
//                    param = param.replaceAll(";", ",");
//                }
//                // [[1,3,5],[3,6]]
//                long[][] value = JsonUtil.toObject(param, long[][].class);
//                return value;
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//    },
//    ANY(new String[] { "any" }, "", "JsonNode", "com.fasterxml.jackson.databind.JsonNode") {
//        @Override
//        public Object parseValue0(String param) {
//            JsonNode jsonNode = null;
//            try {
//                jsonNode = mapper.readTree(param);
//                return jsonNode;
//            } catch (JsonProcessingException e) {
//                logger.error("config error");
//                throw new RuntimeException(e);
//            }
//        }
//    },
//    CONDITION(new String[] { "condition" }, "", "Condition", "com.game.common.condition.Condition") {
//        @Override
//        public Object parseValue0(String param) {
//            JsonNode jsonNode = null;
//            try {
//                jsonNode = mapper.readTree(param);
//                return jsonNode;
//            } catch (JsonProcessingException e) {
//                logger.error("config error");
//                throw new RuntimeException(e);
//            }
//        }
//    },
//    REWARD1(new String[] { "RewardMap", "CostMap" }, "", "RewardMap", "com.cat.server.game.module.resource.domain.RewardMap") {
//    	/**
//    	 * 格式: 1_1,2_2
//    	 */
//        @Override
//        public Object parseValue0(String param) {
//        	try {
//                if (param.indexOf("_") >= 0 && param.indexOf(",") >= 0) {
//                    String[] params = param.split(",");
//                    int length = params.length;
//                    int[][] arr = new int[length][2];
//                    for (int i = 0; i < length; i++) {
//                    	String str = params[i];
//                    	String [] strArr = str.split("_");
//                    	arr[i][0] = Integer.valueOf(strArr[0]);
//                    	arr[i][1] = Integer.valueOf(strArr[1]);
//					}
//                    return arr;
//                }
//                // [[1,3,5],[3,6]]
//                int[][] value = JsonUtil.toObject(param, int[][].class);
//                return value;
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//    },
    /**
     * 资源类型
     */
    RESOURCE_MAP(new String[] { "ResourceMap" }, "", "ResourceMap", "com.cat.server.game.module.resource.domain.ResourceMap") {
    	/**
    	 * 格式: 1_1,2_2
    	 * 格式:[[1,2],[1,2]]
    	 */
        @Override
        public Object parseValue0(String param) {
        	if (param.indexOf("_") >= 0 && param.indexOf(",") >= 0) {
                String[] params = param.split(",");
                int length = params.length;
                Map<Integer, Integer> arrMap = new HashMap<>();
                for (int i = 0; i < length; i++) {
                	String str = params[i];
                	String [] strArr = str.split("_");
                	arrMap.put(Integer.valueOf(strArr[0]), Integer.valueOf(strArr[1]));
    			}
                return arrMap;
        	}
        	if (param.indexOf("[") >= 0 && param.indexOf("]") >= 0) {
        		try {
        			int[][] value = JsonUtil.toObject(param, int[][].class);
            		Map<Integer, Integer> arrMap = new HashMap<>();
            		for (int i = 0; i < value.length; i++) {
            			int [] temArr = value[i];
            			if(temArr.length >= 2) {
            				arrMap.put(temArr[0], temArr[1]);
            			}
    				}
                    return arrMap;
				} catch (Exception e) {
					 throw new RuntimeException(e);
				}
        	}
        	return null;
        }
		
    },
    /**
     * 资源类型
     */
    MAP(new String[] { "Map","map" }, Collections.emptyMap(), "Map<Integer, Integer>", "java.util.Map") {
    	/**
    	 * 格式: 1_1,2_2
    	 * 格式:[[1,2],[1,2]]
    	 */
        @Override
        public Object parseValue0(String param) {
        	if (param.indexOf("_") >= 0 && param.indexOf(",") >= 0) {
                String[] params = param.split(",");
                int length = params.length;
                Map<Integer, Integer> arrMap = new HashMap<>();
                for (int i = 0; i < length; i++) {
                	String str = params[i];
                	String [] strArr = str.split("_");
                	arrMap.put(Integer.valueOf(strArr[0]), Integer.valueOf(strArr[1]));
    			}
                return arrMap;
        	}
        	if (param.indexOf("[") >= 0 && param.indexOf("]") >= 0) {
        		try {
        			int[][] value = JsonUtil.toObject(param, int[][].class);
            		Map<Integer, Integer> arrMap = new HashMap<>();
            		for (int i = 0; i < value.length; i++) {
            			int [] temArr = value[i];
            			if(temArr.length >= 2) {
            				arrMap.put(temArr[0], temArr[1]);
            			}
    				}
                    return arrMap;
				} catch (Exception e) {
					 throw new RuntimeException(e);
				}
        	}
        	return null;
        }
		
    },
    ;

    protected final Logger logger = LoggerFactory.getLogger(this.name());

    private final String[] names;
    private final Object defaultValue;
    private final String fieldClazz;
    private final String importClazz;

    private ParamTypes(String[] names, Object defaultValue, String fieldClazz) {
        this.names = names;
        this.defaultValue = defaultValue;
        this.fieldClazz = fieldClazz;
        this.importClazz = "";
    }

    private ParamTypes(String[] names, Object defaultValue, String fieldClazz, String importClazz) {
        this.names = names;
        this.defaultValue = defaultValue;
        this.fieldClazz = fieldClazz;
        this.importClazz = importClazz;
    }

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public String[] getNames() {
        return names;
    }

    @Override
    public Object defaultValue() {
        return defaultValue;
    }

    @Override
    public String getFieldClazz() {
        return fieldClazz;
    }

    @Override
    public String getImportClazz() {
        return importClazz;
    }
    
    public static ParamType getParamType(String typeName) {
        ParamTypes[] values = ParamTypes.values();
        for (ParamTypes tmp : values) {
            String[] names = tmp.getNames();
            for (String tmpName : names) {
                if (StringUtils.equalsIgnoreCase(typeName, tmpName)) {
                    return tmp;
                }
            }
        }
        return NULL;
    }
    
}
