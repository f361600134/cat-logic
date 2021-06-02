// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: PBStarRiver.proto
#pragma warning disable 1591, 0612, 3021
#region Designer generated code

using pb = global::Google.Protobuf;
using pbc = global::Google.Protobuf.Collections;
using scg = global::System.Collections.Generic;
namespace Protocol {

  #region Messages
  /// <summary>
  //////////////////////星河神殿//////////////////////
  ///星河神殿神位信息
  /// </summary>
  public sealed class StarRiverInfo : pb::IMessage {
    private static readonly pb::MessageParser<StarRiverInfo> _parser = new pb::MessageParser<StarRiverInfo>(() => new StarRiverInfo());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<StarRiverInfo> Parser { get { return _parser; } }

    /// <summary>Field number for the "configId" field.</summary>
    public const int ConfigIdFieldNumber = 1;
    private int configId_;
    /// <summary>
    ///配置id
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int ConfigId {
      get { return configId_; }
      set {
        configId_ = value;
      }
    }

    /// <summary>Field number for the "name" field.</summary>
    public const int NameFieldNumber = 2;
    private string name_ = "";
    /// <summary>
    ///昵称
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public string Name {
      get { return name_; }
      set {
        name_ = pb::ProtoPreconditions.CheckNotNull(value, "value");
      }
    }

    /// <summary>Field number for the "playerImageId" field.</summary>
    public const int PlayerImageIdFieldNumber = 3;
    private int playerImageId_;
    /// <summary>
    ///玩家形象id
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int PlayerImageId {
      get { return playerImageId_; }
      set {
        playerImageId_ = value;
      }
    }

    /// <summary>Field number for the "level" field.</summary>
    public const int LevelFieldNumber = 4;
    private int level_;
    /// <summary>
    ///玩家等级
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Level {
      get { return level_; }
      set {
        level_ = value;
      }
    }

    /// <summary>Field number for the "headImageId" field.</summary>
    public const int HeadImageIdFieldNumber = 5;
    private int headImageId_;
    /// <summary>
    ///头像id
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int HeadImageId {
      get { return headImageId_; }
      set {
        headImageId_ = value;
      }
    }

    /// <summary>Field number for the "evolutionTimes" field.</summary>
    public const int EvolutionTimesFieldNumber = 6;
    private int evolutionTimes_;
    /// <summary>
    ///进化次数
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int EvolutionTimes {
      get { return evolutionTimes_; }
      set {
        evolutionTimes_ = value;
      }
    }

    /// <summary>Field number for the "stageConfigId" field.</summary>
    public const int StageConfigIdFieldNumber = 7;
    private int stageConfigId_;
    /// <summary>
    ///阶数配置id
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int StageConfigId {
      get { return stageConfigId_; }
      set {
        stageConfigId_ = value;
      }
    }

    /// <summary>Field number for the "headFrameId" field.</summary>
    public const int HeadFrameIdFieldNumber = 8;
    private int headFrameId_;
    /// <summary>
    ///头像外框ID
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int HeadFrameId {
      get { return headFrameId_; }
      set {
        headFrameId_ = value;
      }
    }

    /// <summary>Field number for the "playerId" field.</summary>
    public const int PlayerIdFieldNumber = 9;
    private long playerId_;
    /// <summary>
    ///玩家id
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public long PlayerId {
      get { return playerId_; }
      set {
        playerId_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (ConfigId != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(ConfigId);
      }
      if (Name.Length != 0) {
        output.WriteRawTag(18);
        output.WriteString(Name);
      }
      if (PlayerImageId != 0) {
        output.WriteRawTag(24);
        output.WriteInt32(PlayerImageId);
      }
      if (Level != 0) {
        output.WriteRawTag(32);
        output.WriteInt32(Level);
      }
      if (HeadImageId != 0) {
        output.WriteRawTag(40);
        output.WriteInt32(HeadImageId);
      }
      if (EvolutionTimes != 0) {
        output.WriteRawTag(48);
        output.WriteInt32(EvolutionTimes);
      }
      if (StageConfigId != 0) {
        output.WriteRawTag(56);
        output.WriteInt32(StageConfigId);
      }
      if (HeadFrameId != 0) {
        output.WriteRawTag(64);
        output.WriteInt32(HeadFrameId);
      }
      if (PlayerId != 0L) {
        output.WriteRawTag(72);
        output.WriteInt64(PlayerId);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (ConfigId != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(ConfigId);
      }
      if (Name.Length != 0) {
        size += 1 + pb::CodedOutputStream.ComputeStringSize(Name);
      }
      if (PlayerImageId != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(PlayerImageId);
      }
      if (Level != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Level);
      }
      if (HeadImageId != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(HeadImageId);
      }
      if (EvolutionTimes != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(EvolutionTimes);
      }
      if (StageConfigId != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(StageConfigId);
      }
      if (HeadFrameId != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(HeadFrameId);
      }
      if (PlayerId != 0L) {
        size += 1 + pb::CodedOutputStream.ComputeInt64Size(PlayerId);
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
          case 18: {
            Name = input.ReadString();
            break;
          }
          case 24: {
            PlayerImageId = input.ReadInt32();
            break;
          }
          case 32: {
            Level = input.ReadInt32();
            break;
          }
          case 40: {
            HeadImageId = input.ReadInt32();
            break;
          }
          case 48: {
            EvolutionTimes = input.ReadInt32();
            break;
          }
          case 56: {
            StageConfigId = input.ReadInt32();
            break;
          }
          case 64: {
            HeadFrameId = input.ReadInt32();
            break;
          }
          case 72: {
            PlayerId = input.ReadInt64();
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///挑战记录
  /// </summary>
  public sealed class StarRiverLog : pb::IMessage {
    private static readonly pb::MessageParser<StarRiverLog> _parser = new pb::MessageParser<StarRiverLog>(() => new StarRiverLog());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<StarRiverLog> Parser { get { return _parser; } }

    /// <summary>Field number for the "name" field.</summary>
    public const int NameFieldNumber = 1;
    private string name_ = "";
    /// <summary>
    ///昵称
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public string Name {
      get { return name_; }
      set {
        name_ = pb::ProtoPreconditions.CheckNotNull(value, "value");
      }
    }

    /// <summary>Field number for the "strengthenNum" field.</summary>
    public const int StrengthenNumFieldNumber = 2;
    private int strengthenNum_;
    /// <summary>
    ///强化次数
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int StrengthenNum {
      get { return strengthenNum_; }
      set {
        strengthenNum_ = value;
      }
    }

    /// <summary>Field number for the "time" field.</summary>
    public const int TimeFieldNumber = 3;
    private int time_;
    /// <summary>
    ///时间
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Time {
      get { return time_; }
      set {
        time_ = value;
      }
    }

    /// <summary>Field number for the "power" field.</summary>
    public const int PowerFieldNumber = 4;
    private int power_;
    /// <summary>
    ///战力
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Power {
      get { return power_; }
      set {
        power_ = value;
      }
    }

    /// <summary>Field number for the "formation" field.</summary>
    public const int FormationFieldNumber = 5;
    private int formation_;
    /// <summary>
    ///阵型
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Formation {
      get { return formation_; }
      set {
        formation_ = value;
      }
    }

    /// <summary>Field number for the "videoId" field.</summary>
    public const int VideoIdFieldNumber = 6;
    private long videoId_;
    /// <summary>
    ///录像id
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public long VideoId {
      get { return videoId_; }
      set {
        videoId_ = value;
      }
    }

    /// <summary>Field number for the "heroInfo" field.</summary>
    public const int HeroInfoFieldNumber = 7;
    private static readonly pb::FieldCodec<global::Protocol.HeroInfo> _repeated_heroInfo_codec
        = pb::FieldCodec.ForMessage(58, global::Protocol.HeroInfo.Parser);
    private readonly pbc::RepeatedField<global::Protocol.HeroInfo> heroInfo_ = new pbc::RepeatedField<global::Protocol.HeroInfo>();
    /// <summary>
    ///英雄简易信息
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public pbc::RepeatedField<global::Protocol.HeroInfo> HeroInfo {
      get { return heroInfo_; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (Name.Length != 0) {
        output.WriteRawTag(10);
        output.WriteString(Name);
      }
      if (StrengthenNum != 0) {
        output.WriteRawTag(16);
        output.WriteInt32(StrengthenNum);
      }
      if (Time != 0) {
        output.WriteRawTag(24);
        output.WriteInt32(Time);
      }
      if (Power != 0) {
        output.WriteRawTag(32);
        output.WriteInt32(Power);
      }
      if (Formation != 0) {
        output.WriteRawTag(40);
        output.WriteInt32(Formation);
      }
      if (VideoId != 0L) {
        output.WriteRawTag(48);
        output.WriteInt64(VideoId);
      }
      heroInfo_.WriteTo(output, _repeated_heroInfo_codec);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (Name.Length != 0) {
        size += 1 + pb::CodedOutputStream.ComputeStringSize(Name);
      }
      if (StrengthenNum != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(StrengthenNum);
      }
      if (Time != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Time);
      }
      if (Power != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Power);
      }
      if (Formation != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Formation);
      }
      if (VideoId != 0L) {
        size += 1 + pb::CodedOutputStream.ComputeInt64Size(VideoId);
      }
      size += heroInfo_.CalculateSize(_repeated_heroInfo_codec);
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
            Name = input.ReadString();
            break;
          }
          case 16: {
            StrengthenNum = input.ReadInt32();
            break;
          }
          case 24: {
            Time = input.ReadInt32();
            break;
          }
          case 32: {
            Power = input.ReadInt32();
            break;
          }
          case 40: {
            Formation = input.ReadInt32();
            break;
          }
          case 48: {
            VideoId = input.ReadInt64();
            break;
          }
          case 58: {
            heroInfo_.AddEntriesFrom(input, _repeated_heroInfo_codec);
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///请求星河神殿页面
  /// </summary>
  public sealed class ReqStarRiverPage : pb::IMessage {
    private static readonly pb::MessageParser<ReqStarRiverPage> _parser = new pb::MessageParser<ReqStarRiverPage>(() => new ReqStarRiverPage());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ReqStarRiverPage> Parser { get { return _parser; } }

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
  ///返回星河神殿页面
  /// </summary>
  public sealed class AckStarRiverPage : pb::IMessage {
    private static readonly pb::MessageParser<AckStarRiverPage> _parser = new pb::MessageParser<AckStarRiverPage>(() => new AckStarRiverPage());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<AckStarRiverPage> Parser { get { return _parser; } }

    /// <summary>Field number for the "rank" field.</summary>
    public const int RankFieldNumber = 1;
    private int rank_;
    /// <summary>
    ///竞技场排名
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Rank {
      get { return rank_; }
      set {
        rank_ = value;
      }
    }

    /// <summary>Field number for the "info" field.</summary>
    public const int InfoFieldNumber = 2;
    private static readonly pb::FieldCodec<global::Protocol.StarRiverInfo> _repeated_info_codec
        = pb::FieldCodec.ForMessage(18, global::Protocol.StarRiverInfo.Parser);
    private readonly pbc::RepeatedField<global::Protocol.StarRiverInfo> info_ = new pbc::RepeatedField<global::Protocol.StarRiverInfo>();
    /// <summary>
    ///神位信息
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public pbc::RepeatedField<global::Protocol.StarRiverInfo> Info {
      get { return info_; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (Rank != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(Rank);
      }
      info_.WriteTo(output, _repeated_info_codec);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (Rank != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Rank);
      }
      size += info_.CalculateSize(_repeated_info_codec);
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
            Rank = input.ReadInt32();
            break;
          }
          case 18: {
            info_.AddEntriesFrom(input, _repeated_info_codec);
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///请求挑战记录
  /// </summary>
  public sealed class ReqStarRiverLog : pb::IMessage {
    private static readonly pb::MessageParser<ReqStarRiverLog> _parser = new pb::MessageParser<ReqStarRiverLog>(() => new ReqStarRiverLog());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ReqStarRiverLog> Parser { get { return _parser; } }

    /// <summary>Field number for the "configId" field.</summary>
    public const int ConfigIdFieldNumber = 1;
    private int configId_;
    /// <summary>
    ///配置id
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int ConfigId {
      get { return configId_; }
      set {
        configId_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (ConfigId != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(ConfigId);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (ConfigId != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(ConfigId);
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
        }
      }
    }

  }

  /// <summary>
  ///返回挑战记录
  /// </summary>
  public sealed class AckStarRiverLog : pb::IMessage {
    private static readonly pb::MessageParser<AckStarRiverLog> _parser = new pb::MessageParser<AckStarRiverLog>(() => new AckStarRiverLog());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<AckStarRiverLog> Parser { get { return _parser; } }

    /// <summary>Field number for the "log" field.</summary>
    public const int LogFieldNumber = 1;
    private static readonly pb::FieldCodec<global::Protocol.StarRiverLog> _repeated_log_codec
        = pb::FieldCodec.ForMessage(10, global::Protocol.StarRiverLog.Parser);
    private readonly pbc::RepeatedField<global::Protocol.StarRiverLog> log_ = new pbc::RepeatedField<global::Protocol.StarRiverLog>();
    /// <summary>
    ///挑战记录
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public pbc::RepeatedField<global::Protocol.StarRiverLog> Log {
      get { return log_; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      log_.WriteTo(output, _repeated_log_codec);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      size += log_.CalculateSize(_repeated_log_codec);
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
            log_.AddEntriesFrom(input, _repeated_log_codec);
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///请求挑战
  /// </summary>
  public sealed class ReqStarRiverBattle : pb::IMessage {
    private static readonly pb::MessageParser<ReqStarRiverBattle> _parser = new pb::MessageParser<ReqStarRiverBattle>(() => new ReqStarRiverBattle());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ReqStarRiverBattle> Parser { get { return _parser; } }

    /// <summary>Field number for the "configId" field.</summary>
    public const int ConfigIdFieldNumber = 1;
    private int configId_;
    /// <summary>
    ///配置id
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int ConfigId {
      get { return configId_; }
      set {
        configId_ = value;
      }
    }

    /// <summary>Field number for the "times" field.</summary>
    public const int TimesFieldNumber = 2;
    private int times_;
    /// <summary>
    ///进化次数
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Times {
      get { return times_; }
      set {
        times_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (ConfigId != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(ConfigId);
      }
      if (Times != 0) {
        output.WriteRawTag(16);
        output.WriteInt32(Times);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (ConfigId != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(ConfigId);
      }
      if (Times != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Times);
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
            Times = input.ReadInt32();
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///请求挑战
  /// </summary>
  public sealed class AckStarRiverBattle : pb::IMessage {
    private static readonly pb::MessageParser<AckStarRiverBattle> _parser = new pb::MessageParser<AckStarRiverBattle>(() => new AckStarRiverBattle());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<AckStarRiverBattle> Parser { get { return _parser; } }

    /// <summary>Field number for the "code" field.</summary>
    public const int CodeFieldNumber = 1;
    private int code_;
    /// <summary>
    ///错误码
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Code {
      get { return code_; }
      set {
        code_ = value;
      }
    }

    /// <summary>Field number for the "cd" field.</summary>
    public const int CdFieldNumber = 2;
    private int cd_;
    /// <summary>
    ///冷却时间
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Cd {
      get { return cd_; }
      set {
        cd_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (Code != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(Code);
      }
      if (Cd != 0) {
        output.WriteRawTag(16);
        output.WriteInt32(Cd);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (Code != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Code);
      }
      if (Cd != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Cd);
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
          case 16: {
            Cd = input.ReadInt32();
            break;
          }
        }
      }
    }

  }

  #endregion

}

#endregion Designer generated code