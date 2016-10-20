package assignment2;



/**
 * Created by Tisi on 10/19/16.
 *
 */
public class Set<E extends Comparable<E>>  implements SetInterface<E> {

    List<E> list = new List<>();

    public Set() {init();}

    @Override
    public Set<E> init() {
        list.init();
        return this;
    }

    @Override
    public void addElement(E element) {list.insert(element);}

    @Override
    public E getElement() { return list.retrieve();}

    @Override
    public void removeElement() throws APException {list.remove();}

    @Override
    public boolean isEmpty() { return list.isEmpty(); }

    @Override
    public SetInterface<E> complement(SetInterface<E> set) {

        return null;
    }

    @Override
    public SetInterface<E> intersection(SetInterface<E> set) {
        return null;
    }

    @Override
    public SetInterface<E> union(SetInterface<E> set) throws APException {
        return null;
    }

    @Override
    public SetInterface<E> symmetricDifference(SetInterface<E> set) throws APException {
        return null;
    }

    @Override
    public int numberOfElements() {
        return 0;
    }

    @Override
    public boolean contains(E element) {return list.find(element);}
}
