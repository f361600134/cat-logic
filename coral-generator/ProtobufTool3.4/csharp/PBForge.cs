// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: PBForge.proto
#pragma warning disable 1591, 0612, 3021
#region Designer generated code

using pb = global::Google.Protobuf;
using pbc = global::Google.Protobuf.Collections;
using scg = global::System.Collections.Generic;
namespace Protocol {

  #region Messages
  /// <summary>
  ///请求合成符文
  /// </summary>
  public sealed class ReqRuneCompose : pb::IMessage {
    private static readonly pb::MessageParser<ReqRuneCompose> _parser = new pb::MessageParser<ReqRuneCompose>(() => new ReqRuneCompose());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ReqRuneCompose> Parser { get { return _parser; } }

    /// <summary>Field number for the "runeId" field.</summary>
    public const int RuneIdFieldNumber = 1;
    private static readonly pb::FieldCodec<long> _repeated_runeId_codec
        = pb::FieldCodec.ForInt64(10);
    private readonly pbc::RepeatedField<long> runeId_ = new pbc::RepeatedField<long>();
    /// <summary>
    ///符文ID列表
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public pbc::RepeatedField<long> RuneId {
      get { return runeId_; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      runeId_.WriteTo(output, _repeated_runeId_codec);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      size += runeId_.CalculateSize(_repeated_runeId_codec);
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
            runeId_.AddEntriesFrom(input, _repeated_runeId_codec);
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///响应合成符文
  /// </summary>
  public sealed class AckRuneCompose : pb::IMessage {
    private static readonly pb::MessageParser<AckRuneCompose> _parser = new pb::MessageParser<AckRuneCompose>(() => new AckRuneCompose());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<AckRuneCompose> Parser { get { return _parser; } }

    /// <summary>Field number for the "code" field.</summary>
    public const int CodeFieldNumber = 1;
    private int code_;
    /// <summary>
    ///响应状态0为成功1为失败
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Code {
      get { return code_; }
      set {
        code_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (Code != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(Code);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (Code != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Code);
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
            Code = input.ReadInt32();
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///请求合成装备
  /// </summary>
  public sealed class ReqEquipCompose : pb::IMessage {
    private static readonly pb::MessageParser<ReqEquipCompose> _parser = new pb::MessageParser<ReqEquipCompose>(() => new ReqEquipCompose());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ReqEquipCompose> Parser { get { return _parser; } }

    /// <summary>Field number for the "equipParam" field.</summary>
    public const int EquipParamFieldNumber = 1;
    private int equipParam_;
    /// <summary>
    ///普通合成（装备Id  一键合成（合成装备类型）
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int EquipParam {
      get { return equipParam_; }
      set {
        equipParam_ = value;
      }
    }

    /// <summary>Field number for the "composeNum" field.</summary>
    public const int ComposeNumFieldNumber = 2;
    private int composeNum_;
    /// <summary>
    ///普通合成数量
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int ComposeNum {
      get { return composeNum_; }
      set {
        composeNum_ = value;
      }
    }

    /// <summary>Field number for the "composeType" field.</summary>
    public const int ComposeTypeFieldNumber = 3;
    private int composeType_;
    /// <summary>
    ///合成  一键合成（合成装备类型）
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int ComposeType {
      get { return composeType_; }
      set {
        composeType_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (EquipParam != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(EquipParam);
      }
      if (ComposeNum != 0) {
        output.WriteRawTag(16);
        output.WriteInt32(ComposeNum);
      }
      if (ComposeType != 0) {
        output.WriteRawTag(24);
        output.WriteInt32(ComposeType);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (EquipParam != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(EquipParam);
      }
      if (ComposeNum != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(ComposeNum);
      }
      if (ComposeType != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(ComposeType);
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
            EquipParam = input.ReadInt32();
            break;
          }
          case 16: {
            ComposeNum = input.ReadInt32();
            break;
          }
          case 24: {
            ComposeType = input.ReadInt32();
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///响应合成装备
  /// </summary>
  public sealed class AckEquipCompose : pb::IMessage {
    private static readonly pb::MessageParser<AckEquipCompose> _parser = new pb::MessageParser<AckEquipCompose>(() => new AckEquipCompose());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<AckEquipCompose> Parser { get { return _parser; } }

    /// <summary>Field number for the "code" field.</summary>
    public const int CodeFieldNumber = 1;
    private int code_;
    /// <summary>
    ///响应状态0为成功1为失败
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Code {
      get { return code_; }
      set {
        code_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (Code != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(Code);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (Code != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Code);
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
            Code = input.ReadInt32();
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///请求查看合成记录
  /// </summary>
  public sealed class ReqComposeRecord : pb::IMessage {
    private static readonly pb::MessageParser<ReqComposeRecord> _parser = new pb::MessageParser<ReqComposeRecord>(() => new ReqComposeRecord());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ReqComposeRecord> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
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
        }
      }
    }

  }

  /// <summary>
  ///响应查看合成记录
  /// </summary>
  public sealed class AckComposeRecord : pb::IMessage {
    private static readonly pb::MessageParser<AckComposeRecord> _parser = new pb::MessageParser<AckComposeRecord>(() => new AckComposeRecord());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<AckComposeRecord> Parser { get { return _parser; } }

    /// <summary>Field number for the "code" field.</summary>
    public const int CodeFieldNumber = 1;
    private int code_;
    /// <summary>
    ///响应状态0为成功1为失败
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Code {
      get { return code_; }
      set {
        code_ = value;
      }
    }

    /// <summary>Field number for the "records" field.</summary>
    public const int RecordsFieldNumber = 2;
    private static readonly pb::FieldCodec<global::Protocol.RecordEquipCompose> _repeated_records_codec
        = pb::FieldCodec.ForMessage(18, global::Protocol.RecordEquipCompose.Parser);
    private readonly pbc::RepeatedField<global::Protocol.RecordEquipCompose> records_ = new pbc::RepeatedField<global::Protocol.RecordEquipCompose>();
    /// <summary>
    ///合成记录列表
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public pbc::RepeatedField<global::Protocol.RecordEquipCompose> Records {
      get { return records_; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (Code != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(Code);
      }
      records_.WriteTo(output, _repeated_records_codec);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (Code != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Code);
      }
      size += records_.CalculateSize(_repeated_records_codec);
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
            Code = input.ReadInt32();
            break;
          }
          case 18: {
            records_.AddEntriesFrom(input, _repeated_records_codec);
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///领取熔炼值符文
  /// </summary>
  public sealed class ReqGetSmeltRune : pb::IMessage {
    private static readonly pb::MessageParser<ReqGetSmeltRune> _parser = new pb::MessageParser<ReqGetSmeltRune>(() => new ReqGetSmeltRune());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ReqGetSmeltRune> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
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
        }
      }
    }

  }

  /// <summary>
  ///响应领取熔炼值符文
  /// </summary>
  public sealed class AckGetSmeltRune : pb::IMessage {
    private static readonly pb::MessageParser<AckGetSmeltRune> _parser = new pb::MessageParser<AckGetSmeltRune>(() => new AckGetSmeltRune());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<AckGetSmeltRune> Parser { get { return _parser; } }

    /// <summary>Field number for the "code" field.</summary>
    public const int CodeFieldNumber = 1;
    private int code_;
    /// <summary>
    ///响应状态0为成功1为失败
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Code {
      get { return code_; }
      set {
        code_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (Code != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(Code);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (Code != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Code);
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
            Code = input.ReadInt32();
            break;
          }
        }
      }
    }

  }

  public sealed class RecordEquipCompose : pb::IMessage {
    private static readonly pb::MessageParser<RecordEquipCompose> _parser = new pb::MessageParser<RecordEquipCompose>(() => new RecordEquipCompose());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<RecordEquipCompose> Parser { get { return _parser; } }

    /// <summary>Field number for the "items" field.</summary>
    public const int ItemsFieldNumber = 1;
    private static readonly pb::FieldCodec<global::Protocol.ItemInfo> _repeated_items_codec
        = pb::FieldCodec.ForMessage(10, global::Protocol.ItemInfo.Parser);
    private readonly pbc::RepeatedField<global::Protocol.ItemInfo> items_ = new pbc::RepeatedField<global::Protocol.ItemInfo>();
    /// <summary>
    ///合成装备列表
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public pbc::RepeatedField<global::Protocol.ItemInfo> Items {
      get { return items_; }
    }

    /// <summary>Field number for the "costCopper" field.</summary>
    public const int CostCopperFieldNumber = 2;
    private int costCopper_;
    /// <summary>
    ///消耗金币
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CostCopper {
      get { return costCopper_; }
      set {
        costCopper_ = value;
      }
    }

    /// <summary>Field number for the "recordTime" field.</summary>
    public const int RecordTimeFieldNumber = 3;
    private long recordTime_;
    /// <summary>
    ///合成时间
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public long RecordTime {
      get { return recordTime_; }
      set {
        recordTime_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      items_.WriteTo(output, _repeated_items_codec);
      if (CostCopper != 0) {
        output.WriteRawTag(16);
        output.WriteInt32(CostCopper);
      }
      if (RecordTime != 0L) {
        output.WriteRawTag(24);
        output.WriteInt64(RecordTime);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      size += items_.CalculateSize(_repeated_items_codec);
      if (CostCopper != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(CostCopper);
      }
      if (RecordTime != 0L) {
        size += 1 + pb::CodedOutputStream.ComputeInt64Size(RecordTime);
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
          case 10: {
            items_.AddEntriesFrom(input, _repeated_items_codec);
            break;
          }
          case 16: {
            CostCopper = input.ReadInt32();
            break;
          }
          case 24: {
            RecordTime = input.ReadInt64();
            break;
          }
        }
      }
    }

  }

  #endregion

}

#endregion Designer generated code
