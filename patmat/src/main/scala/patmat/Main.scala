package patmat

import patmat.Huffman.*


object Main extends App with Huffman:
  println("Hello from main scala object")

  val sampleTree = makeCodeTree(
    makeCodeTree(Leaf('x', 1), Leaf('e', 1)),
    Leaf('t', 2)
  )

//  this->makeCodeTree(makeCodeTree(Leaf('x', 1), Leaf('e', 1)),Leaf('t', 2))
//  singleton(sampleTree)
//

//  var varOmar = string2Chars("Omar") // input str output char
//  var varOmartimes = times(varOmar) // input char output leafs
//
//  var varOmartimesOrderedLeafList =  makeOrderedLeafList(varOmartimes)
//  var varOmartimesOrderedLeafListsingleton = singleton(varOmartimesOrderedLeafList)


  //print(decodedSecretTest())
  println(decodedSecret)
