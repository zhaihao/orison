package hash

import test.BaseSpec

import java.nio.ByteBuffer
import java.nio.channels.Channels

/** HashingSpec
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2021/3/30
  *   4:15 下午
  */
class HashingSpec extends BaseSpec {

  "fast hash" in {
    // hash a long
    XxHash64.hashLong(123, seed = 0)       ==> -7945237082106452088L
    MurmurHash3_32.hashLong(123, seed = 0) ==> 823512154

    // hash a Array[Byte]
    XxHash64.hashByteArray(Array[Byte](123), seed = 0) ==> -1541173007734213111L

    // hash a ByteBuffer
    XxHash64.hashByteBuffer(ByteBuffer.wrap(Array[Byte](123)), seed = 0) ==> -1541173007734213111L
  }

  "stream hash" in {
    val checksum = StreamingXxHash64(seed = 0)

    val channel = Channels.newChannel((os.resource / "test_file.txt").getInputStream)
    val chunk   = ByteBuffer.allocate(1024)

    var bytesRead = channel.read(chunk)
    while (bytesRead > 0) {
      checksum.updateByteBuffer(chunk, 0, bytesRead)
      chunk.rewind
      bytesRead = channel.read(chunk)
    }

    val hash = checksum.value
    hash ==> 5020219685658847592L
  }

  "hash" - {
    import hash.Implicits._
    "md5" in {
      "hello".md5.hex ==> "5d41402abc4b2a76b9719d911017c592"
    }
  }
}
