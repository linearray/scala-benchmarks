package benchmarks

import java.util.concurrent.{ConcurrentHashMap, TimeUnit}
import org.openjdk.jmh.annotations._
import scala.collection.mutable.{ Set => MSet }
import scala.collection.mutable.{ Map => MMap }
import scala.collection.immutable.Map
import scala.math.Ordering.{ Int => IntOrdering }
import scalaz.{IMap, Order}

// Tests the performance of inserting into various data structures, with collisions //

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
class UniquesBench {

  var arr0: Array[Int] = _
  var arr1: Array[Int] = _
  var arr2: Array[Int] = _

  @Setup
  def setup: Unit = {
    arr0 = Array.range(0, 50) ++ Array.range(0, 50)
    arr1 = Array.range(0, 500) ++ Array.range(0, 500)
    arr2 = Array.range(0, 5000) ++ Array.range(0, 5000)
  }

  @Benchmark
  def hashmap100: ConcurrentHashMap[(Int, Int), Unit] = hashmap(arr0)
  @Benchmark
  def hashmap1000: ConcurrentHashMap[(Int, Int), Unit] = hashmap(arr1)
  @Benchmark
  def hashmap10000: ConcurrentHashMap[(Int, Int), Unit] = hashmap(arr2)

  def hashmap(arr: Array[Int]): ConcurrentHashMap[(Int, Int), Unit] = {
    val c = new ConcurrentHashMap[(Int, Int), Unit]
    var i: Int = 0
    val len: Int = arr.length

    while (i < len) {
      val n = arr(i)

      c.put((n, n), ())
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
  def mapb100: Map[Int, Int] = mapb(arr0)
  @Benchmark
  def mapb1000: Map[Int, Int] = mapb(arr1)
  @Benchmark
  def mapb10000: Map[Int, Int] = mapb(arr2)

  def mapb(arr: Array[Int]): Map[Int, Int] = {
    val mm = Map.newBuilder[Int,Int]
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
  def map100: Map[Int, Int] = map(arr0)
  @Benchmark
  def map1000: Map[Int, Int] = map(arr1)
  @Benchmark
  def map10000: Map[Int, Int] = map(arr2)

  def map(arr: Array[Int]): Map[Int, Int] = {
    arr.foldLeft(Map.empty : Map[Int,Int])((acc: Map[Int,Int], e: Int) => acc + ((e,e)))
  }

  @Benchmark
  def imap100: IMap[Int, Int] = imap(arr0)
  @Benchmark
  def imap1000: IMap[Int, Int] = imap(arr1)
  @Benchmark
  def imap10000: IMap[Int, Int] = imap(arr2)

  def imap(arr: Array[Int]): IMap[Int, Int] = {
    implicit val order = Order.fromScalaOrdering(IntOrdering)

    arr.foldLeft(IMap.empty:IMap[Int,Int])((acc: IMap[Int,Int], e: Int) => acc + ((e,e)))
  }

  @Benchmark
  def set100: Set[(Int, Int)] = set(arr0)
  @Benchmark
  def set1000: Set[(Int, Int)] = set(arr1)
  @Benchmark
  def set10000: Set[(Int, Int)] = set(arr2)

  def set(arr: Array[Int]): Set[(Int, Int)] = {
    arr.foldLeft(Set.empty : Set[(Int,Int)])((acc: Set[(Int,Int)], e: Int) => acc + ((e,e)))
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
