package com.color.util;

/**
 * 存放常量的类
 * @ClassName: Const 
 * @Description: TODO
 * @author color
 * @date 2014-10-14 下午01:48:25
 */
public class Const {

	public static final String keystore = "14276ebf97faab2f565032f4171fdd43";//签名-旧
	public static final String keystore2 ="d2dccd5aeca5ca140e712ac99cc0b50c";//签名-新
	public static final String WX_APP_ID = "wxc175d185d0b8c350";// AppID
																// //wxc175d185d0b8c350
	public static final String USERINFO = "USERINFO";// sharedpreference 文件名
														// 保存用户的一些信息
	/************* 用户信息 **************/
	public static final String USERNAME = "USERNAME";// 用户名
	public static final String PHONENUMBER = "PHONENUMBER";// 用户手机号码
	public static final String PASSWORD = "PASSWORD";// 用户密码
	public static final String LANDSTATE = "LANDSTATE";// 登陆状态
	public static final String INGUIDE = "INGUIDE";// 是否进入过向导界面

	public static final String FLAG_WINE = "FLAG_WINE";

	public static final String FLAG_BAIJIU = "FLAG_BAIJIU";
	public static final String FLAG_PUTAOJIU = "FLAG_PUTAOJIU";
	public static final String FLAG_PIJIU = "FLAG_PIJIU";
	public static final String FLAG_LAOJIU = "FLAG_LAOJIU";

	public static final String WINEINFO = "WINEINFO";//获得酒信息
	public static final String WXFRIEND = "WXFRIEND";// 分享到微信好友
	public static final String WXCIRCLE = "WXCIRCLE";// 分享到微信朋友圈

	public static final String LEFT = "LEFT";
	public static final String RIGHT = "RIGHT";
	public static final String CLEAR = "CLEAR";
	public static final String SET = "SET";
	public static final String SHARE = "SHARE";// 分享
	public static final String COLLECT = "COLLECT";// 收藏
	public static final int PICTURE_WIDE = 450;
	public static final int PICTURE_HEIGHT = 200;
	public static final int WINEGAMEWIDE = 411;
	public static final int WINEGAMEHEIGHT = 262;
	public static final int BASINWIDE = 281;
	public static final int BASINHEIGHT = 253;
	public static final String ADD = "ADD";
	public static final String REDUCE = "REDUCE";
	public static final String HOTSELL = "HOTSELL";// 热销
	public static final String PRICE = "PRICE";// 价格
	public static final String NEWPRODUCT = "NEWPRODUCT";// 新品

	public static final String PERSONAL = "PERSONAL";// 个人信息 sp名称
	public static final String MSGSEND = "MSGSEND";// 消息推送 sp的key

	public static final String VOICEREMIND = "VOICEREMIND";// 声音提醒设置
	public static final String NOPAINT = "NOPAINT";// 2G/3G模式下无图模式

	/************* 数据库表名 **************/
	public static final String SEARCHHISTORY = "search_history";// 搜索历史
	public static final String TABLE_SHOPCAR = "shopcar";// 购物车表
	public static final String TABLE_COLLECTION = "collection";

	public static final String WHITE_SPIRIT = "WHITE_SPIRIT";// 白酒
	public static final String GRAPE_WINE = "GRAPE_WINE";// 葡萄酒
	public static final String BEER = "BEER";// 啤酒
	public static final String OLD_WINE = "OLD_WINE";// 老酒
	public static final String SURROUNDING = "SURROUNDING";// 周边
	public static final String ALL = "ALL";// 全部
	public static final String WINE_RED = "WINE_RED";// 红酒
	public static final int TAKEPHOTO = 0;
	public static final int PICTURE = 1;
	public static final int CANCLE = 2;
	public static final String WINE_PRIOMOTION = "WINE_PRIOMOTION";// 促销酒品
	/** 接口CODE */
	/**
	 * 获取验证码
	 */
	public static final int GETVERIFYCODE = 1001;
	/**
	 * 注册
	 */
	public static final int REGISTER = 1002;
	/**
	 * 登陆
	 */
	public static final int LOGIN = 1003;
	/**
	 * 重置密码
	 */
	public static final int RESETPASSWORD = 1004;

	/**
	 * 验证密码
	 */
	public static final int VERIFYCODE = 1005;

	/**
	 * 更改密码
	 */
	public static final int CHANGEPASSWORD = 1006;
	/**
	 * 获取用户信息
	 */
	public static final int GETUSERINFO = 1007;
	/**
	 * 新增寻酒信息
	 */
	public static final int CREATEPRODUCTREQUEST = 1008;
	/**
	 * 寻酒信息列表
	 */
	public static final int LISTPRODUCTREQUEST = 1009;
	/**
	 * 新增私人订制
	 */
	public static final int CREATECUSTOMIZEDREQUEST = 1010;
	/**
	 * 取消订单
	 */
	public static final int CANCLEORDER = 10011;
	/**
	 * 修改用户名
	 */
	public static final int CHANGEUSERNAME = 3001;
	/**
	 * 获取积分
	 */
	public static final int GETPARTYREWARDPOINT = 10012;
	/**
	 * 修改用户密码
	 */
	public static final String BARCODE = "3760091222060";
	public static final int CHANGEPWD = 3002;// 修改密码
	public static final int PERSONORDER = 3003;// 获取个人中心订单信息
	public static final int CONFIRMERWEIMA = 3004;// 二维码扫描订单
	public static final int IMMEDIATECONFIRME = 3005;// 手动立即验证
	public static final int SYSTEMMESSAGE = 3006; // 系统消息
	public static final int SENDMESSAGE = 3007; // 用户发送消息
	public static final int UPDATEVERSION = 3008; // 更新版本
	public static final int HEALTHDRINK = 4001; // 健康饮酒
	public static final int WINEJOKES = 4002; // 酒段子
	public static final int CUSTOMIZED = 4003; // 私人订制信息列表
	
	public static final int PROMOTION = 2001;// 查询闪购酒品全部信息
	public static final int SUBMITFLASHORDER = 2002;// 提交闪购订单到支付宝
	public static final int LISTFLASHSTATE = 20021;// 支付前获取闪购订单状态及信息
	public static final int LISTFLASHORDERS = 2003;// 获取闪购历史订单全部信息
	public static final int LISTFLASHORDER = 20031;// 获取闪购历史订单全部信息
	
	public static final int GETCATEGORYFEATURES = 2004;// 获取酒品分类特征
	public static final int FINDPRODUCT = 2005;// 查询检索酒品全部信息
	public static final int ADDSHOPPINGLISTITEM = 2006;// 添加商品到购物车
	public static final int LISTSHOPPINGLISTITEM = 2007;// 获取购物车酒品列表
	public static final int FINDSINGLEPRODUCT = 2008;// 查询检索单个酒品全部信息
	public static final int CLEARSHOPPINGLISTITEM = 2009;// 清空购物车商品
	public static final int SETSHOPPINGLISTADDRESS = 2010;// 设置购物车地址
	public static final int SUBMITSHOPPINGLIST = 2011;// 提交订单
	public static final int CREATESHIPPINGADDRESS = 2012;// 新增收货地址
	public static final int LISTSHIPPINGADDRESS = 2013;// 获取收货地址
	public static final int GETGEOCHILDREN = 2014;// 获取省/市/区列表
	public static final int UPDATESHIPPINGADDRESS = 2015;// 修改收货地址
	public static final int DELETESHIPPINGADDRESS = 2016;// 删除收货地址
	public static final int DELETESHOPPINGLISTITEM = 2017;// 删除购物车商品
	public static final int SEARCHPRODUCT = 2018;// 酒搜索
	public static final int REVIEWORDER = 2019;// 评价商品
	public static final int LISTPRODUCTREVIEW = 2020;// 查看商品评价
	public static final int LISTPRODUCTPROMOCODE = 2021;// 获取用户代金卷
	public static final int COUNTPRODUCTVIEW = 2022;// 浏览数

}
