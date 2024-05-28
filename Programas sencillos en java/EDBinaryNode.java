package Practica6;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Imeplements the binary node og a binary tree. It contains three references: to the left and right siblings,
 * and to the parent node. Includes methods to consult, modify the properties, prune and insert sub-trees. All the
 * operations are local to the node, except equals and hashCode which are recursive and take a whole binary tree for
 * which this node is the root.
 *
 * @param <T> Class of the data contained in the node
 */
public class EDBinaryNode<T> {
	private EDBinaryNode<T> parent = null;
	private T data = null;
	private EDBinaryNode<T> left = null;
	private EDBinaryNode<T> right = null;
	
	// constructors
	public EDBinaryNode( T item ) {
		setData(item);
	}

	public EDBinaryNode( T item, EDBinaryNode<T> l, EDBinaryNode<T> r ) {
		setData(item);
		setLeft(l);
		setRight(r);
	}

    // Properties
    public boolean containsNull() {
        return data == null;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public boolean hasLeft() {
        return left != null;
    }

    public boolean hasRight() {
        return right != null;
    }

    public boolean hasBoth() {
        return hasLeft() && hasRight();
    }

    public boolean isLeaf() {
        return !hasLeft() && !hasRight();
    }

	// Getters
	public T data() {
		return data;
	}

	public EDBinaryNode<T> parent() {
		return parent;
	}

	public EDBinaryNode<T> right() {
		return right;
	}

	public EDBinaryNode<T> left() {
		return left;
	}

    // Setters and modifiers
	public T setData(T data) {
		T old = this.data;
		this.data = data;
		return old;
	}

    /**
     * Sets a node as the left subtree. Sets the parent reference.
     *
     * @param left The new left binary node
     * @return The old left binary node
     */
	public EDBinaryNode<T> setLeft(EDBinaryNode<T> left) {
	    if (this.left == left)
	        return this.left;

		EDBinaryNode old = this.left;
	    this.left = left;
		if (left != null)
			this.left.parent = this;
		return  old;
	}

    /**
     * Sets a node as the rigth subtree. Sets the parent reference.
     *
     * @param right The new right binary node
     * @return The old right binary node
     */
	public EDBinaryNode<T> setRight(EDBinaryNode<T> right) {
        if (this.right == right)
            return this.left;

	    EDBinaryNode<T> old = this.right;
		this.right = right;
		if (right != null)
			this.right.parent = this;
		return old;
	}

    /**
     * Cuts this node from its parent.
     *
     * Changes the references to the parent and on the parent node to null.
     * @return The old parent node
     */
    public EDBinaryNode<T> prune() {
        if (parent == null)
            return null;

        if (parent.left == this)
            parent.left = null;
        else
            parent.right = null;

        EDBinaryNode<T> ret = parent;
        parent = null;
        return ret;
    }

    /**
     * Cuts the left subtree.
     *
     * Sets the references bwtween both nodes to null
     * @return The old left node
     */
    public EDBinaryNode<T> pruneLeft() {
	    if (left == null)
	        return null;

        EDBinaryNode<T> ret = left;
        left.parent = null;
        left = null;
        return ret;
    }

    /**
     * Cuts the right subtree
     *
     * Sets the references on both nodes to null.
     * @return The old right node.
     */
    public EDBinaryNode<T> pruneRight() {
	    if (right == null)
	        return null;

	    EDBinaryNode<T> ret = right;
	    right.parent = null;
	    right = null;
	    return ret;
    }

    // Equals and hashCode, BOTH ARE RECURSIVE
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        EDBinaryNode<?> that = (EDBinaryNode<?>) o;

        if (data != null ? !data.equals(that.data) : that.data != null)
            return false;

        if (left != null && !left.equals(that.left))
            return false;

        if (left == null && that.left != null)
            return false;

        if (right != null && !right.equals(that.right))
            return false;

        if (right == null && that.right != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = data != null ? data.hashCode() : 0;

        if (left != null)
            hash = (hash << 3) & left.hashCode();

        if (right != null)
            hash = (hash << 7) & right.hashCode();

        return hash;
	}

	// to string
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("EDBinaryNode{");
        sb.append("data=").append(data).append(", has: ");
        if (hasParent())
            sb.append(" parent");
        if (hasLeft())
            sb.append(" left");
        if (hasRight())
            sb.append(" right");
        sb.append("}");

        return sb.toString();
    }
}
