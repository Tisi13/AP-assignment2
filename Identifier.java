package assignment2;
/* need to take lots things off*/
public class Identifier implements IdentifierInterface{

    char[] charArray;
    int numberOfCharacters;

    @Override
    public void init(char x) {
        charArray[0] = x;
        numberOfCharacters = 1;
    }

    private void copyElements (char[] dest, char[] src, int amount) {
        for (int i = 0; i < amount; i++) {
            dest[i] = src[i];
        }
    }

    private void increaseIdentifierLength () {
        char[] result = new char[2 * charArray.length];
        copyElements(result, charArray, numberOfCharacters);
        charArray = result;
    }

    @Override
    public void addChar(char x) {
        if (numberOfCharacters == charArray.length) {
            increaseIdentifierLength();
        }

        charArray[numberOfCharacters] = x;
        numberOfCharacters += 1;
    }

    @Override
    public boolean isEqual(Identifier iden) {
        if (numberOfCharacters != iden.length()){
            return false;
        }
        for (int i =0; i < numberOfCharacters; i++){
            if (charArray[i] != iden.getChar(i)){
                return false;
            }
        }
        return true;
    }

    @Override
    public char getChar(int pos) {
        return charArray[pos];
    }

    @Override
    public int length() {
        return numberOfCharacters;
    }

}
