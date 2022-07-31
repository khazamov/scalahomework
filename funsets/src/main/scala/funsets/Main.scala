package funsets

def large_nums(x:Int): Boolean = {
  if x>6 then true
  else false
  }

def make_large_nums(x:Int): Int = {
  x*10
  }

object Main extends App:

  import FunSets.*
  println(contains(singletonSet(1), 1))
  println(contains(union(singletonSet(1),singletonSet(5)), 5))
  println(contains(intersect(singletonSet(1),singletonSet(5)), 1))
  println(contains(union(singletonSet(1),singletonSet(5)), 0))
  println("test filter")
  println(contains(filter(union(singletonSet(1),singletonSet(5)),large_nums),1))
  println(contains(filter(union(singletonSet(1),singletonSet(5)),large_nums),5))
  println(contains(map(singletonSet(1),make_large_nums),10))


