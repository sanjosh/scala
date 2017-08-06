/**
 * Split a range into chunks of equal size
 * @param range       range which has to be split 
 * @param chunk_size  size of each chunk
 * @return            list of ranges of equal size (except last)
 */

def splitRangeIntoChunks(range: Range, chunk_size: Int)
		: IndexedSeq[(Int, Int)] = {
	if (range.step != 1) {
	  throw new IllegalArgumentException("Range must have step size equal to 1")
	}
	  
	val num_chunks = (range.length + chunk_size - 1)/chunk_size

	val start_positions = range.by(chunk_size).take(num_chunks)
	val end_positions = start_positions.map(_ - 1).drop(1) :+ range.end

	start_positions.zip(end_positions)
}

