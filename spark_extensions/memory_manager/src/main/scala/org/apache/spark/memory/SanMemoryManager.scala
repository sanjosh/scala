package org.apache.spark.memory

import java.nio.ByteBuffer

import org.apache.spark.SparkConf
import org.apache.spark.memory._
import org.apache.spark.storage.BlockId

/**
  * Created by sandeep on 4/8/17.
  */
class SanMemoryManager private[memory](
    conf: SparkConf,
    override val maxHeapMemory: Long,
    val onHeapStorageRegionSize: Long)
  extends UnifiedMemoryManager(conf,
    maxHeapMemory,
    onHeapStorageRegionSize,
    numCores = 1) {

    override def acquireStorageMemory(
                                     blockId: BlockId,
                                     numBytes: Long,
                                     memoryMode: MemoryMode
                                     ): Boolean = true

    override def acquireUnrollMemory(blockId: BlockId, numBytes: Long, memoryMode: MemoryMode): Boolean = true 

}
