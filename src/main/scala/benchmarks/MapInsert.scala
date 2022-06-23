package benchmarks

import java.util.concurrent.{ TimeUnit, ConcurrentHashMap }

import scala.collection.mutable.{ Set => MSet }
import scala.collection.mutable.{ Map => MMap }
import scala.collection.immutable.{ Map => IMMMap }
import scala.math.Ordering.{ Int => IntOrdering }
import scalaz.IMap
import scalaz.Order


import org.openjdk.jmh.annotations._
import scala.collection.mutable.Map

//
// Tests the performance of inserting into various Map data structures, without collisions
//
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
class MapInsertBench {

  var arr0: Array[Int] = _
  var arr1: Array[Int] = _
  var arr2: Array[Int] = _

  @Setup
  def setup: Unit = {
    arr0 = Array.range(0, 50)
    arr1 = Array.range(0, 500)
    arr2 = Array.range(0, 5000)
  }

  @Benchmark
  def hashmap100: ConcurrentHashMap[Int, Int] = hashmap(arr0)
  @Benchmark
  def hashmap1000: ConcurrentHashMap[Int, Int] = hashmap(arr1)
  @Benchmark
  def hashmap10000: ConcurrentHashMap[Int, Int] = hashmap(arr2)

  def hashmap(arr: Array[Int]): ConcurrentHashMap[Int, Int] = {
    val c = new ConcurrentHashMap[Int, Int]
    var i: Int = 0
    val len: Int = arr.length

    while (i < len) {
      val n = arr(i)

      c.put(n,n)
      i += 1
    }

    c
  }

  @Benchmark
  def mmap100: MMap[Int, Int] = mmap(arr0)
  @Benchmark
  def mmap1000: MMap[Int, Int] = mmap(arr1)
  @Benchmark
  def mmap10000: MMap[Int, Int] = mmap(arr2)

  def mmap(arr: Array[Int]): MMap[Int, Int] = {
    var mm: MMap[Int, Int] = MMap.empty
    var i: Int = 0
    val len: Int = arr.length

    while (i < len) {
      val n = arr(i)

      mm += ((n, n))
      i += 1
    }

    mm
  }


  @Benchmark
  def mapb100: IMMMap[Int, Int] = mapb(arr0)
  @Benchmark
  def mapb1000: IMMMap[Int, Int] = mapb(arr1)
  @Benchmark
  def mapb10000: IMMMap[Int, Int] = mapb(arr2)

  def mapb(arr: Array[Int]): IMMMap[Int, Int] = {
    val mm = scala.collection.immutable.Map.newBuilder[Int,Int]
    var i: Int = 0
    val len: Int = arr.length

    while (i < len) {
      val n : Int = arr(i)

      mm += ((n, n))
      i += 1
    }

    mm.result()
  }


  @Benchmark
  def map100: IMMMap[Int, Int] = map(arr0)
  @Benchmark
  def map1000: IMMMap[Int, Int] = map(arr1)
  @Benchmark
  def map10000: IMMMap[Int, Int] = map(arr2)

  def map(arr: Array[Int]): IMMMap[Int, Int] = {
    var m: IMMMap[Int, Int] = IMMMap.empty
    var i: Int = 0
    val len: Int = arr.length

    // FIXME: This is kinda stupid
    while (i < len) {
      val n = arr(i)

      m = m + ((n, n))
      i += 1
    }

    m
  }

  @Benchmark
  def imap100: IMap[Int, Int] = imap(arr0)
  @Benchmark
  def imap1000: IMap[Int, Int] = imap(arr1)
  @Benchmark
  def imap10000: IMap[Int, Int] = imap(arr2)

  def imap(arr: Array[Int]): IMap[Int, Int] = {
    var m: IMap[Int, Int] = IMap.empty
    var i: Int = 0
    val len: Int = arr.length
    implicit val order = Order.fromScalaOrdering(IntOrdering)

    // FIXME: This is kinda stupid
    while (i < len) {
      val n = arr(i)

      m = m + (n, n)
      i += 1
    }

    m
  }


  @Benchmark
  def set100: Set[(Int, Int)] = set(arr0)
  @Benchmark
  def set1000: Set[(Int, Int)] = set(arr1)
  @Benchmark
  def set10000: Set[(Int, Int)] = set(arr2)

  def set(arr: Array[Int]): Set[(Int, Int)] = {
    var s: Set[(Int, Int)] = Set.empty
    var i: Int = 0
    val len: Int = arr.length

    while (i < len) {
      val n = arr(i)

      s = s + ((n, n))
      i += 1
    }

    s
  }

  @Benchmark
  def mset100: MSet[(Int, Int)] = mset(arr0)
  @Benchmark
  def mset1000: MSet[(Int, Int)] = mset(arr1)
  @Benchmark
  def mset10000: MSet[(Int, Int)] = mset(arr2)

  def mset(arr: Array[Int]): MSet[(Int, Int)] = {
    val s: MSet[(Int, Int)] = MSet.empty
    var i: Int = 0
    val len: Int = arr.length

    while (i < len) {
      val n = arr(i)

      s += ((n, n))
      i += 1
    }

    s
  }

}
