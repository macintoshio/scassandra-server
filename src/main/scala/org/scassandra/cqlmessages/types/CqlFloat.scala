/*
 * Copyright (C) 2014 Christopher Batey and Dogan Narinc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.scassandra.cqlmessages.types

import java.lang

import akka.util.ByteIterator
import org.apache.cassandra.serializers.{FloatSerializer, TypeSerializer}
import org.scassandra.cqlmessages.CqlProtocolHelper

case object CqlFloat extends ColumnType[java.lang.Float](0x0008, "float") {
   override def readValue(byteIterator: ByteIterator): Option[lang.Float] = {
     CqlProtocolHelper.readFloatValue(byteIterator).map(new lang.Float(_))
   }

   def writeValue(value: Any): Array[Byte] = {
     CqlProtocolHelper.serializeFloatValue(value.toString.toFloat)
   }

  override def convertToCorrectCollectionType(list: List[_]) : List[java.lang.Float] = {
    list.map {
      case bd: BigDecimal => new java.lang.Float(bd.toFloat)
      case _ => throw new IllegalArgumentException("Expected list of BigDecimals")
    }
  }

  override def serializer: TypeSerializer[java.lang.Float] = FloatSerializer.instance
 }
