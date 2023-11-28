package MyStore.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String elem) {
        super("L'elemento " + elem + " non è stato trovato. Riprova con un elemento diverso");
    }
}
