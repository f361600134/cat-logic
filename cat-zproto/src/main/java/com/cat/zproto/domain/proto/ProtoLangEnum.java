package com.cat.zproto.domain.proto;

public enum ProtoLangEnum {
	
	JAVA("java", "--java_out"),
	CSHARP("csharp", "--csharp_out"),
	CPP("cpp", "--cpp_out"),
	JS("js", "--cpp_out"),
	;
	
	private String language;
	private String flag;
	
	private ProtoLangEnum(String language, String flag) {
		this.language = language;
		this.flag = flag;
	}

	public String getLanguage() {
		return language;
	}

	public String getFlag() {
		return flag;
	}
	
}
