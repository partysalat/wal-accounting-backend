package org.justkile.wal.event_sourcing.event_bus

import org.justkile.wal.event_sourcing.event_bus.EventBus.EventHandler

import scala.collection.mutable
import scala.language.higherKinds
import scala.reflect.ClassTag

/**
  * Registry that is mapping events to event listeners.
  */
class Registry[F[_]] {
  private[this] val eventHandlers =
    mutable.Map[Class[_], List[EventHandler[F, _]]]()

  def registerEventHandler[T: ClassTag](h: EventHandler[F, T]): Unit = {
    val key = implicitly[ClassTag[T]].runtimeClass
    val l = eventHandlers.getOrElse(key, Nil)
    val x = l :+ h
    eventHandlers.put(key, x)
  }

  def hasEventHandlerFor[T](implicit ct: ClassTag[T]): Boolean =
    doLookup(ct.runtimeClass).nonEmpty

  def lookUpEventHandler[T](e: T): List[EventHandler[F, T]] =
    doLookup[T](e.getClass).reverse

  private def doLookup[T](cls: Class[_]): List[EventHandler[F, T]] = {
    val clazz = supertypes(cls)
    clazz.flatMap { x =>
      eventHandlers.getOrElse(x, Nil).asInstanceOf[List[EventHandler[F, T]]].reverse
    }
  }

  private def supertypes(cls: Class[_]): List[Class[_]] = {
    val parents = Option(cls.getSuperclass).toList ++ cls.getInterfaces.toList
    cls :: parents.flatMap(supertypes)
  }
}
