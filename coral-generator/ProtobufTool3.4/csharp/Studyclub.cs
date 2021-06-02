// <auto-generated>
//     Generated by the protocol buffer compiler.  DO NOT EDIT!
//     source: studyclub.proto
// </auto-generated>
#pragma warning disable 1591, 0612, 3021
#region Designer generated code

using pb = global::Google.Protobuf;
using pbc = global::Google.Protobuf.Collections;
using pbr = global::Google.Protobuf.Reflection;
using scg = global::System.Collections.Generic;
namespace Com.Proto.ReddotMessage {

  /// <summary>Holder for reflection information generated from studyclub.proto</summary>
  public static partial class StudyclubReflection {

    #region Descriptor
    /// <summary>File descriptor for studyclub.proto</summary>
    public static pbr::FileDescriptor Descriptor {
      get { return descriptor; }
    }
    private static pbr::FileDescriptor descriptor;

    static StudyclubReflection() {
      byte[] descriptorData = global::System.Convert.FromBase64String(
          string.Concat(
            "Cg9zdHVkeWNsdWIucHJvdG8SF2NvbS5wcm90by5yZWRkb3RNZXNzYWdlIjsK",
            "F1N0dWR5Q2x1YlJld2FyZER0b1Byb3RvEhAKCGNvbmZpZ0lkGAEgASgFEg4K",
            "BnN0YXR1cxgCIAEoBSIXChVSZXFTdHVkeUNsdWJJbmZvUHJvdG8iKwoXUmVx",
            "U3R1ZHlDbHViUmV3YXJkUHJvdG8SEAoIY29uZmlnSWQYASABKAUiiQEKFVJl",
            "c1N0dWR5Q2x1YkluZm9Qcm90bxINCgVsZXZlbBgBIAEoBRILCgNleHAYAiAB",
            "KAUSEQoJZXhjbHVzaXZlGAMgASgIEkEKB3Jld2FyZHMYBCADKAsyMC5jb20u",
            "cHJvdG8ucmVkZG90TWVzc2FnZS5TdHVkeUNsdWJSZXdhcmREdG9Qcm90byIr",
            "ChdSZXNTdHVkeUNsdWJSZXdhcmRQcm90bxIQCghjb25maWdJZBgBIAEoBUI1",
            "CiJjb20uZ2FtZS5tb2R1bGUuc3R1ZHljbHViLnByb3RvY29sQg9TdHVkeUNs",
            "dWJQcm90b3NiBnByb3RvMw=="));
      descriptor = pbr::FileDescriptor.FromGeneratedCode(descriptorData,
          new pbr::FileDescriptor[] { },
          new pbr::GeneratedClrTypeInfo(null, null, new pbr::GeneratedClrTypeInfo[] {
            new pbr::GeneratedClrTypeInfo(typeof(global::Com.Proto.ReddotMessage.StudyClubRewardDtoProto), global::Com.Proto.ReddotMessage.StudyClubRewardDtoProto.Parser, new[]{ "ConfigId", "Status" }, null, null, null, null),
            new pbr::GeneratedClrTypeInfo(typeof(global::Com.Proto.ReddotMessage.ReqStudyClubInfoProto), global::Com.Proto.ReddotMessage.ReqStudyClubInfoProto.Parser, null, null, null, null, null),
            new pbr::GeneratedClrTypeInfo(typeof(global::Com.Proto.ReddotMessage.ReqStudyClubRewardProto), global::Com.Proto.ReddotMessage.ReqStudyClubRewardProto.Parser, new[]{ "ConfigId" }, null, null, null, null),
            new pbr::GeneratedClrTypeInfo(typeof(global::Com.Proto.ReddotMessage.ResStudyClubInfoProto), global::Com.Proto.ReddotMessage.ResStudyClubInfoProto.Parser, new[]{ "Level", "Exp", "Exclusive", "Rewards" }, null, null, null, null),
            new pbr::GeneratedClrTypeInfo(typeof(global::Com.Proto.ReddotMessage.ResStudyClubRewardProto), global::Com.Proto.ReddotMessage.ResStudyClubRewardProto.Parser, new[]{ "ConfigId" }, null, null, null, null)
          }));
    }
    #endregion

  }
  #region Messages
  /// <summary>
  ///研习社奖励信息		msgId=199002
  /// </summary>
  public sealed partial class StudyClubRewardDtoProto : pb::IMessage<StudyClubRewardDtoProto> {
    private static readonly pb::MessageParser<StudyClubRewardDtoProto> _parser = new pb::MessageParser<StudyClubRewardDtoProto>(() => new StudyClubRewardDtoProto());
    private pb::UnknownFieldSet _unknownFields;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<StudyClubRewardDtoProto> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pbr::MessageDescriptor Descriptor {
      get { return global::Com.Proto.ReddotMessage.StudyclubReflection.Descriptor.MessageTypes[0]; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    pbr::MessageDescriptor pb::IMessage.Descriptor {
      get { return Descriptor; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public StudyClubRewardDtoProto() {
      OnConstruction();
    }

    partial void OnConstruction();

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public StudyClubRewardDtoProto(StudyClubRewardDtoProto other) : this() {
      configId_ = other.configId_;
      status_ = other.status_;
      _unknownFields = pb::UnknownFieldSet.Clone(other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public StudyClubRewardDtoProto Clone() {
      return new StudyClubRewardDtoProto(this);
    }

    /// <summary>Field number for the "configId" field.</summary>
    public const int ConfigIdFieldNumber = 1;
    private int configId_;
    /// <summary>
    ///奖励配置id
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int ConfigId {
      get { return configId_; }
      set {
        configId_ = value;
      }
    }

    /// <summary>Field number for the "status" field.</summary>
    public const int StatusFieldNumber = 2;
    private int status_;
    /// <summary>
    ///奖励状态,状态组合通过位运算实现 1:未达成普通, 2:已达成普通未领取, 4:已领取普通奖励, 8:未达成专属,16:已达成专属未领取,32:全部领取
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Status {
      get { return status_; }
      set {
        status_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override bool Equals(object other) {
      return Equals(other as StudyClubRewardDtoProto);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Equals(StudyClubRewardDtoProto other) {
      if (ReferenceEquals(other, null)) {
        return false;
      }
      if (ReferenceEquals(other, this)) {
        return true;
      }
      if (ConfigId != other.ConfigId) return false;
      if (Status != other.Status) return false;
      return Equals(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override int GetHashCode() {
      int hash = 1;
      if (ConfigId != 0) hash ^= ConfigId.GetHashCode();
      if (Status != 0) hash ^= Status.GetHashCode();
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
      if (ConfigId != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(ConfigId);
      }
      if (Status != 0) {
        output.WriteRawTag(16);
        output.WriteInt32(Status);
      }
      if (_unknownFields != null) {
        _unknownFields.WriteTo(output);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (ConfigId != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(ConfigId);
      }
      if (Status != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Status);
      }
      if (_unknownFields != null) {
        size += _unknownFields.CalculateSize();
      }
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(StudyClubRewardDtoProto other) {
      if (other == null) {
        return;
      }
      if (other.ConfigId != 0) {
        ConfigId = other.ConfigId;
      }
      if (other.Status != 0) {
        Status = other.Status;
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
            ConfigId = input.ReadInt32();
            break;
          }
          case 16: {
            Status = input.ReadInt32();
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///请求研习社信息		msgId=199003
  /// </summary>
  public sealed partial class ReqStudyClubInfoProto : pb::IMessage<ReqStudyClubInfoProto> {
    private static readonly pb::MessageParser<ReqStudyClubInfoProto> _parser = new pb::MessageParser<ReqStudyClubInfoProto>(() => new ReqStudyClubInfoProto());
    private pb::UnknownFieldSet _unknownFields;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ReqStudyClubInfoProto> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pbr::MessageDescriptor Descriptor {
      get { return global::Com.Proto.ReddotMessage.StudyclubReflection.Descriptor.MessageTypes[1]; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    pbr::MessageDescriptor pb::IMessage.Descriptor {
      get { return Descriptor; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ReqStudyClubInfoProto() {
      OnConstruction();
    }

    partial void OnConstruction();

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ReqStudyClubInfoProto(ReqStudyClubInfoProto other) : this() {
      _unknownFields = pb::UnknownFieldSet.Clone(other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ReqStudyClubInfoProto Clone() {
      return new ReqStudyClubInfoProto(this);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override bool Equals(object other) {
      return Equals(other as ReqStudyClubInfoProto);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Equals(ReqStudyClubInfoProto other) {
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
    public void MergeFrom(ReqStudyClubInfoProto other) {
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
  ///请求领取研习社奖励	msgId=199004
  /// </summary>
  public sealed partial class ReqStudyClubRewardProto : pb::IMessage<ReqStudyClubRewardProto> {
    private static readonly pb::MessageParser<ReqStudyClubRewardProto> _parser = new pb::MessageParser<ReqStudyClubRewardProto>(() => new ReqStudyClubRewardProto());
    private pb::UnknownFieldSet _unknownFields;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ReqStudyClubRewardProto> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pbr::MessageDescriptor Descriptor {
      get { return global::Com.Proto.ReddotMessage.StudyclubReflection.Descriptor.MessageTypes[2]; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    pbr::MessageDescriptor pb::IMessage.Descriptor {
      get { return Descriptor; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ReqStudyClubRewardProto() {
      OnConstruction();
    }

    partial void OnConstruction();

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ReqStudyClubRewardProto(ReqStudyClubRewardProto other) : this() {
      configId_ = other.configId_;
      _unknownFields = pb::UnknownFieldSet.Clone(other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ReqStudyClubRewardProto Clone() {
      return new ReqStudyClubRewardProto(this);
    }

    /// <summary>Field number for the "configId" field.</summary>
    public const int ConfigIdFieldNumber = 1;
    private int configId_;
    /// <summary>
    ///领取的配置id
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int ConfigId {
      get { return configId_; }
      set {
        configId_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override bool Equals(object other) {
      return Equals(other as ReqStudyClubRewardProto);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Equals(ReqStudyClubRewardProto other) {
      if (ReferenceEquals(other, null)) {
        return false;
      }
      if (ReferenceEquals(other, this)) {
        return true;
      }
      if (ConfigId != other.ConfigId) return false;
      return Equals(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override int GetHashCode() {
      int hash = 1;
      if (ConfigId != 0) hash ^= ConfigId.GetHashCode();
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
      if (ConfigId != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(ConfigId);
      }
      if (_unknownFields != null) {
        _unknownFields.WriteTo(output);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (ConfigId != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(ConfigId);
      }
      if (_unknownFields != null) {
        size += _unknownFields.CalculateSize();
      }
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(ReqStudyClubRewardProto other) {
      if (other == null) {
        return;
      }
      if (other.ConfigId != 0) {
        ConfigId = other.ConfigId;
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
            ConfigId = input.ReadInt32();
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///返回研习社信息 		msgId=199005
  /// </summary>
  public sealed partial class ResStudyClubInfoProto : pb::IMessage<ResStudyClubInfoProto> {
    private static readonly pb::MessageParser<ResStudyClubInfoProto> _parser = new pb::MessageParser<ResStudyClubInfoProto>(() => new ResStudyClubInfoProto());
    private pb::UnknownFieldSet _unknownFields;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ResStudyClubInfoProto> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pbr::MessageDescriptor Descriptor {
      get { return global::Com.Proto.ReddotMessage.StudyclubReflection.Descriptor.MessageTypes[3]; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    pbr::MessageDescriptor pb::IMessage.Descriptor {
      get { return Descriptor; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ResStudyClubInfoProto() {
      OnConstruction();
    }

    partial void OnConstruction();

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ResStudyClubInfoProto(ResStudyClubInfoProto other) : this() {
      level_ = other.level_;
      exp_ = other.exp_;
      exclusive_ = other.exclusive_;
      rewards_ = other.rewards_.Clone();
      _unknownFields = pb::UnknownFieldSet.Clone(other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ResStudyClubInfoProto Clone() {
      return new ResStudyClubInfoProto(this);
    }

    /// <summary>Field number for the "level" field.</summary>
    public const int LevelFieldNumber = 1;
    private int level_;
    /// <summary>
    ///研习等级
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Level {
      get { return level_; }
      set {
        level_ = value;
      }
    }

    /// <summary>Field number for the "exp" field.</summary>
    public const int ExpFieldNumber = 2;
    private int exp_;
    /// <summary>
    ///研习经验,>0则表示当前经验,否则表示已满.
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Exp {
      get { return exp_; }
      set {
        exp_ = value;
      }
    }

    /// <summary>Field number for the "exclusive" field.</summary>
    public const int ExclusiveFieldNumber = 3;
    private bool exclusive_;
    /// <summary>
    ///是否购买了专属研习
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Exclusive {
      get { return exclusive_; }
      set {
        exclusive_ = value;
      }
    }

    /// <summary>Field number for the "rewards" field.</summary>
    public const int RewardsFieldNumber = 4;
    private static readonly pb::FieldCodec<global::Com.Proto.ReddotMessage.StudyClubRewardDtoProto> _repeated_rewards_codec
        = pb::FieldCodec.ForMessage(34, global::Com.Proto.ReddotMessage.StudyClubRewardDtoProto.Parser);
    private readonly pbc::RepeatedField<global::Com.Proto.ReddotMessage.StudyClubRewardDtoProto> rewards_ = new pbc::RepeatedField<global::Com.Proto.ReddotMessage.StudyClubRewardDtoProto>();
    /// <summary>
    ///玩家研习社奖励信息
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public pbc::RepeatedField<global::Com.Proto.ReddotMessage.StudyClubRewardDtoProto> Rewards {
      get { return rewards_; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override bool Equals(object other) {
      return Equals(other as ResStudyClubInfoProto);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Equals(ResStudyClubInfoProto other) {
      if (ReferenceEquals(other, null)) {
        return false;
      }
      if (ReferenceEquals(other, this)) {
        return true;
      }
      if (Level != other.Level) return false;
      if (Exp != other.Exp) return false;
      if (Exclusive != other.Exclusive) return false;
      if(!rewards_.Equals(other.rewards_)) return false;
      return Equals(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override int GetHashCode() {
      int hash = 1;
      if (Level != 0) hash ^= Level.GetHashCode();
      if (Exp != 0) hash ^= Exp.GetHashCode();
      if (Exclusive != false) hash ^= Exclusive.GetHashCode();
      hash ^= rewards_.GetHashCode();
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
      if (Level != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(Level);
      }
      if (Exp != 0) {
        output.WriteRawTag(16);
        output.WriteInt32(Exp);
      }
      if (Exclusive != false) {
        output.WriteRawTag(24);
        output.WriteBool(Exclusive);
      }
      rewards_.WriteTo(output, _repeated_rewards_codec);
      if (_unknownFields != null) {
        _unknownFields.WriteTo(output);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (Level != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Level);
      }
      if (Exp != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Exp);
      }
      if (Exclusive != false) {
        size += 1 + 1;
      }
      size += rewards_.CalculateSize(_repeated_rewards_codec);
      if (_unknownFields != null) {
        size += _unknownFields.CalculateSize();
      }
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(ResStudyClubInfoProto other) {
      if (other == null) {
        return;
      }
      if (other.Level != 0) {
        Level = other.Level;
      }
      if (other.Exp != 0) {
        Exp = other.Exp;
      }
      if (other.Exclusive != false) {
        Exclusive = other.Exclusive;
      }
      rewards_.Add(other.rewards_);
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
            Level = input.ReadInt32();
            break;
          }
          case 16: {
            Exp = input.ReadInt32();
            break;
          }
          case 24: {
            Exclusive = input.ReadBool();
            break;
          }
          case 34: {
            rewards_.AddEntriesFrom(input, _repeated_rewards_codec);
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///返回领取研习社奖励信息 msgId=199006
  /// </summary>
  public sealed partial class ResStudyClubRewardProto : pb::IMessage<ResStudyClubRewardProto> {
    private static readonly pb::MessageParser<ResStudyClubRewardProto> _parser = new pb::MessageParser<ResStudyClubRewardProto>(() => new ResStudyClubRewardProto());
    private pb::UnknownFieldSet _unknownFields;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ResStudyClubRewardProto> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pbr::MessageDescriptor Descriptor {
      get { return global::Com.Proto.ReddotMessage.StudyclubReflection.Descriptor.MessageTypes[4]; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    pbr::MessageDescriptor pb::IMessage.Descriptor {
      get { return Descriptor; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ResStudyClubRewardProto() {
      OnConstruction();
    }

    partial void OnConstruction();

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ResStudyClubRewardProto(ResStudyClubRewardProto other) : this() {
      configId_ = other.configId_;
      _unknownFields = pb::UnknownFieldSet.Clone(other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ResStudyClubRewardProto Clone() {
      return new ResStudyClubRewardProto(this);
    }

    /// <summary>Field number for the "configId" field.</summary>
    public const int ConfigIdFieldNumber = 1;
    private int configId_;
    /// <summary>
    ///领取的配置id
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int ConfigId {
      get { return configId_; }
      set {
        configId_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override bool Equals(object other) {
      return Equals(other as ResStudyClubRewardProto);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Equals(ResStudyClubRewardProto other) {
      if (ReferenceEquals(other, null)) {
        return false;
      }
      if (ReferenceEquals(other, this)) {
        return true;
      }
      if (ConfigId != other.ConfigId) return false;
      return Equals(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override int GetHashCode() {
      int hash = 1;
      if (ConfigId != 0) hash ^= ConfigId.GetHashCode();
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
      if (ConfigId != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(ConfigId);
      }
      if (_unknownFields != null) {
        _unknownFields.WriteTo(output);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (ConfigId != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(ConfigId);
      }
      if (_unknownFields != null) {
        size += _unknownFields.CalculateSize();
      }
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(ResStudyClubRewardProto other) {
      if (other == null) {
        return;
      }
      if (other.ConfigId != 0) {
        ConfigId = other.ConfigId;
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
            ConfigId = input.ReadInt32();
            break;
          }
        }
      }
    }

  }

  #endregion

}

#endregion Designer generated code