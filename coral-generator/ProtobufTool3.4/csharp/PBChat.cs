// <auto-generated>
//     Generated by the protocol buffer compiler.  DO NOT EDIT!
//     source: PBChat.proto
// </auto-generated>
#pragma warning disable 1591, 0612, 3021
#region Designer generated code

using pb = global::Google.Protobuf;
using pbc = global::Google.Protobuf.Collections;
using pbr = global::Google.Protobuf.Reflection;
using scg = global::System.Collections.Generic;
namespace Protocol {

  /// <summary>Holder for reflection information generated from PBChat.proto</summary>
  public static partial class PBChatReflection {

    #region Descriptor
    /// <summary>File descriptor for PBChat.proto</summary>
    public static pbr::FileDescriptor Descriptor {
      get { return descriptor; }
    }
    private static pbr::FileDescriptor descriptor;

    static PBChatReflection() {
      byte[] descriptorData = global::System.Convert.FromBase64String(
          string.Concat(
            "CgxQQkNoYXQucHJvdG8SCFByb3RvY29sGg5QQlBsYXllci5wcm90byKLAQoK",
            "UEJDaGF0SW5mbxIqCgdwcm9maWxlGAEgASgLMhkuUHJvdG9jb2wuUEJQbGF5",
            "ZXJQcm9maWxlEg8KB2NvbnRlbnQYAiABKAkSEAoIc2VuZFRpbWUYAyABKAMS",
            "LgoLcmVjdlByb2ZpbGUYBCABKAsyGS5Qcm90b2NvbC5QQlBsYXllclByb2Zp",
            "bGUiOwoHUmVxQ2hhdBIPCgdjb250ZW50GAEgASgJEg8KB2NoYW5uZWwYAiAB",
            "KAUSDgoGcmVjdklkGAMgASgDIj8KB0Fja0NoYXQSDwoHY2hhbm5lbBgBIAEo",
            "BRIjCgVjaGF0cxgCIAMoCzIULlByb3RvY29sLlBCQ2hhdEluZm9CKAoeY29t",
            "LmNhdC5zZXJ2ZXIuZ2FtZS5kYXRhLnByb3RvQgZQQkNoYXRiBnByb3RvMw=="));
      descriptor = pbr::FileDescriptor.FromGeneratedCode(descriptorData,
          new pbr::FileDescriptor[] { global::Protocol.PBPlayerReflection.Descriptor, },
          new pbr::GeneratedClrTypeInfo(null, null, new pbr::GeneratedClrTypeInfo[] {
            new pbr::GeneratedClrTypeInfo(typeof(global::Protocol.PBChatInfo), global::Protocol.PBChatInfo.Parser, new[]{ "Profile", "Content", "SendTime", "RecvProfile" }, null, null, null, null),
            new pbr::GeneratedClrTypeInfo(typeof(global::Protocol.ReqChat), global::Protocol.ReqChat.Parser, new[]{ "Content", "Channel", "RecvId" }, null, null, null, null),
            new pbr::GeneratedClrTypeInfo(typeof(global::Protocol.AckChat), global::Protocol.AckChat.Parser, new[]{ "Channel", "Chats" }, null, null, null, null)
          }));
    }
    #endregion

  }
  #region Messages
  /// <summary>
  ///聊天
  /// </summary>
  public sealed partial class PBChatInfo : pb::IMessage<PBChatInfo> {
    private static readonly pb::MessageParser<PBChatInfo> _parser = new pb::MessageParser<PBChatInfo>(() => new PBChatInfo());
    private pb::UnknownFieldSet _unknownFields;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<PBChatInfo> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pbr::MessageDescriptor Descriptor {
      get { return global::Protocol.PBChatReflection.Descriptor.MessageTypes[0]; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    pbr::MessageDescriptor pb::IMessage.Descriptor {
      get { return Descriptor; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public PBChatInfo() {
      OnConstruction();
    }

    partial void OnConstruction();

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public PBChatInfo(PBChatInfo other) : this() {
      profile_ = other.profile_ != null ? other.profile_.Clone() : null;
      content_ = other.content_;
      sendTime_ = other.sendTime_;
      recvProfile_ = other.recvProfile_ != null ? other.recvProfile_.Clone() : null;
      _unknownFields = pb::UnknownFieldSet.Clone(other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public PBChatInfo Clone() {
      return new PBChatInfo(this);
    }

    /// <summary>Field number for the "profile" field.</summary>
    public const int ProfileFieldNumber = 1;
    private global::Protocol.PBPlayerProfile profile_;
    /// <summary>
    ///发送者简要信息
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public global::Protocol.PBPlayerProfile Profile {
      get { return profile_; }
      set {
        profile_ = value;
      }
    }

    /// <summary>Field number for the "content" field.</summary>
    public const int ContentFieldNumber = 2;
    private string content_ = "";
    /// <summary>
    ///聊天内容
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public string Content {
      get { return content_; }
      set {
        content_ = pb::ProtoPreconditions.CheckNotNull(value, "value");
      }
    }

    /// <summary>Field number for the "sendTime" field.</summary>
    public const int SendTimeFieldNumber = 3;
    private long sendTime_;
    /// <summary>
    ///发送时间
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public long SendTime {
      get { return sendTime_; }
      set {
        sendTime_ = value;
      }
    }

    /// <summary>Field number for the "recvProfile" field.</summary>
    public const int RecvProfileFieldNumber = 4;
    private global::Protocol.PBPlayerProfile recvProfile_;
    /// <summary>
    ///接收者简要信息,私聊使用
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public global::Protocol.PBPlayerProfile RecvProfile {
      get { return recvProfile_; }
      set {
        recvProfile_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override bool Equals(object other) {
      return Equals(other as PBChatInfo);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Equals(PBChatInfo other) {
      if (ReferenceEquals(other, null)) {
        return false;
      }
      if (ReferenceEquals(other, this)) {
        return true;
      }
      if (!object.Equals(Profile, other.Profile)) return false;
      if (Content != other.Content) return false;
      if (SendTime != other.SendTime) return false;
      if (!object.Equals(RecvProfile, other.RecvProfile)) return false;
      return Equals(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override int GetHashCode() {
      int hash = 1;
      if (profile_ != null) hash ^= Profile.GetHashCode();
      if (Content.Length != 0) hash ^= Content.GetHashCode();
      if (SendTime != 0L) hash ^= SendTime.GetHashCode();
      if (recvProfile_ != null) hash ^= RecvProfile.GetHashCode();
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
      if (profile_ != null) {
        output.WriteRawTag(10);
        output.WriteMessage(Profile);
      }
      if (Content.Length != 0) {
        output.WriteRawTag(18);
        output.WriteString(Content);
      }
      if (SendTime != 0L) {
        output.WriteRawTag(24);
        output.WriteInt64(SendTime);
      }
      if (recvProfile_ != null) {
        output.WriteRawTag(34);
        output.WriteMessage(RecvProfile);
      }
      if (_unknownFields != null) {
        _unknownFields.WriteTo(output);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (profile_ != null) {
        size += 1 + pb::CodedOutputStream.ComputeMessageSize(Profile);
      }
      if (Content.Length != 0) {
        size += 1 + pb::CodedOutputStream.ComputeStringSize(Content);
      }
      if (SendTime != 0L) {
        size += 1 + pb::CodedOutputStream.ComputeInt64Size(SendTime);
      }
      if (recvProfile_ != null) {
        size += 1 + pb::CodedOutputStream.ComputeMessageSize(RecvProfile);
      }
      if (_unknownFields != null) {
        size += _unknownFields.CalculateSize();
      }
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(PBChatInfo other) {
      if (other == null) {
        return;
      }
      if (other.profile_ != null) {
        if (profile_ == null) {
          Profile = new global::Protocol.PBPlayerProfile();
        }
        Profile.MergeFrom(other.Profile);
      }
      if (other.Content.Length != 0) {
        Content = other.Content;
      }
      if (other.SendTime != 0L) {
        SendTime = other.SendTime;
      }
      if (other.recvProfile_ != null) {
        if (recvProfile_ == null) {
          RecvProfile = new global::Protocol.PBPlayerProfile();
        }
        RecvProfile.MergeFrom(other.RecvProfile);
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
          case 10: {
            if (profile_ == null) {
              Profile = new global::Protocol.PBPlayerProfile();
            }
            input.ReadMessage(Profile);
            break;
          }
          case 18: {
            Content = input.ReadString();
            break;
          }
          case 24: {
            SendTime = input.ReadInt64();
            break;
          }
          case 34: {
            if (recvProfile_ == null) {
              RecvProfile = new global::Protocol.PBPlayerProfile();
            }
            input.ReadMessage(RecvProfile);
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///聊天请求
  /// </summary>
  public sealed partial class ReqChat : pb::IMessage<ReqChat> {
    private static readonly pb::MessageParser<ReqChat> _parser = new pb::MessageParser<ReqChat>(() => new ReqChat());
    private pb::UnknownFieldSet _unknownFields;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ReqChat> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pbr::MessageDescriptor Descriptor {
      get { return global::Protocol.PBChatReflection.Descriptor.MessageTypes[1]; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    pbr::MessageDescriptor pb::IMessage.Descriptor {
      get { return Descriptor; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ReqChat() {
      OnConstruction();
    }

    partial void OnConstruction();

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ReqChat(ReqChat other) : this() {
      content_ = other.content_;
      channel_ = other.channel_;
      recvId_ = other.recvId_;
      _unknownFields = pb::UnknownFieldSet.Clone(other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public ReqChat Clone() {
      return new ReqChat(this);
    }

    /// <summary>Field number for the "content" field.</summary>
    public const int ContentFieldNumber = 1;
    private string content_ = "";
    /// <summary>
    ///内容
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public string Content {
      get { return content_; }
      set {
        content_ = pb::ProtoPreconditions.CheckNotNull(value, "value");
      }
    }

    /// <summary>Field number for the "channel" field.</summary>
    public const int ChannelFieldNumber = 2;
    private int channel_;
    /// <summary>
    ///频道
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Channel {
      get { return channel_; }
      set {
        channel_ = value;
      }
    }

    /// <summary>Field number for the "recvId" field.</summary>
    public const int RecvIdFieldNumber = 3;
    private long recvId_;
    /// <summary>
    ///接收者id
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public long RecvId {
      get { return recvId_; }
      set {
        recvId_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override bool Equals(object other) {
      return Equals(other as ReqChat);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Equals(ReqChat other) {
      if (ReferenceEquals(other, null)) {
        return false;
      }
      if (ReferenceEquals(other, this)) {
        return true;
      }
      if (Content != other.Content) return false;
      if (Channel != other.Channel) return false;
      if (RecvId != other.RecvId) return false;
      return Equals(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override int GetHashCode() {
      int hash = 1;
      if (Content.Length != 0) hash ^= Content.GetHashCode();
      if (Channel != 0) hash ^= Channel.GetHashCode();
      if (RecvId != 0L) hash ^= RecvId.GetHashCode();
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
      if (Content.Length != 0) {
        output.WriteRawTag(10);
        output.WriteString(Content);
      }
      if (Channel != 0) {
        output.WriteRawTag(16);
        output.WriteInt32(Channel);
      }
      if (RecvId != 0L) {
        output.WriteRawTag(24);
        output.WriteInt64(RecvId);
      }
      if (_unknownFields != null) {
        _unknownFields.WriteTo(output);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (Content.Length != 0) {
        size += 1 + pb::CodedOutputStream.ComputeStringSize(Content);
      }
      if (Channel != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Channel);
      }
      if (RecvId != 0L) {
        size += 1 + pb::CodedOutputStream.ComputeInt64Size(RecvId);
      }
      if (_unknownFields != null) {
        size += _unknownFields.CalculateSize();
      }
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(ReqChat other) {
      if (other == null) {
        return;
      }
      if (other.Content.Length != 0) {
        Content = other.Content;
      }
      if (other.Channel != 0) {
        Channel = other.Channel;
      }
      if (other.RecvId != 0L) {
        RecvId = other.RecvId;
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
          case 10: {
            Content = input.ReadString();
            break;
          }
          case 16: {
            Channel = input.ReadInt32();
            break;
          }
          case 24: {
            RecvId = input.ReadInt64();
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///响应聊天请求
  /// </summary>
  public sealed partial class AckChat : pb::IMessage<AckChat> {
    private static readonly pb::MessageParser<AckChat> _parser = new pb::MessageParser<AckChat>(() => new AckChat());
    private pb::UnknownFieldSet _unknownFields;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<AckChat> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pbr::MessageDescriptor Descriptor {
      get { return global::Protocol.PBChatReflection.Descriptor.MessageTypes[2]; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    pbr::MessageDescriptor pb::IMessage.Descriptor {
      get { return Descriptor; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public AckChat() {
      OnConstruction();
    }

    partial void OnConstruction();

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public AckChat(AckChat other) : this() {
      channel_ = other.channel_;
      chats_ = other.chats_.Clone();
      _unknownFields = pb::UnknownFieldSet.Clone(other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public AckChat Clone() {
      return new AckChat(this);
    }

    /// <summary>Field number for the "channel" field.</summary>
    public const int ChannelFieldNumber = 1;
    private int channel_;
    /// <summary>
    ///频道： 0=世界, 1=系统；2=家族；3=好友；4=队伍
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Channel {
      get { return channel_; }
      set {
        channel_ = value;
      }
    }

    /// <summary>Field number for the "chats" field.</summary>
    public const int ChatsFieldNumber = 2;
    private static readonly pb::FieldCodec<global::Protocol.PBChatInfo> _repeated_chats_codec
        = pb::FieldCodec.ForMessage(18, global::Protocol.PBChatInfo.Parser);
    private readonly pbc::RepeatedField<global::Protocol.PBChatInfo> chats_ = new pbc::RepeatedField<global::Protocol.PBChatInfo>();
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public pbc::RepeatedField<global::Protocol.PBChatInfo> Chats {
      get { return chats_; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override bool Equals(object other) {
      return Equals(other as AckChat);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Equals(AckChat other) {
      if (ReferenceEquals(other, null)) {
        return false;
      }
      if (ReferenceEquals(other, this)) {
        return true;
      }
      if (Channel != other.Channel) return false;
      if(!chats_.Equals(other.chats_)) return false;
      return Equals(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override int GetHashCode() {
      int hash = 1;
      if (Channel != 0) hash ^= Channel.GetHashCode();
      hash ^= chats_.GetHashCode();
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
      if (Channel != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(Channel);
      }
      chats_.WriteTo(output, _repeated_chats_codec);
      if (_unknownFields != null) {
        _unknownFields.WriteTo(output);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (Channel != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Channel);
      }
      size += chats_.CalculateSize(_repeated_chats_codec);
      if (_unknownFields != null) {
        size += _unknownFields.CalculateSize();
      }
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(AckChat other) {
      if (other == null) {
        return;
      }
      if (other.Channel != 0) {
        Channel = other.Channel;
      }
      chats_.Add(other.chats_);
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
            Channel = input.ReadInt32();
            break;
          }
          case 18: {
            chats_.AddEntriesFrom(input, _repeated_chats_codec);
            break;
          }
        }
      }
    }

  }

  #endregion

}

#endregion Designer generated code