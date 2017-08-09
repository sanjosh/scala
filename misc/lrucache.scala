
/* 
 * from comment in
 * https://gist.github.com/jeffreyolchovy/3278505 
 *
 * another at
 * https://github.com/spray/spray/blob/master/spray-caching/src/main/scala/spray/caching/LruCache.scala
 */

import collection.JavaConversions._
import collection._
import java.util.Map
import java.util.LinkedHashMap

object LruCache {
	def newCache[A, B](maxEntries: Int): mutable.Map[A, B] =
		new java.util.LinkedHashMap[A, B]() {
  	override def removeEldestEntry(eldest: java.util.Map.Entry[A,B]) = size > maxEntries
	}
}


object CacheTest {

	def main(args: Array[String]) {
		val cacheSize = 10
		var cache = LruCache.newCache[Int, String](cacheSize)

		for (a <- 1 to 100) {
			cache.put(a, "string")
		}

		assert(cache.size == cacheSize)
		cache.foreach(println(_))
	}
}
