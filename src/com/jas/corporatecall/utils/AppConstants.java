package com.jas.corporatecall.utils;

public interface AppConstants
{
	
	public static final String SMS_TYPE_OUTGOING ="6";
	public static final int STYLE_CONFIDENT_DEFAULT = 0;
	public static final int STYLE_CONFIDENT_RED = 1;
	public static final int STYLE_PEACE_GREEN = 2;
	public static final int STYLE_HANDSOME_BLUE = 3;
	public static final int STYLE_CUTE_PINK = 4;
	public static final int STYLE_FREEDOM_YELLOW = 5;
	public static final int STYLE_DIM_WODDEN = 6;
	public static final int STYLE_TRANSPARENT = 7;
	public static int FRAGMENT_HOME = 0;
	public static int FRAGMENT_CAT1 = 1;
	public static int FRAGMENT_CAT2 = 2;
	public static int FRAGMENT_CAT3 = 3;
	public static int FRAGMENT_CAT4 = 4;
	public static int TABS_UNKNOWN = 0;
	public static final int TABS_MUSIC = 1;
	public static final int TABS_VIDEO = 2;
	public static final int TABS_TV = 3;
	public static final int TABS_SETTINGS = 4;
	public static int FRAGMENT_FAVORITE = 5;
	public static final int TYPE_MUSIC = 1;
	public static final int TYPE_MUSIC_DETAILS = 2;
	public static final int TYPE_VIDEO = 3;
	public static final int TYPE_TOP = 0;
	public static final int TYPE_TV = 4;
	public static final int TYPE_LIKE = 5;
	public static final int TYPE_REGISTRATION = 6;
	public static final int TYPE_SEARCH = 7;
	public static final String MUSIC = "music";
	public static final String VIDEO = "video";
	public static final String TOP = "top";
	public static final String TV = "tv";
	public static final String LIKE = "like";
	public static final String REGISTRATION = "registration";
	public static final String SEARCH = "search";
	public static String TV_NAME = "tv_name";

	public static final String VERSION_CONTROL = "version";
	public static final String VERSION_ID = "id";

	public static final String JSON_VIDEOLIST_POPULAR_KEY = "video_list";
	public static final String JSON_ALBUMLIST_KEY = "Albumlist";
	public static final String JSON_ALBUMLIST_ALBUM_KEY = "Album";
	public static final String JSON_STATUS_KEY = "status";
	public static final String JSON_STATUS_CODE_KEY = "code";
	public static final String JSON_STATUS_MSG = "msg";

	public static final String JSON_ALBUMLIST_VIDEOLIST_KEY = "videolist";
	public static final String JSON_ALBUMLIST_VIDEOLIST_VIDEOS_KEY = "videos";
	public static final String JSON_ALBUMLIST_ALBUM_ID_KEY = "id";
	public static final String JSON_ALBUMLIST_ALBUM_NAME_KEY = "name";
	public static final String JSON_ALBUMLIST_ALBUM_DESCRIPTION_KEY = "description";
	public static final String JSON_ALBUMLIST_ALBUM_LANGUAGE_KEY = "language";
	public static final String JSON_ALBUMLIST_ALBUM_PRICE_KEY = "price";
	public static final String JSON_SONG_DURATION = "duration";
	public static final String JSON_ALBUMLIST_ALBUM_SIZE_KEY = "size";
	public static final String JSON_ALBUMLIST_ALBUM_SONGURL_KEY = "song_url";
	public static final String JSON_ALBUMLIST_ALBUM_THUMBURL_KEY = "thumburl";
	public static final String JSON_ALBUMLIST_ALBUM_VIDEOURL_KEY = "url";
	public static final String JSON_ALBUMLIST_ALBUM_DURATION_KEY = "id";

	// implemented keys for structured parsing
	public static final String JSON_CATOGARIES_ID = "catogaryid";
	public static final String JSON_TOPCATOGARIESID = "catogaryid";
	public static final String JSON_CATOGARIES_NAME = "cat_name";
	public static final String JSON_CATOGARIES_NAME_MY = "cat_name_my";
	public static final String JSON_CATOGARIES_KEY = "Category";
	public static final String JSON_DATA_KEY = "data";
	public static final String JSON_CATOGARIES_TYPE = "cat_type";

	// public static final String JSON_SONGLIST="songlist";
	public static final String JSON_SONGLIST = "Records";
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String JSON_PLAYURL = "url";
	public static final String HTTP_REQUEST_ADVERTISEMENT = "http://203.81.162.222/ads/advertisment/";
	public static final String EXTENSION = ".png";

	public static final String[] MUSIC_URLS = { "http://203.81.162.222/json/album.txt", "http://203.81.162.222/json/album_audio_hindi.txt", "http://203.81.162.222/json/album.txt", "http://203.81.162.222/json/album_singer.txt", "http://203.81.162.222/json/album.txt" };
	public static final String[] VIDEO_URLS = { "http://203.81.162.222/json/album_video.txt", "http://203.81.162.222/json/on_demand_movie.txt", "http://203.81.162.222/json/album_video_hindi.txt", "http://203.81.162.222/json/album_video.txt", "http://203.81.162.222/json/album_video_english.txt" };
	public static final String[] TV_URLS = { "http://203.81.162.222/json/tv.txt", "http://203.81.162.222/json/tv_sports.txt", "http://203.81.162.222/json/tv_educational.txt", "http://203.81.162.222/json/tv_news.txt", "http://203.81.162.222/json/tv_cartoon.txt", "http://203.81.162.222/json/tv_religios.txt" };

	public static final String VERSION_URL = "http://203.81.162.222/json/version.txt";
	public static final String VERSION_APK_URLDummy = "http://203.81.162.222/json/testing.apk";
	public static final String VERSION_APK_URL = "http://203.81.162.222/json/NetTv.apk";
	public static final String HTTP_BASE_URL = "http://122.248.121.189/streaming/";
	public static final String HTTP_BASE_DEMO_URL = "http://122.248.121.189/streaming_demo/";
	public static final String PHP_TOP = "toplist.php?";
	public static final String PHP_MEDIA = "getdata_parameters.php?";
	public static final String PHP_REGISTRATION = "registration.php?";
	public static final String PHP_SEARCH = "searching.php?";
	public static final String HTTP_LIKEDISLIKE_URL = "http://122.248.121.189/streamingadmin/webservices/likeDislike.php";
	public static final String HTTP_TOP_URL = HTTP_BASE_URL + PHP_TOP;
	public static final String HTTP_MEDIA_URL = HTTP_BASE_URL + PHP_MEDIA;
	public static final String HTTP_REGISTRATION = HTTP_BASE_URL + PHP_REGISTRATION;
	public static final String HTTP_SEARCH = HTTP_BASE_DEMO_URL + PHP_SEARCH;

	public static final String HTTP_VIDEO_TESTING_URL = "http://122.248.121.189/streaming_demo/getdata_parameters.php";

}
