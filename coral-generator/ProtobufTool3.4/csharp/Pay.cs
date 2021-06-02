// <auto-generated>
//     Generated by the protocol buffer compiler.  DO NOT EDIT!
//     source: pay.proto
// </auto-generated>
#pragma warning disable 1591, 0612, 3021
#region Designer generated code

using pb = global::Google.Protobuf;
using pbc = global::Google.Protobuf.Collections;
using pbr = global::Google.Protobuf.Reflection;
using scg = global::System.Collections.Generic;
namespace Com.Proto.PayMessage {

  /// <summary>Holder for reflection information generated from pay.proto</summary>
  public static partial class PayReflection {

    #region Descriptor
    /// <summary>File descriptor for pay.proto</summary>
    public static pbr::FileDescriptor Descriptor {
      get { return descriptor; }
    }
    private static pbr::FileDescriptor descriptor;

    static PayReflection() {
      byte[] descriptorData = global::System.Convert.FromBase64String(
          string.Concat(
            "CglwYXkucHJvdG8SFGNvbS5wcm90by5wYXlNZXNzYWdlIjIKFEZyZWVQYXlD",
            "b3VudER0b1Byb3RvEg0KBXBheUlkGAEgASgFEgsKA251bRgCIAEoBSIRCg9S",
            "ZXFQYXlJbmZvUHJvdG8iIAoPUmVxRnJlZVBheVByb3RvEg0KBXBheUlkGAEg",
            "ASgFIqUBCg9SZXNQYXlJbmZvUHJvdG8SEwoLZmlyc3RQYXlJZHMYASADKAUS",
            "GwoTbW9udGhDYXJkRXhwaXJlVGltZRgCIAEoBRIaChJ5ZWFyQ2FyZEV4cGly",
            "ZVRpbWUYAyABKAUSRAoQZnJlZVBheUNvdW50TGlzdBgEIAMoCzIqLmNvbS5w",
            "cm90by5wYXlNZXNzYWdlLkZyZWVQYXlDb3VudER0b1Byb3RvIjYKElJlc1Bh",
            "eVN1Y2Nlc3NQcm90bxIPCgdnb29kc0lkGAEgASgFEg8KB29yZGVySWQYAiAB",
            "KAkiNQoRUmVzVmlwQ2hhbmdlUHJvdG8SEAoIdmlwTGV2ZWwYASABKAUSDgoG",
            "dmlwRXhwGAIgASgFIl8KF1Jlc1VwZGF0ZUZyZWVDb3VudFByb3RvEkQKEGZy",
            "ZWVQYXlDb3VudExpc3QYASADKAsyKi5jb20ucHJvdG8ucGF5TWVzc2FnZS5G",
            "cmVlUGF5Q291bnREdG9Qcm90b0IpChxjb20uZ2FtZS5tb2R1bGUucGF5LnBy",
            "b3RvY29sQglQYXlQcm90b3NiBnByb3RvMw=="));
      descriptor = pbr::FileDescriptor.FromGeneratedCode(descriptorData,
          new pbr::FileDescriptor[] { },
          new pbr::GeneratedClrTypeInfo(null, null, new pbr::GeneratedClrTypeInfo[] {
            new pbr::GeneratedClrTypeInfo(typeof(global::Com.Proto.PayMessage.FreePayCountDtoProto), global::Com.Proto.PayMessage.FreePayCountDtoProto.Parser, new[]{ "PayId", "Num" }, null, null, null, null),
            new pbr::GeneratedClrTypeInfo(typeof(global::Com.Proto.PayMessage.ReqPayInfoProto), global::Com.Proto.PayMessage.ReqPayInfoProto.Parser, null, null, null, null, null),
            new pbr::GeneratedClrTypeInfo(typeof(global::Com.Proto.PayMessage.ReqFreePayProto), global::Com.Proto.PayMessage.ReqFreePayProto.Parser, new[]{ "PayId" }, null, null, null, null),
            new pbr::GeneratedClrTypeInfo(typeof(global::Com.Proto.PayMessage.ResPayInfoProto), global::Com.Proto.PayMessage.ResPayInfoProto.Parser, new[]{ "FirstPayIds", "MonthCardExpireTime", "YearCardExpireTime", "FreePayCountList" }, null, null, null, null),
            new pbr::GeneratedClrTypeInfo(typeof(global::Com.Proto.PayMessage.ResPaySuccessProto), global::Com.Proto.PayMessage.ResPaySuccessProto.Parser, new[]{ "GoodsId", "OrderId" }, null, null, null, null),
            new pbr::GeneratedClrTypeInfo(typeof(global::Com.Proto.PayMessage.ResVipChangeProto), global::Com.Proto.PayMessage.ResVipChangeProto.Parser, new[]{ "VipLevel", "VipExp" }, null, null, null, null),
            new pbr::GeneratedClrTypeInfo(typeof(global::Com.Proto.PayMessage.ResUpdateFreeCountProto), global::Com.Proto.PayMessage.ResUpdateFreeCountProto.Parser, new[]{ "FreePayCountList" }, null, null, null, null)
          }));
    }
    #endregion

  }
  #region Messages
  /// <summary>
  /// 协议列表
  /// 免费充值计数		msgId=114001
  /// </summary>
  public sealed partial class FreePayCountDtoProto : pb::IMessage<FreePayCountDtoProto> {
    private static readonly pb::MessageParser<FreePayCountDtoProto> _parser = new pb::MessageParser<FreePayCountDtoProto>(() => new FreePayCountDtoProto());
    private pb::UnknownFieldSet _unknownFields;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<FreePayCountDtoProto> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pbr::MessageDescriptor Descriptor {
      get { return global::Com.Proto.PayMessage.PayReflection.Descriptor.MessageTypes[0]; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    pbr::MessageDescriptor pb::IMessage.Descriptor {
      get { return Descriptor; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public FreePayCountDtoProto() {
      OnConstruction();
    }

    partial void OnConstruction();

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public FreePayCountDtoProto(FreePayCountDtoProto other) : this() {
      payId_ = other.payId_;
      num_ = other.num_;
      _unknownFields = pb::UnknownFieldSet.Clone(other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public FreePayCountDtoProto Clone() {
      return new FreePayCountDtoProto(this);
    }

    /// <summary>Field number for the "payId" field.</summary>
    public const int PayIdFieldNumber = 1;
    private int payId_;
    /// <summary>
    ///充值 id(配置表 id)
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int PayId {
      get { return payId_; }
      set {
        payId_ = value;
      }
    }

    /// <summary>Field number for the "num" field.</summary>
    public const int NumFieldNumber = 2;
    private int num_;
    /// <summary>
    ///已免费充值的次数
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Num {
      get { return num_; }
      set {
        num_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override bool Equals(object other) {
      return Equals(other as FreePayCountDtoProto);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Equals(FreePayCountDtoProto other) {
      if (ReferenceEquals(other, null)) {
        return false;
      }
      if (ReferenceEquals(other, this)) {
        return true;
      }
      if (PayId != other.PayId) return false;
      if (Num != other.Num) return false;
      return Equals(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override int GetHashCode() {
      int hash = 1;
      if (PayId != 0) hash ^= PayId.GetHashCode();
      if (Num != 0) hash ^= Num.GetHashCode();
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
      if (PayId != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(PayId);
      }
      if (Num != 0) {
        output.WriteRawTag(16);
        output.WriteInt32(Num);
      }
      if (_unknownFields != null) {
        _unknownFields.WriteTo(output);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (PayId != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(PayId);
      }
      if (Num != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Num);
      }
      if (_unknownFields != null) {
        size += _unknownFields.CalculateSize();
      }
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(FreePayCountDtoProto other) {
      if (other == null) {
        return;
      }
      if (other.PayId != 0) {
        PayId = other.PayId;
      }
      if (other.Num != 0) {
        Num = other.Num;
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
            PayId = input.ReadInt32();
            break;
          }
          case 16: {
            Num = input.ReadInt32();
            break;
          }
        }
      }
    }

  }

  /// <summary>
  /// 请求充值信息		msgId=114101
  /// </summary>
  public sealed partial class ReqPayInfoProto : pb::IMessage<ReqPayInfoProto> {
    private static readonly pb::MessageParser<ReqPayInfoProto> _parser = new pb::MessageParser<ReqPayInfoProto>(() => new ReqPayInfoProto());
    private pb::UnknownFieldSet _unknownFields;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ReqPayInfoProto> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pbr::MessageDescriptor Descriptor {
      get { return global::Com.Proto.PayMessage.PayReflection.Descriptor.MessageTypes[1]; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    pbr::MessageDescriptor pb::IMessage.Descriptor {
      get { return Descriptor; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ReqPayInfoProto() {
      OnConstruction();
    }

    partial void OnConstruction();

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ReqPayInfoProto(ReqPayInfoProto other) : this() {
      _unknownFields = pb::UnknownFieldSet.Clone(other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ReqPayInfoProto Clone() {
      return new ReqPayInfoProto(this);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override bool Equals(object other) {
      return Equals(other as ReqPayInfoProto);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Equals(ReqPayInfoProto other) {
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
    public void MergeFrom(ReqPayInfoProto other) {
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
  /// 请求免费充值		msgId=114102
  /// </summary>
  public sealed partial class ReqFreePayProto : pb::IMessage<ReqFreePayProto> {
    private static readonly pb::MessageParser<ReqFreePayProto> _parser = new pb::MessageParser<ReqFreePayProto>(() => new ReqFreePayProto());
    private pb::UnknownFieldSet _unknownFields;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ReqFreePayProto> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pbr::MessageDescriptor Descriptor {
      get { return global::Com.Proto.PayMessage.PayReflection.Descriptor.MessageTypes[2]; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    pbr::MessageDescriptor pb::IMessage.Descriptor {
      get { return Descriptor; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ReqFreePayProto() {
      OnConstruction();
    }

    partial void OnConstruction();

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ReqFreePayProto(ReqFreePayProto other) : this() {
      payId_ = other.payId_;
      _unknownFields = pb::UnknownFieldSet.Clone(other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ReqFreePayProto Clone() {
      return new ReqFreePayProto(this);
    }

    /// <summary>Field number for the "payId" field.</summary>
    public const int PayIdFieldNumber = 1;
    private int payId_;
    /// <summary>
    ///充值配置 id
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int PayId {
      get { return payId_; }
      set {
        payId_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override bool Equals(object other) {
      return Equals(other as ReqFreePayProto);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Equals(ReqFreePayProto other) {
      if (ReferenceEquals(other, null)) {
        return false;
      }
      if (ReferenceEquals(other, this)) {
        return true;
      }
      if (PayId != other.PayId) return false;
      return Equals(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override int GetHashCode() {
      int hash = 1;
      if (PayId != 0) hash ^= PayId.GetHashCode();
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
      if (PayId != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(PayId);
      }
      if (_unknownFields != null) {
        _unknownFields.WriteTo(output);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (PayId != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(PayId);
      }
      if (_unknownFields != null) {
        size += _unknownFields.CalculateSize();
      }
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(ReqFreePayProto other) {
      if (other == null) {
        return;
      }
      if (other.PayId != 0) {
        PayId = other.PayId;
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
            PayId = input.ReadInt32();
            break;
          }
        }
      }
    }

  }

  /// <summary>
  /// 返回回充值信息		msgId=114201
  /// </summary>
  public sealed partial class ResPayInfoProto : pb::IMessage<ResPayInfoProto> {
    private static readonly pb::MessageParser<ResPayInfoProto> _parser = new pb::MessageParser<ResPayInfoProto>(() => new ResPayInfoProto());
    private pb::UnknownFieldSet _unknownFields;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ResPayInfoProto> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pbr::MessageDescriptor Descriptor {
      get { return global::Com.Proto.PayMessage.PayReflection.Descriptor.MessageTypes[3]; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    pbr::MessageDescriptor pb::IMessage.Descriptor {
      get { return Descriptor; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ResPayInfoProto() {
      OnConstruction();
    }

    partial void OnConstruction();

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ResPayInfoProto(ResPayInfoProto other) : this() {
      firstPayIds_ = other.firstPayIds_.Clone();
      monthCardExpireTime_ = other.monthCardExpireTime_;
      yearCardExpireTime_ = other.yearCardExpireTime_;
      freePayCountList_ = other.freePayCountList_.Clone();
      _unknownFields = pb::UnknownFieldSet.Clone(other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ResPayInfoProto Clone() {
      return new ResPayInfoProto(this);
    }

    /// <summary>Field number for the "firstPayIds" field.</summary>
    public const int FirstPayIdsFieldNumber = 1;
    private static readonly pb::FieldCodec<int> _repeated_firstPayIds_codec
        = pb::FieldCodec.ForInt32(10);
    private readonly pbc::RepeatedField<int> firstPayIds_ = new pbc::RepeatedField<int>();
    /// <summary>
    ///完成首充的充值 id
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public pbc::RepeatedField<int> FirstPayIds {
      get { return firstPayIds_; }
    }

    /// <summary>Field number for the "monthCardExpireTime" field.</summary>
    public const int MonthCardExpireTimeFieldNumber = 2;
    private int monthCardExpireTime_;
    /// <summary>
    ///月卡过期时间
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int MonthCardExpireTime {
      get { return monthCardExpireTime_; }
      set {
        monthCardExpireTime_ = value;
      }
    }

    /// <summary>Field number for the "yearCardExpireTime" field.</summary>
    public const int YearCardExpireTimeFieldNumber = 3;
    private int yearCardExpireTime_;
    /// <summary>
    ///年卡过期时间
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int YearCardExpireTime {
      get { return yearCardExpireTime_; }
      set {
        yearCardExpireTime_ = value;
      }
    }

    /// <summary>Field number for the "freePayCountList" field.</summary>
    public const int FreePayCountListFieldNumber = 4;
    private static readonly pb::FieldCodec<global::Com.Proto.PayMessage.FreePayCountDtoProto> _repeated_freePayCountList_codec
        = pb::FieldCodec.ForMessage(34, global::Com.Proto.PayMessage.FreePayCountDtoProto.Parser);
    private readonly pbc::RepeatedField<global::Com.Proto.PayMessage.FreePayCountDtoProto> freePayCountList_ = new pbc::RepeatedField<global::Com.Proto.PayMessage.FreePayCountDtoProto>();
    /// <summary>
    ///免费充值计数(只发送数值 > 0 的)
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public pbc::RepeatedField<global::Com.Proto.PayMessage.FreePayCountDtoProto> FreePayCountList {
      get { return freePayCountList_; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override bool Equals(object other) {
      return Equals(other as ResPayInfoProto);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Equals(ResPayInfoProto other) {
      if (ReferenceEquals(other, null)) {
        return false;
      }
      if (ReferenceEquals(other, this)) {
        return true;
      }
      if(!firstPayIds_.Equals(other.firstPayIds_)) return false;
      if (MonthCardExpireTime != other.MonthCardExpireTime) return false;
      if (YearCardExpireTime != other.YearCardExpireTime) return false;
      if(!freePayCountList_.Equals(other.freePayCountList_)) return false;
      return Equals(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override int GetHashCode() {
      int hash = 1;
      hash ^= firstPayIds_.GetHashCode();
      if (MonthCardExpireTime != 0) hash ^= MonthCardExpireTime.GetHashCode();
      if (YearCardExpireTime != 0) hash ^= YearCardExpireTime.GetHashCode();
      hash ^= freePayCountList_.GetHashCode();
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
      firstPayIds_.WriteTo(output, _repeated_firstPayIds_codec);
      if (MonthCardExpireTime != 0) {
        output.WriteRawTag(16);
        output.WriteInt32(MonthCardExpireTime);
      }
      if (YearCardExpireTime != 0) {
        output.WriteRawTag(24);
        output.WriteInt32(YearCardExpireTime);
      }
      freePayCountList_.WriteTo(output, _repeated_freePayCountList_codec);
      if (_unknownFields != null) {
        _unknownFields.WriteTo(output);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      size += firstPayIds_.CalculateSize(_repeated_firstPayIds_codec);
      if (MonthCardExpireTime != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(MonthCardExpireTime);
      }
      if (YearCardExpireTime != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(YearCardExpireTime);
      }
      size += freePayCountList_.CalculateSize(_repeated_freePayCountList_codec);
      if (_unknownFields != null) {
        size += _unknownFields.CalculateSize();
      }
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(ResPayInfoProto other) {
      if (other == null) {
        return;
      }
      firstPayIds_.Add(other.firstPayIds_);
      if (other.MonthCardExpireTime != 0) {
        MonthCardExpireTime = other.MonthCardExpireTime;
      }
      if (other.YearCardExpireTime != 0) {
        YearCardExpireTime = other.YearCardExpireTime;
      }
      freePayCountList_.Add(other.freePayCountList_);
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
          case 10:
          case 8: {
            firstPayIds_.AddEntriesFrom(input, _repeated_firstPayIds_codec);
            break;
          }
          case 16: {
            MonthCardExpireTime = input.ReadInt32();
            break;
          }
          case 24: {
            YearCardExpireTime = input.ReadInt32();
            break;
          }
          case 34: {
            freePayCountList_.AddEntriesFrom(input, _repeated_freePayCountList_codec);
            break;
          }
        }
      }
    }

  }

  /// <summary>
  /// 返回充值成功		msgId=114202
  /// </summary>
  public sealed partial class ResPaySuccessProto : pb::IMessage<ResPaySuccessProto> {
    private static readonly pb::MessageParser<ResPaySuccessProto> _parser = new pb::MessageParser<ResPaySuccessProto>(() => new ResPaySuccessProto());
    private pb::UnknownFieldSet _unknownFields;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ResPaySuccessProto> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pbr::MessageDescriptor Descriptor {
      get { return global::Com.Proto.PayMessage.PayReflection.Descriptor.MessageTypes[4]; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    pbr::MessageDescriptor pb::IMessage.Descriptor {
      get { return Descriptor; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ResPaySuccessProto() {
      OnConstruction();
    }

    partial void OnConstruction();

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ResPaySuccessProto(ResPaySuccessProto other) : this() {
      goodsId_ = other.goodsId_;
      orderId_ = other.orderId_;
      _unknownFields = pb::UnknownFieldSet.Clone(other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ResPaySuccessProto Clone() {
      return new ResPaySuccessProto(this);
    }

    /// <summary>Field number for the "goodsId" field.</summary>
    public const int GoodsIdFieldNumber = 1;
    private int goodsId_;
    /// <summary>
    ///商品id
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int GoodsId {
      get { return goodsId_; }
      set {
        goodsId_ = value;
      }
    }

    /// <summary>Field number for the "orderId" field.</summary>
    public const int OrderIdFieldNumber = 2;
    private string orderId_ = "";
    /// <summary>
    ///订单号
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public string OrderId {
      get { return orderId_; }
      set {
        orderId_ = pb::ProtoPreconditions.CheckNotNull(value, "value");
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override bool Equals(object other) {
      return Equals(other as ResPaySuccessProto);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Equals(ResPaySuccessProto other) {
      if (ReferenceEquals(other, null)) {
        return false;
      }
      if (ReferenceEquals(other, this)) {
        return true;
      }
      if (GoodsId != other.GoodsId) return false;
      if (OrderId != other.OrderId) return false;
      return Equals(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override int GetHashCode() {
      int hash = 1;
      if (GoodsId != 0) hash ^= GoodsId.GetHashCode();
      if (OrderId.Length != 0) hash ^= OrderId.GetHashCode();
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
      if (GoodsId != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(GoodsId);
      }
      if (OrderId.Length != 0) {
        output.WriteRawTag(18);
        output.WriteString(OrderId);
      }
      if (_unknownFields != null) {
        _unknownFields.WriteTo(output);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (GoodsId != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(GoodsId);
      }
      if (OrderId.Length != 0) {
        size += 1 + pb::CodedOutputStream.ComputeStringSize(OrderId);
      }
      if (_unknownFields != null) {
        size += _unknownFields.CalculateSize();
      }
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(ResPaySuccessProto other) {
      if (other == null) {
        return;
      }
      if (other.GoodsId != 0) {
        GoodsId = other.GoodsId;
      }
      if (other.OrderId.Length != 0) {
        OrderId = other.OrderId;
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
            GoodsId = input.ReadInt32();
            break;
          }
          case 18: {
            OrderId = input.ReadString();
            break;
          }
        }
      }
    }

  }

  /// <summary>
  /// 返回vip变化		msgId=114203
  /// </summary>
  public sealed partial class ResVipChangeProto : pb::IMessage<ResVipChangeProto> {
    private static readonly pb::MessageParser<ResVipChangeProto> _parser = new pb::MessageParser<ResVipChangeProto>(() => new ResVipChangeProto());
    private pb::UnknownFieldSet _unknownFields;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ResVipChangeProto> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pbr::MessageDescriptor Descriptor {
      get { return global::Com.Proto.PayMessage.PayReflection.Descriptor.MessageTypes[5]; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    pbr::MessageDescriptor pb::IMessage.Descriptor {
      get { return Descriptor; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ResVipChangeProto() {
      OnConstruction();
    }

    partial void OnConstruction();

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ResVipChangeProto(ResVipChangeProto other) : this() {
      vipLevel_ = other.vipLevel_;
      vipExp_ = other.vipExp_;
      _unknownFields = pb::UnknownFieldSet.Clone(other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ResVipChangeProto Clone() {
      return new ResVipChangeProto(this);
    }

    /// <summary>Field number for the "vipLevel" field.</summary>
    public const int VipLevelFieldNumber = 1;
    private int vipLevel_;
    /// <summary>
    ///vip等级
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int VipLevel {
      get { return vipLevel_; }
      set {
        vipLevel_ = value;
      }
    }

    /// <summary>Field number for the "vipExp" field.</summary>
    public const int VipExpFieldNumber = 2;
    private int vipExp_;
    /// <summary>
    ///vip经验
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int VipExp {
      get { return vipExp_; }
      set {
        vipExp_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override bool Equals(object other) {
      return Equals(other as ResVipChangeProto);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Equals(ResVipChangeProto other) {
      if (ReferenceEquals(other, null)) {
        return false;
      }
      if (ReferenceEquals(other, this)) {
        return true;
      }
      if (VipLevel != other.VipLevel) return false;
      if (VipExp != other.VipExp) return false;
      return Equals(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override int GetHashCode() {
      int hash = 1;
      if (VipLevel != 0) hash ^= VipLevel.GetHashCode();
      if (VipExp != 0) hash ^= VipExp.GetHashCode();
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
      if (VipLevel != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(VipLevel);
      }
      if (VipExp != 0) {
        output.WriteRawTag(16);
        output.WriteInt32(VipExp);
      }
      if (_unknownFields != null) {
        _unknownFields.WriteTo(output);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (VipLevel != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(VipLevel);
      }
      if (VipExp != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(VipExp);
      }
      if (_unknownFields != null) {
        size += _unknownFields.CalculateSize();
      }
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(ResVipChangeProto other) {
      if (other == null) {
        return;
      }
      if (other.VipLevel != 0) {
        VipLevel = other.VipLevel;
      }
      if (other.VipExp != 0) {
        VipExp = other.VipExp;
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
            VipLevel = input.ReadInt32();
            break;
          }
          case 16: {
            VipExp = input.ReadInt32();
            break;
          }
        }
      }
    }

  }

  /// <summary>
  /// 刷新充值计数（一般0点时发送）		msgId=114204
  /// </summary>
  public sealed partial class ResUpdateFreeCountProto : pb::IMessage<ResUpdateFreeCountProto> {
    private static readonly pb::MessageParser<ResUpdateFreeCountProto> _parser = new pb::MessageParser<ResUpdateFreeCountProto>(() => new ResUpdateFreeCountProto());
    private pb::UnknownFieldSet _unknownFields;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ResUpdateFreeCountProto> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pbr::MessageDescriptor Descriptor {
      get { return global::Com.Proto.PayMessage.PayReflection.Descriptor.MessageTypes[6]; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    pbr::MessageDescriptor pb::IMessage.Descriptor {
      get { return Descriptor; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ResUpdateFreeCountProto() {
      OnConstruction();
    }

    partial void OnConstruction();

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ResUpdateFreeCountProto(ResUpdateFreeCountProto other) : this() {
      freePayCountList_ = other.freePayCountList_.Clone();
      _unknownFields = pb::UnknownFieldSet.Clone(other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ResUpdateFreeCountProto Clone() {
      return new ResUpdateFreeCountProto(this);
    }

    /// <summary>Field number for the "freePayCountList" field.</summary>
    public const int FreePayCountListFieldNumber = 1;
    private static readonly pb::FieldCodec<global::Com.Proto.PayMessage.FreePayCountDtoProto> _repeated_freePayCountList_codec
        = pb::FieldCodec.ForMessage(10, global::Com.Proto.PayMessage.FreePayCountDtoProto.Parser);
    private readonly pbc::RepeatedField<global::Com.Proto.PayMessage.FreePayCountDtoProto> freePayCountList_ = new pbc::RepeatedField<global::Com.Proto.PayMessage.FreePayCountDtoProto>();
    /// <summary>
    ///免费充值计数(只发送数值 > 0 的)
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public pbc::RepeatedField<global::Com.Proto.PayMessage.FreePayCountDtoProto> FreePayCountList {
      get { return freePayCountList_; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override bool Equals(object other) {
      return Equals(other as ResUpdateFreeCountProto);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Equals(ResUpdateFreeCountProto other) {
      if (ReferenceEquals(other, null)) {
        return false;
      }
      if (ReferenceEquals(other, this)) {
        return true;
      }
      if(!freePayCountList_.Equals(other.freePayCountList_)) return false;
      return Equals(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override int GetHashCode() {
      int hash = 1;
      hash ^= freePayCountList_.GetHashCode();
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
      freePayCountList_.WriteTo(output, _repeated_freePayCountList_codec);
      if (_unknownFields != null) {
        _unknownFields.WriteTo(output);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      size += freePayCountList_.CalculateSize(_repeated_freePayCountList_codec);
      if (_unknownFields != null) {
        size += _unknownFields.CalculateSize();
      }
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(ResUpdateFreeCountProto other) {
      if (other == null) {
        return;
      }
      freePayCountList_.Add(other.freePayCountList_);
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
          case 10: {
            freePayCountList_.AddEntriesFrom(input, _repeated_freePayCountList_codec);
            break;
          }
        }
      }
    }

  }

  #endregion

}

#endregion Designer generated code