import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.ref.SoftReference;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * object pointed to by strong ref never reclaimed
 * use soft ref to point to object which is being cached (but can be dispensed)
 * use weak ref to build weak collections, 
 *   to store metadata about obj only as long as they live
 * use phantom ref to know if referent(obj being pointed to) has been gc'ed
 *   get() always returns null
 *   use for connection pool
 * http://www.kdgregory.com/index.php?page=java.refobj
 * https://www.ibm.com/developerworks/library/j-jtp01246/index.html
 * https://www.ibm.com/developerworks/java/library/j-jtp11225/index.html
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
