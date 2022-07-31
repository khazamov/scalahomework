package funsets

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite extends munit.FunSuite:

  import FunSets.*

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets:
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = union(singletonSet(3),s2)
    val s4 =  union(singletonSet(3),s1)

  /**
   * This test is currently disabled (by using @Ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remove the
   * .ignore annotation.
   */
  test("singleton set one contains one") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets:
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
  }

  test("union contains all elements of each set") {
    new TestSets:
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
  }

  test("intersection contains common elements of each set") {
    new TestSets:
        val s = intersect(s3, s4)
        assert(!contains(s, 1), "Intersect 1")
        assert(!contains(s, 2), "Intersect 2")
        assert(contains(s, 3), "Intersect 3")
  }

  test("diff contains  elements of the first set that are not present in the second set") {
      new TestSets:
        val s = diff(s3, s4)
        assert(contains(s, 2), "diff 1")
        assert(!contains(s, 1), "Intersect 2")
        assert(!contains(s, 3), "Intersect 3")
  }

  test("filter contains  elements of the first set that satisfy a filter condition") {
    new TestSets:
      def bignum(x:Int):Boolean = { if (x>2)  true else  false }
      val s = filter(union(s3,s4),bignum)
      assert(!contains(s, 2), "filter 1")
      assert(!contains(s, 1), "filter 2")
      assert(contains(s, 3), "filter 3")
  }

  test("forall is true if all  elements of the   set  satisfies a function condition") {
    new TestSets:
      def lessthanfive(x:Int):Boolean = { if (x<(5))  true else  false }
      def lessthanthousand(x:Int):Boolean = { if (x<(1000))  true else  false }
      var basesingleton = singletonSet(-1000)
      var basesingleton2 = singletonSet(-1000)
      for (i <- -999 to 4) {
          basesingleton = union (singletonSet(i), basesingleton)
      }
      for (i <- -999 to 1) {
        basesingleton2 = union (singletonSet(i), basesingleton2)
      }
      for (i <- 1 to 4) {
       basesingleton2 = union (singletonSet(10), basesingleton2)
      }
      val s = forall(basesingleton, lessthanfive)
      val s_2 = forall(basesingleton, lessthanthousand)
      val s_3 = forall(basesingleton2,lessthanfive)
      assert(s, "forall 1: strictly less than 5")
      assert(s_2, "forall 2: strictly less than 1000")
      assert(!s_3, "forall 3 new set: strictly less than 5")
  }

  test("exists is true if at least one  element of the  set  satisfies a function condition") {
    new TestSets:
      def bignumforall1(x:Int):Boolean = { if (x>(-1001))  true else  false }
      def bignumforall2(x:Int):Boolean = { if (x>2)  true else  false }
      var basesingleton = singletonSet(-1000)
      for (i <- -999 to 1000) {
        basesingleton = union (singletonSet(i), basesingleton)
      }
      val s = exists(basesingleton, bignumforall1)
      val s_2 = exists(basesingleton, bignumforall2)
      assert(s, "exists 1")
      assert(s_2, "exists 2")
  }

  test("map will apply a function to all elements within") {
    new TestSets:
      def make_large_nums(x:Int): Int = {
        x*10
      }

      var basesingleton = singletonSet(-1000)
      for (i <- -999 to 1000) {
        basesingleton = union (singletonSet(i), basesingleton)
      }

      var basesingleton2 = union (singletonSet(1), singletonSet(-1000))


      val s = map(basesingleton, make_large_nums)
      val s_2 = map(basesingleton2, make_large_nums)
      assert(contains(s,10000), "map test 1")
      assert(!contains(s,11000), "map test 2")
      assert(!contains(s_2,10000), "map test 3")
  }



  import scala.concurrent.duration.*
  override val munitTimeout = 10.seconds
