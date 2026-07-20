package backend;
import java.io.*;
import java.util.ArrayList;

public class DataStore<T> {

    // Custom OutputStream to skip headers in append mode
    public static class MyObjectOutputStream extends ObjectOutputStream {

        public MyObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            // Do NOT write a header when appending
            reset();
        }
    }

    // Save (append) single object
    public void saveToFile(String fileName, T obj) {
        File file = new File(fileName);

        try {
            boolean fileExists = file.exists() && file.length() > 0;

            FileOutputStream fos = new FileOutputStream(file, true);
            ObjectOutputStream oos;

            if (fileExists) {
                oos = new MyObjectOutputStream(fos);   // append mode
            } else {
                oos = new ObjectOutputStream(fos);      // first object, write header
            }

            oos.writeObject(obj);
            oos.close();
            System.out.println("Object saved in " + fileName + " successfully");

        } catch (IOException e) {
            System.out.println("IO Exception occurred while saving data to " + fileName);
            e.printStackTrace();
        }
    }

    // Read all objects from file
    public ArrayList<Object> readAll(String fileName) {
        ArrayList<Object> list = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists() || file.length() == 0) {
            System.out.println("File is empty or doesn't exist: " + fileName);
            return list;
        }

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            while (true) {
                try {
                    Object obj = ois.readObject();
                    list.add(obj);
                } catch (EOFException ex) {
                    // End of file reached normally
                    break;
                }
            }
        } catch (InvalidClassException e) {
            System.out.println("ERROR: Class version mismatch. The file was written with a different version of the classes.");
            System.out.println("SOLUTION: Delete " + fileName + " and run the program again.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: Class not found while reading object.");
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            System.out.println("ERROR: File is corrupted (StreamCorruptedException).");
            System.out.println("SOLUTION: Delete " + fileName + " and run the program again.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO Exception occurred while reading from " + fileName);
            e.printStackTrace();
        }

        return list;
    }

    public Object getSingleObjFirst(String filename){ 
        try{
        FileInputStream fd = new FileInputStream(filename);
        ObjectInputStream od  = new ObjectInputStream(fd);
        
        Object readObj = od.readObject();
        od.close();
        return readObj;
        
        }catch(IOException e){
            System.out.println("IOException occred while getSingleObjFirst");
        }catch(ClassNotFoundException ne){
            System.out.println("Class not found exception occured while getSingleObjFirst");
        }
        return null;
    }

    // Overwrite entire file with new list
public void overwriteFile(String fileName, ArrayList<T> newList) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
        for (T obj : newList) {
            oos.writeObject(obj);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}