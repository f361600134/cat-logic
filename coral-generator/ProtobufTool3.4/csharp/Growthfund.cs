// <auto-generated>
//     Generated by the protocol buffer compiler.  DO NOT EDIT!
//     source: growthfund.proto
// </auto-generated>
#pragma warning disable 1591, 0612, 3021
#region Designer generated code

using pb = global::Google.Protobuf;
using pbc = global::Google.Protobuf.Collections;
using pbr = global::Google.Protobuf.Reflection;
using scg = global::System.Collections.Generic;
namespace Com.Proto.GrowthfundMessage {

  /// <summary>Holder for reflection information generated from growthfund.proto</summary>
  public static partial class GrowthfundReflection {

    #region Descriptor
    /// <summary>File descriptor for growthfund.proto</summary>
    public static pbr::FileDescriptor Descriptor {
      get { return descriptor; }
    }
    private static pbr::FileDescriptor descriptor;

    static GrowthfundReflection() {
      byte[] descriptorData = global::System.Convert.FromBase64String(
          string.Concat(
            "ChBncm93dGhmdW5kLnByb3RvEhtjb20ucHJvdG8uZ3Jvd3RoZnVuZE1lc3Nh",
            "Z2UiGAoWUmVxR3Jvd3RoRnVuZEluZm9Qcm90byIrChhSZXFHcm93dGhGdW5k",
            "UmV3YXJkUHJvdG8SDwoHcXVlc3RJZBgBIAEoBSI9ChZSZXNHcm93dGhGdW5k",
            "SW5mb1Byb3RvEgwKBHBhaWQYASABKAgSFQoNZmluaXNoUXVlc3RJZBgCIAMo",
            "BUI3CiNjb20uZ2FtZS5tb2R1bGUuZ3Jvd3RoZnVuZC5wcm90b2NvbEIQR3Jv",
            "d3RoZnVuZFByb3Rvc2IGcHJvdG8z"));
      descriptor = pbr::FileDescriptor.FromGeneratedCode(descriptorData,
          new pbr::FileDescriptor[] { },
          new pbr::GeneratedClrTypeInfo(null, null, new pbr::GeneratedClrTypeInfo[] {
            new pbr::GeneratedClrTypeInfo(typeof(global::Com.Proto.GrowthfundMessage.ReqGrowthFundInfoProto), global::Com.Proto.GrowthfundMessage.ReqGrowthFundInfoProto.Parser, null, null, null, null, null),
            new pbr::GeneratedClrTypeInfo(typeof(global::Com.Proto.GrowthfundMessage.ReqGrowthFundRewardProto), global::Com.Proto.GrowthfundMessage.ReqGrowthFundRewardProto.Parser, new[]{ "QuestId" }, null, null, null, null),
            new pbr::GeneratedClrTypeInfo(typeof(global::Com.Proto.GrowthfundMessage.ResGrowthFundInfoProto), global::Com.Proto.GrowthfundMessage.ResGrowthFundInfoProto.Parser, new[]{ "Paid", "FinishQuestId" }, null, null, null, null)
          }));
    }
    #endregion

  }
  #region Messages
  /// <summary>
  /// 协议列表
  /// 请求成长基金数据		msgId=173001
  /// </summary>
  public sealed partial class ReqGrowthFundInfoProto : pb::IMessage<ReqGrowthFundInfoProto> {
    private static readonly pb::MessageParser<ReqGrowthFundInfoProto> _parser = new pb::MessageParser<ReqGrowthFundInfoProto>(() => new ReqGrowthFundInfoProto());
    private pb::UnknownFieldSet _unknownFields;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ReqGrowthFundInfoProto> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pbr::MessageDescriptor Descriptor {
      get { return global::Com.Proto.GrowthfundMessage.GrowthfundReflection.Descriptor.MessageTypes[0]; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    pbr::MessageDescriptor pb::IMessage.Descriptor {
      get { return Descriptor; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ReqGrowthFundInfoProto() {
      OnConstruction();
    }

    partial void OnConstruction();

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ReqGrowthFundInfoProto(ReqGrowthFundInfoProto other) : this() {
      _unknownFields = pb::UnknownFieldSet.Clone(other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ReqGrowthFundInfoProto Clone() {
      return new ReqGrowthFundInfoProto(this);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override bool Equals(object other) {
      return Equals(other as ReqGrowthFundInfoProto);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Equals(ReqGrowthFundInfoProto other) {
      if (ReferenceEquals(other, null)) {
        return false;
      }
      if (ReferenceEquals(other, this)) {
        return true;
      }
      return Equals(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override int GetHashCode() {
      int hash = 1;
      if (_unknownFields != null) {
        hash ^= _unknownFields.GetHashCode();
      }
      return hash;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override string ToString() {
      return pb::JsonFormatter.ToDiagnosticString(this);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (_unknownFields != null) {
        _unknownFields.WriteTo(output);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (_unknownFields != null) {
        size += _unknownFields.CalculateSize();
      }
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(ReqGrowthFundInfoProto other) {
      if (other == null) {
        return;
      }
      _unknownFields = pb::UnknownFieldSet.MergeFrom(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(pb::CodedInputStream input) {
      uint tag;
      while ((tag = input.ReadTag()) != 0) {
        switch(tag) {
          default:
            _unknownFields = pb::UnknownFieldSet.MergeFieldFrom(_unknownFields, input);
            break;
        }
      }
    }

  }

  /// <summary>
  /// 领取成长基金奖励		msgId=173002
  /// </summary>
  public sealed partial class ReqGrowthFundRewardProto : pb::IMessage<ReqGrowthFundRewardProto> {
    private static readonly pb::MessageParser<ReqGrowthFundRewardProto> _parser = new pb::MessageParser<ReqGrowthFundRewardProto>(() => new ReqGrowthFundRewardProto());
    private pb::UnknownFieldSet _unknownFields;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ReqGrowthFundRewardProto> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pbr::MessageDescriptor Descriptor {
      get { return global::Com.Proto.GrowthfundMessage.GrowthfundReflection.Descriptor.MessageTypes[1]; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    pbr::MessageDescriptor pb::IMessage.Descriptor {
      get { return Descriptor; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ReqGrowthFundRewardProto() {
      OnConstruction();
    }

    partial void OnConstruction();

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ReqGrowthFundRewardProto(ReqGrowthFundRewardProto other) : this() {
      questId_ = other.questId_;
      _unknownFields = pb::UnknownFieldSet.Clone(other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ReqGrowthFundRewardProto Clone() {
      return new ReqGrowthFundRewardProto(this);
    }

    /// <summary>Field number for the "questId" field.</summary>
    public const int QuestIdFieldNumber = 1;
    private int questId_;
    /// <summary>
    ///请求奖励的ID
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int QuestId {
      get { return questId_; }
      set {
        questId_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override bool Equals(object other) {
      return Equals(other as ReqGrowthFundRewardProto);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Equals(ReqGrowthFundRewardProto other) {
      if (ReferenceEquals(other, null)) {
        return false;
      }
      if (ReferenceEquals(other, this)) {
        return true;
      }
      if (QuestId != other.QuestId) return false;
      return Equals(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override int GetHashCode() {
      int hash = 1;
      if (QuestId != 0) hash ^= QuestId.GetHashCode();
      if (_unknownFields != null) {
        hash ^= _unknownFields.GetHashCode();
      }
      return hash;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override string ToString() {
      return pb::JsonFormatter.ToDiagnosticString(this);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (QuestId != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(QuestId);
      }
      if (_unknownFields != null) {
        _unknownFields.WriteTo(output);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (QuestId != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(QuestId);
      }
      if (_unknownFields != null) {
        size += _unknownFields.CalculateSize();
      }
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(ReqGrowthFundRewardProto other) {
      if (other == null) {
        return;
      }
      if (other.QuestId != 0) {
        QuestId = other.QuestId;
      }
      _unknownFields = pb::UnknownFieldSet.MergeFrom(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(pb::CodedInputStream input) {
      uint tag;
      while ((tag = input.ReadTag()) != 0) {
        switch(tag) {
          default:
            _unknownFields = pb::UnknownFieldSet.MergeFieldFrom(_unknownFields, input);
            break;
          case 8: {
            QuestId = input.ReadInt32();
            break;
          }
        }
      }
    }

  }

  /// <summary>
  /// 响应成长基金数据		msgId=173101
  /// </summary>
  public sealed partial class ResGrowthFundInfoProto : pb::IMessage<ResGrowthFundInfoProto> {
    private static readonly pb::MessageParser<ResGrowthFundInfoProto> _parser = new pb::MessageParser<ResGrowthFundInfoProto>(() => new ResGrowthFundInfoProto());
    private pb::UnknownFieldSet _unknownFields;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ResGrowthFundInfoProto> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pbr::MessageDescriptor Descriptor {
      get { return global::Com.Proto.GrowthfundMessage.GrowthfundReflection.Descriptor.MessageTypes[2]; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    pbr::MessageDescriptor pb::IMessage.Descriptor {
      get { return Descriptor; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ResGrowthFundInfoProto() {
      OnConstruction();
    }

    partial void OnConstruction();

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ResGrowthFundInfoProto(ResGrowthFundInfoProto other) : this() {
      paid_ = other.paid_;
      finishQuestId_ = other.finishQuestId_.Clone();
      _unknownFields = pb::UnknownFieldSet.Clone(other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ResGrowthFundInfoProto Clone() {
      return new ResGrowthFundInfoProto(this);
    }

    /// <summary>Field number for the "paid" field.</summary>
    public const int PaidFieldNumber = 1;
    private bool paid_;
    /// <summary>
    ///是否已经购买成长基金
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Paid {
      get { return paid_; }
      set {
        paid_ = value;
      }
    }

    /// <summary>Field number for the "finishQuestId" field.</summary>
    public const int FinishQuestIdFieldNumber = 2;
    private static readonly pb::FieldCodec<int> _repeated_finishQuestId_codec
        = pb::FieldCodec.ForInt32(18);
    private readonly pbc::RepeatedField<int> finishQuestId_ = new pbc::RepeatedField<int>();
    /// <summary>
    ///已经领取的奖励ID
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public pbc::RepeatedField<int> FinishQuestId {
      get { return finishQuestId_; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override bool Equals(object other) {
      return Equals(other as ResGrowthFundInfoProto);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Equals(ResGrowthFundInfoProto other) {
      if (ReferenceEquals(other, null)) {
        return false;
      }
      if (ReferenceEquals(other, this)) {
        return true;
      }
      if (Paid != other.Paid) return false;
      if(!finishQuestId_.Equals(other.finishQuestId_)) return false;
      return Equals(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override int GetHashCode() {
      int hash = 1;
      if (Paid != false) hash ^= Paid.GetHashCode();
      hash ^= finishQuestId_.GetHashCode();
      if (_unknownFields != null) {
        hash ^= _unknownFields.GetHashCode();
      }
      return hash;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override string ToString() {
      return pb::JsonFormatter.ToDiagnosticString(this);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (Paid != false) {
        output.WriteRawTag(8);
        output.WriteBool(Paid);
      }
      finishQuestId_.WriteTo(output, _repeated_finishQuestId_codec);
      if (_unknownFields != null) {
        _unknownFields.WriteTo(output);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (Paid != false) {
        size += 1 + 1;
      }
      size += finishQuestId_.CalculateSize(_repeated_finishQuestId_codec);
      if (_unknownFields != null) {
        size += _unknownFields.CalculateSize();
      }
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(ResGrowthFundInfoProto other) {
      if (other == null) {
        return;
      }
      if (other.Paid != false) {
        Paid = other.Paid;
      }
      finishQuestId_.Add(other.finishQuestId_);
      _unknownFields = pb::UnknownFieldSet.MergeFrom(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(pb::CodedInputStream input) {
      uint tag;
      while ((tag = input.ReadTag()) != 0) {
        switch(tag) {
          default:
            _unknownFields = pb::UnknownFieldSet.MergeFieldFrom(_unknownFields, input);
            break;
          case 8: {
            Paid = input.ReadBool();
            break;
          }
          case 18:
          case 16: {
            finishQuestId_.AddEntriesFrom(input, _repeated_finishQuestId_codec);
            break;
          }
        }
      }
    }

  }

  #endregion

}

#endregion Designer generated code