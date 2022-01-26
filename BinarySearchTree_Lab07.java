import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

/*
 	Lab-07: BinarySearchTree Implementation

 	Rules:
 		1. Allow Tester to iterate through all nodes using the in-order traversal as the default.
 			This means, in Tester the following code should work for an instance of this class
 			called bst that is storing Student objects for the data:

 				BinarySearchTree_Lab07<String> bst = new BinarySearchTree_Lab07<String>();
 				bst.add("Man");		bst.add("Soda");	bst.add("Flag");
 				bst.add("Home");	bst.add("Today");	bst.add("Jack");

 				for(String s : bst) 
 					System.out.println(s);
 */
/**
 * 
 * @author David Martin
 * @author Caleb Allen
 *
 * @param <T>
 */

public class BinarySearchTree_Lab07<T> implements Iterable<T> {
	//====================================================================================== Properties
	private Node root;
	private int nodeCount;
	private Comparator<T> comp = new Comparator<T>() {
		@Override
		public int compare(T o1, T o2) {
			return ((Comparable)o1).compareTo(o2);
		}
	};

	//====================================================================================== Constructors
	public BinarySearchTree_Lab07() {
		super();
		root = null;
		nodeCount = 0;
	}

	// Constructor that takes an array of items and populates the tree
	public BinarySearchTree_Lab07(T[] items) {
		this();
		//LinkedList<T> ll = new LinkedList<>();
		//		for(int i = 0; i < items.length; i++) {
		//			add(items[i]);
		//		}
		for(T t : items)	add(t);
	}

	//====================================================================================== Methods
	//	public BinarySearchTree_Lab07 add(T data) {
	//		if(data == null)	return this;
	//		root = add(root, data);
	//		nodeCount++;
	//		return this;
	//	}
	public void add(T data) {	// Implement recursively and do NOT allow duplicates
		if(data == null)	return;
		root = add(root, data);
		nodeCount++;
	}

	private Node add(Node n, T t) {
		//		if(n == null) return new Node(t);
		//		int d = comp.compare(t,  n.data);
		//		if(d < 0)	n.left = add(n.left, t);
		//		else if (d > 0)		n.right = add(n.right, t);
		//		return n;
		if(n == null)	return new Node(t);
		if(comp.compare(t, n.data) == 0) { nodeCount--; return n;}
		if(comp.compare(t, n.data) <= 0) 	n.left = add(n.left, t);
		else								n.right = add(n.right, t);
		return n;
	}

	//LinkedList<T> ls = new LinkedList<T>();  // can't have a global link list won't be in order
	// Returns the traversal of this tree as an array
	public String[] preOrder_Traversal() {
		String[] ret = new String[nodeCount];
		LinkedList<T> ls = new LinkedList<T>();
		preOrder(root, ls);
		for(int i = 0; i < ret.length; i++) {
			ret[i] = "" + ls.get(i);
		}
		return ret;
	}

	private void preOrder(Node n, LinkedList<T> ll) {
		if(n == null)	return;
		ll.add(n.data);
		if(n.left != null)	preOrder(n.left, ll);
		if(n.right != null)	preOrder(n.right, ll);
	}

	public String[] inOrder_Traversal() {
		String[] ret = new String[nodeCount];
		LinkedList<T> ls = new LinkedList<T>();
		inOrder(root, ls);
		for(int i = 0; i < ret.length; i++) {
			ret[i] = "" + ls.get(i);
		}
		return ret;
	}

	private void inOrder(Node n, LinkedList<T> ll) {
		if(n == null)	return;
		if(n.left != null)	inOrder(n.left, ll);
		ll.add(n.data);
		if(n.right != null)	inOrder(n.right, ll);
	}

	public String[] postOrder_Traversal() {
		String[] ret = new String[nodeCount];
		LinkedList<T> ls = new LinkedList<T>();
		postOrder(root, ls);
		for(int i = 0; i < ret.length; i++) {
			ret[i] = "" + ls.get(i);
		}
		return ret;
	}

	private void postOrder(Node n, LinkedList<T> ll) {
		if(n == null)	return;
		if(n.left != null)	postOrder(n.left, ll);
		if(n.right != null)	postOrder(n.right, ll);
		ll.add(n.data);
	}

	public String[] breadthFirst_Traversal() {
		String[] ret = new String[nodeCount];
		LinkedList<T> ls = new LinkedList<T>();
		breathFirst(root, ls);
		for(int i = 0; i < ret.length; i++) {
			ret[i] = "" + ls.get(i);
		}
		return ret;		
	}

	private void breathFirst(Node n, LinkedList<T> ll) {
		if(n == null)	return;
		LinkedList<Node> q = new LinkedList<Node>();
		q.add(n);
		while(!q.isEmpty()) {
			Node tmp = q.remove();
			ll.add(tmp.data);
			if(tmp.left != null)	q.add(tmp.left);
			if(tmp.right != null)	q.add(tmp.right);
		}
	}

	public Iterator<T> iterator() {
		LinkedList<T> ll = new LinkedList<T>();
		inOrder(root, ll);	
		return new Iterator<T>() {

			@Override
			public boolean hasNext() { return !ll.isEmpty(); }

			@Override
			public T next() {
				if(!hasNext()) throw new IndexOutOfBoundsException();
				return ll.remove();
			}

		};
	}

	// Since this is a binary SEARCH tree, you should write
	// an efficient solution to this that takes advantage of the order
	// of the nodes in a BST.  Your algorithm should be, on average,
	// O(h) where h is the height of the BST.
	public boolean contains(T data) {
		return get(root, data) != null;
	}

	private T get(Node n, T o) {
		if(n == null)	return null;
		int d = comp.compare(o, n.data);
		if(d == 0)	return n.data;
		return get((d < 0 ? n.left:n.right ),o); // Ternary if statement
	}

	// returns the smallest value in the tree
	// or throws an IllegalStateException() if the
	// tree is empty.  Write the recursive version 
	public T min() { return min(root); }		// this method is done for you.

	private T min(Node n) {	// Write this method.
		if(isEmpty())	throw new IllegalStateException();
		Node tmp = n;
		while(tmp.left != null) tmp = tmp.left;
		return tmp.data;
	}

	// returns the largest value in the tree
	// or throws an IllegalStateException() if the
	// tree is empty.  Write the recursive version
	public T max() { return max(root); }		// this method is done for you.

	private T max(Node n) {	// Write this method.
		if(isEmpty())	throw new IllegalStateException();
		Node tmp = n;
		while(tmp.right != null) tmp = tmp.right;
		return tmp.data;
	}

	// Returns whether the tree is empty
	public boolean isEmpty() {
		return root == null;
	}

	// returns the height of this BST. If a BST is empty, then
	// consider its height to be -1.
	public int getHeight() {
		if(isEmpty())	return -1;
		return getHeight(root);
	}

	private int getHeight(Node n) {
		return Math.max(n.left != null ? getHeight(n.left) + 1 : 0,
				n.right != null ? getHeight(n.right) + 1 : 0);
	}


	// returns the number of leaf nodes
	public int leafCount() {
		return leafCount(root) + 1; // remove + 1
	}

	private int leafCount(Node n) {
		if(n == null) return 0;
		int val = (n.left == null && n.right == null) ? 1 : 0;
		return val + leafCount(n.left) + leafCount(n.right);
	}


	// returns the "level" of the value in a tree.
	// the root is considered level 0
	// the children of the root are level 1
	// the children of the children of the root are level 2
	// and so on.  If a value does not appear in the tree, return -1
	//              15
	//             /  \
	//            10  28
	//              \   \
	//              12  40
	//                 /
	//                30
	// In the tree above:
	// getLevel(15) would return 0
	// getLevel(10) would return 1
	// getLevel(30) would return 3
	// getLevel(8) would return -1
	public int getLevel(T n) {
		if(n == null)	return - 1;
		return getLevel(root, n, 0);
	}

	private int getLevel(Node n, T data, int level) {
		if (n == null)
			return 0;

		if (n.data == data)
			return level;

		int downlevel = getLevel(n.left, data, level+1);
		if (downlevel != 0)
			return downlevel;

		downlevel = getLevel(n.right, data, level+1);
		return downlevel;
	}


	// *********************************************************
	// EXTRA CREDIT: 5pts
	// *********************************************************
	// A tree is height-balanced if at each node, the heights
	// of the node's two subtrees differs by no more than 1.
	//  Special note about null subtrees:
	//            10
	//              \
	//               20
	// Notice in this example that 10's left subtree is null,
	// and its right subtree has height 0. We would consider this
	// to be a balanced tree. If the tree is empty, return true;
	public boolean isBalanced() {
		return false;
	}


	//====================================================================================== Inner Node Class
	private class Node {
		private T data;
		private Node left, right;

		private Node(T data) {
			this.data = data;
			left = right = null;
		}
	}

	// ================================================= Helper
}

