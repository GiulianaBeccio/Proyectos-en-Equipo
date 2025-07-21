package TablaRAF;

import java.io.RandomAccessFile;

public interface Registro
{
    public long largo();
    public char getActivo();
    public void setActivo(char c);
    public void leer(RandomAccessFile raf) throws Exception;
    public void grabar(RandomAccessFile raf) throws Exception;
    public long getClave();
   

    }