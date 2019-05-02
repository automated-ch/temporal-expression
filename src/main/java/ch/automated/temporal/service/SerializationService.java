/*
 * The MIT License
 *
 * Copyright 2019-03-01 automated.ch, Tobi Tiggers.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package ch.automated.temporal.service;

import ch.automated.temporal.TemporalExpression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wit
 */
public class SerializationService {

    private static final String PATH_TO_SER_DIR = "/data/auto/te";
    
    private final String TIME_ZONE_ID = "Europe/Zurich";

    public static List<TemporalExpression> deserializeTemporalExpression() {

        List<TemporalExpression> tes = new ArrayList<>();

        try {
            File folder = new File(PATH_TO_SER_DIR);
            File[] listOfFiles = folder.listFiles();

            for (File file : listOfFiles) {
                if (file.isFile()) {
                    TemporalExpression te = null;
                    FileInputStream inputStream = new FileInputStream(file);
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                    te = (TemporalExpression) objectInputStream.readObject();
                    objectInputStream.close();
                    inputStream.close();
                    tes.add(te);
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(SerializationService.class.getName()).log(Level.SEVERE, "file does not exists.", ex);
        } catch (IOException ex) {
            Logger.getLogger(SerializationService.class.getName()).log(Level.SEVERE, "IO exception", ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SerializationService.class.getName()).log(Level.SEVERE, "Class could not be foud for de-serialization", ex);
        }
        return tes;
    }

    public static void serializeTemporalExpression(TemporalExpression te) {
        File file = new File(PATH_TO_SER_DIR, (te.getName().isEmpty()? "": te.getName()+"-")+LocalDateTime.now().toString().replace(":", "-"));

        try {
            FileOutputStream fileOutStr
                    = new FileOutputStream(file);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutStr);
            outputStream.writeObject(te);
            outputStream.close();
            fileOutStr.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(SerializationService.class.getName()).log(Level.SEVERE, "File ", ex);
        } catch (IOException ex) {
            Logger.getLogger(SerializationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    private void scheduleTimer(TemporalExpression te) {
        LocalDateTime localNow = LocalDateTime.now();
        ZoneId currentZone = ZoneId.of(TIME_ZONE_ID);
        ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
      
        boolean includes = te.includes(zonedNow.toLocalDateTime());
        if(includes){
         Logger.getLogger(SerializationService.class.getName()).log(Level.INFO, "Must be scheduled...");  
        }
       
    }
}
