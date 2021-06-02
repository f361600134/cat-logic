// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: PBFunction.proto
#pragma warning disable 1591, 0612, 3021
#region Designer generated code

using pb = global::Google.Protobuf;
using pbc = global::Google.Protobuf.Collections;
using scg = global::System.Collections.Generic;
namespace Protocol {

  #region Messages
  /// <summary>
  ///红点信息
  /// </summary>
  public sealed class RedInfo : pb::IMessage {
    private static readonly pb::MessageParser<RedInfo> _parser = new pb::MessageParser<RedInfo>(() => new RedInfo());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<RedInfo> Parser { get { return _parser; } }

    /// <summary>Field number for the "redID" field.</summary>
    public const int RedIDFieldNumber = 1;
    private int redID_;
    /// <summary>
    ///红点ID
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int RedID {
      get { return redID_; }
      set {
        redID_ = value;
      }
    }

    /// <summary>Field number for the "redNum" field.</summary>
    public const int RedNumFieldNumber = 2;
    private int redNum_;
    /// <summary>
    ///红点数量
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int RedNum {
      get { return redNum_; }
      set {
        redNum_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (RedID != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(RedID);
      }
      if (RedNum != 0) {
        output.WriteRawTag(16);
        output.WriteInt32(RedNum);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (RedID != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(RedID);
      }
      if (RedNum != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(RedNum);
      }
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(pb::CodedInputStream input) {
      uint tag;
      while ((tag = input.ReadTag()) != 0) {
        switch(tag) {
          default:
            input.SkipLastField();
            break;
          case 8: {
            RedID = input.ReadInt32();
            break;
          }
          case 16: {
            RedNum = input.ReadInt32();
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///功能模块信息
  /// </summary>
  public sealed class FunctionInfo : pb::IMessage {
    private static readonly pb::MessageParser<FunctionInfo> _parser = new pb::MessageParser<FunctionInfo>(() => new FunctionInfo());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<FunctionInfo> Parser { get { return _parser; } }

    /// <summary>Field number for the "configId" field.</summary>
    public const int ConfigIdFieldNumber = 1;
    private int configId_;
    /// <summary>
    ///配置ID
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int ConfigId {
      get { return configId_; }
      set {
        configId_ = value;
      }
    }

    /// <summary>Field number for the "isOpen" field.</summary>
    public const int IsOpenFieldNumber = 2;
    private int isOpen_;
    /// <summary>
    ///是否解锁0=否,1=是
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int IsOpen {
      get { return isOpen_; }
      set {
        isOpen_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (ConfigId != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(ConfigId);
      }
      if (IsOpen != 0) {
        output.WriteRawTag(16);
        output.WriteInt32(IsOpen);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (ConfigId != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(ConfigId);
      }
      if (IsOpen != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(IsOpen);
      }
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(pb::CodedInputStream input) {
      uint tag;
      while ((tag = input.ReadTag()) != 0) {
        switch(tag) {
          default:
            input.SkipLastField();
            break;
          case 8: {
            ConfigId = input.ReadInt32();
            break;
          }
          case 16: {
            IsOpen = input.ReadInt32();
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///返回功能列表
  /// </summary>
  public sealed class AckFunction : pb::IMessage {
    private static readonly pb::MessageParser<AckFunction> _parser = new pb::MessageParser<AckFunction>(() => new AckFunction());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<AckFunction> Parser { get { return _parser; } }

    /// <summary>Field number for the "functions" field.</summary>
    public const int FunctionsFieldNumber = 1;
    private static readonly pb::FieldCodec<global::Protocol.FunctionInfo> _repeated_functions_codec
        = pb::FieldCodec.ForMessage(10, global::Protocol.FunctionInfo.Parser);
    private readonly pbc::RepeatedField<global::Protocol.FunctionInfo> functions_ = new pbc::RepeatedField<global::Protocol.FunctionInfo>();
    /// <summary>
    ///功能信息
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public pbc::RepeatedField<global::Protocol.FunctionInfo> Functions {
      get { return functions_; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      functions_.WriteTo(output, _repeated_functions_codec);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      size += functions_.CalculateSize(_repeated_functions_codec);
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(pb::CodedInputStream input) {
      uint tag;
      while ((tag = input.ReadTag()) != 0) {
        switch(tag) {
          default:
            input.SkipLastField();
            break;
          case 10: {
            functions_.AddEntriesFrom(input, _repeated_functions_codec);
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///红点信息
  /// </summary>
  public sealed class AckRedList : pb::IMessage {
    private static readonly pb::MessageParser<AckRedList> _parser = new pb::MessageParser<AckRedList>(() => new AckRedList());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<AckRedList> Parser { get { return _parser; } }

    /// <summary>Field number for the "reds" field.</summary>
    public const int RedsFieldNumber = 1;
    private static readonly pb::FieldCodec<global::Protocol.RedInfo> _repeated_reds_codec
        = pb::FieldCodec.ForMessage(10, global::Protocol.RedInfo.Parser);
    private readonly pbc::RepeatedField<global::Protocol.RedInfo> reds_ = new pbc::RepeatedField<global::Protocol.RedInfo>();
    /// <summary>
    ///红点信息
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public pbc::RepeatedField<global::Protocol.RedInfo> Reds {
      get { return reds_; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      reds_.WriteTo(output, _repeated_reds_codec);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      size += reds_.CalculateSize(_repeated_reds_codec);
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(pb::CodedInputStream input) {
      uint tag;
      while ((tag = input.ReadTag()) != 0) {
        switch(tag) {
          default:
            input.SkipLastField();
            break;
          case 10: {
            reds_.AddEntriesFrom(input, _repeated_reds_codec);
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///请求阅读红点
  /// </summary>
  public sealed class ReqReadRed : pb::IMessage {
    private static readonly pb::MessageParser<ReqReadRed> _parser = new pb::MessageParser<ReqReadRed>(() => new ReqReadRed());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ReqReadRed> Parser { get { return _parser; } }

    /// <summary>Field number for the "redIds" field.</summary>
    public const int RedIdsFieldNumber = 1;
    private static readonly pb::FieldCodec<int> _repeated_redIds_codec
        = pb::FieldCodec.ForInt32(10);
    private readonly pbc::RepeatedField<int> redIds_ = new pbc::RepeatedField<int>();
    /// <summary>
    ///红点ID
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public pbc::RepeatedField<int> RedIds {
      get { return redIds_; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      redIds_.WriteTo(output, _repeated_redIds_codec);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      size += redIds_.CalculateSize(_repeated_redIds_codec);
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(pb::CodedInputStream input) {
      uint tag;
      while ((tag = input.ReadTag()) != 0) {
        switch(tag) {
          default:
            input.SkipLastField();
            break;
          case 10:
          case 8: {
            redIds_.AddEntriesFrom(input, _repeated_redIds_codec);
            break;
          }
        }
      }
    }

  }

  #endregion

}

#endregion Designer generated code
