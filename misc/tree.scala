// evolving prototype of binary tree in scala 
// based on various sources(odersky, alvinalexander, etc)
// and http://aperiodic.net/phil/scala/s-99/

sealed trait Tree[+T] {
	def elem: T
	def left: Tree[T]
	def right: Tree[T]

	def height: Int
	def leafCount: Int

	def preOrder: List[T]
	def inOrder: List[T]

	def atLevel(level: Int): List[T]
}

object EmptyTree extends Tree[Nothing] {
	def elem = throw new NoSuchElementException("EmptyTree.elem")
	def left = throw new NoSuchElementException("EmptyTree.left")
	def right = throw new NoSuchElementException("EmptyTree.right")

	override def toString = "."

	override def height = 0
	override def leafCount = 0

	override def preOrder = Nil
	override def inOrder = Nil

	override def atLevel(level: Int) = Nil
}

class Branch[+T] (
	val elem: T,
	val left: Tree[T],
	val right: Tree[T]
) extends Tree[T] {

	// another constructor
	def this (value: T) = this(value, EmptyTree, EmptyTree)

	override def equals(other: Any) = other match {
		case that: Branch[T] => this.elem == that.elem &&
								this.left == that.left &&
								this.right == that.right 
		case _   => false
	}

	override def toString = "T(" + elem.toString + " " + left.toString + " " + right.toString + ")"

	override def height = Seq(left.height, right.height).max + 1

	override def leafCount = (left, right) match {
		case (EmptyTree, EmptyTree) => 1
		case _                      => left.leafCount + right.leafCount
	}

	// remember ":::" is list concat
	override def preOrder = elem :: left.preOrder ::: right.preOrder
	override def inOrder = left.inOrder ::: elem :: right.inOrder

	override def atLevel(level: Int) = level match {
		case 0 => Nil
		case 1 => List(elem)
		case n => left.atLevel(n - 1) ::: right.atLevel(n - 1)
	}
}

// run as "scala Hello"
object Hello extends App {

	val char_tree = new Branch('a', new Branch('b'), new Branch('c'))
	println(char_tree.toString)

	val int_tree = new Branch(0, new Branch(1, new Branch(2), new Branch(3)), EmptyTree)
	println(int_tree.toString)

	println("height=" + int_tree.height)

	println("num leaves=" + int_tree.leafCount)

	println("preorder=" + int_tree.preOrder)
	println("inorder=" + int_tree.inOrder)

	println("nodes at level 2=" + int_tree.atLevel(2))
}
