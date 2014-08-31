package co.jp.demo.genpara;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import android.app.Application;
import co.jp.demo.genpara.ui.utils.ZImageLoader;

public class MApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		// 初始化UIL
		initImageLoader();
		ZImageLoader.init();
	}

	private void initImageLoader() {
		int screenW = getResources().getDisplayMetrics().widthPixels;
		int screenH = getResources().getDisplayMetrics().heightPixels;

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.memoryCacheExtraOptions(screenW, screenH)
				// .discCacheExtraOptions(screenW, screenH,
				// CompressFormat.JPEG,75, null)
				// .taskExecutor(...)
				// .taskExecutorForCachedImages(...)
				.threadPoolSize(3)
				.threadPriority(Thread.NORM_PRIORITY - 1)
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024)
				.memoryCacheSizePercentage(13)
				// .discCache(new UnlimitedDiscCache(cacheDir))
				.diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(100)
				.diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
				.imageDownloader(new BaseImageDownloader(this))
				.imageDecoder(new BaseImageDecoder(true)) // default
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
				// .writeDebugLogs()
				.build();
		ImageLoader.getInstance().init(config);
	}
}
