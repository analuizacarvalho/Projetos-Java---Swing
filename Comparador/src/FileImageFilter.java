import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

public class FileImageFilter implements FileFilter {  
  
        public boolean accept(File pathname) {  
  
            if (!pathname.isFile()) {  
                return false;  
            }  
  
            FileImageInputStream input = null;  
            boolean ret;  
            try {  
                input = new FileImageInputStream(pathname);  
                Iterator<?> i = ImageIO.getImageReaders(input);  
                ret = i.hasNext();  
            } catch (FileNotFoundException e) {  
                throw new RuntimeException(e);  
            } catch (IOException e) {  
                throw new RuntimeException(e);  
            } finally {  
                if (input != null) {  
                    try {  
                        input.close();  
                    } catch (IOException e) {  
                        throw new RuntimeException(e);  
                    }  
                }  
            }  
            return ret;  
        }  
  
    }  
 
