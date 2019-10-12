package org.justkile.wal.utils

import java.io._

import com.sksamuel.avro4s.{AvroInputStream, AvroOutputStream, Decoder, Encoder}
import org.apache.avro.Schema

object AvroSerializer {
  def serialise[T: Encoder](value: T)(implicit schema: Schema): Array[Byte] = {
    val stream: ByteArrayOutputStream = new ByteArrayOutputStream()
    val os: AvroOutputStream[T] = AvroOutputStream.binary[T].to(stream).build(schema)
    os.write(value)
    os.flush()
    os.close()
    stream.toByteArray
  }

  def deserialise[T: Decoder](bytes: Array[Byte])(implicit schema: Schema): T = {
    val byteArrayInputStream = new ByteArrayInputStream(bytes)
    val iss: AvroInputStream[T] = AvroInputStream.binary[T].from(byteArrayInputStream).build(schema)
    val res = iss.iterator.toSeq.head
    iss.close()
    res
  }
}
