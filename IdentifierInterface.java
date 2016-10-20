package assignment2;


/** ADT for the class Identifier 
 *
 * @author Tisiana Henricus, Iliana Kyritsi
 * @elements
 * Characters of the Type char.
 * @structure
 * linear
 * @domain
 *      The identifier consists of alphanumeric characters. The first
 *      character of an identifier is a letter. The identifier cannot be empty.
 * @constructor
 * Identifier();
 *     <dl>
 *  <dt><b>PRE-condition</b><dd>
 *          -
 *  <dt><b>POST-condition</b><dd>
 *          The new Identifier-object contain character 'a'.
 *     </dl>
 *    	<br>
 **/

public interface IdentifierInterface {

	/** Initializes the Identifier object to a character x.
	 * @precondition
	 *	   x has to be a letter.
	 * @postcondition
	 *	   The identifier contains only character x.
	 **/
	void init(char x);

	/** Adds a character to the Identifier object.
	 * @precondition
	 *	   x is alphanumeric.
	 * @postcondition
	 * 	   x is added to the identifier.
	 **/
	void addChar(char x);


	/** Check if two identifiers are the same.
	 * @precondition
	 *	    -
	 * @postcondition
	 *	    - TRUE: If both identifiers are the same.
	 *      - FALSE: If the identifiers are not the same.
	 **/
	public boolean isEqual(Identifier iden);

	/**Return the character at given index.
	 *
	 * @precondition
	 *        The index is less than the length.
	 * @postcondition
	 *        Character at position pos is returned.
	 */
	char getChar(int pos);

	/**Return the length of the identifier.
	 *
	 * @precondition
	 *        -
	 * @postcondition
	 *        The length of the identifier is returned.
	 */
	int length();

}
