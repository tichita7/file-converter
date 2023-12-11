package org.example.Factory;
public class FactoryCreator {
    public static AbstractFactory getFactory(String choice){
        if(choice.equalsIgnoreCase("Reader")){
            return new ReaderFactory();
        } else if(choice.equalsIgnoreCase("Writer")){
            return new WriterFactory();
        }
        return null;
    }
}
