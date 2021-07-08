package com.cat.zproto.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(StringUtil.class);
	
	private final static String UNDERLINE = "_";
	
	

	/**
	 * 将字符串首字符小写
	 * 
	 * @param str
	 *            源字符串
	 * @return 首字符大写后的字符串
	 * @see [类、类#方法、类#成员]
	 */
	public static String firstCharLower(String str) {
		char ch = str.charAt(0);
		if (Character.isLowerCase(ch)) {
			return str;
		}
		char[] cs = str.toCharArray();
		cs[0] += 32;
		return String.valueOf(cs);
	}

	/**
	 * 将字符串首字符大写
	 * 
	 * @param str
	 *            源字符串
	 * @return 首字符大写后的字符串
	 * @see [类、类#方法、类#成员]
	 */
	public static String firstCharUpper(String str) {
		if (Character.isUpperCase(str.charAt(0))) {
			return str;
		}
		char[] cs = str.toCharArray();
		cs[0] -= 32;
		return String.valueOf(cs);
	}

	/**
	 * 下划线风格转小写驼峰
	 */
	public static String underlineToLowerCamal(String s) {
		String[] ss = s.split("_");
		for (int i = 1; i < ss.length; i++) {
			ss[i] = firstCharLower(ss[i]);
		}
		return join("", ss);
	}
	
	/**
	 * 下划线风格转大写驼峰
	 */
	public static String split(String str) {
		str = str.replace("_", "");
		return firstCharUpper(str);
	}

	/**
	 * 下划线风格转大写驼峰
	 */
	public static String underlineToUpperCamal(String s) {
		String[] ss = s.split("_");
		for (int i = 0; i < ss.length; i++) {
			ss[i] = firstCharUpper(ss[i]);
		}
		return join("", ss);
	}

	/**
	 * 连接数组
	 * 
	 * @param s
	 *            分隔符
	 * @param objects
	 */
	public static String join(String s, String... objects) {
		if (objects.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder(objects[0].toString());
		for (int i = 1; i < objects.length; i++) {
			sb.append(s).append(objects[i]);
		}
		return sb.toString();
	}
	
	/**
	 * 判断是否为Java基础类型
	 * @param type
	 * @return  
	 * @return String  
	 * @date 2019年9月17日下午4:50:53
	 */
	public static boolean isJavaType(String type){
		if(type.equals("long")){
			return true;
		}
		else if(type.equals("int")){
			return true;
		}
		else if (type.equals("short")) {
			return true;
		}
		else if(type.equals("float")){
			return true;
		}
		else if(type.equals("double")){
			return true;
		}
		else if(type.contains("String")){
			return true;
		}
		else if(type.contains("byte[]")){
			return true;
		}
		else if (type.contains("boolean")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据protp类型获取Java基础类型
	 * @param type
	 * @return  
	 * @return String  
	 * @date 2019年9月17日下午4:50:53
	 */
	public static String protoTypeToJavaType(String type){
		if(type.equals("int64") || type.equals("uint64") ||  type.equals("sint64") || type.equals("fixed64") || type.equals("sfixed64")){
			return "long";
		}
		else if(type.equals("int32") || type.equals("uint32") || type.equals("sint32") || type.equals("fixed32") || type.equals("sfixed32")){
			return "int";
		}
		else if(type.equals("float")){
			return "float";
		}
		else if(type.equals("double")){
			return "double";
		}
		else if(type.equals("string")){
			return "String";
		}
		else if (type.contains("bool")) {
			return "boolean";
		}
		else {
			return type;
		}
		// 支持了绝大部分proto类型
		//throw new RuntimeException("unsupported type = " + type);
	}

	/**
	 * 根据sql类型获取Java基础类型
	 * @param type
	 * @return  
	 * @return String  
	 * @date 2019年9月17日下午4:50:53
	 */
	public static String sqlTypeToJavaType(String type){
		if(type.equals("BIGINT") || type.equals("BIGINT UNSIGNED")){
			return "long";
		}
		else if(type.equals("INT") || type.equals("INT UNSIGNED")){
			return "int";
		}
		else if (type.equals("SMALLINT") || type.equals("SMALLINT UNSIGNED")) {
			return "short";
		}
		else if (type.equals("TINYINT") || type.equals("TINYINT UNSIGNED")) {
			return "byte";
		}
		else if(type.equals("FLOAT") || type.equals("FLOAT UNSIGNED")){
			return "float";
		}
		else if(type.equals("DOUBLE") || type.equals("DOUBLE UNSIGNED")){
			return "double";
		}
		else if(type.contains("CHAR") || type.contains("TEXT")){
			return "String";
		}
		else if(type.contains("BINARY") || type.contains("BLOB")){
			return "byte[]";
		}
		else if(type.contains("DATE") || type.contains("TIME")){
			return "java.util.Date";
		}
		else if (type.contains("BIT")) {
			return "boolean";
		}
		else if(type.contains("DOUBLE UNSIGNED")){
			return "Object";
		}
		// 支持了绝大部分sql类型
		throw new RuntimeException("unsupported type = " + type);
	}
	
	/**
	 * sql类型转NOSQL可支持的Java包装类型
	 * @param type
	 * @return  
	 * @return String  
	 * @date 2019年9月17日下午5:03:01
	 */
	public static String sqlTypeToWrapperType(String type){
		if(type.equals("BIGINT") || type.equals("BIGINT UNSIGNED")){
			return "java.lang.Long";
		}
		else if(type.contains("INT") || type.equals("INT UNSIGNED")){
			return "java.lang.Integer";
		}
		else if (type.equals("SMALLINT") || type.equals("SMALLINT UNSIGNED")) {
			return "java.lang.Short";
		}
		else if(type.equals("FLOAT") || type.equals("FLOAT UNSIGNED")){
			return "java.lang.Float";
		}
		else if(type.equals("DOUBLE") || type.equals("DOUBLE UNSIGNED")){
			return "java.lang.Float";
		}
		else if(type.contains("CHAR") || type.contains("TEXT")){
			return "java.lang.String";
		}
		else if(type.contains("BINARY") || type.contains("BLOB")){
			return "byte[]";
		}
		else if(type.contains("DATE") || type.contains("TIME")){
			return "java.util.Date";
		}
		else if (type.contains("BIT")) {
			return "java.lang.Boolean";
		}
		else if(type.contains("DOUBLE UNSIGNED")){
			return "java.lang.Object";
		}
		// 支持了绝大部分sql类型
		throw new RuntimeException("unsupported type = " + type);
	}
	
//	/**
//	 * sql类型转包装类型
//	 * @param type
//	 * @return  
//	 * @return String  
//	 * @date 2019年9月17日下午5:03:01
//	 */
//	public static String sqlTypeToJavaWrapperType(String type){
//		if(type.equals("BIGINT")){
//			return "java.lang.Long";
//		}
//		if(type.equals("SMALLINT")){
//			return "java.lang.Short";
//		}
//		if(type.contains("INT")){
//			return "java.lang.Integer";
//		}
//		if(type.equals("FLOAT")){
//			return "java.lang.Float";
//		}
//		if(type.equals("DOUBLE")){
//			return "java.lang.Float";
//		}
//		if(type.contains("CHAR") || type.contains("TEXT")){
//			return "java.lang.String";
//		}
//		if(type.contains("BINARY") || type.contains("BLOB")){
//			return "byte[]";
//		}
//		if(type.contains("DATE") || type.contains("TIME")){
//			return "java.util.Date";
//		}
//		if (type.contains("BIT")) {
//			return "java.lang.Boolean";
//		}
//		// 支持了绝大部分sql类型
//		throw new RuntimeException("unsupported type = " + type);
//	}


//	/**
//	 * 根据基础类型, 获得包装类型
//	 * @param type
//	 * @return
//	 * @return String
//	 * @date 2019年9月17日下午4:46:23
//	 */
//	public static String getWrapper(String type) {
//		if (type.equals("long")) {
//			 return "java.lang.Long";
//		} else if (type.equals("short")) {
//			 return "java.lang.Short";
//		} else if (type.contains("int")) {
//			 return "java.lang.Integer";
//		} else if (type.equals("float")) {
//			 return "java.lang.Float";
//		} else if (type.equals("float")) {
//			 return "java.lang.Float";
//		} else if (type.contains("boolean")) {
//			 return "java.lang.Boolean";
//		}else {
//			return type;
//		}
//	}

	// /***
	// * 下划线命名转为驼峰命名
	// *
	// * @param para
	// * 下划线命名的字符串
	// */
	// public static String underlineToHump(String para, boolean isFirstUpper) {
	// String ret = underlineToHump(para);
	// if (isFirstUpper)
	// return firstCharUpper(ret);
	// return ret;
	// }
	//
	// /***
	// * 下划线命名转为驼峰命名
	// *
	// * @param para
	// * 下划线命名的字符串
	// */
	// public static String underlineToHump(String para) {
	// StringBuilder result = new StringBuilder();
	// String a[] = para.split(UNDERLINE);
	// for (String s : a) {
	// if (!para.contains(UNDERLINE)) {
	// result.append(s);
	// continue;
	// }
	// if (result.length() == 0) {
	// result.append(s.toLowerCase());
	// } else {
	// result.append(s.substring(0, 1).toUpperCase());
	// result.append(s.substring(1).toLowerCase());
	// }
	// }
	// return result.toString();
	// }
	//
	// /***
	// * 驼峰命名转为下划线命名
	// *
	// * @param para
	// * 驼峰命名的字符串
	// */
	// public static String humpToUnderline(String para) {
	// StringBuilder sb = new StringBuilder(para);
	// int temp = 0;//定位
	// if (!para.contains(UNDERLINE)) {
	// for (int i = 0; i < para.length(); i++) {
	// if (Character.isUpperCase(para.charAt(i))) {
	// sb.insert(i + temp, UNDERLINE);
	// temp += 1;
	// }
	// }
	// }
	// return sb.toString().toLowerCase();
	// }

//	/**
//     * 检验手机号
//     * @param phone
//     * @return
//     */
//    public static boolean isPhone(String phone){
//        phone = isNull(phone);
//        Pattern regex = Pattern
////                .compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
//                .compile("^((1[34578]{1}[0-9]))\\d{8}$");
//        Matcher matcher = regex.matcher(phone);
//        boolean isMatched = matcher.matches();
//        return isMatched;
//    }
//    
//    /**
//     * 检查是否全中文，返回true 表示是 反之为否
//     * @param realname
//     * @return
//     */
//    public static boolean isChinese(String realname){
//        realname = isNull(realname);
//        Pattern regex = Pattern.compile("[\\u4e00-\\u9fa5]{2,25}");
//        Matcher matcher = regex.matcher(realname);
//        boolean isMatched = matcher.matches();
//        return isMatched;
//    }
//    
//    /**
//     * 检查email是否是邮箱格式，返回true表示是，反之为否
//     * @param email
//     * @return
//     */
//    public static boolean isEmail(String email) {
//        email = isNull(email);
//        Pattern regex = Pattern
//                .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
//        Matcher matcher = regex.matcher(email);
//        boolean isMatched = matcher.matches();
//        return isMatched;
//    }
    
    
//    /**
//     * 检查身份证的格式，返回true表示是，反之为否
//     * @param email
//     * @return
//     */
//    public static boolean isCard(String cardId) {
//        cardId = isNull(cardId);
//        //身份证正则表达式(15位) 
//        Pattern isIDCard1=Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$"); 
//        //身份证正则表达式(18位) 
//        Pattern isIDCard2=Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$"); 
//        Matcher matcher1= isIDCard1.matcher(cardId);
//        Matcher matcher2= isIDCard2.matcher(cardId);
//        boolean isMatched = matcher1.matches()||matcher2.matches();
//        return isMatched;
//    }
    

//    /**
//     * 判断字符串是否为整数
//     * @param str
//     * @return
//     */
//    public static boolean isInteger(String str) {
//        if (isEmpty(str)) {
//            return false;
//        }
//        Pattern regex = Pattern.compile("\\d*");
//        Matcher matcher = regex.matcher(str);
//        boolean isMatched = matcher.matches();
//        return isMatched;
//    }
    

//    /**
//     * 判断字符串是否为数字
//     * @param str
//     * @return
//     */
//    public static boolean isNumber(String str) {
//        if (isEmpty(str)) {
//            return false;
//        }
//        Pattern regex = Pattern.compile("(-)?\\d*(.\\d*)?");
//        Matcher matcher = regex.matcher(str);
//        boolean isMatched = matcher.matches();
//        return isMatched;
//    }
    
    
    /**
     * 判断字符串是否为纯字母
     * @param str
     * @return
     */
    public static boolean isEnglish(String str) {
        if (org.apache.commons.lang3.StringUtils.isBlank(str)) {
            return false;
        }
        Pattern regex = Pattern.compile("[a-zA-Z]{1,}(.\\d*)");
        Matcher matcher = regex.matcher(str);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

//    /**
//     * 根据身份证计算性别
//     * @param cardId
//     * @return
//     */
//    public static int getSexByCardid(String cardId) {
//        /*String sexNum = "";
//        if (cardId.length() == 15) {
//            sexNum = cardId.substring(13, 14);
//        } else {
//            sexNum = cardId.substring(16, 17);
//        }
//        
//        if (Integer.parseInt(sexNum) % 2 == 1) {
//            return 1;
//        } else {
//            return 0;
//        }*/
//        int sexNum = 0;
//        if (cardId.length() == 15) {
//            sexNum = cardId.charAt(13);
//        } else {
//            sexNum = cardId.charAt(16);
//        }
//        if (sexNum % 2 == 1) {
//            return 1;
//        } else {
//            return 0;
//        }
//    }


//    /**
//     * 根据身份证计算生日
//     * @param cardId
//     * @return
//     */
//    public static String getBirthdayByCardid(String cardId) {
//        String birth = null;
//        if (cardId.length() == 15) {
//            birth = cardId.substring(6, 12);
//        } else {
//            birth = cardId.substring(6, 14);
//        }
//        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
//        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
//        String birthday = null;
//        try {
//            birthday = sf2.format(sf1.parse(birth));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return birthday;
//    }
	
}
