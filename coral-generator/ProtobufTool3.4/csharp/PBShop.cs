// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: PBShop.proto
#pragma warning disable 1591, 0612, 3021
#region Designer generated code

using pb = global::Google.Protobuf;
using pbc = global::Google.Protobuf.Collections;
using scg = global::System.Collections.Generic;
namespace Protocol {

  #region Messages
  public sealed class PBShopItem : pb::IMessage {
    private static readonly pb::MessageParser<PBShopItem> _parser = new pb::MessageParser<PBShopItem>(() => new PBShopItem());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<PBShopItem> Parser { get { return _parser; } }

    /// <summary>Field number for the "configId" field.</summary>
    public const int ConfigIdFieldNumber = 1;
    private int configId_;
    /// <summary>
    ///商店配置ID, 不是道具id
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int ConfigId {
      get { return configId_; }
      set {
        configId_ = value;
      }
    }

    /// <summary>Field number for the "buyCount" field.</summary>
    public const int BuyCountFieldNumber = 2;
    private int buyCount_;
    /// <summary>
    ///已购买数量
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int BuyCount {
      get { return buyCount_; }
      set {
        buyCount_ = value;
      }
    }

    /// <summary>Field number for the "state" field.</summary>
    public const int StateFieldNumber = 3;
    private int state_;
    /// <summary>
    ///状态：0=未解锁,1=已解锁,2售罄
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int State {
      get { return state_; }
      set {
        state_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (ConfigId != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(ConfigId);
      }
      if (BuyCount != 0) {
        output.WriteRawTag(16);
        output.WriteInt32(BuyCount);
      }
      if (State != 0) {
        output.WriteRawTag(24);
        output.WriteInt32(State);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (ConfigId != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(ConfigId);
      }
      if (BuyCount != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(BuyCount);
      }
      if (State != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(State);
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
            BuyCount = input.ReadInt32();
            break;
          }
          case 24: {
            State = input.ReadInt32();
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///请求商品列表
  /// </summary>
  public sealed class ReqShopList : pb::IMessage {
    private static readonly pb::MessageParser<ReqShopList> _parser = new pb::MessageParser<ReqShopList>(() => new ReqShopList());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ReqShopList> Parser { get { return _parser; } }

    /// <summary>Field number for the "shopType" field.</summary>
    public const int ShopTypeFieldNumber = 1;
    private int shopType_;
    /// <summary>
    ///商店类型
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int ShopType {
      get { return shopType_; }
      set {
        shopType_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (ShopType != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(ShopType);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (ShopType != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(ShopType);
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
            ShopType = input.ReadInt32();
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///返回商品列表
  /// </summary>
  public sealed class AckShopList : pb::IMessage {
    private static readonly pb::MessageParser<AckShopList> _parser = new pb::MessageParser<AckShopList>(() => new AckShopList());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<AckShopList> Parser { get { return _parser; } }

    /// <summary>Field number for the "shopType" field.</summary>
    public const int ShopTypeFieldNumber = 2;
    private int shopType_;
    /// <summary>
    ///int32 code = 1;					//错误码
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int ShopType {
      get { return shopType_; }
      set {
        shopType_ = value;
      }
    }

    /// <summary>Field number for the "itemList" field.</summary>
    public const int ItemListFieldNumber = 3;
    private static readonly pb::FieldCodec<global::Protocol.PBShopItem> _repeated_itemList_codec
        = pb::FieldCodec.ForMessage(26, global::Protocol.PBShopItem.Parser);
    private readonly pbc::RepeatedField<global::Protocol.PBShopItem> itemList_ = new pbc::RepeatedField<global::Protocol.PBShopItem>();
    /// <summary>
    ///商店物品列表
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public pbc::RepeatedField<global::Protocol.PBShopItem> ItemList {
      get { return itemList_; }
    }

    /// <summary>Field number for the "refreshTick" field.</summary>
    public const int RefreshTickFieldNumber = 4;
    private int refreshTick_;
    /// <summary>
    ///刷新倒计时
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int RefreshTick {
      get { return refreshTick_; }
      set {
        refreshTick_ = value;
      }
    }

    /// <summary>Field number for the "refreshFreeRemain" field.</summary>
    public const int RefreshFreeRemainFieldNumber = 5;
    private int refreshFreeRemain_;
    /// <summary>
    ///免费刷新的剩余次数
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int RefreshFreeRemain {
      get { return refreshFreeRemain_; }
      set {
        refreshFreeRemain_ = value;
      }
    }

    /// <summary>Field number for the "refreshCostCount" field.</summary>
    public const int RefreshCostCountFieldNumber = 6;
    private int refreshCostCount_;
    /// <summary>
    ///花钱的刷新次数
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int RefreshCostCount {
      get { return refreshCostCount_; }
      set {
        refreshCostCount_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (ShopType != 0) {
        output.WriteRawTag(16);
        output.WriteInt32(ShopType);
      }
      itemList_.WriteTo(output, _repeated_itemList_codec);
      if (RefreshTick != 0) {
        output.WriteRawTag(32);
        output.WriteInt32(RefreshTick);
      }
      if (RefreshFreeRemain != 0) {
        output.WriteRawTag(40);
        output.WriteInt32(RefreshFreeRemain);
      }
      if (RefreshCostCount != 0) {
        output.WriteRawTag(48);
        output.WriteInt32(RefreshCostCount);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (ShopType != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(ShopType);
      }
      size += itemList_.CalculateSize(_repeated_itemList_codec);
      if (RefreshTick != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(RefreshTick);
      }
      if (RefreshFreeRemain != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(RefreshFreeRemain);
      }
      if (RefreshCostCount != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(RefreshCostCount);
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
          case 16: {
            ShopType = input.ReadInt32();
            break;
          }
          case 26: {
            itemList_.AddEntriesFrom(input, _repeated_itemList_codec);
            break;
          }
          case 32: {
            RefreshTick = input.ReadInt32();
            break;
          }
          case 40: {
            RefreshFreeRemain = input.ReadInt32();
            break;
          }
          case 48: {
            RefreshCostCount = input.ReadInt32();
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///请求购买物品
  /// </summary>
  public sealed class ReqShopBuyItem : pb::IMessage {
    private static readonly pb::MessageParser<ReqShopBuyItem> _parser = new pb::MessageParser<ReqShopBuyItem>(() => new ReqShopBuyItem());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ReqShopBuyItem> Parser { get { return _parser; } }

    /// <summary>Field number for the "shopType" field.</summary>
    public const int ShopTypeFieldNumber = 1;
    private int shopType_;
    /// <summary>
    ///商店类型
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int ShopType {
      get { return shopType_; }
      set {
        shopType_ = value;
      }
    }

    /// <summary>Field number for the "configId" field.</summary>
    public const int ConfigIdFieldNumber = 2;
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

    /// <summary>Field number for the "count" field.</summary>
    public const int CountFieldNumber = 3;
    private int count_;
    /// <summary>
    ///数量
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Count {
      get { return count_; }
      set {
        count_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (ShopType != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(ShopType);
      }
      if (ConfigId != 0) {
        output.WriteRawTag(16);
        output.WriteInt32(ConfigId);
      }
      if (Count != 0) {
        output.WriteRawTag(24);
        output.WriteInt32(Count);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (ShopType != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(ShopType);
      }
      if (ConfigId != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(ConfigId);
      }
      if (Count != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Count);
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
            ShopType = input.ReadInt32();
            break;
          }
          case 16: {
            ConfigId = input.ReadInt32();
            break;
          }
          case 24: {
            Count = input.ReadInt32();
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///返回购买物品
  /// </summary>
  public sealed class AckShopBuyItem : pb::IMessage {
    private static readonly pb::MessageParser<AckShopBuyItem> _parser = new pb::MessageParser<AckShopBuyItem>(() => new AckShopBuyItem());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<AckShopBuyItem> Parser { get { return _parser; } }

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

    /// <summary>Field number for the "item" field.</summary>
    public const int ItemFieldNumber = 2;
    private global::Protocol.PBShopItem item_;
    /// <summary>
    ///商品信息
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public global::Protocol.PBShopItem Item {
      get { return item_; }
      set {
        item_ = value;
      }
    }

    /// <summary>Field number for the "shopType" field.</summary>
    public const int ShopTypeFieldNumber = 3;
    private int shopType_;
    /// <summary>
    ///商店类型
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int ShopType {
      get { return shopType_; }
      set {
        shopType_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (Code != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(Code);
      }
      if (item_ != null) {
        output.WriteRawTag(18);
        output.WriteMessage(Item);
      }
      if (ShopType != 0) {
        output.WriteRawTag(24);
        output.WriteInt32(ShopType);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (Code != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Code);
      }
      if (item_ != null) {
        size += 1 + pb::CodedOutputStream.ComputeMessageSize(Item);
      }
      if (ShopType != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(ShopType);
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
          case 18: {
            if (item_ == null) {
              item_ = new global::Protocol.PBShopItem();
            }
            input.ReadMessage(item_);
            break;
          }
          case 24: {
            ShopType = input.ReadInt32();
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///请求刷新商店
  /// </summary>
  public sealed class ReqRefreshShop : pb::IMessage {
    private static readonly pb::MessageParser<ReqRefreshShop> _parser = new pb::MessageParser<ReqRefreshShop>(() => new ReqRefreshShop());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<ReqRefreshShop> Parser { get { return _parser; } }

    /// <summary>Field number for the "shopType" field.</summary>
    public const int ShopTypeFieldNumber = 1;
    private int shopType_;
    /// <summary>
    ///商店类型
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int ShopType {
      get { return shopType_; }
      set {
        shopType_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (ShopType != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(ShopType);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (ShopType != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(ShopType);
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
            ShopType = input.ReadInt32();
            break;
          }
        }
      }
    }

  }

  /// <summary>
  ///返回刷新商店
  /// </summary>
  public sealed class AckRefreshShop : pb::IMessage {
    private static readonly pb::MessageParser<AckRefreshShop> _parser = new pb::MessageParser<AckRefreshShop>(() => new AckRefreshShop());
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<AckRefreshShop> Parser { get { return _parser; } }

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

  #endregion

}

#endregion Designer generated code
