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
package no.trank.openpipe.step;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import no.trank.openpipe.api.MultiInputOutputFieldPipelineStep;
import no.trank.openpipe.api.PipelineException;
import no.trank.openpipe.api.document.AnnotatedField;
import no.trank.openpipe.api.document.BaseAnnotatedField;
import no.trank.openpipe.api.document.Document;
import no.trank.openpipe.config.annotation.NotNull;

/**
 * This step offers java regex functionality. 
 * 
 * @version $Revision$
 */
public class RegexField extends MultiInputOutputFieldPipelineStep {
   private static final Logger log = LoggerFactory.getLogger(RegexField.class);
   @NotNull
   private Pattern fromPattern;
   @NotNull
   private String toPattern;
   private boolean copyOnMiss;
   private boolean deleteOnEmpty = true;
   private boolean nullIsBlank;

   public RegexField() {
      super(false);
   }

   @Override
   protected void process(Document doc, String inputFieldName, List<AnnotatedField> inputFields, String outputFieldName)
         throws PipelineException {
      List<String> outValues = new ArrayList<String>();

      if (inputFields.isEmpty() && nullIsBlank) {
         inputFields = new ArrayList<AnnotatedField>();
         inputFields.add(new BaseAnnotatedField(""));
      }

      for (AnnotatedField field : inputFields) {
         final Matcher m = fromPattern.matcher(field.getValue());
         if (m.find()) {
            log.debug("Field '{}' matches", inputFieldName);
            outValues.add(m.replaceAll(toPattern));
         } else {
            log.debug("Field '{}' does not match", inputFieldName);
            if (copyOnMiss) {
               outValues.add(field.getValue());
            }
         }
      }
      
      if (outValues.isEmpty()) {
         if (deleteOnEmpty) {
            doc.removeField(outputFieldName);
         }
      } else {
         doc.setFieldValues(outputFieldName, outValues);
      }
   }

   @Override
   public String getRevision() {
      return "$Revision$";
   }

   /**
    * Gets the regex pattern used for matching against the input field values.
    * 
    * @return the regex pattern
    */
   public String getFromPattern() {
      return fromPattern != null ? fromPattern.pattern() : null;
   }

   /**
    * Compiles the regex pattern used for matching against the input field values.
    * Note that {@link Matcher#find()} is called during the matching process, to allow for replace all effects. 
    * 
    * @param fromPattern the pattern to be compiled
    */
   public void setFromPattern(String fromPattern) {
      this.fromPattern = Pattern.compile(fromPattern);
   }

   /**
    * Gets the pattern that is applied when producing the output field values.
    * 
    * @return the pattern
    */
   public String getToPattern() {
      return toPattern;
   }

   /**
    * Sets the pattern that is applied when producing the output field values through calls to
    * {@link Matcher#replaceAll(String)}.
    * 
    * @param toPattern the output pattern
    */
   public void setToPattern(String toPattern) {
      this.toPattern = toPattern;
   }

   /**
    * Gets whether the input field value should be copied to the output field if the input field value does not
    * match the from pattern.
    * 
    * @return <code>true</code> if the input field value should be copied to the output field if the input field value
    *         does not match the from pattern, <code>false</code> otherwise
    */
   public boolean isCopyOnMiss() {
      return copyOnMiss;
   }

   /**
    * Specifies whether the input field value should be copied to the output field if the input field value
    * does not match the from pattern. 
    * 
    * @param copyOnMiss <code>true</code> if the input field value should be copied to the output field if pattern
    * doesn't match.
    */
   public void setCopyOnMiss(boolean copyOnMiss) {
      this.copyOnMiss = copyOnMiss;
   }

   public boolean isDeleteOnEmpty() {
      return deleteOnEmpty;
   }

   /**
    * Specifies whether the output field value should be removed if the input field value does not match the from
    * pattern and isCopyOnMiss is false.
    *
    * @param deleteOnEmpty <code>true</code> if the the output field should be deleted.
    */
   public void setDeleteOnEmpty(boolean deleteOnEmpty) {
      this.deleteOnEmpty = deleteOnEmpty;
   }

   /**
    * Specifies if the RegexMatcher treats a null field as a blank field
    * @return true if the RegexMatcher should treat a null field as a blank field
    */
   public boolean isNullIsBlank() {
      return nullIsBlank;
   }

   /**
    * Specifies that the RegexMatcher should treat a null field as a blank field. This makes it possible to match the
    * empty field by using the match string "^$".
    *
    * @param nullIsBlank true if the RegexMatcher should treat a null field as a blank field
    */
   public void setNullIsBlank(boolean nullIsBlank) {
      this.nullIsBlank = nullIsBlank;
   }
}