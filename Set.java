package assignment2;

/**
 *
 */
public class Set<E extends Comparable<E>> implements SetInterface<E> {

    List<E> list = new List<>();

    public Set() {init();}

    @Override
    public Set<E> init() {
        list.init();
        return this;
    }

    public Set<E> copy() {
        Set<E> copySet = new Set<E>();
        list.goToFirst();
        while (!isEmpty()){
            copySet.addElement(getElement());
            list.goToNext();
        }
        return copySet;
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
    public Set<E> complement(Set<E> set) throws APException {

        Set<E> copySet = set.copy();

        Set<E> complementSet = new Set<E>();

        while (!copySet.isEmpty()){
            if(!contains(getElement())){
                complementSet.addElement(getElement());
            }
            copySet.removeElement();
        }
        return complementSet;
    }

    @Override
    public Set<E> intersection(Set<E> set) throws APException {
        Set<E> copySet = this.copy();

        Set<E> intersectionSet = new Set<E>();

        while (!copySet.isEmpty()){
            if (set.contains(copySet.getElement())){
                intersectionSet.addElement(copySet.getElement());;
            }
            copySet.removeElement();
        }

        return intersectionSet;
    }

    @Override
    public Set<E> union(Set<E> set) throws APException {

        Set<E> unionSet = copy();

        Set<E> complementCopy = complement(set);

        while (!complementCopy.isEmpty()){
            unionSet.addElement(complementCopy.getElement());;
            complementCopy.removeElement();
        }

        return unionSet;
    }

    @Override
    public Set<E> symmetricDifference(Set<E> set) throws APException {
        Set<E> unionSet = union(set);
        Set<E> intersectionSet = intersection(set);

        while (!intersectionSet.isEmpty()){
            if (unionSet.contains(intersectionSet.getElement())){
                try {
                    unionSet.removeElement();;
                } catch (APException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            intersectionSet.removeElement();
        }
        return unionSet;
    }

    @Override
    public int numberOfElements() {
        return list.size();
    }

    @Override
    public boolean contains(E element) {return list.find(element);}
}

