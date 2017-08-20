import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.ref.SoftReference;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * Soft ref for implementing cache
 */
public class GCTest {

	public static void main(String[] args) {
		Runtime runtime = Runtime.getRuntime();

		ReferenceQueue<GCObj> queue = new ReferenceQueue<GCObj>();

		for (long j = 0; j < 50000000; j++) {
			System.out.println("Free=" + runtime.freeMemory());
			GCObj obj = new GCObj("weak");
			WeakReference<GCObj> ref = new WeakReference<GCObj>(obj);
			SoftReference<GCObj> ref2 = new SoftReference<GCObj>(new GCObj("soft"));
			PhantomReference<GCObj> ref3 = new PhantomReference<GCObj>(new GCObj("phantom"), queue);
			System.gc();
		//	Reference freeObj = queue.poll();
		}
	}
}
