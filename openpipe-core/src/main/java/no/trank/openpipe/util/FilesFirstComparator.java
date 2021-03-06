/*
 * Copyright 2007  T-Rank AS
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package no.trank.openpipe.util;

import java.io.File;
import java.util.Comparator;

/**
 * @version $Revision$
 */
public class FilesFirstComparator implements Comparator<File> {

   @Override
   public int compare(File f1, File f2) {
      if (f1.isDirectory()) {
         if (f2.isDirectory()) {
            return f1.compareTo(f2);
         }
         return -1;
      }
      if (f2.isDirectory()) {
         return 1;
      }
      return f1.compareTo(f2);
   }

}
