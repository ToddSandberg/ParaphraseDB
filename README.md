# ParaphraseDB

Takes a PPDB list and serializes it as java objects in a `HashMap`. Then using the **Paraphrase** class it allows for easy access to Term1, Term2, PPDB2.0Score, and the syntactical usage of term1, as well as the WordNet hypernyms and synonyms. There is also a synonyms check method. The **App** class is an example implementation of **Paraphrase**. You are required to unzip the inputs in the folder at the moment.

- shortenedList.txt is a PPDB file that only has the Term1, Term2, PPDB2.0Score, and the syntactical usage of term1.
- paraphraseHash.ser is a serialized HashMap that can be accessed through the reader class.

